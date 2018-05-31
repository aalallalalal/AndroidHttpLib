package dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib;


import java.io.IOException;

import dup.baselib.http.interfaces.RequestCallBack;
import dup.baselib.log.LogOperate;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 将Okhttp回调 进行详细分发。转调为自定义的网络请求回调。
 *
 * @author dupeng
 * @date 2018/2/25
 */
public final class OkHttpWrapCallback implements Callback {
    /**
     * 自定义的回调接口
     */
    private RequestCallBack mRef;

    public OkHttpWrapCallback(RequestCallBack callBack) {
        mRef = callBack;
    }

    /**
     * 请求失败回调
     *
     * @param call 请求
     * @param e    异常
     */
    @Override
    public void onFailure(Call call, IOException e) {
        boolean isCancel = false;
        try {
            LogOperate.d(call.request().url() + "请求Response failure，执行onFailure()...");
            if (call.isCanceled()) {
                mRef.onCancel(call);
                isCancel = true;
            } else {
                if (e != null && e.getMessage() != null) {
                    mRef.onFailure(call, e.getMessage().toString());
                } else {
                    mRef.onFailure(call, "请求onFailure，错误未知！");
                }
            }
        } catch (NullPointerException ex) {
            LogOperate.e(ex);
            mRef.onFailure(call, ex.getMessage().toString());
        } finally {
            if (mRef != null) {
                mRef.onFinish(call, isCancel ? 0 : -1);
            }
        }
    }

    /**
     * 成功回调
     *
     * @param call     请求
     * @param response 返回数据
     */
    @Override
    public void onResponse(Call call, Response response) {
        boolean isSuccess = false;
        try {
            if (response == null) {
                LogOperate.d("请求Response 为空！");
                mRef.onFailure(call, "请求返回的Response对象为空！");
                return;
            }
            if (mRef != null) {
                if (response.isSuccessful()) {
                    LogOperate.d("请求Response success，执行onSuccess()...");
                    mRef.onSuccess(call, response.body());
                    isSuccess = true;
                } else {
                    ResponseBody errorBody = response.body();
                    try {
                        mRef.onFailure(call, errorBody != null ? errorBody.string() : "请求Response isSuccessful()为false，并且errorBody为空！");
                    } catch (IOException e) {
                        LogOperate.e(e);
                        mRef.onFailure(call, "请求Response isSuccessful()为false，并且ResponseBody.string()发生异常！");
                    }
                }
            } else {
                LogOperate.d("请求Callback 为空！");
            }
        } catch (Exception e) {
            LogOperate.e(e);
        } finally {
            if (mRef != null) {
                mRef.onFinish(call, isSuccess ? 1 : -1);
            }
        }
    }
}