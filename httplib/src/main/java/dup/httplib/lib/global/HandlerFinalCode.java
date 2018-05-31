package dup.httplib.lib.global;

/**
 * Handler网络数据静态值
 * @author dupeng
 * @time 2018/3/2
 */

public class HandlerFinalCode {
    /**
     * 请求成功
     */
    public static final int HANDLER_WHAT_CODE_SUCCESS = -1;
    /**
     * 请求失败
     */
    public static final int HANDLER_WHAT_CODE_FAILURE = -2;
    /**
     * 请求开始
     */
    public static final int HANDLER_WHAT_CODE_START = -3;
    /**
     * 请求取消
     */
    public static final int HANDLER_WHAT_CODE_CANCEL = -4;
    /**
     * 请求结束
     */
    public static final int HANDLER_WHAT_CODE_FINISH = -5;
    /**
     * 上传进度
     */
    public static final int HANDLER_WHAT_CODE_UPLOAD_PROGRESS = -6;
    /**
     * 下载进度
     */
    public static final int HANDLER_WHAT_CODE_DOWNLOAD_PROGRESS = -7;
    /**
     * 下载成功
     */
    public static final int HANDLER_WHAT_CODE_DOWNLOAD_SUCCESS = -8;
    /**
     * 下载失败
     */
    public static final int HANDLER_WHAT_CODE_DOWNLOAD_FAILURE = -9;


    /**
     * handler key 值：是否展示Loading
     */
    public static final String HANDLER_KEY_ISSHOWLOADER = "HANDLER_KEY_ISSHOWLOADER";
    /**
     * handler key 值:请求数据
     */
    public static final String HANDLER_KEY_CALL = "HANDLER_KEY_CALL";
    /**
     * handler key 值:请求结果
     */
    public static final String HANDLER_KEY_RESULT_CODE = "HANDLER_KEY_RESULT_CODE";
    /**
     * handler key 值:返回的string
     */
    public static final String HANDLER_KEY_RESULT_TEXT = "HANDLER_KEY_RESULT_TEXT";
    /**
     * handler key 值:返回的obj
     */
    public static final String HANDLER_KEY_OBJ = "HANDLER_KEY_OBJ";
    /**
     * handler key 值:失败异常内容
     */
    public static final String HANDLER_KEY_EXCEPTION = "HANDLER_KEY_EXCEPTION";

    /**
     * 数据格式分割符
     */
    public static final String FILE_SPLITER = "&&&###@@@";
}
