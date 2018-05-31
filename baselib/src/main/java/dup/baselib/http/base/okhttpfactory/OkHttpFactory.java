package dup.baselib.http.base.okhttpfactory;


import dup.baselib.http.base.okhttpfactory.plugins.OkHttpInitPlugin;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.OkHttpPlugin;
import dup.baselib.http.interfaces.InterHttpFactory;

/**
 * 封装Okhttp 的插件工厂。
 * @author dupeng
 * @date 2018/2/27
 */

public final class OkHttpFactory implements InterHttpFactory {
    private static OkHttpFactory INSTANCE;

    public static OkHttpFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (OkHttpFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkHttpFactory();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 返回请求功能插件
     * @return 请求功能插件
     */
    @Override
    public OkHttpPlugin getHttpPlugin() {
        return OkHttpPlugin.getInstance();
    }

    /**
     * 返回初始化请求参数功能插件
     * @return 配置网络请求初始化参数 功能插件
     */
    @Override
    public OkHttpInitPlugin getInitPlugin() {
        return OkHttpInitPlugin.getInstance();
    }

}