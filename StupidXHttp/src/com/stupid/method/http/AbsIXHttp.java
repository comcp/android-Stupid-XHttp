package com.stupid.method.http;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author wangx
 * @version 2.0
 *
 * @param <kernel>
 *            实现网络请求的核心类
 */
abstract public class AbsIXHttp<kernel> implements IXHttp {
	final public static String getVersion() {
		return "2.0";
	}

	/**
	 * 设置默认时间
	 * 
	 * 
	 * @param timeout
	 *            超时时间 毫秒
	 * @return
	 */

	@Override
	public void setDefHeader(String name, String value) {
		defHeads.put(name, value);
	}

	/**
	 * 获得核心实现类
	 * */
	abstract public kernel getHttpKernel();

	private static final Map<String, String> defHeads = new HashMap<String, String>(
			3);

	static {
		defHeads.put("xHttp-version", getVersion());
	}
	{
		defHeads.put("xHttp-impl", this.getClass().getSimpleName());
		if (getHttpKernel() != null)
			defHeads.put("xHttp-kernel", getHttpKernel().getClass()
					.getSimpleName());
	}

	protected Map<String, String> defHeads() {
		return defHeads;
	}

	@Override
	final public void post(int requestCode, String url,
			Map<String, ?> requestParams, IXResultListener resultListener) {
		post(requestCode, url, null, requestParams, resultListener);
	}

	@Override
	final public void post(int requestCode, String url, String entity,
			String contentType, IXResultListener resultListener) {
		post(requestCode, url, null, entity, contentType, resultListener);
	}

	@Override
	final public void get(int requestCode, String url,
			Map<String, String> heads, IXResultListener resultListener) {
		get(requestCode, url, heads, null, resultListener);

	}

	@Override
	final public void post(int requestCode, String url,
			IXResultListener resultListener) {
		post(requestCode, url, null, "", resultListener);
	}

	@Override
	final public void get(int requestCode, String url,
			IXResultListener resultListener) {
		get(requestCode, url, null, resultListener);
	}

}
