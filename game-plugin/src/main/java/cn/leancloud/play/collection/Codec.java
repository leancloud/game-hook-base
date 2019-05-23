package cn.leancloud.play.collection;

public interface Codec {
    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes);
}
