package com.stupid.method.http.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

public class MultipartEntity implements HttpEntity {

	private byte[] boundary = null;
	private static final byte[] octet = "Content-Type: application/octet-stream"
			.getBytes();
	private static final byte[] binary = "Content-Transfer-Encoding: binary"
			.getBytes();
	private static final byte[] lineEnd = "\r\n".getBytes();
	private static final byte[] twoHyphens = "--".getBytes();

	ByteArrayOutputStream out = new ByteArrayOutputStream();
	private Map<String, ?> params;

	public MultipartEntity(Map<String, ?> params) {

		this.boundary = new String("***XHTTP_time:"
				+ System.currentTimeMillis() + "**").getBytes();
		this.params = params;
	}

	@Override
	public long getContentLength() {

		return params.size();
	}

	@Override
	public Header getContentType() {
		return new BasicHeader("Content-Type", "multipart/form-data; boundary="
				+ boundary);
	}

	@Override
	public boolean isChunked() {
		return false;
	}

	@Override
	public boolean isRepeatable() {
		return false;
	}

	@Override
	public boolean isStreaming() {
		return false;
	}

	@Override
	public void writeTo(final OutputStream dos) throws IOException {

		for (Map.Entry<String, ?> entry : params.entrySet()) {

			try {
				writeObject(dos, entry.getKey(), entry.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void initData() {
		for (Map.Entry<String, ?> entry : params.entrySet()) {
			try {
				writeObject(out, entry.getKey(), entry.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public Header getContentEncoding() {
		return null;
	}

	@Override
	public void consumeContent() throws IOException,
			UnsupportedOperationException {
		if (isStreaming()) {
			throw new UnsupportedOperationException(
					"Streaming entity does not implement #consumeContent()");
		}
	}

	@Override
	public InputStream getContent() throws IOException,
			UnsupportedOperationException {
		initData();
		return new ByteArrayInputStream(out.toByteArray());
	}

	public static void copy(InputStream in, OutputStream out)
			throws IOException {

		byte[] b = new byte[1024 * 4];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}

	}

	private void writeObject(OutputStream dos, String name, Object obj)
			throws IOException {

		if (obj == null)
			return;

		if (obj instanceof File) {

			File file = (File) obj;
			writeData(dos, name, file.getName(), new FileInputStream(file));

		} else if (obj instanceof byte[]) {
			writeData(dos, name, name, new ByteArrayInputStream((byte[]) obj));
		} else if (obj instanceof InputStream) {
			writeData(dos, name, name, (InputStream) obj);
		} else {
			writeField(dos, name, obj.toString());
		}

	}

	private void writeData(OutputStream dos, String name, String filename,
			InputStream is) throws IOException {

		dos.write(twoHyphens);
		dos.write(boundary);
		dos.write(lineEnd);
		dos.write(String.format(
				"Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"",
				name, filename).getBytes());
		dos.write(octet);
		dos.write(lineEnd);
		dos.write(binary);
		dos.write(lineEnd);
		dos.write(lineEnd);
		copy(is, dos);
		dos.write(lineEnd);

	}

	private void writeField(OutputStream dos, String name, String value)
			throws IOException {
		dos.write(twoHyphens);
		dos.write(boundary);
		dos.write(lineEnd);
		dos.write(String.format("Content-Disposition: form-data; name=\"%s\"",
				name).getBytes());
		dos.write(lineEnd);
		dos.write(lineEnd);
		byte[] data = value.getBytes("UTF-8");
		dos.write(data);
		dos.write(lineEnd);
	}

}