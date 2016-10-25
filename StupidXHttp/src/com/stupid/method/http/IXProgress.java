package com.stupid.method.http;

public interface IXProgress extends IXResultListener {
	void onProgress(int requestCode, long bytesWritten, long totalSize);

	abstract public class XUploadProgress implements IXProgress {

		@Override
		public void onServerResult(int requestCode, String data, boolean state,
				XHttpResultRaw raw) {
			if (listener != null)
				listener.onServerResult(requestCode, data, state, raw);
		}

		IXResultListener listener;

		public XUploadProgress() {

		}

		public XUploadProgress(IXResultListener listener) {
			this.listener = listener;
		}

	}

}
