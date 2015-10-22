package com.stupid.method.util.http;

public interface IXDataFilter {

	boolean onDataFilter(IXServerResultListener listener, String data);
}
