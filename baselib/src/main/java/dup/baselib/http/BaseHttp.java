package dup.baselib.http;

import dup.baselib.http.base.okhttpfactory.OkHttpFactory;
import dup.baselib.http.base.okhttpfactory.plugins.OkHttpInitPlugin;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.OkHttpPlugin;

/**
 * 网络请求对外API。方法的返回值 以及 方法里面调用哪个Factory 依据需要替换。
 *
 * @author dupeng
 * @date 2018/2/23
 */

public class BaseHttp {

    /**
     * 获取请求功能插件
     *
     * @return  OkHttpPlugin
     */
    public static OkHttpPlugin request() {
        return OkHttpFactory.getInstance().getHttpPlugin();
    }

    /**
     * 获取初始化请求参数插件
     *
     * @return  OkHttpInitPlugin
     */
    public static OkHttpInitPlugin init() {
        return OkHttpFactory.getInstance().getInitPlugin();
    }

}
