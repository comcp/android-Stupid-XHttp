package com.stupid.method.http.impl;

import java.io.File;
import java.util.Map;

import org.apache.http.HttpEntity;

import com.stupid.method.http.IXHttp;
import com.stupid.method.http.IXServerResultListener;

/**  好像没什么卵用 */
public class XHttp implements IXHttp {
	IXHttp http;

	public XHttp(IXHttp http) {
		if (http == null)
			throw new NullPointerException();
		this.http = http;
	}

	public Map<String, String> defineHead() {
		return http.defineHead();
	}

	public IXHttp download(int resultCode, String url, File target,
			IXServerResultListener resultListener) {
		return http.download(resultCode, url, target, resultListener);
	}

	public IXHttp get(int resultCode, String url,
			IXServerResultListener resultListener) {
		return http.get(resultCode, url, resultListener);
	}

	public IXHttp get(int resultCode, String url, Map<String, String> header,
			IXServerResultListener resultListener) {
		return http.get(resultCode, url, header, resultListener);
	}

	public IXHttp get(int resultCode, String url, Map<String, String> params,
			Map<String, String> headers, IXServerResultListener resultListener) {
		return http.get(resultCode, url, params, headers, resultListener);
	}

	public String getCharset() {
		return http.getCharset();
	}

	public String getContentType() {
		return http.getContentType();
	}

	public IXHttp post(int resultCode, String contentType, String url,
			HttpEntity entity, Map<String, String> headers,
			IXServerResultListener resultListener) {
		return http.post(resultCode, contentType, url, entity, headers,
				resultListener);
	}

	public IXHttp post(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener) {
		return http.post(resultCode, contentType, url, params, headers,
				resultListener);
	}

	public IXHttp postMap(int resultCode, String url, Map<String, ?> params,
			IXServerResultListener resultListener) {
		return http.postMap(resultCode, url, params, resultListener);
	}

	public IXHttp postMap(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener) {
		return http.postMap(resultCode, contentType, url, params, headers,
				resultListener);
	}

	public IXHttp postString(int resultCode, String url, String data,
			IXServerResultListener resultListener) {
		return http.postString(resultCode, url, data, resultListener);
	}

	public IXHttp postString(int resultCode, String contentType, String url,
			String data, Map<String, String> headers,
			IXServerResultListener resultListener) {
		return http.postString(resultCode, contentType, url, data, headers,
				resultListener);
	}

	public IXHttp setCharset(String charset) {
		return http.setCharset(charset);
	}

	public IXHttp setContentType(String type) {
		return http.setContentType(type);
	}

	public IXHttp postString(int resultCode, String contentType, String url,
			String data, IXServerResultListener resultListener) {
		return http.postString(resultCode, contentType, url, data,
				resultListener);
	}

}
