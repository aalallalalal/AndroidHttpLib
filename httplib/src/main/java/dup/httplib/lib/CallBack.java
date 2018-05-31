package dup.httplib.lib;

/**
 * handler处理后，调用的回调接口。主线程的回调类。<br>
 * <b>类中的回调方法都是在主线程。</b>
 *
 * @author dupeng
 * @time 2018/3/2
 */

public abstract class CallBack<T> {
    /**
     * 请求成功<br>
     * <b>注:文件下载不会调用onSuccess()回调</b>
     *
     * @param url 请求url
     * @param obj 返回的实体类
     */
    public void onSuccess(String url, T obj) {
    }

    /**
     * 请求成功<br>
     * <b>注:文件下载不会调用onSuccess()回调,与onSuccess(url,obj)回调一样,只是多返回了文本数据</b>
     *
     * @param url        请求url
     * @param resultText 返回的数据,即与返回的实体类所对应的json值.
     * @param obj        返回的实体类
     */
    public void onSuccess(String url, String resultText, T obj) {
    }

    /**
     * 请求开始
     *
     * @param url       请求url
     * @param isLoading 是否展示loading
     */
    public void onStart(String url, boolean isLoading) {
    }

    /**
     * 请求失败
     *
     * @param url          请求url
     * @param isLoading    是否展示loading
     * @param exceptionStr 失败提示
     */
    public void onFailure(String url, boolean isLoading, String exceptionStr) {
    }

    /**
     * 请求取消
     *
     * @param url       请求url
     * @param isLoading 是否展示loading
     */
    public void onCancel(String url, boolean isLoading) {
    }

    /**
     * 请求结束.不管失败/成功/文件 都会调用
     *
     * @param url        请求url
     * @param resultCode 1:成功，0：cancel，-1：失败
     * @param isLoading  是否展示loading
     */
    public void onFinish(String url, int resultCode, boolean isLoading) {
    }

    /**
     * 上传文件，进度回调
     *
     * @param filePath 上传的文件路径
     * @param progress 进度 0-100 的整数
     */
    public void onUpLoadProgress(String filePath, int progress) {
    }

    /**
     * 文件下载,进度回调
     *
     * @param url      请求url
     * @param filePath 要保存到的文件路径
     * @param progress 进度 0-100 的整数
     */
    public void onDownLoadProgress(String url, String filePath, int progress) {
    }

    /**
     * 文件下载失败.<br>
     * <b>注:文件下载不会调用onSuccess()回调</b>
     *
     * @param url      请求url
     * @param filePath 要保存到的文件路径
     */
    public void onDownLoadFailure(String url, String filePath) {
    }

    /**
     * 文件下载成功<br>
     * <b>注:文件下载不会调用onSuccess()回调</b>
     *
     * @param url      请求url
     * @param filePath 要保存到的文件路径
     */
    public void onDownLoadSuccess(String url, String filePath) {
    }
}
