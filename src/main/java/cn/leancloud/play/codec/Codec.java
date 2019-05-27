package cn.leancloud.play.codec;

public interface Codec {
    /**
     * 将一个 Object 序列化为字节数组
     * @param obj 待序列化的对象
     * @return 序列化后的字节数组
     */
    byte[] serialize(Object obj);

    /**
     * 将一个字节数组反序列化为一个对象
     *
     * @param bytes 待反序列化的字节数组
     * @param <T> 序列化后得到的对象类型
     * @return 序列化后得到的对象
     */
    <T> T deserialize(byte[] bytes);
}
