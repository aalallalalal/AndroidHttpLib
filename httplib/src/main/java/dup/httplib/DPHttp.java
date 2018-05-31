package dup.httplib;

import android.os.Handler;

import java.util.Map;

import dup.baselib.http.BaseHttp;
import dup.baselib.http.interfaces.OnceSetting;
import dup.httplib.lib.CallBack;
import dup.httplib.lib.callback.HttpDefCallBack;
import dup.httplib.lib.callback.HttpDownLoadCallBack;
import dup.httplib.lib.handler.HttpDefHandler;
import dup.httplib.lib.setting.HttpInitManager;
import dup.httplib.lib.setting.Settings;
import dup.httplib.lib.setting.TransitionLayerManager;
import dup.httplib.lib.wrap.WrapCall;


/**
 * 网络请求组件API
 *
 * @author dupeng
 * @time 2018/3/1
 */

public class DPHttp {
    /**
     * 获取初始化接口
     *
     * @return 初始化接口类
     */
    public static HttpInitManager init() {
        return new HttpInitManager();
    }

    /**
     * get请求
     *
     * @param url          请求url路径
     * @param params       参数.请求参数可以为Object 或 请求参数如果为Map(必须为Map< String,Object >)
     * @param claz         需要返回的实体类
     * @param callBack     回调
     * @param isShowLoader 是否展示Loading
     * @param settings     一次性超时时间设置
     * @param <T>          需要返回的实体类
     * @return 请求
     */
    public static <T> WrapCall get(String url, Object params, Class<T> claz, CallBack<T> callBack, boolean isShowLoader, Settings... settings) {
        Map<String, Object> hashParams = null;
        if (params != null) {
            //param转为Map
            hashParams = TransitionLayerManager.getInstance().getPreTransitionLayer().parseObjectToMap(params);
        }
        //将base组件setting转为lib setting
        OnceSetting onceSetting = parseSetting(settings);
        //请求
        return new WrapCall(BaseHttp.request().get(url, hashParams, new HttpDefCallBack(claz, getHandler(callBack), isShowLoader), onceSetting));
    }

    /**
     * post请求
     *
     * @param url          请求url路径
     * @param params       参数.请求参数可以为Object 或 请求参数如果为Map(必须为Map< String,Object >)
     * @param claz         需要返回的实体类
     * @param callBack     回调
     * @param isShowLoader 是否展示Loading
     * @param settings     一次性参数设置:超时时间,请求类型等
     * @param <T>          需要返回的实体类
     * @return 请求
     */
    public static <T> WrapCall post(String url, Object params, Class<T> claz, CallBack<T> callBack, boolean isShowLoader, Settings... settings) {
        Map<String, Object> hashParams = null;
        if (params != null) {
            //param转为Map
            hashParams = TransitionLayerManager.getInstance().getPreTransitionLayer().parseObjectToMap(params);
        }
        //将base组件setting转为lib setting
        OnceSetting onceSetting = parseSetting(settings);
        //请求
        return new WrapCall(BaseHttp.request().post(url, hashParams, new HttpDefCallBack(claz, getHandler(callBack), isShowLoader), onceSetting));
    }

    /**
     * put请求
     *
     * @param url          请求url路径
     * @param params       参数.请求参数可以为Object 或 请求参数如果为Map(必须为Map< String,Object >)
     * @param claz         需要返回的实体类
     * @param callBack     回调
     * @param isShowLoader 是否展示Loading
     * @param settings     一次性超时时间设置
     * @param <T>          需要返回的实体类
     * @return 请求
     */
    public static <T> WrapCall put(String url, Object params, Class<T> claz, CallBack<T> callBack, boolean isShowLoader, Settings... settings) {
        Map<String, Object> hashParams = null;
        if (params != null) {
            //param转为Map
            hashParams = TransitionLayerManager.getInstance().getPreTransitionLayer().parseObjectToMap(params);
        }
        //将base组件setting转为lib setting
        OnceSetting onceSetting = parseSetting(settings);
        //请求
        return new WrapCall(BaseHttp.request().put(url, hashParams, new HttpDefCallBack(claz, getHandler(callBack), isShowLoader), onceSetting));
    }

    /**
     * delete请求
     *
     * @param url          请求url路径
     * @param params       参数.请求参数可以为Object 或 请求参数如果为Map(必须为Map< String,Object >)
     * @param claz         需要返回的实体类
     * @param callBack     回调
     * @param isShowLoader 是否展示Loading
     * @param settings     一次性超时时间设置
     * @param <T>          需要返回的实体类
     * @return 请求
     */
    public static <T> WrapCall delete(String url, Object params, Class<T> claz, CallBack<T> callBack, boolean isShowLoader, Settings... settings) {
        Map<String, Object> hashParams = null;
        if (params != null) {
            //param转为Map
            hashParams = TransitionLayerManager.getInstance().getPreTransitionLayer().parseObjectToMap(params);
        }
        //将base组件setting转为lib setting
        OnceSetting onceSetting = parseSetting(settings);
        //请求
        return new WrapCall(BaseHttp.request().delete(url, hashParams, new HttpDefCallBack(claz, getHandler(callBack), isShowLoader), onceSetting));
    }

    /**
     * patch请求
     *
     * @param url          请求url路径
     * @param params       参数.请求参数可以为Object 或 请求参数如果为Map(必须为Map< String,Object >)
     * @param claz         需要返回的实体类
     * @param callBack     回调
     * @param isShowLoader 是否展示Loading
     * @param settings     一次性超时时间设置
     * @param <T>          需要返回的实体类
     * @return 请求
     */
    public static <T> WrapCall patch(String url, Object params, Class<T> claz, CallBack<T> callBack, boolean isShowLoader, Settings... settings) {
        Map<String, Object> hashParams = null;
        if (params != null) {
            //param转为Map
            hashParams = TransitionLayerManager.getInstance().getPreTransitionLayer().parseObjectToMap(params);
        }
        //将base组件setting转为lib setting
        OnceSetting onceSetting = parseSetting(settings);
        //请求
        return new WrapCall(BaseHttp.request().patch(url, hashParams, new HttpDefCallBack(claz, getHandler(callBack), isShowLoader), onceSetting));
    }

    /**
     * 上传文件。要上传的文件为File类型。
     *
     * @param url          请求url路径
     * @param params       参数.请求参数可以为Object 或 请求参数如果为Map(必须为Map< String,Object >)
     * @param claz         需要返回的实体类
     * @param callBack     回调
     * @param isShowLoader 是否展示Loading
     * @param settings     一次性超时时间设置
     * @param <T>          需要返回的实体类
     * @return 请求
     */
    public static <T> WrapCall upLoad(String url, Object params, Class<T> claz, CallBack<T> callBack, boolean isShowLoader, Settings... settings) {
        Map<String, Object> hashParams = null;
        if (params != null) {
            //param转为Map
            hashParams = TransitionLayerManager.getInstance().getPreTransitionLayer().parseObjectToMap(params);
        }

        //将base组件setting转为lib setting
        OnceSetting onceSetting = parseSetting(settings);

        //回调
        HttpDefCallBack lkHttpDefCallBack = new HttpDefCallBack(claz, getHandler(callBack), isShowLoader);
        return new WrapCall(BaseHttp.request().upLoad(url, hashParams, lkHttpDefCallBack, lkHttpDefCallBack, onceSetting));
    }

    /**
     * post请求下载文件
     *
     * @param url          请求url路径
     * @param params       参数.请求参数可以为Object 或 请求参数如果为Map(必须为Map< String,Object >)
     * @param filePath     文件保存路径
     * @param isCover      当文件已存在时是否覆盖掉。默认为true。
     * @param callBack     回调
     * @param isShowLoader 是否展示Loading
     * @param settings     一次性超时时间设置
     * @return 请求
     */
    public static WrapCall downLoadWithPost(String url, Object params, String filePath, boolean isCover, CallBack<String> callBack,
                                            boolean isShowLoader, Settings... settings) {
        Map<String, Object> hashParams = null;
        if (params != null) {
            //param转为Map
            hashParams = TransitionLayerManager.getInstance().getPreTransitionLayer().parseObjectToMap(params);
        }

        //将base组件setting转为lib setting
        OnceSetting onceSetting = parseSetting(settings);

        //回调
        HttpDownLoadCallBack lkHttpDownLoadCallBack = new HttpDownLoadCallBack(filePath, isCover, String.class, getHandler(callBack), isShowLoader);

        return new WrapCall(BaseHttp.request().downLoadWithPost(url, hashParams, lkHttpDownLoadCallBack, onceSetting));
    }

    /**
     * get请求下载文件
     *
     * @param url          请求url路径
     * @param params       参数.请求参数可以为Object 或 请求参数如果为Map(必须为Map< String,Object >)
     * @param filePath     文件保存路径
     * @param isCover      当文件已存在时是否覆盖掉。默认为true。
     * @param callBack     回调
     * @param isShowLoader 是否展示Loading
     * @param settings     一次性超时时间设置
     * @return 请求
     */
    public static WrapCall downLoadWithGet(String url, Object params, String filePath, boolean isCover, CallBack<String> callBack,
                                           boolean isShowLoader, Settings... settings) {
        Map<String, Object> hashParams = null;
        if (params != null) {
            //param转为Map
            hashParams = TransitionLayerManager.getInstance().getPreTransitionLayer().parseObjectToMap(params);
        }

        //将base组件setting转为lib setting
        OnceSetting onceSetting = parseSetting(settings);

        //回调
        HttpDownLoadCallBack lkHttpDownLoadCallBack = new HttpDownLoadCallBack(filePath, isCover, String.class, getHandler(callBack), isShowLoader);
        return new WrapCall(BaseHttp.request().downLoadWithGet(url, hashParams, lkHttpDownLoadCallBack, onceSetting));
    }


    /**
     * 提供自定义的网络回调 处理Handler
     *
     * @return 网络回调 处理Handler
     */
    private static Handler getHandler(CallBack callBack) {
        return new HttpDefHandler(callBack);
    }


    /**
     * 将baselib库setting 转为 Lib库的setting
     *
     * @param settings
     * @return
     */
    private static OnceSetting parseSetting(Settings[] settings) {
        if (settings != null && settings.length > 0 && settings[0] != null) {
            OnceSetting setting = new OnceSetting(settings[0].connectTimeout, settings[0].writeTimeout, settings[0].readTimeout);
            setting.mediaTypeWrap = settings[0].mediaTypeWrap;
            return setting;
        }
        return null;
    }

}
