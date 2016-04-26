package com.stupid.method.http.volley;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.http.HttpEntity;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.stupid.method.http.AbsIXHttp;
import com.stupid.method.http.IXHttp;
import com.stupid.method.http.IXHttpProgress;
import com.stupid.method.http.IXServerResultListener;

public class VolleyHttp extends AbsIXHttp {
	private RequestQueue queue;
	private static WeakHashMap<Context, RequestQueue> weakHashMap = new WeakHashMap<Context, RequestQueue>();

	public VolleyHttp(Context context) {

		setQueue(weakHashMap.get(context) == null ? Volley.newRequestQueue(
				context, 2) : weakHashMap.get(context));
		weakHashMap.put(context, getQueue());
	}

	public RequestQueue getQueue() {
		return queue;
	}

	public void setQueue(RequestQueue queue) {
		this.queue = queue;
	}

	@Override
	public IXHttp download(int resultCode, String url, File target,
			IXServerResultListener resultListener) {

		VolleyRequestFile request = new VolleyRequestFile(Method.GET, url,
				target, new VolleyResultFile(resultCode, resultListener));
		add(request);

		return this;
	}

	@Override
	public IXHttp post(int resultCode, String contentType, String url,
			HttpEntity entity, Map<String, String> headers,
			IXServerResultListener resultListener) {
		VolleyRequestString request;

		request = new VolleyRequestString(url, entity, getListener(resultCode,
				resultListener));
		request.setHeaders(getHeaderMap(headers));
		add(request);

		return this;
	}

	@Override
	public IXHttp post(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener) {
		VolleyRequestString request = new VolleyRequestString(Method.POST, url,
				params, getListener(resultCode, resultListener));
		request.addHeaders(getHeaderMap(headers));
		add(request);

		return this;
	}

	@Override
	public IXHttp get(int resultCode, String url, Map<String, String> params,
			Map<String, String> headers, IXServerResultListener resultListener) {
		VolleyRequestString request = new VolleyRequestString(url,
				new VolleyResultString(resultCode, resultListener));
		request.setHeaders(getHeaderMap(headers));
		add(request);
		return this;
	}

	private void add(Request request) {

		getQueue().add(request);
	}

	private static VolleyResultString getListener(int resultCode,
			IXServerResultListener resultListener) {
		return new VolleyResultString(resultCode, resultListener);
	}

	@Override
	public IXHttp download(int resultCode, String url, File target,
			IXServerResultListener resultListener, IXHttpProgress progress) {
		throw new NullPointerException("没实现");
	}

	@Override
	public IXHttp postMap(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener, IXHttpProgress progress) {

		throw new NullPointerException("没实现");

	}
}
