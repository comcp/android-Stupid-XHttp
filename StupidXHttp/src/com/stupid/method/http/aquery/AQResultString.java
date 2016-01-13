package com.stupid.method.http.aquery;

import java.util.HashMap;
import java.util.Map;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.Constants;
import com.stupid.method.http.IXHttpResult;
import com.stupid.method.http.IXServerResultListener;
import com.stupid.method.http.util.XLog;

/** 基于 Aquery ***/
public class AQResultString extends AjaxCallback<String> implements
		IXHttpResult {

	private static final String tag = "AQResultString";
	private int resultCode;
	private IXServerResultListener resultListener;

	public AQResultString(int resultCode, IXServerResultListener resultListener) {
		setResultCode(resultCode);
		setResultListener(resultListener);
		fileCache(false);

	}

	public AQResultString setMethodPOST() {

		method(Constants.METHOD_POST);
		return this;
	}

	public AQResultString headers(Map<String, String> head) {
		if (headers == null)
			headers = new HashMap<String, String>();
		if (head != null)
			headers.putAll(head);
		return this;
	}

	public AQResultString setMethodGet() {
		method(Constants.METHOD_GET);
		return this;
	}

	@Override
	public void callback(String url, String data, AjaxStatus status) {
		if (resultListener != null)
			if (status.getCode() == 200) {
				resultListener.onServerResult(resultCode, data, true,
						status.getCode());
			} else {
				resultListener.onServerResult(resultCode, data, false,
						status.getCode());

			}
		else {
			XLog.e(tag, "resultListener is null");
		}
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

}
