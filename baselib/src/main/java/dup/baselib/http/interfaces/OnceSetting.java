package dup.baselib.http.interfaces;


import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.MediaTypeWrap;

/**
 * 一次性 超时时间配置 类。参数默认为 初始化的超时时间。
 * @author dupeng
 * @date 2018/2/24
 */
public final class OnceSetting {

    public MediaTypeWrap mediaTypeWrap;
    public long connectTimeout = 0;
    public long writeTimeout = 0;
    public long readTimeout = 0;

    /**
     * 设置请求类型
     * @param mediaTypeWrap
     */
    public OnceSetting(MediaTypeWrap mediaTypeWrap) {
        this.mediaTypeWrap = mediaTypeWrap;
    }

    public OnceSetting(long connectTimeout, long writeTimeout, long readTimeout) {
        this.connectTimeout = connectTimeout;
        this.writeTimeout = writeTimeout;
        this.readTimeout = readTimeout;
    }
}