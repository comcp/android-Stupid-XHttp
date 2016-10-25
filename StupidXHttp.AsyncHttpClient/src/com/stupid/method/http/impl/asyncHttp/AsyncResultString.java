package com.stupid.method.http.impl.asyncHttp;

import org.apache.http.Header;

import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;
import com.stupid.method.http.IXProgress;
import com.stupid.method.http.IXResultListener;
import com.stupid.method.http.XHttpResultRaw;

public class AsyncResultString extends TextHttpResponseHandler {
	private static final String tag = "AsyncResultString";
	private int requestCode;
	private IXResultListener resultListener;

	public AsyncResultString(int requestCode, IXResultListener resultListener) {
		setResultListener(resultListener);
		setRequestCode(requestCode);
	}

	/**
	 * Called when request fails
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param responseString
	 *            string response of given charset
	 * @param throwable
	 *            throwable returned when processing request
	 */
	public void onFailure(int statusCode, Header[] headers, String data,
			Throwable throwable) {
		if (resultListener != null) {
			XHttpResultRaw raw = new XHttpResultRaw();
			raw.setStatusCode(statusCode);
			raw.setThrowable(throwable);
			raw.setErrorMsg(throwable.getMessage());
			if (headers != null) {
				for (int i = 0, s = headers.length; i < s; i++) {
					raw.addHead(headers[i].getName(), headers[i].getValue());
				}
			}
			resultListener.onServerResult(getRequestCode(), data, false, raw);
		} else {
			Log.e(tag, "resultListener is null");
		}
	}

	/**
	 * Called when request succeeds
	 * 
	 * @param statusCode
	 *            http response status line
	 * @param headers
	 *            response headers if any
	 * @param responseString
	 *            string response of given charset
	 */
	public void onSuccess(int statusCode, Header[] headers, String data) {
		if (resultListener != null) {

			XHttpResultRaw raw = new XHttpResultRaw();
			raw.setStatusCode(200);
			if (headers != null) {
				for (int i = 0, s = headers.length; i < s; i++) {
					raw.addHead(headers[i].getName(), headers[i].getValue());
				}
			}
			resultListener.onServerResult(getRequestCode(), data, true, raw);

		} else {
			Log.e(tag, "resultListener is null");
		}
	}

	@Override
	public void onCancel() {
		if (resultListener != null) {
			XHttpResultRaw raw = new XHttpResultRaw();
			raw.setErrorMsg("cancel");
			resultListener.onServerResult(getRequestCode(), null, false, raw);

		} else {
			Log.e(tag, "resultListener is null");
		}
		super.onCancel();
	}

	public IXResultListener getResultListener() {
		return resultListener;
	}

	public void setResultListener(IXResultListener resultListener) {
		this.resultListener = resultListener;
	}

	@Override
	public void onProgress(int bytesWritten, int totalSize) {
		if (this.resultListener != null && resultListener instanceof IXProgress) {
			((IXProgress) resultListener).onProgress(requestCode, bytesWritten,
					totalSize);
		}
	}

	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}

}
