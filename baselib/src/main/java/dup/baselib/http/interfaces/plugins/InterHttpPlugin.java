package dup.baselib.http.interfaces.plugins;

import android.support.annotation.Nullable;

import java.util.Map;

import dup.baselib.http.interfaces.OnceSetting;
import dup.baselib.http.interfaces.ProgressCallBack;
import dup.baselib.http.interfaces.RequestCallBack;


/**
 * 网络请求插件的接口 。
 *
 * @param <C> 请求类型。
 * @param <R> 请求回调返回的返回类型。
 * @param <T> 请求返回的请求类型。
 * @author dupeng
 * @date 2018/2/23
 */

public interface InterHttpPlugin<C, R, T> {

    /**
     * get网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     */
    T get(String url, Map<String, Object> params, RequestCallBack<C, R> callBack, OnceSetting... onceSetting);

    /**
     * post网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     */
    T post(String url, Map<String, Object> params, RequestCallBack<C, R> callBack, OnceSetting... onceSetting);

    /**
     * put网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     */
    T put(String url, Map<String, Object> params, RequestCallBack<C, R> callBack, OnceSetting... onceSetting);

    /**
     * delete网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     */
    T delete(String url, Map<String, Object> params, RequestCallBack<C, R> callBack, OnceSetting... onceSetting);

    /**
     * patch网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     */
    T patch(String url, Map<String, Object> params, RequestCallBack<C, R> callBack, OnceSetting... onceSetting);

    /**
     * 文件上传 、多文件上传
     *
     * @param url         请求地址
     * @param params      请求参数，上传文件的话，value 为 File
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     */
    T upLoad(String url, Map<String, Object> params, RequestCallBack<C, R> callBack, @Nullable ProgressCallBack progressCallBack, OnceSetting... onceSetting);

    /**
     * post文件下载。
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     */
    T downLoadWithPost(String url, Map<String, Object> params, RequestCallBack<C, R> callBack, OnceSetting... onceSetting);

    /**
     * get文件下载。
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     */
    T downLoadWithGet(String url, Map<String, Object> params, RequestCallBack<C, R> callBack, OnceSetting... onceSetting);


}
