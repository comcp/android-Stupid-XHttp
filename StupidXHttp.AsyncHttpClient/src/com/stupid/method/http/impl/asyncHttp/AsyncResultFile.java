package com.stupid.method.http.impl.asyncHttp;

import java.io.File;

import org.apache.http.Header;

import android.util.Log;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.stupid.method.http.IXProgress;
import com.stupid.method.http.IXResultListener;
import com.stupid.method.http.XHttpResultRaw;

public class AsyncResultFile extends FileAsyncHttpResponseHandler {
	private static final String tag = "AsyncResultFile";
	private int requestCode;
	private IXResultListener resultListener;

	public AsyncResultFile(File target, int resultCode,
			IXResultListener resultListener) {
		super(target);
		setRequestCode(resultCode);
		setResultListener(resultListener);
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
	public void onFailure(int statusCode, Header[] headers,
			Throwable throwable, File data) {

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
			resultListener.onServerResult(getRequestCode(), null, false, raw);
		} else {
			Log.e(tag, "resultListener is null");
		}
	}

	@Override
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
	public void onSuccess(int statusCode, Header[] headers, File data) {
		if (resultListener != null) {
			XHttpResultRaw raw = new XHttpResultRaw();
			raw.setStatusCode(200);
			if (headers != null) {
				for (int i = 0, s = headers.length; i < s; i++) {
					raw.addHead(headers[i].getName(), headers[i].getValue());
				}
			}
			resultListener.onServerResult(getRequestCode(),
					data.getAbsolutePath(), true, raw);
		} else {
			Log.e(tag, "resultListener is null");
		}
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
