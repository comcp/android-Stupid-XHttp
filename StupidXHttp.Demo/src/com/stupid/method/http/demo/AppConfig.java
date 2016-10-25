package com.stupid.method.http.demo;

public class AppConfig {
	public static AppConfig conf = new AppConfig();
	public String host;
	public String upload;
	public String get, methodPost;

	public AppConfig() {
		this("http://10.1.24.155:8080/IXHttpTestWebApp/");

	}

	public AppConfig(String host) {
		this.host = host;
		init();
	}

	private void init() {
		upload = host + "upload";
		get = host + "get";
		methodPost = host + "methodPost";
	}
}
