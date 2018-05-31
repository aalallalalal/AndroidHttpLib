package dup.baselib.log;

/**
 * Log基本信息配置
 *
 */
interface LogConfig {
    /**
     * 双竖直分割线
     */
    char LINE_VERTICAL_DOUBLE = '║';
    /**
     * 文件中开行标识
     */
    String LINE_HORIZONTAL_FILE_TOP = "═══════════════一人之下═══════════════";
    /**
     * 单横向分割线
     */
    String LINE_HORIZONTAL_SINGLE = "────────────────────────────────────────────────────────────────────────────────────────⊙";

    /**
     * 顶部线条
     */
    String LINT_TOP = "╔════一人之下════════════════════════════════════════════════════════════════════════════════╗";
    /**
     * 底部线条
     */
    String LINT_BOTTOM = "╚════════════════════════════════════════════════════════════════════════════════════════╝";
    /**
     * 中间线条
     */
    String LINE_MIDDLE = LINE_VERTICAL_DOUBLE + LINE_HORIZONTAL_SINGLE;

    /**
     * 日志级别
     */
    char V = 'V', D = 'D', I = 'I', W = 'W', E = 'E', A = 'A';
    /**
     * 换行
     */
    String LINE_SEPARATOR = System.getProperty("line.separator");
    /**
     * Json格式数据缩进字符数
     */
    int JSON_INDENT = 4;
}
