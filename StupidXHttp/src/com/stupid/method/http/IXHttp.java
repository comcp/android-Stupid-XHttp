package com.stupid.method.http;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author wangx
 *
 */
public interface IXHttp {

	void downloadFromGet(int requestCode, String url, File saveTo,
			IXResultListener progress);

	void downloadFromPost(int requestCode, String url, File saveTo,
			Map<String, String> heads, Map<String, ?> params,
			IXResultListener progress);

	/**
	 * @param timeout
	 */
	void setDeftTimeout(int timeout);

	/**
	 * @param name
	 * @param value
	 */
	void setDefHeader(String name, String value);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * 
	 */
	void delete(int requestCode, String url, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码
	 * @param url
	 *            服务器地址
	 * @param heads
	 *            https 请求头参数
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * 
	 */
	void delete(int requestCode, String url, Map<String, String> heads,
			IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码, 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, XHttpResultRaw)}
	 *            的一个参数传人
	 * @param url
	 *            请求地址
	 * @param heads
	 *            http 头
	 * @param requestParams
	 * 
	 *            参数 支持 {@link InputStream},{@link String},{@link File} ,byte[]
	 * @param resultListener
	 */
	void delete(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码
	 * @param url
	 * @param resultListener
	 * 
	 */
	void get(int requestCode, String url, IXResultListener resultListener);

	/**
	 * @param requestCode
	 * @param url
	 *            服务器地址
	 * @param heads
	 *            http head 数据
	 * @param resultListener
	 *            {@link IXResultListener} 请求结果回调函数
	 * 
	 */
	void get(int requestCode, String url, Map<String, String> heads,
			IXResultListener resultListener);

	/**
	 * @param requestCode
	 * @param url
	 * @param heads
	 * @param requestParams
	 *            发送给服务器的数据 ,所有的value都会被toString();
	 * @param resultListener
	 *            {@link IXResultListener} 请求结果回调函数
	 */

	void get(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * 
	 */
	void head(int requestCode, String url, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * @param requestParams
	 * 
	 */
	void head(int requestCode, String url, Map<String, ?> requestParams,
			IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * @param heads
	 * @param requestParams
	 * 
	 * 
	 */
	void head(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * @param stringEntity
	 * @param contentType
	 * @param resultListener
	 * 
	 */
	void post(int requestCode, String url, String stringEntity,
			String contentType, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * 
	 */
	void post(int requestCode, String url, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数 * @param resultListener
	 * 
	 */
	void post(int requestCode, String url, Map<String, ?> requestParams,
			IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * @param heads
	 * @param stringEntity
	 *            post String to server
	 * @param contentType
	 *            <a
	 *            href="http://tool.oschina.net/commons">Content-Type常用对照表</a>
	 * 
	 */
	void post(int requestCode, String url, Map<String, String> heads,
			String stringEntity, String contentType,
			IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * @param heads
	 * @param requestParams
	 * @param contentType
	 *            <a
	 *            href="http://tool.oschina.net/commons">Content-Type常用对照表</a>
	 * 
	 */
	void post(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * @param requestParams
	 * @param contentType
	 *            <a
	 *            href="http://tool.oschina.net/commons">Content-Type常用对照表</a>
	 * 
	 */
	void put(int requestCode, String url, String requestParams,
			String contentType, IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * @param requestParams
	 * 
	 */
	void put(int requestCode, String url, Map<String, ?> requestParams,
			IXResultListener resultListener);

	/**
	 * @param requestCode
	 *            请求码 最终会在
	 *            {@link IXResultListener#onServerResult(int, String, boolean, int)}
	 *            的第一个参数 返回回来
	 * @param url
	 *            请求地址
	 * @param resultListener
	 *            {@link IXResultListener} 结果回调函数
	 * @param heads
	 * @param requestParams
	 * 
	 */
	void put(int requestCode, String url, Map<String, String> heads,
			Map<String, ?> requestParams, IXResultListener resultListener);

}