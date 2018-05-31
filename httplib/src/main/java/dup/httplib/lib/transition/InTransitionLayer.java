package dup.httplib.lib.transition;

import android.os.Build;
import android.util.ArrayMap;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dup.baselib.log.LogOperate;


/**
 * 发送请求前的数据处理类
 *
 * @author dupeng
 * @time 2018/3/1
 */

public class InTransitionLayer implements InterInTransitionLayer {

    private static InTransitionLayer INSTANCE;

    public static InTransitionLayer getInstance() {
        if (INSTANCE == null) {
            synchronized (InTransitionLayer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new InTransitionLayer();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * object转化为Map
     *
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> parseObjectToMap(Object param) {
        if (param instanceof Map) {
            try {
                return (Map<String, Object>) param;
            } catch (ClassCastException e) {
                LogOperate.e("请求参数如果为Map时，必须为Map<String,Object>!");
                return null;
            }
        } else {
            //Json将Object转为Map
            Map<String, Object> arrayMap = null;
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    arrayMap = new ArrayMap<>();
                } else {
                    arrayMap = new HashMap<>(5);
                }
                Class clazz = param.getClass();
                List<Class> clazzs = new ArrayList<>();
                do {
                    clazzs.add(clazz);
                    clazz = clazz.getSuperclass();
                } while (!clazz.equals(Object.class));

                for (Class iClazz : clazzs) {
                    Field[] fields = iClazz.getDeclaredFields();
                    for (Field field : fields) {
                        Object objVal = null;
                        field.setAccessible(true);

                        objVal = field.get(param);
                        /*需要忽略基类中的默认字段.Android Studio2.0的.Instant Run 的问题,会出现change属性*/
                        if (!field.getName().contains("serialVersionUID") && !field.getName().contains("change")) {
                            arrayMap.put(field.getName(), objVal);
                        }
                    }
                }
            } catch (Exception e) {
                LogOperate.e("请求前，将Object转为Map失败！");
                LogOperate.e(e.getMessage());
            }
            return arrayMap;
        }
    }

}
