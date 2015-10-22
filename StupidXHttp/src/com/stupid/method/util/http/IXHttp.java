package com.stupid.method.util.http;

import java.io.File;
import java.util.Map;

import org.apache.http.HttpEntity;

public interface IXHttp {

	public static final String CHARSET_GB2312 = "GB2312";
	public static final String CHARSET_GBK = "GBK";
	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String CONTENT_TYPE = "%s; charset=%s";
	public static final String CONTENT_TYPE_CHARSET = "%s; charset=%s";

	public static final String CONTENT_TYPE_JSON = "application/json";
	public static final String CONTENT_TYPE_TEXT = "text/plain";
	public static final String CONTENT_TYPE_XML = "application/xml";

	public static final int TIMEOUT = 20000;
	public static String CHARSET_DEFAULT = CHARSET_UTF8;
	public static String CONTENT_TYPE_DEFAULT = CONTENT_TYPE_TEXT;

	Map<String, String> defineHead();

	IXHttp download(int resultCode, String url, File target,
			IXServerResultListener resultListener);

	IXHttp get(int resultCode, String url, IXServerResultListener resultListener);

	IXHttp get(int resultCode, String url, Map<String, String> header,
			IXServerResultListener resultListener);

	IXHttp get(int resultCode, String url, Map<String, String> params,
			Map<String, String> headers, IXServerResultListener resultListener);

	String getCharset();

	String getContentType();

	IXHttp post(int resultCode, String contentType, String url,
			HttpEntity entity, Map<String, String> headers,
			IXServerResultListener resultListener);

	IXHttp post(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener);

	IXHttp postMap(int resultCode, String url, Map<String, ?> params,
			IXServerResultListener resultListener);

	IXHttp postMap(int resultCode, String contentType, String url,
			Map<String, ?> params, Map<String, String> headers,
			IXServerResultListener resultListener);

	IXHttp postString(int resultCode, String url, String data,
			IXServerResultListener resultListener);

	IXHttp postString(int resultCode, String contentType, String url,
			String data, Map<String, String> headers,
			IXServerResultListener resultListener);

	IXHttp setCharset(String charset);

	IXHttp setContentType(String type);

	IXHttp postString(int resultCode, String contentType, String url,
			String data, IXServerResultListener resultListener);

}
