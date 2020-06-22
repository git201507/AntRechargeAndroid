package com.ant.recharge.common;

import android.content.Context;
import android.util.Log;

import com.ant.recharge.fragment2.planner.entity.PlannerCustomerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

/**
 *
 * 理财师，我的客户
 * 返回的数据结构与state,msg评级，单独创建一个callback为其服务
 * Created by kwc on 2016/12/1.
 */
public abstract class NCallbackPlannerCustomer implements Callback<PlannerCustomerEntity> {

    Logger logger = Logger.getLogger(NCallback.class.getSimpleName());
    private Context context;
    public NCallbackPlannerCustomer(Context context) {
        this.context = context;
    }


    private boolean isCancel = false;

    @Override
    public void success(PlannerCustomerEntity nResponse, Response response) {
        Log.d("-------", "-----------------------------");
        if (isCancel) {
            return;
        }
        Log.d("-------", "-----------------------------");
        try {
            if (Profile.DEBUGABLE) {
                Log.d("----------接口返回", "State=" + nResponse.getState());
                Log.d("----------接口返回", "msg=" + nResponse.getMsg());

            }

            if ("1".equals(nResponse.getState())) {
                List node = nResponse.getData();
                if (Profile.DEBUGABLE) {
                    Log.d("----------接口返回", "" + node);
                }

                if (null != node) {
                    try {
                        onSuccess(response.getStatus(), response.getHeaders(),
                                nResponse);
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

    private void onFailure(PlannerCustomerEntity nResponse, Response response) {
        onFailure(response.getStatus(), response.getHeaders(), nResponse.getState(), nResponse.getMsg(),
                new Throwable("Known Error!"));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void failure(RetrofitError retrofitError) {
        Log.d("-------", "----------retrofitError=" + retrofitError.getMessage());
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
        } else if (retrofitError.getKind().equals(RetrofitError.Kind.HTTP)) {

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

    public abstract void onSuccess(int statusCode, List<Header> headers, PlannerCustomerEntity nResponse);

    public abstract void onFailure(int statusCode, List<Header> headers,
                                   String infoCode, String infoMessage, Throwable throwable);



    public void cancel() {
        isCancel = true;
    }

    public boolean isCancel() {
        return isCancel;
    }

}
