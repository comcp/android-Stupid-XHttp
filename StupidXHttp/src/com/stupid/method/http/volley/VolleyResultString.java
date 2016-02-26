package com.stupid.method.http.volley;

import java.util.Map;

import com.stupid.method.http.IXServerResultListener;
import com.stupid.method.http.util.XLog;

public class VolleyResultString extends VolleyResult {

	private static final String tag = "VolleyResultString";

	public VolleyResultString(int resultCode,
			IXServerResultListener resultListener) {
		super(resultCode, resultListener);

	}

	@Override
	public void onServerResponse(String data, int statusCode,
			Map<String, String> headers) {
		if (resultListener != null) {
			resultListener.onServerResult(resultCode, data, true, statusCode);
		} else
			XLog.e(tag, "resultListener");

	}

}
