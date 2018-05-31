package dup.baselib.http.interfaces;


import dup.baselib.http.interfaces.plugins.InterHttpPlugin;
import dup.baselib.http.interfaces.plugins.InterInitPlugin;

/**
 * 工厂接口。提供各类功能插件。
 *
 * @author dupeng
 * @date 2018/2/23
 */

public interface InterHttpFactory {
    /**
     * 提供请求功能插件
     *
     * @return 请求功能插件接口
     */
    InterHttpPlugin getHttpPlugin();

    /**
     * 提供初始化请求参数 功能插件
     *
     * @return 初始化请求参数插件接口
     */
    InterInitPlugin getInitPlugin();
}
