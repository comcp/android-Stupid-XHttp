package com.stupid.method.http.volley;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.stupid.method.http.IXHttpResult;
import com.stupid.method.http.IXServerResultListener;
import com.stupid.method.http.util.XLog;

public abstract class VolleyResult<T> implements Listener<T>, IXHttpResult,
		ErrorListener {
	protected static final String tag = "VolleyResult";
	protected int resultCode;
	protected IXServerResultListener resultListener;

	public VolleyResult(int resultCode, IXServerResultListener resultListener) {
		setResultCode(resultCode);
		setResultListener(resultListener);
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public IXServerResultListener getResultListener() {
		return resultListener;
	}

	public void setResultListener(IXServerResultListener resultListener) {
		this.resultListener = resultListener;
	}

	@Override
	public void onErrorResponse(VolleyError v) {
		if (resultListener != null) {
			resultListener.onServerResult(resultCode, v.getMessage(), false,
					v.networkResponse.statusCode);
		} else
			XLog.e(tag, "resultListener");

	}
}
