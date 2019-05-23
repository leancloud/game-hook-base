package cn.leancloud.play.collection;

public interface Serializer<T> {
    byte[] serialize();

    T deserialize();
}
