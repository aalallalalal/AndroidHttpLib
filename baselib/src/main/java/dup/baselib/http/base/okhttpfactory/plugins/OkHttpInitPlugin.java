package dup.baselib.http.base.okhttpfactory.plugins;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.MediaTypeWrap;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib.HttpLoggingInterceptor;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib.OkHttpInitManager;
import dup.baselib.http.interfaces.plugins.InterInitPlugin;
import dup.baselib.log.LogOperate;
import okhttp3.Interceptor;

/**
 * Okhttp网络参数初始化 功能插件
 *
 * @author dupeng
 * @date 2018/2/26
 */
public final class OkHttpInitPlugin implements InterInitPlugin {
    private static OkHttpInitPlugin INSTANCE;

    public static OkHttpInitPlugin getInstance() {
        if (INSTANCE == null) {
            synchronized (OkHttpInitPlugin.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkHttpInitPlugin();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 设置HttpLog等级.
     *
     * @param level log打印 级别
     * @see HttpLoggingInterceptor.Level
     */
    public void setHttpLogLevel(HttpLoggingInterceptor.Level level) {
        OkHttpInitManager.setHttpLogLevel(level);
    }

    /**
     * 设置连接超时
     *
     * @param timeout 连接超时时间. 0：无限制。>0：单位毫秒
     * @return 此插件
     */
    @Override
    public InterInitPlugin setConnectTimeout(long timeout) {
        OkHttpInitManager.setConnectTimeout(timeout);
        return this;
    }

    /**
     * 设置写超时
     *
     * @param timeout 写超时时间. 0：无限制。>0：单位毫秒
     * @return 此插件
     */
    @Override
    public InterInitPlugin setWriteTimeout(long timeout) {
        OkHttpInitManager.setWriteTimeout(timeout);
        return this;
    }

    /**
     * 设置读超时
     *
     * @param timeout 读超时时间. 0：无限制。>0：单位毫秒
     * @return 此插件
     */
    @Override
    public InterInitPlugin setReadTimeout(long timeout) {
        OkHttpInitManager.setReadTimeout(timeout);
        return this;
    }


    @Override
    public long getConnectTimeout() {
        return OkHttpInitManager.CONNECT_TIMEOUT;
    }

    @Override
    public long getWriteTimeout() {
        return OkHttpInitManager.WRITE_TIMEOUT;
    }

    @Override
    public long getReadTimeout() {
        return OkHttpInitManager.READ_TIMEOUT;
    }

    /**
     * 设置请求类型,默认form表单提交
     *
     * @param mediaTypeWrap
     */
    @Override
    public void setMediaType(MediaTypeWrap mediaTypeWrap) {
        OkHttpInitManager.setMediaType(mediaTypeWrap);
    }

    /**
     * 设置https的认证证书
     *
     * @param inputStream 认证证书流.eg:getAssets().open("..");
     * @return 此插件
     */
    @Override
    public InterInitPlugin setCertificates(InputStream... inputStream) {
        OkHttpInitManager.setSSLSocketFactory(getSSLSocketFactory(inputStream));
        return this;
    }

    /**
     * 添加先于所有拦截器执行的拦截器。
     *
     * @param interceptor 自定义拦截器
     * @return 此插件
     */
    public OkHttpInitPlugin addInterceptorToFirst(Interceptor interceptor) {
        OkHttpInitManager.addInterceptorToFirst(interceptor);
        return this;
    }

    /**
     * 添加后于所有拦截器执行的拦截器。
     *
     * @param interceptor 自定义拦截器
     * @return 此插件
     */
    public OkHttpInitPlugin addInterceptorToLast(Interceptor interceptor) {
        OkHttpInitManager.addInterceptorToLast(interceptor);
        return this;
    }

    /**
     * 去掉指定拦截器。
     *
     * @param interceptor 要去掉的自定义拦截器
     * @return 此插件
     */
    public OkHttpInitPlugin removeInterceptor(Interceptor interceptor) {
        OkHttpInitManager.removeInterceptor(interceptor);
        return this;
    }

    /**
     * 载入证书
     *
     * @param certificates https证书流
     * @return 配置好认定证书 的SSL工厂。
     */
    private SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
        try {
            SSLContext sslContext = null;
            TrustManager[] trustManager;
            if (certificates == null || certificates.length <= 0) {
                //没有证书参数
                return null;
            }

            //用我们的证书创建一个keystore
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                    LogOperate.e(e.getMessage());
                }
            }
            //创建一个trustmanager，只信任我们创建的keystore
            sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            trustManager = trustManagerFactory.getTrustManagers();

            sslContext.init(
                    null,
                    trustManager,
                    new SecureRandom()
            );
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
            return null;
        }
    }
}
