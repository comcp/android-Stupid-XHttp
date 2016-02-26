package com.stupid.method.http;

public interface IXHttpResult {
	public static final int RESULT_CANCEL = 1;
	public static final int RESULT_OK = 200;
	public static final int RESULT_NO_LINK_NETWORK = -20;

	int getResultCode();

	IXServerResultListener getResultListener();

	void setResultCode(int resultCode);

	void setResultListener(IXServerResultListener resultListener);

}
