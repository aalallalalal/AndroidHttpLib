package dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib;

import java.util.LinkedList;

import javax.net.ssl.SSLSocketFactory;

import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.MediaTypeWrap;
import dup.baselib.http.util.HttpLog;
import okhttp3.Interceptor;

/**
 * Okhttp网络初始化数据 管理类
 *
 * @author dupeng
 * @date 2018/2/26
 */

public final class OkHttpInitManager {
    /**
     * 是否有变动 标志位
     */
    public static boolean isChanged = true;
    /**
     * 连接超时时间
     */
    public static long CONNECT_TIMEOUT = 10000;
    /**
     * 写超时时间
     */
    public static long WRITE_TIMEOUT = 0;
    /**
     * 读超时时间
     */
    public static long READ_TIMEOUT = 0;

    /**
     * 保存拦截器
     */
    public static LinkedList<Interceptor> INTERCEPTORS = new LinkedList<>();

    /**
     * debug 模式下 httpLog 等级
     */
    public static HttpLoggingInterceptor.Level HTTP_LOG_LEVEL = HttpLoggingInterceptor.Level.EASY;

    /**
     * Https的证书认证，可能为空。
     */
    public static SSLSocketFactory SSLSocket;

    /**
     * 请求类型\.默认form
     */
    private static MediaTypeWrap mediaType = MediaTypeWrap.MEDIA_TYPE_FORM;

    /**
     * 设置连接超时。0：无限制。
     *
     * @param connectTimeout
     */
    public static void setConnectTimeout(long connectTimeout) {
        CONNECT_TIMEOUT = connectTimeout;
        isChanged = true;
    }


    /**
     * 设置写超时。0：无限制。
     *
     * @param writeTimeout
     */
    public static void setWriteTimeout(long writeTimeout) {
        WRITE_TIMEOUT = writeTimeout;
        isChanged = true;
    }

    /**
     * 设置读超时。0：无限制。
     *
     * @param readTimeout
     */
    public static void setReadTimeout(long readTimeout) {
        READ_TIMEOUT = readTimeout;
        isChanged = true;
    }

    public static void addInterceptorToFirst(Interceptor interceptor) {
        if (INTERCEPTORS != null && interceptor != null) {
            INTERCEPTORS.addFirst(interceptor);
            isChanged = true;
        }
    }

    public static void addInterceptorToLast(Interceptor interceptor) {
        if (INTERCEPTORS != null && interceptor != null) {
            INTERCEPTORS.addLast(interceptor);
            isChanged = true;
        }
    }

    public static void removeInterceptor(Interceptor interceptor) {
        if (INTERCEPTORS != null && interceptor != null) {
            INTERCEPTORS.remove(interceptor);
            isChanged = true;
        }
    }

    public static void setHttpLogLevel(HttpLoggingInterceptor.Level level) {
        HttpLog.isPrint = (level == HttpLoggingInterceptor.Level.NONE ? false : true);
        HTTP_LOG_LEVEL = level;
    }

    /**
     * 设置https证书认证的工厂
     *
     * @param SSLSocketFactory https证书认证的工厂
     */
    public static void setSSLSocketFactory(SSLSocketFactory SSLSocketFactory) {
        SSLSocket = SSLSocketFactory;
    }

    public static void setMediaType(MediaTypeWrap mediaType) {
        OkHttpInitManager.mediaType = mediaType;
    }

    public static MediaTypeWrap getMediaType() {
        return mediaType;
    }
}
