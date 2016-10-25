package com.stupid.method.http.demo.test;

import android.test.AndroidTestCase;

import com.stupid.method.http.util.ContentType;

public class ContentTypeTest extends AndroidTestCase {

	public void test() {
		ContentType content = ContentType.parse("text/html;charset=UTF-8");
		assertEquals(content.getCharset(), content.getCharset(), "UTF-8");
	}

	public void test2() {
		ContentType content = ContentType.parse("application/json");
		assertEquals(content.getCharset(), content.getCharset(), null);

	}
}
