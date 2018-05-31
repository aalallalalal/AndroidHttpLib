package dup.httplib.lib.handler;

import android.text.TextUtils;
import android.util.ArrayMap;

import dup.baselib.log.LogOperate;
import dup.httplib.lib.CallBack;
import dup.httplib.lib.global.HandlerFinalCode;
import okhttp3.Call;

/**
 * 实现handler 分发出去的数据处理，并调用至主线程环境的回调。<br>
 * 可以重写此类，实现分发数据的处理。
 *
 * @author dupeng
 * @Time 2018/3/2
 */

public class HttpDefHandler extends HttpBaseHandler {

    public HttpDefHandler(CallBack callBack) {
        super(callBack);
    }

    /**
     * 下载文件失败回调
     *
     * @param msgObj 格式： url$$$###@@@要保存到的file路径
     */
    @Override
    protected void downLoadFailure(String msgObj) {
        if (TextUtils.isEmpty(msgObj)) {
            return;
        }
        String url = "";
        String filePath = "";
        try {
            String[] split = msgObj.split(HandlerFinalCode.FILE_SPLITER);
            url = split[0];
            filePath = split[1];
        } catch (Exception e) {
            LogOperate.e("文件下载失败传递数据解析失败:" + msgObj);
            LogOperate.e(e.getMessage());
        } finally {
            if (mCallBack != null) {
                mCallBack.onDownLoadFailure(url, filePath);
            }
        }
    }

    /**
     * 下载文件成功回调
     *
     * @param msgObj 格式： url$$$###@@@要保存到的file路径
     */
    @Override
    protected void downLoadSuccess(String msgObj) {
        if (TextUtils.isEmpty(msgObj)) {
            return;
        }
        String url = "";
        String filePath = "";
        try {
            String[] split = msgObj.split(HandlerFinalCode.FILE_SPLITER);
            url = split[0];
            filePath = split[1];
        } catch (Exception e) {
            LogOperate.e("文件下载成功传递数据解析失败:" + msgObj);
            LogOperate.e(e.getMessage());
        } finally {
            if (mCallBack != null) {
                mCallBack.onDownLoadSuccess(url, filePath);
            }
        }
    }

    /**
     * 下载文件进度回调。进度是0-100的整数。
     *
     * @param msgObj 格式：url$$$###@@@filePath$$$###@@@进度
     */
    @Override
    protected void downLoadProgress(String msgObj) {
        if (TextUtils.isEmpty(msgObj)) {
            return;
        }
        String url = "";
        String filePath = "";
        int progress = 0;
        try {
            String[] split = msgObj.split(HandlerFinalCode.FILE_SPLITER);
            url = split[0];
            filePath = split[1];
            progress = Integer.parseInt(split[2]);
        } catch (Exception e) {
            LogOperate.e("文件下载进度传递数据解析失败:" + msgObj);
            LogOperate.e(e.getMessage());
        } finally {
            if (mCallBack != null) {
                mCallBack.onDownLoadProgress(url, filePath, progress);
            }
        }
    }

    /**
     * 上传文件进度回调。进度是0-100的整数。
     *
     * @param msgObj 格式：filePath$$$###@@@进度
     */
    @Override
    protected void upLoadProgress(String msgObj) {
        if (msgObj == null) {
            return;
        }
        int progress = 0;
        String filePath = "";
        try {
            String[] split = msgObj.split(HandlerFinalCode.FILE_SPLITER);
            filePath = split[0];
            progress = Integer.parseInt(split[1]);
        } catch (Exception e) {
            LogOperate.e("文件上传进度传递数据解析失败:" + msgObj);
            LogOperate.e(e.getMessage());
        } finally {
            if (mCallBack != null) {
                mCallBack.onUpLoadProgress(filePath, progress);
            }
        }
    }

    /**
     * 请求结束.所有请求的成功失败 后 都会执行这方法.
     *
     * @param msgObj : 包含数据:是否showload  ,  请求Call
     */
    @Override
    protected void  finish(ArrayMap<String, Object> msgObj) {
        if (msgObj == null) {
            return;
        }
        boolean isLoading = false;
        String url = "";
        int resultCode = 1;
        try {
            Call call = (Call) msgObj.get(HandlerFinalCode.HANDLER_KEY_CALL);
            isLoading = (boolean) msgObj.get(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER);
            resultCode = (int) msgObj.get(HandlerFinalCode.HANDLER_KEY_RESULT_CODE);
            if (call != null && call.request() != null) {
                url = call.request().url().toString();
            }
        } catch (Exception e) {
            LogOperate.e("Handler finish() 数据解析失败！");
            LogOperate.e(e.getMessage());
        } finally {
            if (mCallBack != null) {
                mCallBack.onFinish(url, resultCode, isLoading);
            }
            mCallBack = null;
        }
    }

    /**
     * 请求取消 回调
     *
     * @param msgObj : 包含数据:是否showload  ,  请求Call
     */
    @Override
    protected void cancel(ArrayMap<String, Object> msgObj) {
        if (msgObj == null) {
            return;
        }
        boolean isLoading = false;
        String url = "";
        try {
            Call call = (Call) msgObj.get(HandlerFinalCode.HANDLER_KEY_CALL);
            isLoading = (boolean) msgObj.get(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER);

            if (call != null && call.request() != null) {
                url = call.request().url().toString();
            }
        } catch (Exception e) {
            LogOperate.e("Handler cancel() 数据解析失败！");
            LogOperate.e(e.getMessage());
        } finally {
            if (mCallBack != null) {
                mCallBack.onCancel(url, isLoading);
            }
        }
    }

    /**
     * 请求失败 回调
     *
     * @param msgObj ： 包含数据：是否showload ， 请求Call ， Exception
     */
    @Override
    protected void failure(ArrayMap<String, Object> msgObj) {
        if (msgObj == null) {
            return;
        }
        boolean isLoading = false;
        String url = "";
        String exceptionStr = "";
        try {
            Call call = (Call) msgObj.get(HandlerFinalCode.HANDLER_KEY_CALL);
            isLoading = (boolean) msgObj.get(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER);
            exceptionStr = (String) msgObj.get(HandlerFinalCode.HANDLER_KEY_EXCEPTION);

            if (call != null && call.request() != null) {
                url = call.request().url().toString();
            }
        } catch (Exception e) {
            LogOperate.e("Handler failure() 数据解析失败！");
            LogOperate.e(e.getMessage());
        } finally {
            if (mCallBack != null) {
                mCallBack.onFailure(url, isLoading, exceptionStr);
            }
        }
    }

    /**
     * 请求开始 回调
     *
     * @param msgObj : 包含数据:是否showload  ,  请求Call
     */
    @Override
    protected void start(ArrayMap<String, Object> msgObj) {
        if (msgObj == null) {
            return;
        }
        boolean isLoading = false;
        String url = "";
        try {
            Call call = (Call) msgObj.get(HandlerFinalCode.HANDLER_KEY_CALL);
            isLoading = (boolean) msgObj.get(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER);

            if (call != null && call.request() != null) {
                url = call.request().url().toString();
            }
        } catch (Exception e) {
            LogOperate.e("Handler start() 数据解析失败！");
            LogOperate.e(e.getMessage());
        } finally {
            if (mCallBack != null) {
                mCallBack.onStart(url, isLoading);
            }
        }
    }

    /**
     * 请求成功 回调.<br>
     * <b>注意：文件下载功能，不会调用此方法。</b>
     *
     * @param msgObj : 包含数据:是否showload  ,  请求Call  ,  返回数据的实体类
     */
    @Override
    protected void success(ArrayMap<String, Object> msgObj) {
        if (msgObj == null) {
            return;
        }

        Object o = null;
        String url = "";
        String resultText = "";
        try {
            Call call = (Call) msgObj.get(HandlerFinalCode.HANDLER_KEY_CALL);
            o = msgObj.get(HandlerFinalCode.HANDLER_KEY_OBJ);
            resultText = (String) msgObj.get(HandlerFinalCode.HANDLER_KEY_RESULT_TEXT);

            if (call != null && call.request() != null) {
                url = call.request().url().toString();
            }
        } catch (Exception e) {
            LogOperate.e("Handler success() 数据解析失败！",e);
        } finally {
            if (mCallBack != null) {
                mCallBack.onSuccess(url, o);
                mCallBack.onSuccess(url, resultText, o);
            }
        }
    }
}
