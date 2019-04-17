package cn.leancloud.play.hook.request;

public interface RoomSystemProperty<K, V> {
    K getPropertyKey();

    V getValueToSet();

    V getExpectedValue();
}
