package com.stupid.method.util.http.volley;

import com.stupid.method.util.XLog;
import com.stupid.method.util.http.IXHttpResult;
import com.stupid.method.util.http.IXServerResultListener;

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
