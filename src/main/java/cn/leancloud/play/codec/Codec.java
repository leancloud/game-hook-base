package cn.leancloud.play.codec;

public interface Codec {
    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes);
}
