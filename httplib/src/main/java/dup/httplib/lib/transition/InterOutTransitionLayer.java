package dup.httplib.lib.transition;

/**
 * 返回的网络数据处理工具类
 *
 * @author dupeng
 * @date 2018/4/16
 */

public interface InterOutTransitionLayer {
    /**
     * 将数据转为obj
     *
     * @param toObj
     * @param responseText
     * @return
     */
    <T> T parseToObj(Class<T> toObj, String responseText);
}
