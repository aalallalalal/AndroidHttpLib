package dup.baselib.log;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 日志相关操作基类,扩展类可继承该类
 */
public class LogBase {


    /**
     * 日志打印开关
     */
    static boolean ISDEBUG = false;
    /**
     * 日志tag
     */
    static String TAG = "DUP";

    /**
     * 日志文件夹名
     * 该文件夹将创建在Android/data/项目包名/files/目录下
     */
    static String FILE_DIR = "dupLog";
    /**
     * 日志文件名
     */
    static String FILE_PREFIX = "DUP";
    /**
     * 日志文件格式,禁止修改
     */
    private static final String FILE_FORMAT = ".log";
    /**
     * 日志文件
     */
    private static File mLogFile;


    /**
     * 打印log
     *
     * @param level 日志级别
     * @param msg   日志内容
     */
    protected static void printLog(String tag, char level, String... msg) {
        printThread(tag, level);
        printLocation(tag, level);
        printMsg(tag, level, "日志内容", msg);
    }


    /**
     * 打印当前日志线程信息
     *
     * @param level 日志级别
     */
    protected static void printThread(String tag, char level) {
        printFun(tag, level, LogConfig.LINT_TOP);
        try {
            printFun(tag, level, LogConfig.LINE_VERTICAL_DOUBLE + "   线程:");
            printFun(tag, level, LogConfig.LINE_VERTICAL_DOUBLE + "     " + Thread.currentThread().getName());
            printFun(tag, level, LogConfig.LINE_MIDDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印日志调用位置部分(类、行数、函数)
     *
     * @param level 日志级别
     */
    protected static String printLocation(String tag, char level) {

        String printLocation = "位置定位";
        try {
            /**
             * 获取该线程堆栈转储的堆栈跟踪元素数组
             */
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            int i = 0;
            for (StackTraceElement e : stack) {
                String name = e.getClassName();
                if (!name.equals(LogBase.class.getName())) {
                    i++;
                } else {
                    break;
                }
            }
            i += 3;
            String className = stack[i].getFileName();
            String methodName = stack[i].getMethodName();
            int lineNumber = stack[i].getLineNumber();
            StringBuilder sb = new StringBuilder();
            printFun(tag, level, LogConfig.LINE_VERTICAL_DOUBLE + "   位置:");
            sb.append(LogConfig.LINE_VERTICAL_DOUBLE)
                    .append("     类名:(").append(className).append(":").append(lineNumber).append(") --> 函数名: ").append(methodName);
            printLocation = sb.toString();
            printFun(tag, level, printLocation);
            printFun(tag, level, LogConfig.LINE_MIDDLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printLocation;
    }


    /**
     * 打印具体消息部分
     *
     * @param level 打印级别
     * @param title 内容区域标题
     * @param msg   具体打印消息
     */
    protected static void printMsg(String tag, char level, String title, String... msg) {
        printFun(tag, level, LogConfig.LINE_VERTICAL_DOUBLE + "   " + title + ":");
        if (null == msg || msg.length == 0) {
            printFun(tag, level, LogConfig.LINE_VERTICAL_DOUBLE + "   ");
            printFun(tag, level, LogConfig.LINE_VERTICAL_DOUBLE + "   ");
        } else {
            for (String str : msg) {
                printFun(tag, level, LogConfig.LINE_VERTICAL_DOUBLE + "     " + str);
            }
        }
        printFun(tag, level, LogConfig.LINT_BOTTOM);
    }


    /**
     * 基本打印处理
     *
     * @param level 打印级别
     * @param msg   打印内容
     */
    protected static void printFun(String tag, char level, String msg) {
        String logTag = TAG;
        if (!TextUtils.isEmpty(tag)) {
            logTag = tag;
        }
        switch (level) {
            case LogConfig.V:
                Log.v(logTag, msg);
                break;
            case LogConfig.D:
                Log.d(logTag, msg);
                break;
            case LogConfig.I:
                Log.i(logTag, msg);
                break;
            case LogConfig.W:
                Log.w(logTag, msg);
                break;
            case LogConfig.E:
                Log.e(logTag, msg);
                break;
            case LogConfig.A:
                Log.wtf(logTag, msg);
                break;
            default:
                Log.v(logTag, msg);
        }
    }


    /**
     * 打印log
     *
     * @param level     日志级别
     * @param msg       msg
     * @param throwable 异常
     */
    protected static void printThrowableLog(String tag, char level, String msg, Throwable throwable) {
        printThread(tag, level);
        printLocation(tag, level);
        printThrowableMsg(tag, level, "错误日志", msg, throwable);
    }

    /**
     * 异常打印
     *
     * @param level     日志级别
     * @param title     标题
     * @param throwable 异常
     */
    private static void printThrowableMsg(String tag, char level, String title, String msg, Throwable throwable) {
        printFun(tag, level, LogConfig.LINE_VERTICAL_DOUBLE + "   " + title + ":");
        String throwStr = LogConfig.LINE_VERTICAL_DOUBLE + "      " + Log.getStackTraceString(throwable);
        String _throwStr = throwStr.replace(LogConfig.LINE_SEPARATOR, LogConfig.LINE_SEPARATOR + LogConfig.LINE_VERTICAL_DOUBLE + "        ");
        if (!TextUtils.isEmpty(msg)) {
            String msgStr = LogConfig.LINE_VERTICAL_DOUBLE + "      " + msg + LogConfig.LINE_SEPARATOR;
            printFun(tag, level, msgStr);
        }
        printFun(tag, level, _throwStr);
        printFun(tag, level, LogConfig.LINT_BOTTOM);
    }


    /**
     * xml转换
     *
     * @param xml xml文本内容
     * @return 转换后的xml内容
     */
    protected static String formatXML(String xml) {
        if (TextUtils.isEmpty(xml)) {
            return xml;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">" + LogConfig.LINE_SEPARATOR);
        } catch (Exception e) {
            e.printStackTrace();
            return xml;
        }
    }

    /**
     * 打印文件日志
     *
     * @param logFileDir 日志文件夹
     * @param msg        需要保存的消息
     */
    protected static void printFileLog(String tag, File logFileDir, String msg) {
        printThread(tag, LogConfig.E);
        String fileContent = String.format(Locale.CHINESE, "%s\r\n\r\n位置---%s\r\n内容---%s     %s\r\n\r\n\r\n"
                , LogConfig.LINE_HORIZONTAL_FILE_TOP
                , printLocation(tag, LogConfig.E)
                , LogConfig.LINE_VERTICAL_DOUBLE
                , msg);
        boolean isSave = saveFile(logFileDir, fileContent);
        String logFilePath = "  ";
        if (isSave) {
            logFilePath = String.format(Locale.CHINESE, "  %s", getLogFilePath());
        }
        String showMsg = String.format(Locale.CHINESE, "  %s", msg);
        printMsg(tag, LogConfig.E, "文件位置", logFilePath, "写入内容:", showMsg);
    }

    /**
     * 保存内容到文件
     *
     * @param logFileDir 文件夹路径
     * @param msg        需要保存的内容
     * @return 是否保存成功
     */
    protected static boolean saveFile(File logFileDir, String msg) {
        mLogFile = new File(logFileDir, getFileName());
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mLogFile, true), "UTF-8"));
            out.write(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            mLogFile = null;
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成文件名
     *
     * @return 日志文件名称
     */
    private static String getFileName() {
        return String.format(Locale.CHINESE, "%s_LOG_%s", FILE_PREFIX, FILE_FORMAT);
    }

    /**
     * 获取保存的日志文件路径
     *
     * @return 日志文件路径
     */
    protected static String getLogFilePath() {
        String logFilePath = "";
        if (null != mLogFile) {
            logFilePath = mLogFile.getAbsolutePath();
        }
        return logFilePath;
    }
}