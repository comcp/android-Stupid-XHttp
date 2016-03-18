package com.stupid.method.http;

import java.util.Map;

public abstract class XHeaderHandler {

	abstract public Map<String, String> getDefCookie();

	abstract public void exeuHeader(Map<String, String> header);

}
