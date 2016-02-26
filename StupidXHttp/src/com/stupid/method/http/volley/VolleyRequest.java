package com.stupid.method.http.volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.androidquery.AQuery;
import com.stupid.method.http.MultipartEntity;
import com.stupid.method.http.util.MapUtil;
import com.stupid.method.http.util.URLUtil;

abstract class VolleyRequest<T> extends Request<T> {
	public static final String POST_ENTITY = "%entity";

	// private static String multipart = "multipart/form-data";
	// private static String urlencoded = "application/x-www-form-urlencoded";

	private String charset = "utf-8";

	/** Content type for request. */
	private String contentType = "application/json";
	VolleyResult resultListener;
	protected Map<String, Object> params = new HashMap<String, Object>();
	protected Map<String, String> headers;
	{
		setShouldCache(false);
	}

	public VolleyRequest(int method, String url, VolleyResult result) {
		super(method, url, result);
		this.resultListener = result;
		setTimeout(10000);
	}

	public VolleyRequest(String url, HttpEntity entity, VolleyResult result) {
		this(Method.POST, url, result);
		params.put(POST_ENTITY, entity);
	}

	public VolleyRequest(int method, String url, Map<String, ?> params,
			VolleyResult result) {
		this(method, url, result);
		this.params.putAll(params);
	}

	public VolleyRequest<T> setTimeout(int timeout) {
		setRetryPolicy(new DefaultRetryPolicy(timeout, 1, 1.0f));

		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {

		this.charset = charset;

	}

	@Override
	final public String getUrl() {

		if (getMethod() == Method.GET)
			return URLUtil.getUrlWithParas(super.getUrl(), params);
		else
			return super.getUrl();
	}

	@Override
	public String getBodyContentType() {

		Object value = params.get(POST_ENTITY);
		if (value instanceof HttpEntity) {
			return ((HttpEntity) value).getContentType().getValue();
		} else if (isMultiPart(params))
			return "multipart/form-data; charset=utf-8";

		return String.format("%s; charset=%s", getContentType(), getCharset());
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {

		return MapUtil.isEmpty(headers) ? super.getHeaders() : headers;
	}

	public VolleyRequest<T> setHeaders(Map<String, String> headers) {

		if (MapUtil.isEmpty(headers))
			return this;
		if (null == headers)
			headers = new HashMap<String, String>();
		headers.putAll(headers);
		return this;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {

		HttpEntity entity = null;

		Object value = params.get(AQuery.POST_ENTITY);

		if (value instanceof HttpEntity) {
			entity = (HttpEntity) value;
		} else if (isMultiPart(params)) {
			entity = new MultipartEntity(params);
		} else {
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			for (Map.Entry<String, Object> e : params.entrySet()) {
				pairs.add(new BasicNameValuePair(e.getKey(), e.getValue()
						.toString()));
			}
			try {
				entity = new UrlEncodedFormEntity(pairs, getCharset());
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

		}

		if (entity != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				entity.writeTo(baos);
				return baos.toByteArray();
			} catch (IOException e) {

				return null;
			}
		} else
			return super.getBody();

	}

	public VolleyRequest<T> addParam(String key, Object value) {
		if (null != params)
			params = new HashMap<String, Object>();
		params.put(key, value);
		return this;

	}

	public VolleyRequest<T> addHeaders(Map<String, String> header) {
		if (header == null)
			return this;
		if (null == headers)
			headers = new HashMap<String, String>();
		headers.putAll(header);
		return this;
	}

	public VolleyRequest<T> addHeader(String key, String value) {
		if (null != headers)
			headers = new HashMap<String, String>();
		headers.put(key, value);
		return this;
	}

	private static boolean isMultiPart(Map<String, Object> params) {

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof File || value instanceof byte[]
					|| value instanceof InputStream)
				return true;
		}
		return false;
	}
}
