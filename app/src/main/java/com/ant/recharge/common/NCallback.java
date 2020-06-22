package com.ant.recharge.common;

import android.content.Context;
import android.util.Log;

import com.ant.recharge.common.json.JsonUtil;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;

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
 * @author ztt564
 *
 * @param <T>
 */
public abstract class NCallback<T> implements Callback<NetModel<JsonNode>> {
	Logger logger = Logger.getLogger(NCallback.class.getSimpleName());
	private Context context;
	private Class<T> valueType;
	private TypeReference<T> typeReference;
	private boolean isUseTypeReference = false;

	public NCallback(Context context, Class<T> valueType) {
		this.context = context;
		this.valueType = valueType;
	}

	public NCallback(Context context, TypeReference<T> typeReference) {
		this.context = context;
		this.typeReference = typeReference;
		isUseTypeReference = true;
	}

	private boolean isCancel = false;

	@Override
	public void success(NetModel<JsonNode> nResponse, Response response) {
		if (isCancel) {
			return;
		}

		try {
			T oRet;
			if (Profile.DEBUGABLE) {
				Log.d("----------接口返回", "State=" + nResponse.getState());
				Log.d("----------接口返回", "msg=" + nResponse.getMsg());
			}

			if ("1".equals(nResponse.getState())) {
				JsonNode node = nResponse.getData();
				if (Profile.DEBUGABLE) {
					Log.d("----------接口返回", "" + node);
				}

				if (null != node) {
					try {
						if (isUseTypeReference) {
							oRet = JsonUtil.decode(node, typeReference);
						} else {
							oRet = JsonUtil.decode(node, valueType);
						}
						onSuccess(response.getStatus(), response.getHeaders(),
								oRet);
					} catch (Exception e) {
						Log.d("---e", e.getMessage() + "");
						throw e;
					}
				} else {
					//针对 vip申请的特殊处理
					if("成功".equals(nResponse.getMsg())){
						onSuccess(response.getStatus(), response.getHeaders(), null);
					} else {
						throw new Exception("No Data!");
					}
				}
			} else {
				onFailure(nResponse, response);
			}
		} catch (Exception e) {



			if (e.getMessage() != null) {
				onFailure(response.getStatus(), response.getHeaders(),
						nResponse.getState(), nResponse.getMsg(),
						new Throwable(e));
			} else {
				if(nResponse == null){
					onFailure(response.getStatus(), response.getHeaders(),
							"599", "服务器异常！",
							new Throwable("Unknown Error!"));
					return;
				}

				onFailure(response.getStatus(), response.getHeaders(),
						nResponse.getState(), nResponse.getMsg(),
						new Throwable("Unknown Error!"));
			}

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

				//本项目没有token, 不用判断
//				if (retrofitError.getResponse().getReason()
//						.equals(Constants.ERROR_REFRESH_TOKEN)) {
//					Toast.makeText(
//							context,
//							context.getResources().getString(
//									R.string.activity_login_not_refreshtoken),
//							Toast.LENGTH_SHORT).show();
//
//					onFailure(retrofitError.getResponse().getStatus(),
//							retrofitError.getResponse().getHeaders(), -1001,
//							retrofitError.getMessage(),
//							retrofitError.getCause());
//
//					LoginModel.Instance(context).clearLoginInfo();
//					final Intent intent = new Intent();
//					intent.setAction(Constants.LOGOUT_ACTION);
//					context.sendBroadcast(intent);
//					LoginModel.Instance(context).startLoginActivity();
//				} else {
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

	public abstract void onSuccess(int statusCode, List<Header> headers, T oRet);

	public abstract void onFailure(int statusCode, List<Header> headers,
			String infoCode, String infoMessage, Throwable throwable);



	public void cancel() {
		isCancel = true;
	}

	public boolean isCancel() {
		return isCancel;
	}

}
