package com.stupid.method.http;

public abstract interface IXResultListener {

	/**
	 * @param requestCode
	 *            请求接口时 所发送的code
	 * @param data
	 *            从服务器上请求回来的数据
	 * @param state
	 *            请求结果
	 * @param raw
	 *            原始数据
	 */
	abstract void onServerResult(int requestCode, String data, boolean state,
			XHttpResultRaw raw);
}
