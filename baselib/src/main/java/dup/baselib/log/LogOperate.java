package dup.baselib.log;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 日志打印类
 *
 */
public class LogOperate extends LogBase {

    /**
     * 禁止实例化
     */
    private LogOperate() {
    }

    /**
     * 设置日志打印开关
     *
     * @param isDebug 是否进行打印
     */
    public static void setIsDebug(boolean isDebug) {
        ISDEBUG = isDebug;
    }

    /**
     * 设置日志tag
     *
     * @param tag 日志tag
     */
    public static void setTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        TAG = tag;
    }

    /**
     * 设置保存的文件夹名称
     * 文件保存在Android/data/项目包名/files/文件夹名   目录中
     *
     * @param dirName Android/data/项目包名/files/目录下的 文件夹名称
     */
    public static void setFileDir(String dirName) {
        if (TextUtils.isEmpty(dirName)) {
            return;
        }
        FILE_DIR = dirName;
    }

    /**
     * 设置文件前缀
     * 文件名格式: 前缀_LOG_.log
     *
     * @param filePrefix 文件前缀
     */
    public static void setFilePrefix(String filePrefix) {
        if (TextUtils.isEmpty(filePrefix)) {
            return;
        }
        FILE_PREFIX = filePrefix.replace(".", "");
    }

    /**
     * V级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void v(String... msg) {
        if (ISDEBUG){
            printLog(null, LogConfig.V, msg);
        }
    }

    /**
     * D级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void d(String... msg) {
        if (ISDEBUG){
            printLog(null, LogConfig.D, msg);
        }
    }

    /**
     * I级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void i(String... msg) {
        if (ISDEBUG){
            printLog(null, LogConfig.I, msg);
        }
    }

    /**
     * W级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void w(String... msg) {
        if (ISDEBUG){
            printLog(null, LogConfig.W, msg);
        }
    }

    /**
     * E级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void e(String... msg) {
        if (ISDEBUG){
            printLog(null, LogConfig.E, msg);
        }
    }


    /**
     * 异常日志打印
     *
     * @param throwable 异常
     */
    public synchronized static void e(Throwable throwable) {
        printThrowableLog(null, LogConfig.E, null, throwable);
    }

    /**
     * 异常日志打印
     *
     * @param msg       自定义消息内容
     * @param throwable 错误日志
     */
    public synchronized static void e(String msg, Throwable throwable) {
        printThrowableLog(null, LogConfig.E, msg, throwable);
    }

    /**
     * 打印JSON数据
     *
     * @param jsonStr 需要打印的json字符串
     */
    public synchronized static void json(String jsonStr) {
        if (ISDEBUG) {
            String jsonMsg;
            try {
                if (jsonStr.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    /**
                     * 缩进指定字符
                     */
                    jsonMsg = jsonObject.toString(LogConfig.JSON_INDENT);
                } else if (jsonStr.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    jsonMsg = jsonArray.toString(LogConfig.JSON_INDENT);
                } else {
                    jsonMsg = jsonStr;
                }
            } catch (JSONException e) {
                jsonMsg = jsonStr;
            }
            String[] lines = jsonMsg.split(LogConfig.LINE_SEPARATOR);
            printLog(null, LogConfig.E, lines);
        }
    }

    /**
     * 打印List数据,T需要重写toString
     *
     * @param list List
     * @param <T>  泛型
     */
    public synchronized static <T> void list(List<T> list) {
        if (null == list) {
            printLog(null, LogConfig.E, "List集合为Null");
        } else if (list.size() < 1) {
            printLog(null, LogConfig.E, "[]");
        } else {
            int i = 0;
            String[] msg = new String[list.size()];
            for (T t : list) {
                try {
                    msg[i] = t.toString() + LogConfig.LINE_SEPARATOR;
                } catch (Exception e) {
                    msg[i] = t + LogConfig.LINE_SEPARATOR;
                }
                i++;
            }
            printLog(null, LogConfig.E, msg);
        }
    }

    /**
     * 打印MAP数据
     *
     * @param map 需要打印的MAP内容
     */
    public synchronized static <T, V> void map(Map<T, V> map) {
        if (null == map) {
            printLog(null, LogConfig.E, "Map集合为Null");
        } else {
            Set<Map.Entry<T, V>> entrySet = map.entrySet();
            if (entrySet.size() < 1) {
                printLog(null, LogConfig.E, "[]");
                return;
            }
            int i = 0;
            String[] msg = new String[entrySet.size()];
            for (Map.Entry<T, V> entry : entrySet) {
                T key = entry.getKey();
                V value = entry.getValue();
                try {
                    msg[i] = key.toString() + " = " + value.toString() + "," + LogConfig.LINE_SEPARATOR;
                } catch (Exception e) {
                    msg[i] = key + " = " + value + "," + LogConfig.LINE_SEPARATOR;
                }
                i++;
            }
            printLog(null, LogConfig.E, msg);
        }
    }

    /**
     * XML打印
     *
     * @param xml 需要打印的xml字符串
     */
    public synchronized static void xml(String xml) {
        xml = formatXML(xml);
        String[] lines = xml.split(LogConfig.LINE_SEPARATOR);
        printLog(null, LogConfig.E, lines);
    }

    /**
     * 保存日志到文件
     *
     * @param msg 需要写入文件中的日志内容
     */
    public synchronized static void file(Context context, String msg) {
        File logFileDir = context.getExternalFilesDir(FILE_DIR);
        printFileLog(null,logFileDir, msg);
    }


    //=================================自定义Tag=========================================

    /**
     * V级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void vT(String tag, String... msg) {
        if (ISDEBUG){
            printLog(tag, LogConfig.V, msg);
        }
    }

    /**
     * D级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void dT(String tag, String... msg) {
        if (ISDEBUG){
            printLog(tag, LogConfig.D, msg);
        }
    }

    /**
     * I级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void iT(String tag, String... msg) {
        if (ISDEBUG){
            printLog(tag, LogConfig.I, msg);
        }
    }

    /**
     * W级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void wT(String tag, String... msg) {
        if (ISDEBUG){
            printLog(tag, LogConfig.W, msg);
        }
    }

    /**
     * E级别日志打印
     *
     * @param msg 日志内容
     */
    public synchronized static void eT(String tag, String... msg) {
        if (ISDEBUG){
            printLog(tag, LogConfig.E, msg);
        }
    }


    /**
     * 异常日志打印
     *
     * @param throwable 异常
     */
    public synchronized static void eT(String tag, Throwable throwable) {
        printThrowableLog(tag, LogConfig.E, null, throwable);
    }

    /**
     * 异常日志打印
     *
     * @param msg       自定义消息内容
     * @param throwable 错误日志
     */
    public synchronized static void eT(String tag, String msg, Throwable throwable) {
        printThrowableLog(tag, LogConfig.E, msg, throwable);
    }

    /**
     * 打印JSON数据
     *
     * @param jsonStr 需要打印的json字符串
     */
    public synchronized static void jsonT(String tag,String jsonStr) {
        if (ISDEBUG) {
            String jsonMsg;
            try {
                if (jsonStr.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    /**
                     * 缩进指定字符
                     */
                    jsonMsg = jsonObject.toString(LogConfig.JSON_INDENT);
                } else if (jsonStr.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    jsonMsg = jsonArray.toString(LogConfig.JSON_INDENT);
                } else {
                    jsonMsg = jsonStr;
                }
            } catch (JSONException e) {
                jsonMsg = jsonStr;
            }
            String[] lines = jsonMsg.split(LogConfig.LINE_SEPARATOR);
            printLog(tag, LogConfig.E, lines);
        }
    }

    /**
     * 打印List数据,T需要重写toString
     *
     * @param list List
     * @param <T>  泛型
     */
    public synchronized static <T> void listT(String tag,List<T> list) {
        if (null == list) {
            printLog(tag, LogConfig.E, "List集合为Null");
        } else if (list.size() < 1) {
            printLog(tag, LogConfig.E, "[]");
        } else {
            int i = 0;
            String[] msg = new String[list.size()];
            for (T t : list) {
                try {
                    msg[i] = t.toString() + LogConfig.LINE_SEPARATOR;
                } catch (Exception e) {
                    msg[i] = t + LogConfig.LINE_SEPARATOR;
                }
                i++;
            }
            printLog(tag, LogConfig.E, msg);
        }
    }

    /**
     * 打印MAP数据
     *
     * @param map 需要打印的MAP内容
     */
    public synchronized static <T, V> void mapT(String tag,Map<T, V> map) {
        if (null == map) {
            printLog(tag, LogConfig.E, "Map集合为Null");
        } else {
            Set<Map.Entry<T, V>> entrySet = map.entrySet();
            if (entrySet.size() < 1) {
                printLog(tag, LogConfig.E, "[]");
                return;
            }
            int i = 0;
            String[] msg = new String[entrySet.size()];
            for (Map.Entry<T, V> entry : entrySet) {
                T key = entry.getKey();
                V value = entry.getValue();
                try {
                    msg[i] = key.toString() + " = " + value.toString() + "," + LogConfig.LINE_SEPARATOR;
                } catch (Exception e) {
                    msg[i] = key + " = " + value + "," + LogConfig.LINE_SEPARATOR;
                }
                i++;
            }
            printLog(tag, LogConfig.E, msg);
        }
    }

    /**
     * XML打印
     *
     * @param xml 需要打印的xml字符串
     */
    public synchronized static void xmlT(String tag,String xml) {
        xml = formatXML(xml);
        String[] lines = xml.split(LogConfig.LINE_SEPARATOR);
        printLog(tag, LogConfig.E, lines);
    }

    /**
     * 保存日志到文件
     *
     * @param msg 需要写入文件中的日志内容
     */
    public synchronized static void fileT(String tag,Context context, String msg) {
        File logFileDir = context.getExternalFilesDir(FILE_DIR);
        printFileLog(tag,logFileDir, msg);
    }
}