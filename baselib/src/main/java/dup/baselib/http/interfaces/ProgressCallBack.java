package dup.baselib.http.interfaces;

import java.io.File;

/**
 * 上传文件进度回调接口
 *
 * @author dupeng
 * @date 2018/2/24
 */
public interface ProgressCallBack {
    /**
     * 响应进度更新
     *
     * @param file    上传的文件
     * @param total   文件总大小
     * @param current 当前已上传的大小
     */
    void onProgress(File file, long total, long current);
}