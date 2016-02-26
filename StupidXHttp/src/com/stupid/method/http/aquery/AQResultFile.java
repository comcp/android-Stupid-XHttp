package com.stupid.method.http.aquery;

import java.io.File;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.IXProgress;
import com.stupid.method.http.IXHttpProgress;
import com.stupid.method.http.IXHttpResult;
import com.stupid.method.http.IXServerResultListener;
import com.stupid.method.http.util.XLog;

public class AQResultFile extends AjaxCallback<File> implements IXHttpResult,
		IXProgress {
	private static final String tag = "AQResultFile";
	private int resultCode;
	private IXServerResultListener resultListener;
	private IXHttpProgress progress;

	AQResultFile(int resultCode, IXServerResultListener resultListener) {
		setResultCode(resultCode);
		setResultListener(resultListener);
		progress(this);
	}

	@Override
	public void callback(String url, File data, AjaxStatus status) {
		if (resultListener != null)
			if (status.getCode() == 200) {
				resultListener.onServerResult(resultCode,
						data.getAbsolutePath(), true, status.getCode());
			} else {
				resultListener.onServerResult(resultCode, status.getMessage(),
						false,
						status.getCode() == -101 ? RESULT_NO_LINK_NETWORK
								: status.getCode());

			}
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
		if (progress != null)
			progress.onProgress(bytesWritten, totalSize);
		else
			XLog.d(tag, bytesWritten + "/" + totalSize);
	}

}
