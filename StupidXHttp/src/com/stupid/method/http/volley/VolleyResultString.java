package com.stupid.method.http.volley;

import com.stupid.method.http.IXHttpResult;
import com.stupid.method.http.IXServerResultListener;
import com.stupid.method.http.util.XLog;

public class VolleyResultString extends VolleyResult<String> {

	private static final String tag = "VolleyResultString";

	public VolleyResultString(int resultCode,
			IXServerResultListener resultListener) {
		super(resultCode, resultListener);

	}

	@Override
	public void onResponse(String data) {
		if (resultListener != null) {
			resultListener.onServerResult(resultCode, data, true,
					IXHttpResult.RESULT_OK);
		} else
			XLog.e(tag, "resultListener");
	}

}
