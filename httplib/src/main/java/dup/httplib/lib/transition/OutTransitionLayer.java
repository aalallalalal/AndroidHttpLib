package dup.httplib.lib.transition;

import android.text.TextUtils;

import com.google.gson.Gson;

import dup.baselib.log.LogOperate;


/**
 * 返回的网络数据处理工具类
 *
 * @author dupeng
 * @time 2018/3/1
 */
public class OutTransitionLayer implements InterOutTransitionLayer{
    private static OutTransitionLayer INSTANCE;

    public static OutTransitionLayer getInstance() {
        if (INSTANCE == null) {
            synchronized (OutTransitionLayer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OutTransitionLayer();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 将数据转为obj
     *
     * @param toObj
     * @param responseText
     * @return
     */
    @Override
    public <T> T parseToObj(Class<T> toObj, String responseText) {
        if (toObj == null || TextUtils.isEmpty(responseText)) {
            return null;
        }
        //注意，response在调用过string()后,会清除掉数据,之后再调用就为空.
        T parse = null;
        try {
            parse = new Gson().fromJson(responseText, toObj);
        } catch (Exception e) {
            LogOperate.e("网络请求结果onSuccess()中 json  -"+responseText+"-   转obj失败!",e);
        }
        return parse;
    }
}
