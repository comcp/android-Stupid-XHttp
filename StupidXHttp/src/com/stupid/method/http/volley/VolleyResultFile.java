package com.stupid.method.http.volley;

import java.util.Map;

import com.stupid.method.http.IXServerResultListener;

public class VolleyResultFile extends VolleyResult {

	public VolleyResultFile(int resultCode,
			IXServerResultListener resultListener) {
		super(resultCode, resultListener);

	}

	private static final String tag = "VolleyResultFile";

	@Override
	public void onServerResponse(String data, int statusCode,
			Map<String, String> headers) {

		resultListener.onServerResult(getResultCode(), data, true, statusCode);

	}

}
