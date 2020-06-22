package com.ant.recharge.login;

import android.util.Log;

import com.ant.recharge.common.NetModel;

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
public abstract class VerifyCodeNCallback implements Callback<String> {
	Logger logger = Logger.getLogger(VerifyCodeNCallback.class.getSimpleName());

	private boolean isCancel = false;

	@Override
	public void success(String nResponse, Response response) {

		if (isCancel) {
			return;
		}

		try {
			Log.d("-----", "" + nResponse);
			if("success:120".equals(nResponse)){
				//成功
				onSuccess(response.getStatus(), response.getHeaders(), "您生成验证码并发送到您的手机！");
			} else if("codeerror".equals(nResponse)){
				onFailure(response.getStatus(), response.getHeaders(),
						"499", "图形验证码填写错误！",
						new Throwable("图形验证码填写错误！"));


			} else if("haveMakeOutError".equals(nResponse)){
				onFailure(response.getStatus(), response.getHeaders(),
						"499", "在一段时间内不能重复生成验证码！",
						new Throwable("在一段时间内不能重复生成验证码！"));


			} else {
				onFailure(response.getStatus(), response.getHeaders(),
						"499", "手机验证码发送失败，请稍后重试！",
						new Throwable("手机验证码发送失败，请稍后重试！"));
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
