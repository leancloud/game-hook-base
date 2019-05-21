package cn.leancloud.play.plugin.request;

import clojure.lang.Keyword;

/**
 * 房间系统属性接口，所有房间系统属性均会实现。
 * @param <V> 房间系统属性值类型
 */
public interface RoomSystemProperty<V> {
    /**
     * 房间属性 Key，Game Server 使用，Game Plugin 实现者无需使用
     *
     * @return 房间属性 Key
     */
    Keyword getPropertyKey();

    /**
     * 房间属性值
     * @return 房间属性值
     */
    V getPropertyValueToSet();

    /**
     * Game Server 内使用的房间属性值，Game Plugin 实现者无需使用
     * @return 房间属性值
     */
    default Object getSerializedPropertyValue() {
        return getPropertyValueToSet();
    }
}
