package dup.httplib.lib.setting;


import dup.baselib.http.BaseHttp;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.MediaTypeWrap;

/**
 * 超时时间实体类.超时时间为基http库中默认的超时时间。
 *
 * @author dupeng
 * @time 2018/3/1
 */
public class Settings {
    /**
     * 请求类型
     */
    public MediaTypeWrap mediaTypeWrap;
    /**
     * 链接超时时间。就是连接到服务器的时间。
     */
    public long connectTimeout = BaseHttp.init().getConnectTimeout();
    /**
     * 写超时时间.就是文件数据上传的时间.
     */
    public long writeTimeout = BaseHttp.init().getWriteTimeout();
    /**
     * 读超时时间.就是链接成功后,进行内容数据的获取时间.
     */
    public long readTimeout = BaseHttp.init().getReadTimeout();

    /**
     * 设置请求类型
     * @param mediaTypeWrap
     */
    public Settings(MediaTypeWrap mediaTypeWrap) {
        this.mediaTypeWrap = mediaTypeWrap;
    }

    /**
     * @param connectTimeout 链接超时时间。就是连接到服务器的时间。
     * @param writeTimeout   写超时时间.就是文件数据上传的时间.
     * @param readTimeout    读超时时间.就是链接成功后,进行内容数据的获取时间.
     */
    public Settings(long connectTimeout, long writeTimeout, long readTimeout) {
        this.connectTimeout = connectTimeout;
        this.writeTimeout = writeTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * 设置连接超时时间
     *
     * @param connectTimeout 连接超时时间
     */
    public Settings(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}
