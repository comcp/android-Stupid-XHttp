package com.stupid.method.http.demo.test;

import java.util.HashMap;
import java.util.Map;

import android.test.AndroidTestCase;

import com.stupid.method.http.util.URLUtil;

public class URLUtilTest extends AndroidTestCase {

	public void test() {
		Map<String, Object> parasMap = new HashMap<String, Object>(4);
		parasMap.put("A", 1);
		parasMap.put("B", "B");
		String url = URLUtil.getUrlWithParas("http://www.baidu.com", parasMap);
		assertEquals(url, "http://www.baidu.com?A=1&B=B");

	}

	public void test2() {
		Map<String, Object> parasMap = new HashMap<String, Object>(4);
		parasMap.put("A", 1);
		parasMap.put("B", "B");
		String url = URLUtil.getUrlWithParas("http://www.baidu.com?A=2",
				parasMap);
		assertEquals(url, "http://www.baidu.com?A=1&B=B");

	}

	public void test3() {
		Map<String, Object> parasMap = new HashMap<String, Object>(4);
		parasMap.put("A", 1);
		parasMap.put("B", "B");
		String url = URLUtil.getUrlWithParas("http://www.baidu.com?C=2",
				parasMap);
		assertEquals(url, "http://www.baidu.com?C=2&A=1&B=B");

	}
}
