package dup.baselib.http.util;

import android.util.Log;

/**
 * @author dupeng
 * @date 2018/4/2
 */

public class HttpLog {
    private static final String TAG = "LKHTTP_TAG";
    public static boolean isPrint = true;

    /**
     * V级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void v(String msg) {
        if (isPrint) {
            Log.v(TAG, msg);
        }
    }

    /**
     * D级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void d(String msg) {
        if (isPrint) {
            Log.d(TAG, msg);
        }
    }

    /**
     * I级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void i(String msg) {
        if (isPrint) {
            Log.i(TAG, msg);
        }
    }

    /**
     * W级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void w(String msg) {
        if (isPrint) {
            Log.w(TAG, msg);
        }
    }

    /**
     * E级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void e(String msg) {
        if (isPrint) {
            Log.e(TAG, msg);
        }
    }

}
