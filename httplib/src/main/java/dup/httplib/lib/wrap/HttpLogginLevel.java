package dup.httplib.lib.wrap;

/**
 * httpLoggin 等级。
 *
 * @author dupeng
 * @date 2018/3/19
 */

public class HttpLogginLevel {
    public enum Level {
        /**
         * 不打印
         */
        NONE,
        EASY,
        BASIC,
        HEADERS,
        BODY;

        Level() {
        }
    }
}
