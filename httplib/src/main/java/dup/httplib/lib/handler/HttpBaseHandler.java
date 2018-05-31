package dup.httplib.lib.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.ArrayMap;

import dup.baselib.log.LogOperate;
import dup.httplib.lib.CallBack;
import dup.httplib.lib.global.HandlerFinalCode;


/**
 * handle到 回调数据，数据分发并转换到主线程，调用主线程回调。
 * 可以重写此类,来重新定义数据的处理/分发.
 *
 * @author dupeng
 * @time 2018/3/1
 */

public abstract class HttpBaseHandler extends Handler {
    /**
     * 回调接口
     */
    protected CallBack mCallBack;

    public HttpBaseHandler(CallBack callBack) {
        super(Looper.getMainLooper());
        this.mCallBack = callBack;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mCallBack == null) {
            return;
        }
        try {
            switch (msg.what) {
                case HandlerFinalCode.HANDLER_WHAT_CODE_START:
                    start((ArrayMap<String, Object>) msg.obj);
                    break;
                case HandlerFinalCode.HANDLER_WHAT_CODE_SUCCESS:
                    success((ArrayMap<String, Object>) msg.obj);
                    break;
                case HandlerFinalCode.HANDLER_WHAT_CODE_FAILURE:
                    failure((ArrayMap<String, Object>) msg.obj);
                    break;
                case HandlerFinalCode.HANDLER_WHAT_CODE_CANCEL:
                    cancel((ArrayMap<String, Object>) msg.obj);
                    break;
                case HandlerFinalCode.HANDLER_WHAT_CODE_FINISH:
                    finish((ArrayMap<String, Object>) msg.obj);
                    removeCallbacksAndMessages(null);
                    mCallBack = null;
                    break;
                case HandlerFinalCode.HANDLER_WHAT_CODE_UPLOAD_PROGRESS:
                    upLoadProgress((String) msg.obj);
                    break;
                case HandlerFinalCode.HANDLER_WHAT_CODE_DOWNLOAD_PROGRESS:
                    downLoadProgress((String) msg.obj);
                    break;
                case HandlerFinalCode.HANDLER_WHAT_CODE_DOWNLOAD_SUCCESS:
                    downLoadSuccess((String) msg.obj);
                    break;
                case HandlerFinalCode.HANDLER_WHAT_CODE_DOWNLOAD_FAILURE:
                    downLoadFailure((String) msg.obj);
                    break;
                default:
                    break;
            }
        } catch (ClassCastException e) {
            LogOperate.e(e.getMessage());
        }
    }

    /**
     * 下载文件失败回调
     *
     * @param obj 格式： url$$$###@@@要保存到的file路径
     */
    protected abstract void downLoadFailure(String obj);

    /**
     * 下载文件成功回调
     *
     * @param obj 格式： url$$$###@@@要保存到的file路径
     */
    protected abstract void downLoadSuccess(String obj);

    /**
     * 下载文件进度回调。进度是0-100的整数。
     *
     * @param obj 格式：url$$$###@@@filePath###@@@进度
     */
    protected abstract void downLoadProgress(String obj);

    /**
     * 上传文件进度回调。进度是0-100的整数。
     *
     * @param obj 格式：filePath$$$###@@@进度
     */
    protected abstract void upLoadProgress(String obj);

    /**
     * 请求结束.所有请求的成功失败 后 都会执行这方法.
     *
     * @param msgObj : 包含数据:是否showload  ,  请求Call
     */
    protected abstract void finish(ArrayMap<String, Object> msgObj);

    /**
     * 请求取消 回调
     *
     * @param msgObj : 包含数据:是否showload  ,  请求Call
     */
    protected abstract void cancel(ArrayMap<String, Object> msgObj);

    /**
     * 请求失败 回调
     *
     * @param msgObj ： 包含数据：是否showload ， 请求Call ， Exception
     */
    protected abstract void failure(ArrayMap<String, Object> msgObj);

    /**
     * 请求开始 回调
     *
     * @param msgObj : 包含数据:是否showload  ,  请求Call
     */
    protected abstract void start(ArrayMap<String, Object> msgObj);

    /**
     * 请求成功 回调.<br>
     * <b>注意：文件下载功能，不会调用此方法。</b>
     *
     * @param msgObj : 包含数据:是否showload  ,  请求Call  ,  返回数据的实体类
     */
    protected abstract void success(ArrayMap<String, Object> msgObj);

}
