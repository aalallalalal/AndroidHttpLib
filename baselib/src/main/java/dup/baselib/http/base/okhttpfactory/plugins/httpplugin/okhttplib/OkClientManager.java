package dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib;


import java.util.concurrent.TimeUnit;

import dup.baselib.http.interfaces.OnceSetting;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * OKHttp3 管理类.创建OKHttpClient.
 *
 * @author dupeng
 * @date 2018/2/23
 */

public final class OkClientManager {
    /**
     * 默认请求client。
     */
    private static OkHttpClient client;

    /**
     * 获取默认请求client
     *
     * @return OkHttpClient
     */
    public static OkHttpClient getClient() {
        //如果设置有改变，则重新生成client，并将设置状态改为 没有更改。 如果设置没有更改，则按单例获取。
        if (OkHttpInitManager.isChanged) {
            client = getBaseBuilder(new OnceSetting(OkHttpInitManager.CONNECT_TIMEOUT, OkHttpInitManager.WRITE_TIMEOUT, OkHttpInitManager.READ_TIMEOUT)).build();
            OkHttpInitManager.isChanged = false;
        }
        if (client == null) {
            synchronized (OkClientManager.class) {
                if (client == null) {
                    client = getBaseBuilder(
                            new OnceSetting(OkHttpInitManager.CONNECT_TIMEOUT, OkHttpInitManager.WRITE_TIMEOUT, OkHttpInitManager.READ_TIMEOUT))
                            .build();
                }
            }
        }
        return client;
    }

    /**
     * 获取临时改变超时时间的client。不建议大量调用此方法，因为调用此方法会build一次client，微影响性能。
     *
     * @param onceSetting 临时超时时间数据。如果为空，则返回默认client。
     * @return OkhttpClient
     */
    public static OkHttpClient getClient(OnceSetting[] onceSetting) {
        if (onceSetting != null && onceSetting.length > 0 && onceSetting[0] != null) {
            OkHttpClient client = getBaseBuilder(
                    new OnceSetting(onceSetting[0].connectTimeout, onceSetting[0].writeTimeout, onceSetting[0].readTimeout))
                    .build();
            return client;
        } else {
            return getClient();
        }
    }

    /**
     * 获得基础网络配置
     *
     * @param timeoutSetting 超时时间
     * @return
     */
    private static OkHttpClient.Builder getBaseBuilder(OnceSetting timeoutSetting) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(timeoutSetting.connectTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeoutSetting.writeTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeoutSetting.readTimeout, TimeUnit.MILLISECONDS);

        if (OkHttpInitManager.SSLSocket != null) {
            builder.sslSocketFactory(OkHttpInitManager.SSLSocket);
        }

        for (Interceptor interceptor :
                OkHttpInitManager.INTERCEPTORS) {
            builder.addInterceptor(interceptor);
        }
        builder.addInterceptor(createHttpLoggingInterceptor());

        return builder;
    }

    /**
     * 日志拦截器
     *
     * @return 返回日志拦截器
     */
    private static Interceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(OkHttpInitManager.HTTP_LOG_LEVEL);
        return loggingInterceptor;
    }

}
