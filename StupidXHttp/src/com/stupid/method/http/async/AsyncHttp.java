package com.stupid.method.http.async;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.stupid.method.http.AbsIXHttp;
import com.stupid.method.http.IXHttp;
import com.stupid.method.http.IXHttpProgress;
import com.stupid.method.http.IXServerResultListener;

public class AsyncHttp extends AbsIXHttp {

	private static AsyncHttpClient query = new AsyncHttpClient();
	static {
		getQuery().setTimeout(TIMEOUT);
	}

	@Override
	public IXHttp get(int resultCode, String url, Map<String, String> params,
			Map<String, String> headers, IXServerResultListener resultListener) {

		getQuery().get(null, url, getHeaders(headers),
				new RequestParams(params),
				new AsyncResultString(resultCode, resultListener));

		return this;
	}

	public IXHttp download(int resultCode, String url, File target,
			IXServerResultListener resultListener) {

		getQuery().get(url,
				new AsyncResultFile(target, resultCode, resultListener));
		return this;
	}

	public static AsyncHttpClient getQuery() {
		return query;
	}

	public static void setQuery(AsyncHttpClient query) {
		AsyncHttp.query = query;
	}

	/**
	 * 取消所有http请求
	 */
	public static void cancelAllRequests() {
		// 会触发 responseHandler实现的的onCancel();
		getQuery().cancelAllRequests(true);

	}

	public IXHttp post(int resultCode, String contentType, String url,
			HttpEntity entity, Map<String, String> headers,
			IXServerResultListener resultListener) {

		getQuery().post(null, url, getHeaders(headers), entity, contentType,
				new AsyncResultString(resultCode, resultListener));

		return this;
	}

	@Override
	public IXHttp post(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener) {

		postMap(resultCode, contentType, url, params, headers, resultListener,
				null);

		return this;
	}

	@Override
	public IXHttp download(int resultCode, String url, File target,
			IXServerResultListener resultListener, IXHttpProgress progress) {

		getQuery().get(
				url,
				new AsyncResultFile(target, resultCode, resultListener,
						progress));
		return this;
	}

	@Override
	public IXHttp postMap(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener, IXHttpProgress progress) {
		RequestParams requestParams = new RequestParams();
		if (null != params)
			for (Entry<String, ?> p : params.entrySet()) {

				if (p.getValue() instanceof File)
					try {
						requestParams.put(p.getKey(), (File) p.getValue());
					} catch (FileNotFoundException e) {
						requestParams.put(p.getKey(), p.getValue());
					}
				else if (p.getValue() instanceof InputStream) {

					requestParams.put(p.getKey(), (InputStream) p.getValue());

				} else if (p.getValue() instanceof byte[]) {
					requestParams.put(p.getKey(), new ByteArrayInputStream(
							(byte[]) p.getValue()));
				} else {
					requestParams.put(p.getKey(), p.getValue() == null ? "null"
							: p.getValue());
				}

			}

		getQuery().post(null, url, getHeaders(headers), requestParams,
				contentType,
				new AsyncResultString(resultCode, resultListener, progress));
		return this;
	}
}
