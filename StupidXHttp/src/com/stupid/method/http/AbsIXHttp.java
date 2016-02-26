package com.stupid.method.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.stupid.method.http.impl.XHttp;
import com.stupid.method.http.util.MapUtil;

abstract public class AbsIXHttp implements IXHttp {

	private String charset = CHARSET_DEFAULT;
	private String contentType = CONTENT_TYPE_DEFAULT;
	private static final Map<String, String> defineHead = MapUtil.asMap(
			"class-name", "AbsIXHttp").add("tools", "IXHTTP");

	{
		defineHead.put("class-name", this.getClass().getSimpleName());

	}

	@Override
	final public Map<String, String> defineHead() {

		return defineHead;
	}

	@Override
	final public IXHttp get(int resultCode, String url,
			IXServerResultListener resultListener) {

		return get(resultCode, url, null, resultListener);
	}

	@Override
	final public IXHttp get(int resultCode, String url,
			Map<String, String> header, IXServerResultListener resultListener) {

		return get(resultCode, url, null, header, resultListener);
	}

	final public String getCharset() {
		return charset;
	}

	final public String getContentType() {
		return contentType;
	}

	protected Map<String, String> getHeaderMap(Map<String, String> headers) {
		if (headers == null)
			return defineHead();
		else {
			headers.putAll(defineHead());
			return headers;
		}

	}

	protected Header[] getHeaders(Map<String, String> headers) {

		Header[] heads = null;
		if (headers == null)
			headers = defineHead();
		else
			headers.putAll(defineHead());
		heads = new Header[headers.size()];
		int i = 0;
		for (Map.Entry<String, String> head : headers.entrySet()) {
			heads[i++] = new BasicHeader(head.getKey(), head.getValue());
		}
		return heads;

	}

	@Override
	public IXHttp postMap(int resultCode, String url, Map<String, ?> params,
			IXServerResultListener resultListener) {

		postMap(resultCode, getContentType(), url, params, null, resultListener);
		return this;
	}

	@Override
	public IXHttp postMap(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener) {

		return post(resultCode, contentType, url, params, headers,
				resultListener);

	}

	@Override
	public IXHttp postString(int resultCode, String url, String data,
			IXServerResultListener resultListener) {

		return postString(resultCode, getContentType(), url, data,
				resultListener);
	}

	@Override
	public IXHttp postString(int resultCode, String contentType, String url,
			String data, IXServerResultListener resultListener) {
		return postString(resultCode, contentType, url, data, null,
				resultListener);
	}

	@Override
	public IXHttp postString(int resultCode, String contentType, String url,
			String data, Map<String, String> headers,
			IXServerResultListener resultListener) {
		try {
			post(resultCode, contentType, url, new StringEntity(data,
					getCharset()), headers, resultListener);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return this;
	}

	final public IXHttp setCharset(String charset) {
		this.charset = charset;
		return this;
	}

	final public IXHttp setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public XHttp getXhttp() {
		return new XHttp(this);
	}

}
