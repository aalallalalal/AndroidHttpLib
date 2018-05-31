package dup.httplib.lib.wrap;


import dup.baselib.log.LogOperate;

/**
 * 包裹Okhttp 的Call类.避免暴露三方类给使用该组件者.
 *
 * @author dupeng
 * @date 2018/3/7
 */

public class WrapCall {

    private final okhttp3.Call mCall;

    public WrapCall(okhttp3.Call call) {
        this.mCall = call;
    }

    /**
     * 获取请求的地址
     *
     * @return
     */
    public String url() {
        String url = "";
        try {
            if (mCall != null) {
                url = mCall.request().url().toString();
            }
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return url;
    }

    /**
     * 取消请求
     */
    public void cancel() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    /**
     * 是否可以取消请求
     *
     * @return
     */
    public boolean isCanceled() {
        if (mCall != null) {
            return mCall.isCanceled();
        } else {
            return false;
        }
    }

    /**
     * 是否已经执行
     *
     * @return
     */
    public boolean isExecuted() {
        if (mCall != null) {
            return mCall.isExecuted();
        } else {
            return false;
        }
    }

}
