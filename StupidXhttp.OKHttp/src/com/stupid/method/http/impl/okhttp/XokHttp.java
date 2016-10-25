package com.stupid.method.http.impl.okhttp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.stupid.method.http.AbsIXHttp;
import com.stupid.method.http.IXProgress;
import com.stupid.method.http.IXResultListener;
import com.stupid.method.http.util.ContentType;
import com.stupid.method.http.util.URLUtil;

public class XokHttp extends AbsIXHttp<OkHttpClient> {
	OkHttpClient kernel = new OkHttpClient();

	@Override
	public void delete(int requestCode, String url,
			IXResultListener resultListener) {

	}

	@Override
	public void delete(int requestCode, String url, Map<String, String> heads,
			IXResultListener resultListener) {

	}

	@Override
	public void delete(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener) {

	}

	@Override
	public void downloadFromGet(int requestCode, String url, File saveTo,
			IXResultListener progress) {

	}

	@Override
	public void downloadFromPost(int requestCode, String url, File saveTo,
			Map<String, String> heads, Map<String, ?> params,
			IXResultListener progress) {
	}

	private void enqueue(Request request, final int requestCode,
			final IXResultListener resultListener) {
		Interceptor interceptor;
		if (resultListener instanceof IXProgress)
			interceptor = new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					// 拦截
					Response originalResponse = chain.proceed(chain.request());
					// 包装响应体并返回
					
					return originalResponse
							.newBuilder()
							.body(new ProxyResponseBody(requestCode,
									originalResponse.body(),
									(IXProgress) resultListener)).build();
				}
			};
		kernel.newCall(request).enqueue(
				new ResultCallback(requestCode, resultListener));
	}

	@Override
	public void get(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener) {
		Request request = getRequestBuilder(heads).url(
				URLUtil.getUrlWithParas(url, requestParams)).build();
		enqueue(request, requestCode, resultListener);
	}

	private Builder getRequestBuilder(Map<String, String> heads) {
		return new Request.Builder().headers(getHeaders(heads));
	}

	private Headers getHeaders(Map<String, String> headers) {
		Map<String, String> ret = new HashMap<String, String>(super.defHeads());
		if (headers != null)
			ret.putAll(headers);
		return Headers.of(ret);
	}

	@Override
	public OkHttpClient getHttpKernel() {
		return kernel;
	}

	@Override
	public void head(int requestCode, String url,
			IXResultListener resultListener) {

	}

	@Override
	public void head(int requestCode, String url, Map<String, ?> requestParams,
			IXResultListener resultListener) {

	}

	@Override
	public void head(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener) {

	}

	@Override
	public void post(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener) {
		RequestBody body = getMultipartBody(requestParams);
		if (resultListener instanceof IXProgress)
			body = new ProxyRequestBody(requestCode, body,
					(IXProgress) resultListener);
		Request request = getRequestBuilder(heads).url(url).post(body).build();
		enqueue(request, requestCode, resultListener);
	}

	private RequestBody getMultipartBody(Map<String, ?> requestParams) {
		MultipartBody.Builder builder = new MultipartBody.Builder();

		for (Map.Entry<String, ?> entry : requestParams.entrySet()) {

			if (entry.getValue() instanceof String) {
				builder.addFormDataPart(entry.getKey(),
						(String) entry.getValue());
			}
			if (entry.getValue() instanceof byte[]) {
				byte[] a = (byte[]) entry.getValue();
				builder.addFormDataPart(
						entry.getKey(),
						entry.getKey(),
						MultipartBody.create(
								MediaType.parse("application/octet-stream"), a));
			} else if (entry.getValue() instanceof InputStream) {
				InputStream is = (InputStream) entry.getValue();
				byte[] buffer;
				try {
					buffer = new byte[is.available()];
					is.read(buffer);
					builder.addFormDataPart(
							entry.getKey(),
							entry.getKey(),
							MultipartBody.create(
									MediaType.parse("application/octet-stream"),
									buffer));
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} else if (entry.getValue() instanceof File) {
				File file = (File) entry.getValue();
				builder.addFormDataPart(entry.getKey(), file.getName(),
						RequestBody.create(MediaType.parse(ContentType
								.getContentTyep(file)), file));
			}

		}
		return builder.build();
	}

	public void post(int requestCode, String url, Map<String, String> heads,
			String entity, String contentType, IXResultListener resultListener) {
		RequestBody body = null;

		if (entity != null) {
			body = RequestBody.create(MediaType.parse(contentType), entity);
			if (resultListener instanceof IXProgress)
				body = new ProxyRequestBody(requestCode, body,
						(IXProgress) resultListener);
		} else
			body = MultipartBody.create(
					MediaType.parse("txt/plain;charset=utf-8"), "");
		Request.Builder builder = getRequestBuilder(heads).url(url).post(body);
		enqueue(builder.build(), requestCode, resultListener);
	}

	@Override
	public void put(int requestCode, String url, Map<String, ?> requestParams,
			IXResultListener resultListener) {

	}

	@Override
	public void put(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener) {

	}

	@Override
	public void put(int requestCode, String url, String requestParams,
			String contentType, IXResultListener resultListener) {

	}

	@Override
	public void setDeftTimeout(int timeout) {
		OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
		kernel = httpBuilder.connectTimeout(timeout, TimeUnit.SECONDS).build();

	}

}
