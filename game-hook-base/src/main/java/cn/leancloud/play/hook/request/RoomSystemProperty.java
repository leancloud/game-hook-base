package cn.leancloud.play.hook.request;

import clojure.lang.Keyword;

public interface RoomSystemProperty<V> {
    Keyword getPropertyKey();

    V getPropertyValueToSet();

    default Object getSerializedPropertyValue() {
        return getPropertyValueToSet();
    }
}
