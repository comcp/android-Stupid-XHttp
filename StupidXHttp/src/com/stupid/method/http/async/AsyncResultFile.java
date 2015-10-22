package com.stupid.method.http.async;

import java.io.File;

import org.apache.http.Header;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.stupid.method.http.IXHttpProgress;
import com.stupid.method.http.IXServerResultListener;
import com.stupid.method.http.util.XLog;

public class AsyncResultFile extends FileAsyncHttpResponseHandler {
	private static final String tag = "AsyncResultFile";
	private int resultCode;
	private IXServerResultListener resultListener;
	private IXHttpProgress mProgress;

	public AsyncResultFile(File target, int resultCode,
			IXServerResultListener resultListener) {
		super(target);
		setResultCode(resultCode);
		setResultListener(resultListener);
	}

	public AsyncResultFile(File target, int resultCode,
			IXServerResultListener resultListener, IXHttpProgress progress) {
		this(target, resultCode, resultListener);
		mProgress = progress;
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

		if (resultListener != null)
			resultListener.onServerResult(getResultCode(), null, true,
					statusCode);
		else {
			XLog.e(tag, "resultListener is null");
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
		if (resultListener != null)
			resultListener.onServerResult(getResultCode(),
					data.getAbsolutePath(), false, statusCode);
		else {
			XLog.e(tag, "resultListener is null");
		}
	}

	public IXServerResultListener getResultListener() {
		return resultListener;
	}

	public void setResultListener(IXServerResultListener resultListener) {
		this.resultListener = resultListener;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	@Override
	public void onProgress(int bytesWritten, int totalSize) {
		if (mProgress != null)
			mProgress.onProgress(bytesWritten, totalSize);
	}
}
