/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stupid.method.http.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * A request for retrieving a T type response body at a given URL that also
 * optionally sends along a JSON body in the request specified.
 * 
 * @param <String>
 *            JSON type of response expected
 */
public class VolleyRequestString extends VolleyRequest<String> {

	public VolleyRequestString(int method, String url, String requestBody,
			VolleyResult result) throws UnsupportedEncodingException {
		this(url, new StringEntity(requestBody), result);
	}

	public VolleyRequestString(String url, HttpEntity entity,
			VolleyResult result) {
		super(url, entity, result);

	}

	public VolleyRequestString(int method, String url, Map<String, ?> map,
			VolleyResult result) {
		super(method, url, map, result);
	}

	/** Method:GET */
	public VolleyRequestString(String url, VolleyResult result) {
		super(Method.GET, url, result);

	}

	@Override
	protected void deliverResponse(String response) {

	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {

		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers,
							getCharset()));

			if (resultListener != null) {
				resultListener.onServerResponse(jsonString,
						response.statusCode, response.headers);
			}

			return Response.success(jsonString,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {

			return Response.error(new ParseError(e));
		}
	}
}
