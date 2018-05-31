package dup.baselib.http.interfaces;


import okhttp3.Call;

/**
 * 网络请求结果回调
 *
 * @param <C> 请求数据类型
 * @param <R> 请求返回数据类型
 * @author dupeng
 * @date 2018/2/24
 */
public interface RequestCallBack<C, R> {
    /**
     * 请求失败
     *
     * @param call
     * @param exception
     */
    void onFailure(C call, String exception);

    /**
     * 请求结束
     *
     * @param call
     * @param result 1:成功，0：cancel，-1：失败
     */
    void onFinish(C call, int result);

    /**
     * 请求成功
     *
     * @param call
     * @param response
     */
    void onSuccess(C call, R response);

    /**
     * 开始请求
     *
     * @param call
     */
    void onStart(C call);

    /**
     * 请求取消
     *
     * @param call
     */
    void onCancel(Call call);
}
