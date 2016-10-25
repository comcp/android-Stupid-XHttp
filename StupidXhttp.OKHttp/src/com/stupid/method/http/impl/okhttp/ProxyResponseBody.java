package com.stupid.method.http.impl.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

import com.stupid.method.http.IXProgress;

class ProxyResponseBody extends ResponseBody {
	private final ResponseBody responseBody;
	private final IXProgress progressListener;
	private BufferedSource bufferedSource;

	/**
	 * @param requestCode
	 * @param responseBody
	 * @param progressListener
	 */
	public ProxyResponseBody(int requestCode, ResponseBody responseBody,
			IXProgress progressListener) {
		this.requestCode = requestCode;
		this.responseBody = responseBody;
		this.progressListener = progressListener;
	}

	/**
	 * 
	 * @return MediaType
	 */
	@Override
	public MediaType contentType() {
		return responseBody.contentType();
	}

	/**
	 * 
	 * @return contentLength
	 * @throws IOException
	 */
	@Override
	public long contentLength() {
		return responseBody.contentLength();
	}

	/**
	 * 
	 * @return BufferedSource
	 * @throws IOException
	 */
	@Override
	public BufferedSource source() {
		if (bufferedSource == null) {
			bufferedSource = Okio.buffer(source(responseBody.source()));
		}
		return bufferedSource;
	}

	/**
	 * 
	 * @param source
	 *            Source
	 * @return Source
	 */
	private Source source(Source source) {

		return new ForwardingSource(source) {
			long totalBytesRead = 0L;

			@Override
			public long read(Buffer sink, long byteCount) throws IOException {
				long bytesRead = super.read(sink, byteCount);
				totalBytesRead += bytesRead != -1 ? bytesRead : 0;
				if (progressListener != null) {
					progressListener.onProgress(requestCode, totalBytesRead,
							responseBody.contentLength());
				}
				return bytesRead;
			}
		};
	}

	private int requestCode;

}
