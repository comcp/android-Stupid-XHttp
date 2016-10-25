package com.stupid.method.http.demo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stupid.method.http.IXHttp;
import com.stupid.method.http.IXProgress;
import com.stupid.method.http.IXResultListener;
import com.stupid.method.http.XHttpResultRaw;
import com.stupid.method.http.impl.asyncHttp.XAsyncIXHttp;
import com.stupid.method.http.impl.okhttp.XokHttp;

public class MainActivity extends Activity implements OnClickListener,
		IXResultListener {

	static int[] buttonIds = new int[] { R.id.button_get, R.id.button_post,
			R.id.button_upload };
	IXHttp http;
	TextView logshow;
	ProgressBar progressBar1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		http = new XAsyncIXHttp();// new XokHttp();
		logshow = (TextView) findViewById(R.id.textView_log);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		for (int i : buttonIds) {
			findViewById(i).setOnClickListener(this);
		}
	}

	public static boolean isEmpty(CharSequence str) {
		return (str == null || str.length() == 0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_get:
			http.get(v.getId(), AppConfig.conf.get, this);
			break;
		case R.id.button_post:

			http.post(
					v.getId(),
					AppConfig.conf.methodPost,
					"{\"para\":{\"password\":\"zhangsan\",\"requestType\":\"login\",\"user_type\":\"0\",\"username\":\"zhangsan\"},\"requestType\":\"login\"}",
					"application/json", this);

			break;
		case R.id.button_upload:
			upload();
			break;
		default:
			break;
		}
	}

	private void upload() {
		File ff = new File("mnt/sdcard/emm_demo.zip");
		if (ff.canRead())
			Toast.makeText(this, "isfile", Toast.LENGTH_LONG).show();
		Map<String, Object> requestParams = new HashMap<String, Object>();

		requestParams.put("file", ff);

		http.post(1, AppConfig.conf.upload, requestParams,
				new IXProgress.XUploadProgress(this) {

					@Override
					public void onProgress(int requestCode, long bytesWritten,
							long totalSize) {
						progressBar1.setMax((int) totalSize);
						progressBar1.setProgress((int) bytesWritten);
					}

				});

	}

	@Override
	public void onServerResult(int requestCode, String data, boolean state,
			XHttpResultRaw raw) {

		if (!isEmpty(raw.getHeads().get("Set-Cookie"))) {
			String cookie = raw.getHeads().get("Set-Cookie");
			String[] cookies = cookie.split(";");
			if (cookies.length > 0)
				cookie = cookies[0];
			http.setDefHeader("Cookie", cookie);
		}
		final String log = String.format(
				"请求%s \nrequestCode:%d\nHeads:%s\nData: \n%s \n", state ? "成功"
						: "失败", requestCode, raw.getHeads().toString(), data);
		if (isUIThread())
			logshow.setText(log);
		else
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					logshow.setText(log);
				}
			});
	}

	public static boolean isUIThread() {

		return Looper.getMainLooper().getThread().getId() == Thread
				.currentThread().getId();

	}
}
