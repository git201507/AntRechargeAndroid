package com.ant.recharge.common;

import android.util.Log;

import org.codehaus.jackson.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;
import retrofit.client.Header;
import retrofit.client.Response;

/**
 * 
 * @author kangwc
 */
public abstract class NCallbackMsg implements Callback<NetModel<JsonNode>> {
	Logger logger = Logger.getLogger(NCallbackMsg.class.getSimpleName());

	private boolean isCancel = false;

	@Override
	public void success(NetModel<JsonNode> nResponse, Response response) {

		if (isCancel) {
			return;
		}

		try {
			if(Profile.DEBUGABLE) {
				Log.d("-----", "------接口返回State：" + nResponse.getState());
			}
			if ("1".equals(nResponse.getState())) {
				if(Profile.DEBUGABLE) {
					Log.d("-----", "------接口返回getMsg：" + nResponse.getMsg());
				}
				onSuccess(response.getStatus(), response.getHeaders(), nResponse.getMsg());
			} else {
				onFailure(response.getStatus(), response.getHeaders(),
						"599", nResponse.getMsg(),
						new Throwable("state 0 Error!"));
			}

		} catch (Exception e) {
			onFailure(response.getStatus(), response.getHeaders(),
					"499", e.getMessage(),
					new Throwable("Unknown Error!"));
		}
	}

	private void onFailure(NetModel<JsonNode> nResponse, Response response) {
		onFailure(response.getStatus(), response.getHeaders(), nResponse.getState(), nResponse.getMsg(),
				new Throwable("Known Error!"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void failure(RetrofitError retrofitError) {
		if (isCancel) {
			return;
		}
		if (retrofitError.isNetworkError()) {
			if (null == retrofitError.getResponse()) {
				if (retrofitError.getCause() == null) {
					onFailure(-1000, new ArrayList<Header>(),"-1000",
							"NET Error!" + retrofitError.getMessage(),
							new Throwable("NET Error!"));
				} else {
					onFailure(-1000, new ArrayList<Header>(), "-1000",
							"NET Error!" + retrofitError.getMessage(),
							retrofitError.getCause());
				}

			} else {
				if (retrofitError.getCause() == null) {
					onFailure(retrofitError.getResponse().getStatus(),
							retrofitError.getResponse().getHeaders(), "-1000",
							"NET Error!" + retrofitError.getMessage(),
							new Throwable("NET Error!"));
				} else {
					onFailure(retrofitError.getResponse().getStatus(),
							retrofitError.getResponse().getHeaders(), "-1000",
							"NET Error!" + retrofitError.getMessage(),
							retrofitError.getCause());
				}

			}
		} else if (retrofitError.getKind().equals(Kind.HTTP)) {

			if (retrofitError.getResponse().getStatus() == 403) {
					onFailure(retrofitError.getResponse().getStatus(),
							retrofitError.getResponse().getHeaders(),
							retrofitError.getResponse().getStatus() + "",
							"HTTP Error!" + retrofitError.getMessage(),
							retrofitError.getCause());
//				}
			} else {
				onFailure(retrofitError.getResponse().getStatus(),
						retrofitError.getResponse().getHeaders(), retrofitError
								.getResponse().getStatus() + "", "HTTP Error!"
								+ retrofitError.getMessage(),
						retrofitError.getCause());
			}

		} else {
			if (null == retrofitError.getResponse()) {

				if (retrofitError.getCause() == null) {
					onFailure(
							-100,
							new ArrayList<Header>(),
							"-100",
							retrofitError.getKind().toString()
									+ retrofitError.getMessage(),
							new Throwable(retrofitError.getKind().toString()));
				} else {
					onFailure(
							-100,
							new ArrayList<Header>(),
							"-100",
							retrofitError.getKind().toString()
									+ retrofitError.getMessage(),
							retrofitError.getCause());
				}

			} else {
				if (retrofitError.getCause() == null) {
					onFailure(
							retrofitError.getResponse().getStatus(),
							retrofitError.getResponse().getHeaders(),
							"-100",
							retrofitError.getKind().toString()
									+ retrofitError.getMessage(),
							new Throwable(retrofitError.getKind().toString()));
				} else {
					onFailure(
							retrofitError.getResponse().getStatus(),
							retrofitError.getResponse().getHeaders(),
							"-100",
							retrofitError.getKind().toString()
									+ retrofitError.getMessage(),
							retrofitError.getCause());
				}

			}

		}
	}

	public abstract void onSuccess(int statusCode, List<Header> headers, String result);

	public abstract void onFailure(int statusCode, List<Header> headers,
			String infoCode, String infoMessage, Throwable throwable);



	public void cancel() {
		isCancel = true;
	}

	public boolean isCancel() {
		return isCancel;
	}

}
