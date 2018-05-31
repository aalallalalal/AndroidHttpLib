package dup.httplib.lib.callback;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import dup.baselib.log.LogOperate;
import dup.httplib.lib.global.HandlerFinalCode;
import okhttp3.Call;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;


/**
 * 实现与lib库连接的 回调接口。并将数据发给Handler。
 * <br>
 * 此类为文件下载提供，重写onSuccess(),将数据保存到本地,并将保存进度/结果 传递给Handler.
 *
 * @author dupeng
 * @time 2018/3/2
 */
public class HttpDownLoadCallBack extends HttpDefCallBack {

    /**
     * 要保存的路径
     */
    private String filePath = "";

    /**
     * 是否覆盖保存
     */
    private boolean isCover = true;

    public HttpDownLoadCallBack(String filePath, boolean isCover, Class<?> toObj, Handler handler, boolean isShowLoader) {
        super(toObj, handler, isShowLoader);
        this.filePath = filePath;
        this.isCover = isCover;
    }

    /**
     * 上一个进度数据.用来防止进度回调过快,保证1%为单位.
     */
    private int preProgress = -1;

    @Override
    public void onSuccess(Call call, ResponseBody response) {

        boolean isSuccess = true;
        String url = call.request().url().toString();

        File dest = new File(filePath);
        if (dest == null) {
            LogOperate.w("下载文件，保存文件，new File(path)失败,文件路径无效：" + filePath);
            sendHandlerFailure(url);
            return;
        }

        if (!isCover) {
            //不覆盖文件
            if (dest != null && dest.exists()) {
                LogOperate.w("下载文件，保存文件已存在：" + filePath);
                sendHandlerSuccess(url);
                return;
            }
        }

        if (!dest.exists()) {
            try {
                boolean newFile = dest.createNewFile();
                if (!newFile) {
                    LogOperate.w("下载文件，创建文件失败：" + filePath);
                    sendHandlerFailure(url);
                    return;
                }
            } catch (IOException e) {
                isSuccess = false;
                LogOperate.e("下载文件，创建文件异常：" + filePath);
                LogOperate.e(e.getMessage());
            }
        }

        LogOperate.d("开始保存文件：" + filePath);
        BufferedSink bufferedSink = null;
        try {
            InputStream inputStream = response.byteStream();
            long total = response.contentLength();

            Sink sink = Okio.sink(dest);
            bufferedSink = Okio.buffer(sink);

            byte[] buf = new byte[2048];
            long current = 0;
            for (int readCount; (readCount = inputStream.read(buf)) != -1; ) {
                bufferedSink.write(buf, 0, readCount);
                current += readCount;
                if (mHandler != null) {
                    int progress = (int) (((float) current / total) * 100);
                    if (progress == preProgress) {
                        continue;
                    }
                    preProgress = progress;
                    sendHandlerProgress(url, progress);
                }

            }
            LogOperate.d("保存文件完成：" + filePath);
        } catch (Exception e) {
            isSuccess = false;
            LogOperate.e("保存文件异常：" + filePath);
            LogOperate.e(e.getMessage());
        } finally {
            if (bufferedSink != null) {
                try {
                    bufferedSink.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (isSuccess) {
            LogOperate.d("保存文件成功：" + filePath);
            sendHandlerSuccess(url);
        } else {
            LogOperate.w("保存文件失败：" + filePath);
            sendHandlerFailure(url);
        }
    }

    /**
     * 发送进度信息给Handler
     *
     * @param url
     * @param progress
     */
    private synchronized void sendHandlerProgress(String url, int progress) {
        if (mHandler != null) {
            Message message = mHandler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_DOWNLOAD_PROGRESS);
            String data = url +  HandlerFinalCode.FILE_SPLITER+ filePath + HandlerFinalCode.FILE_SPLITER + progress;
            message.obj = data;
            mHandler.sendMessage(message);
        }
    }

    /**
     * 发送失败信息给Handler
     *
     * @param url
     */
    private void sendHandlerFailure(String url) {
        if (mHandler != null) {
            Message message = mHandler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_DOWNLOAD_FAILURE);
            String data = url + HandlerFinalCode.FILE_SPLITER + filePath;
            message.obj = data;
            mHandler.sendMessage(message);
        }
    }

    /**
     * 发送成功信息给Handler
     *
     * @param url
     */
    private void sendHandlerSuccess(String url) {
        if (mHandler != null) {
            Message message = mHandler.obtainMessage(HandlerFinalCode.HANDLER_WHAT_CODE_DOWNLOAD_SUCCESS);
            String data = url +  HandlerFinalCode.FILE_SPLITER + filePath;
            message.obj = data;
            mHandler.sendMessage(message);
        }
    }


}
