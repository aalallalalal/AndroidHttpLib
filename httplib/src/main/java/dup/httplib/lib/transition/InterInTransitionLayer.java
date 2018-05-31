package dup.httplib.lib.transition;

import java.util.Map;

/**
 * 发送请求前的数据处理类
 *
 * @author dupeng
 * @date 2018/4/16
 */

public interface InterInTransitionLayer {
    /**
     * object转化为Map
     *
     * @param param
     * @return
     */
    Map<String, Object> parseObjectToMap(Object param);
}
