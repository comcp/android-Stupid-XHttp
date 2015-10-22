package com.stupid.method.http;

public interface IXDataFilter {

	boolean onDataFilter(IXServerResultListener listener, String data);
}
