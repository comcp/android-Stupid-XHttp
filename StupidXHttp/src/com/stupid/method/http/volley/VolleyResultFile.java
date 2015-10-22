package com.stupid.method.http.volley;

import java.io.File;

import com.stupid.method.http.IXHttpResult;
import com.stupid.method.http.IXServerResultListener;
import com.stupid.method.http.util.XLog;

public class VolleyResultFile extends VolleyResult<File> {

	public VolleyResultFile(int resultCode, IXServerResultListener resultListener) {
		super(resultCode, resultListener);
	}

	private static final String tag = "VolleyResultFile";

	@Override
	public void onResponse(File data) {
		if (resultListener != null) {
			resultListener.onServerResult(resultCode, data.getAbsolutePath(),
					true, IXHttpResult.RESULT_OK);
		} else
			XLog.e(tag, "resultListener");
	}

}
