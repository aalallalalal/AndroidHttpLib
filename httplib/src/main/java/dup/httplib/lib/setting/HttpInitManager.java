package dup.httplib.lib.setting;


import java.io.InputStream;

import dup.baselib.http.BaseHttp;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.MediaTypeWrap;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib.HttpLoggingInterceptor;
import dup.httplib.lib.transition.InterInTransitionLayer;
import dup.httplib.lib.transition.InterOutTransitionLayer;
import dup.httplib.lib.wrap.HttpLogginLevel;
import dup.httplib.lib.wrap.InterceptorWrap;


/**
 * 网络框架 初始化工具类
 *
 * @author dupeng
 * @time 2018/3/1
 */

public final class HttpInitManager {

    /**
     * 超时时间数据实体类
     */
    public static Settings initTimeout;

    /**
     * 设置post等请求的默认请求方式.默认为form表单请求.
     * <br>
     * 注意:文件上传下载固定为form表单.其他的为设置的.
     * json,form....
     *
     * @param mediaTypeWrap {@link MediaTypeWrap}
     * @return
     */
    public HttpInitManager initRequestType(MediaTypeWrap mediaTypeWrap) {
        if (mediaTypeWrap != null) {
            BaseHttp.init().setMediaType(mediaTypeWrap);
        }
        return this;
    }

    /**
     * 设置超时时间。如果不调用此方法，则使用base网络项目中默认超时时间。
     *
     * @param initTimeout 超时时间参数。
     */
    public HttpInitManager initTimeout(Settings initTimeout) {
        if (initTimeout == null) {
            return this;
        }
        HttpInitManager.initTimeout = initTimeout;
        BaseHttp.init().setConnectTimeout(initTimeout.connectTimeout);
        BaseHttp.init().setReadTimeout(initTimeout.readTimeout);
        BaseHttp.init().setWriteTimeout(initTimeout.writeTimeout);
        return this;
    }

    /**
     * 设置Https 认证证书。如果不需要https证书认证功能，不需要调用此方法。
     * <ul>
     * <li>如果参数为空，则请求信任所有证书（OKHttp自带功能）</li>
     * <li>如果参数不为空，则请求验证传入证书</li>
     * </ul>
     *
     * @param inputStreams 证书流。eg：getAssets().open("..")
     */
    public HttpInitManager initHttpsCer(InputStream... inputStreams) {
        BaseHttp.init().setCertificates(inputStreams);
        return this;
    }

    /**
     * 设置HttpLog等级。控制打印的内容。默认EASY模式。
     *
     * @param level 日志打印等级。上线时，最好设置成NONE，可以微微得提高性能,并安全。
     * @see dup.httplib.lib.wrap.HttpLogginLevel.Level
     */
    public HttpInitManager initLogLevel(HttpLogginLevel.Level level) {
        HttpLoggingInterceptor.Level resultLevel;
        switch (level) {
            case EASY:
                resultLevel = HttpLoggingInterceptor.Level.EASY;
                break;
            case BASIC:
                resultLevel = HttpLoggingInterceptor.Level.BASIC;
                break;
            case BODY:
                resultLevel = HttpLoggingInterceptor.Level.BODY;
                break;
            case HEADERS:
                resultLevel = HttpLoggingInterceptor.Level.HEADERS;
                break;
            case NONE:
                resultLevel = HttpLoggingInterceptor.Level.NONE;
                break;
            default:
                resultLevel = HttpLoggingInterceptor.Level.EASY;
                break;
        }

        BaseHttp.init().setHttpLogLevel(resultLevel);
        return this;
    }

    /**
     * 设置请求前 数据转换层
     *
     * @param inTransitionLayer
     * @return
     */
    public HttpInitManager setPreTransitionLayer(InterInTransitionLayer inTransitionLayer) {
        TransitionLayerManager.getInstance().setPreTransitionLayer(inTransitionLayer);
        return this;
    }

    /**
     * 设置请求后 数据转化ceng
     *
     * @param outTransitionLayer
     * @return
     */
    public HttpInitManager setAfterTransitionLayer(InterOutTransitionLayer outTransitionLayer) {
        TransitionLayerManager.getInstance().setAfterTransitionLayer(outTransitionLayer);
        return this;
    }

    /**
     * 添加拦截器。
     *
     * @param interceptor 自定义拦截器
     * @param firstOrLast true:先于所有拦截器执行。false：后于所有拦截器执行。
     */
    public HttpInitManager addIntercepter(InterceptorWrap interceptor, boolean firstOrLast) {
        if (firstOrLast) {
            //拦截器插在头
            BaseHttp.init().addInterceptorToFirst(interceptor);
        } else {
            //拦截器插在尾
            BaseHttp.init().addInterceptorToLast(interceptor);
        }
        return this;
    }

    /**
     * 去掉指定拦截器。
     *
     * @param interceptor 指定拦截器
     */
    public HttpInitManager removeIntercepter(InterceptorWrap interceptor) {
        if (interceptor != null) {
            BaseHttp.init().removeInterceptor(interceptor);
        }
        return this;
    }

}
