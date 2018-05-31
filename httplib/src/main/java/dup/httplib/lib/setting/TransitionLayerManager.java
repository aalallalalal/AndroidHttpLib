package dup.httplib.lib.setting;


import dup.httplib.lib.transition.InTransitionLayer;
import dup.httplib.lib.transition.InterInTransitionLayer;
import dup.httplib.lib.transition.InterOutTransitionLayer;
import dup.httplib.lib.transition.OutTransitionLayer;

/**
 * @author dupeng
 * @date 2018/4/16
 */

public class TransitionLayerManager {
    /**
     * 请求前数据转换层
     */
    private InterInTransitionLayer mInTransitionLayer;
    /**
     * 请求后数据转换层
     */
    private InterOutTransitionLayer mOutTransitionLayer;


    private static TransitionLayerManager INSTANCE;

    public static TransitionLayerManager getInstance() {
        if (INSTANCE == null) {
            synchronized (TransitionLayerManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TransitionLayerManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 设置请求前 数据转换层
     *
     * @param inTransitionLayer
     * @return
     */
    public void setPreTransitionLayer(InterInTransitionLayer inTransitionLayer) {
        this.mInTransitionLayer = inTransitionLayer;
    }

    /**
     * 设置请求后 数据转化ceng
     *
     * @param outTransitionLayer
     * @return
     */
    public void setAfterTransitionLayer(InterOutTransitionLayer outTransitionLayer) {
        this.mOutTransitionLayer = outTransitionLayer;
    }


    /**
     * 获取 请求后的数据转化层
     *
     * @return
     */
    public InterOutTransitionLayer getAfterTransitionLayer() {
        if (mOutTransitionLayer == null) {
            return OutTransitionLayer.getInstance();
        }
        return mOutTransitionLayer;
    }


    /**
     * 获取 请求前的数据转化层
     *
     * @return
     */
    public InterInTransitionLayer getPreTransitionLayer() {
        if (mInTransitionLayer == null) {
            return InTransitionLayer.getInstance();
        }
        return mInTransitionLayer;
    }
}
