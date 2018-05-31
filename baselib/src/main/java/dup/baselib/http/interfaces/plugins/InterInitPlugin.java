package dup.baselib.http.interfaces.plugins;

import java.io.InputStream;

import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.MediaTypeWrap;


/**
 * 请求参数初始化插件 接口
 *
 * @author dupeng
 * @date 2018/2/26
 */
public interface InterInitPlugin {

    /**
     * 设置连接超时
     *
     * @param timeout 连接超时时间
     * @return 此插件 接口
     */
    InterInitPlugin setConnectTimeout(long timeout);

    /**
     * 设置写超时
     *
     * @param timeout 写超时时间
     * @return 此插件 接口
     */
    InterInitPlugin setWriteTimeout(long timeout);

    /**
     * 设置读超时
     *
     * @param timeout 读超时时间
     * @return 此插件 接口
     */
    InterInitPlugin setReadTimeout(long timeout);

    /**
     * 设置https的认证证书
     *
     * @param inputStream 认证证书流.eg:getAssets().open("..");
     * @return 此插件 接口
     */
    InterInitPlugin setCertificates(InputStream... inputStream);

    /**
     * 获取连接超时时间
     *
     * @return 超时时间
     */
    long getConnectTimeout();

    /**
     * 获取写超时时间
     *
     * @return 写超时时间
     */
    long getWriteTimeout();

    /**
     * 获取读超时时间
     *
     * @return 读超时时间
     */
    long getReadTimeout();

    /**
     * 设置默认请求类型
     *
     * @param mediaTypeWrap
     */
    void setMediaType(MediaTypeWrap mediaTypeWrap);
}

