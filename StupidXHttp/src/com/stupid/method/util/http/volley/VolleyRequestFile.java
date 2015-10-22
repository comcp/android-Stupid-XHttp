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

package com.stupid.method.util.http.volley;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * A canned request for getting an image at a given URL and calling back with a
 * decoded Bitmap.
 */
public class VolleyRequestFile extends VolleyRequest<File> {

	private Listener<File> mListener;
	private File target;
	/**
	 * Decoding lock so that we don't decode more than one image at a time (to
	 * avoid OOM's)
	 */
	private static final Object sDecodeLock = new Object();

	public VolleyRequestFile(int method, String url, File target,
			VolleyResult<File> listener) {
		super(method, url, listener);
		mListener = listener;
		this.target = target;

	}

	@Override
	protected void deliverResponse(File response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<File> parseNetworkResponse(NetworkResponse response) {
		synchronized (sDecodeLock) {
			try {
				return doParse(response);
			} catch (OutOfMemoryError e) {
				VolleyLog.e("Caught OOM for %d byte image, url=%s",
						response.data.length, getUrl());
				return Response.error(new ParseError(e));
			}
		}
	}

	private Response<File> doParse(NetworkResponse response) {
		byte[] data = response.data;
		try {

			target.createNewFile();
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					target));
			os.write(data);
			os.flush();
			os.close();

		} catch (IOException e) {
			return Response.error(new ParseError(e));
		}

		return Response.success(target,
				HttpHeaderParser.parseCacheHeaders(response));
	}

}
