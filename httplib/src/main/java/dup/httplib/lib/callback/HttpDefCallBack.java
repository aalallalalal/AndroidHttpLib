package dup.httplib.lib.callback;

import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dup.baselib.http.interfaces.ProgressCallBack;
import dup.baselib.http.interfaces.RequestCallBack;
import dup.baselib.log.LogOperate;
import dup.httplib.lib.global.HandlerFinalCode;
import dup.httplib.lib.setting.TransitionLayerManager;
import okhttp3.Call;
import okhttp3.ResponseBody;


/**
 * 实现与lib库连接的 回调接口。并将数据发给Handler。
 *
 * @author dupeng
 * @time 2018/3/1
 */

public class HttpDefCallBack implements RequestCallBack<Call, ResponseBody>, ProgressCallBack {

    /**
     * 要转换的对象
     */
    protected final Class<?> mToObj;
    /**
     * 是否展示loader
     */
    protected boolean isShowLoader;
    /**
     * 存放handler
     */
    protected Handler mHandler;

    public HttpDefCallBack(Class<?> toObj, Handler handler, boolean isShowLoader) {
        this.mHandler = handler;
        this.isShowLoader = isShowLoader;
        this.mToObj = toObj;
    }

    @Override
    public void onFailure(Call call, String exception) {
        Handler handler = mHandler;
        if (handler != null) {
            Map handlerObj = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                handlerObj = new ArrayMap<String, Object>(3);
            } else {
                handlerObj = new HashMap<String, Object>(3);
            }
            Message message = handler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_FAILURE);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER, isShowLoader);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_CALL, call);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_EXCEPTION, exception);
            message.obj = handlerObj;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onFinish(Call call, int result) {
        Handler handler = mHandler;
        if (handler != null) {
            Map handlerObj = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                handlerObj = new ArrayMap<String, Object>(3);
            } else {
                handlerObj = new HashMap<String, Object>(3);
            }
            Message message = handler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_FINISH);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER, isShowLoader);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_CALL, call);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_RESULT_CODE, result);
            message.obj = handlerObj;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onSuccess(Call call, ResponseBody response) {
        Handler handler = mHandler;
        if (handler != null) {
            Map handlerObj = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                handlerObj = new ArrayMap<String, Object>(3);
            } else {
                handlerObj = new HashMap<String, Object>(3);
            }
            String resultText = "";
            try {
                resultText = response.string();
            } catch (Exception e) {
                LogOperate.e("网络请求onSuccess()中,response.string()异常");
            }
            Message message = handler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_SUCCESS);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER, isShowLoader);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_CALL, call);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_RESULT_TEXT, resultText);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_OBJ, TransitionLayerManager.getInstance().
                    getAfterTransitionLayer().parseToObj(mToObj, resultText));
            message.obj = handlerObj;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onStart(Call call) {
        Handler handler = mHandler;
        if (handler != null) {
            Map handlerObj = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                handlerObj = new ArrayMap<String, Object>(2);
            } else {
                handlerObj = new HashMap<String, Object>(2);
            }
            Message message = handler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_START);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER, isShowLoader);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_CALL, call);
            message.obj = handlerObj;
            handler.sendMessage(message);
        }
    }

    @Override
    public void onCancel(Call call) {
        Handler handler = mHandler;
        if (handler != null) {
            Map handlerObj = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                handlerObj = new ArrayMap<String, Object>(2);
            } else {
                handlerObj = new HashMap<String, Object>(2);
            }
            Message message = handler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_CANCEL);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_ISSHOWLOADER, isShowLoader);
            handlerObj.put(HandlerFinalCode.HANDLER_KEY_CALL, call);
            message.obj = handlerObj;
            handler.sendMessage(message);
        }
    }

    /**
     * 上一个进度数据
     */
    private int preProgress = -1;

    /**
     * 上传文件进度回调
     *
     * @param file
     * @param total
     * @param current
     */
    @Override
    public synchronized void onProgress(File file, long total, long current) {
        Handler handler = mHandler;
        if (handler != null) {
            int progress = (int) (((float) current / total) * 100);
            if (progress == preProgress) {
                return;
            }
            preProgress = progress;
            Message message = handler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_UPLOAD_PROGRESS);
            String data = (file == null ? "" : file.getAbsolutePath()) + "###@@@" + progress;
            message.obj = data;
            handler.sendMessage(message);
        }
    }
}