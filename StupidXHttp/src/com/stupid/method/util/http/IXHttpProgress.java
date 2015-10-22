package com.stupid.method.util.http;

public interface IXHttpProgress {
	void onProgress(int bytesWritten, int totalSize);
}
