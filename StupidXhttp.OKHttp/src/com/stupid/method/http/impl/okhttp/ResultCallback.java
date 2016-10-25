package com.stupid.method.http.impl.okhttp;

import java.io.IOException;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

import com.stupid.method.http.IXResultListener;
import com.stupid.method.http.XHttpResultRaw;

public class ResultCallback implements Callback {

	IXResultListener len;
	int requestCode;

	public ResultCallback(int requestCode, IXResultListener resultListener) {
		len = resultListener;
		this.requestCode = requestCode;

	}

	@Override
	public void onFailure(Call arg0, IOException arg1) {
		if (len != null) {
			XHttpResultRaw raw = new XHttpResultRaw();
			raw.setStatusCode(200);
			raw.setErrorMsg(arg1.getMessage());
			raw.setThrowable(arg1);
			len.onServerResult(requestCode, null, false, raw);
		}
	}

	@Override
	public void onResponse(Call arg0, Response arg1) throws IOException {
		if (len != null) {
			XHttpResultRaw raw = new XHttpResultRaw();
			raw.setStatusCode(200);
			Headers hd = arg1.headers();
			Set<String> names = hd.names();
			for (String name : names) {
				raw.addHead(name, hd.get(name));
			}
			len.onServerResult(requestCode, arg1.body().string(), true, raw);

		}
	}

}
