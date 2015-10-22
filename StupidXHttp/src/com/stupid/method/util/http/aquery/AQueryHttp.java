package com.stupid.method.util.http.aquery;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.http.HttpEntity;

import android.content.Context;

import com.androidquery.AQuery;
import com.stupid.method.util.StringUtils;
import com.stupid.method.util.http.AbsIXHttp;
import com.stupid.method.util.http.IXDataFilter;
import com.stupid.method.util.http.IXHttp;
import com.stupid.method.util.http.IXServerResultListener;

public class AQueryHttp extends AbsIXHttp {

	private AQuery query;
	private static WeakHashMap<Context, AQuery> weakHashMap = new WeakHashMap<Context, AQuery>();

	public AQueryHttp(Context context) {

		setQuery(weakHashMap.get(context) == null ? new AQuery(context)
				: weakHashMap.get(context));
		weakHashMap.put(context, getQuery());

	}

	public AQueryHttp(AQuery query) {
		setQuery(query);
	}

	public IXHttp download(int resultCode, String url, File target,
			IXServerResultListener resultListener) {
		getQuery().download(url, target,
				new AQResultFile(resultCode, resultListener));
		return this;
	}

	@Override
	public IXHttp get(int resultCode, String url, Map<String, String> params,
			Map<String, String> headers, IXServerResultListener resultListener) {
		AQResultString callback = new AQResultString(resultCode, resultListener)
				.setMethodGet();
		callback.params(params);
		callback.headers(getHeaderMap(headers));

		getQuery().ajax(url, String.class, callback);
		return null;
	}

	public IXHttp post(int resultCode, String contentType, String url,
			HttpEntity entity, Map<String, String> headers,
			IXServerResultListener resultListener) {

		Map<String, HttpEntity> params = new HashMap<String, HttpEntity>();
		params.put(AQuery.POST_ENTITY, entity);
		post(resultCode, contentType, url, params, headers, resultListener);
		return this;
	}

	public IXHttp post(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener) {

		AQResultString callback = new AQResultString(resultCode, resultListener);
		callback.setMethodPOST();
		callback.params(params);
		if (!StringUtils.isEmpty(contentType))
			callback.header("Content-Type", String.format(CONTENT_TYPE_CHARSET,
					contentType, getCharset()));
		callback.headers(getHeaderMap(headers));

		getQuery().ajax(url, String.class, callback);
		return this;

	}

	public AQuery getQuery() {
		return query;
	}

	public void setQuery(AQuery aQuery) {
		this.query = aQuery;
	}

}
