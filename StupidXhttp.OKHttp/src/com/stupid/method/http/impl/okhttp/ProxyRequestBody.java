package com.stupid.method.http.impl.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

import com.stupid.method.http.IXProgress;

class ProxyRequestBody extends RequestBody {
	private final RequestBody requestBody;
	private final IXProgress progressListener;
	private BufferedSink bufferedSink;

	private int requestCode;

	/**
	 * 
	 * @param requestBody
	 * @param progressListener
	 */
	public ProxyRequestBody(int requestCode, RequestBody requestBody,
			IXProgress progressListener) {
		this.requestCode = requestCode;
		this.requestBody = requestBody;
		this.progressListener = progressListener;
	}

	/**
	 * 
	 * @return contentLength
	 * @throws IOException
	 */
	@Override
	public long contentLength() throws IOException {
		return requestBody.contentLength();
	}

	@Override
	public MediaType contentType() {
		return requestBody.contentType();
	}

	/**
	 * 
	 * @param sink
	 *            Sink
	 * @return Sink
	 */
	private Sink sink(Sink sink) {
		return new ForwardingSink(sink) {
			long bytesWritten = 0;
			long contentLength = -1;

			@Override
			public void write(Buffer source, long byteCount) throws IOException {
				super.write(source, byteCount);
				if (contentLength == -1) {
					contentLength = contentLength();
				}
				bytesWritten += byteCount;
				progressListener.onProgress(requestCode, bytesWritten,
						contentLength);

			}
		};
	}

	/**
	 * 
	 * @param sink
	 * @throws IOException
	 */
	@Override
	public void writeTo(BufferedSink sink) throws IOException {
		if (bufferedSink == null) {
			bufferedSink = Okio.buffer(sink(sink));
		}
		requestBody.writeTo(bufferedSink);
		bufferedSink.flush();

	}

}