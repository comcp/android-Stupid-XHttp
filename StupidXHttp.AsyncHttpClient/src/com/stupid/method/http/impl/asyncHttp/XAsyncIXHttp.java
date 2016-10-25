package com.stupid.method.http.impl.asyncHttp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.stupid.method.http.AbsIXHttp;
import com.stupid.method.http.IXResultListener;
import com.stupid.method.http.util.ContentType;

/**
 * @author wangx
 * @version 2.0
 */
public class XAsyncIXHttp extends AbsIXHttp<AsyncHttpClient> {
	private static final String tag = "XAsyncIXHttp";
	final private AsyncHttpClient kernel;

	/**
	 * @param nThreads
	 *            {@link Executors#newFixedThreadPool(int)}
	 * 
	 */
	public XAsyncIXHttp(int nThreads) {
		this(Executors.newFixedThreadPool(nThreads));
	}

	/**
	 * @param service
	 *            {@link ExecutorService}
	 */
	public XAsyncIXHttp(ExecutorService service) {
		kernel = new AsyncHttpClient();
		kernel.setThreadPool(service);
	}

	/**
	 * 默认线程数3
	 * **/
	public XAsyncIXHttp() {
		this(3);
	}

	/**
	 * @param kernel
	 */
	public XAsyncIXHttp(AsyncHttpClient kernel) {
		if (kernel == null)
			throw new NullPointerException();
		this.kernel = kernel;
	}

	@Override
	public void setDefHeader(String name, String value) {
		kernel.addHeader(name, value);
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
	public void get(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener) {

		RequestParams params = new RequestParams();
		if (requestParams != null)
			for (Map.Entry<String, ?> entry : requestParams.entrySet()) {
				params.put(entry.getKey(), entry.getValue());
			}
		kernel.get(null, url, getHeaders(heads), params, new AsyncResultString(
				requestCode, resultListener));

	}

	@Override
	public void head(int requestCode, String url,
			IXResultListener resultListener) {
		head(requestCode, url, null, null, resultListener);

	}

	@Override
	public void head(int requestCode, String url, Map<String, ?> requestParams,
			IXResultListener resultListener) {
		head(requestCode, url, null, requestParams, resultListener);

	}

	@Override
	public void head(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> params, IXResultListener resultListener) {

		RequestParams requestParams = new RequestParams();
		if (params != null) {
			for (Map.Entry<String, ?> ent : params.entrySet()) {
				requestParams.put(ent.getKey(), ent.getValue());
			}
		}
		kernel.head(null, url, getHeaders(heads), requestParams,
				new AsyncResultString(requestCode, resultListener));

	}

	// @Override
	// public void post(int requestCode, String url, String entity,
	// String contentType, IX ResultListener resultListener) {
	// try {
	// String charset = ContentType.parse(contentType).getCharset();
	//
	// StringEntity entity2 = new StringEntity(entity,
	// charset == null ? "UTF-8" : charset);
	// entity2.setContentType(contentType);
	// kernel.post(null, url, getHeaders(null), entity2, contentType,
	// new AsyncResultString(requestCode, resultListener));
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	//
	// }

	@Override
	public void post(int requestCode, String url, Map<String, String> heads,
			String entity, String contentType, IXResultListener resultListener) {

		StringEntity ent = null;
		if (entity != null) {
			try {
				String charset = ContentType.parse(contentType).getCharset();
				ent = new StringEntity(entity, charset == null ? "UTF-8"
						: charset);
				ent.setContentType(contentType);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		kernel.post(null, url, getHeaders(heads), ent, contentType,
				new AsyncResultString(requestCode, resultListener));

	}

	@Override
	public void post(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> params, IXResultListener resultListener) {
		RequestParams requestParams = new RequestParams();
		if (null != params)
			for (Entry<String, ?> p : params.entrySet()) {

				if (p.getValue() instanceof File) {
					File file = (File) p.getValue();
					try {
						requestParams.put(p.getKey(), file,
								ContentType.getContentTyep(file),
								file.getName());
					} catch (FileNotFoundException e) {
						Log.e(tag, "文件未找到", e);
					}
				} else if (p.getValue() instanceof InputStream) {

					requestParams.put(p.getKey(), (InputStream) p.getValue());

				} else if (p.getValue() instanceof byte[]) {
					requestParams.put(p.getKey(), new ByteArrayInputStream(
							(byte[]) p.getValue()));
				} else {

					requestParams.put(p.getKey(), p.getValue() == null ? "null"
							: p.getValue());
				}

			}

		kernel.post(null, url, getHeaders(heads), requestParams, "",
				new AsyncResultString(requestCode, resultListener));

	}

	@Override
	public void put(int requestCode, String url, String requestParams,
			String contentType, IXResultListener resultListener) {
		String charset = ContentType.parse(contentType).getCharset();

	}

	@Override
	public void put(int requestCode, String url, Map<String, ?> requestParams,
			IXResultListener resultListener) {

	}

	public void setThreadPool(ExecutorService threadPool) {
		getHttpKernel().setThreadPool(threadPool);

	}

	@Override
	public AsyncHttpClient getHttpKernel() {
		return kernel;
	}

	@Override
	public void setDeftTimeout(int timeout) {
		getHttpKernel().setTimeout(timeout);
	}

	protected Header[] getHeaders(Map<String, String> headers) {

		Map<String, String> defHeaders = new HashMap<String, String>(defHeads());
		Header[] heads = null;
		if (headers != null)
			defHeaders.putAll(headers);
		heads = new Header[defHeaders.size()];
		int i = 0;
		for (Map.Entry<String, String> head : defHeaders.entrySet()) {
			heads[i++] = new BasicHeader(head.getKey(), head.getValue());
		}
		return heads;
	}

	@Override
	public void put(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener) {

	}

}
