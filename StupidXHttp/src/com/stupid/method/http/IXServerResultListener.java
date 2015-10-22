package com.stupid.method.http;

/**
 * 一切源自懒癌
 * 
 * 服务器返回调用
 * 
 * @author comcp@126.com <br>
 *         github: https://github.com/comcp/android-StupidMethod
 * @version v1.6
 * **/
public interface IXServerResultListener {

	/**
	 * @param resultCode
	 *            回调code, 就是你请求的时候填写的 int
	 * 
	 * @param data
	 *            服务器返回的数据
	 * @param state
	 *            请求状态
	 * @param httpCode
	 *            http request code
	 * ****/
	void onServerResult(int resultCode, String data, boolean state,
			int statusCode);

}
