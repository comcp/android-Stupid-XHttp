package com.stupid.method.http.async;

import org.apache.http.Header;

import com.loopj.android.http.TextHttpResponseHandler;
import com.stupid.method.http.IXHttpProgress;
import com.stupid.method.http.IXHttpResult;
import com.stupid.method.http.IXServerResultListener;
import com.stupid.method.http.util.XLog;

public class AsyncResultString extends TextHttpResponseHandler implements
		IXHttpResult {
	private static final String tag = "AsyncResultString";
	private int resultCode;
	private IXServerResultListener resultListener;
	private IXHttpProgress mProgress;

	public AsyncResultString(int resultCode,
			IXServerResultListener resultListener) {
		this(resultCode, resultListener, null);

	}

	public AsyncResultString(int resultCode,
			IXServerResultListener resultListener, IXHttpProgress progress) {
		setResultCode(resultCode);
		setResultListener(resultListener);
		setProgress(progress);

	}

	public AsyncResultString setCharset2(String charset) {

		super.setCharset(charset);
		return this;
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
		if (resultListener != null)
			resultListener.onServerResult(getResultCode(), data, false,
					statusCode);
		else {
			XLog.e(tag, "resultListener is null");
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
		if (resultListener != null)
			resultListener.onServerResult(getResultCode(), data, true,
					statusCode);
		else {
			XLog.e(tag, "resultListener is null");
		}
	}

	@Override
	public void onCancel() {
		if (resultListener != null)
			resultListener.onServerResult(getResultCode(), null, false,
					IXHttpResult.RESULT_CANCEL);
		else {
			XLog.e(tag, "resultListener is null");
		}
		super.onCancel();
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
	public void onProgress(int bytesWritten, int totalSize) {
		super.onProgress(bytesWritten, totalSize);
		if (mProgress != null)
			mProgress.onProgress(bytesWritten, totalSize);
	}

	public IXHttpProgress getProgress() {
		return mProgress;
	}

	/**
	 * @param mProgress
	 *            the mProgress to set
	 */
	public void setProgress(IXHttpProgress mProgress) {
		this.mProgress = mProgress;
	}

}
