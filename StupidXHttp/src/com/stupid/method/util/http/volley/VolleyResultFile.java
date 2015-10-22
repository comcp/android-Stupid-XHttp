package com.stupid.method.util.http.volley;

import java.io.File;

import com.stupid.method.util.XLog;
import com.stupid.method.util.http.IXHttpResult;
import com.stupid.method.util.http.IXServerResultListener;

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
