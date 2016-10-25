package com.stupid.method.http.impl.okhttp;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class XRequestBody extends RequestBody {
	RequestBody mBody;

	public XRequestBody(RequestBody body) {
		mBody = body;
	}

	@Override
	public MediaType contentType() {
		return mBody.contentType();
	}

	@Override
	public void writeTo(BufferedSink arg0) throws IOException {
	}

	@Override
	public long contentLength() throws IOException {

		return mBody.contentLength();
	}
}
