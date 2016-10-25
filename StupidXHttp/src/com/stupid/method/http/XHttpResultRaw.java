package com.stupid.method.http;

import java.util.HashMap;
import java.util.Map;

public class XHttpResultRaw {

	static final Map<String, String> EMPTY_MAP = new HashMap<String, String>(1);
	private Map<String, String> heads;
	private int statusCode = -1;
	Throwable throwable;
	private String errorMsg;

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public void setHeads(Map<String, String> headers) {
		this.heads = headers;

	}

	public Map<String, String> getHeads() {
		return heads == null ? EMPTY_MAP : heads;
	}

	public XHttpResultRaw() {
		heads = new HashMap<String, String>();
	}

	public void addHead(String key, String value) {
		heads.put(key, value);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
