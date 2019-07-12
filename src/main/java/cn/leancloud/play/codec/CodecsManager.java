package cn.leancloud.play.codec;

import cn.leancloud.play.collection.PlayArray;
import cn.leancloud.play.collection.PlayObject;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;

public final class CodecsManager {
    private static final IdentityHashMap<Class<?>, Codec> registeredCodec = new IdentityHashMap<>();
    private static final IdentityHashMap<Class<?>, Byte> registeredObjectTypeId = new IdentityHashMap<>();

    static {
        CodecsManager.registerCodec(PlayObject.class, new PlayObjectCodec());
        CodecsManager.registerCodec(PlayArray.class, new ListCodec());
        CodecsManager.registerCodec(List.class, new ListCodec());
    }

    public static void registerCodec(Class<?> type, byte objectTypeId, Codec codec) {
        registerCodec(type, codec);
        registeredObjectTypeId.put(type, objectTypeId);
    }

    private static void registerCodec(Class<?> type, Codec codec) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(codec);

        registeredCodec.put(type, codec);
    }

    static Codec getCodec(Class<?> type) {
        return registeredCodec.get(type);
    }

    static Byte getObjectTypeId(Class<?> type){
        return registeredObjectTypeId.get(type);
    }

    public static byte[] serialize(Object obj) {
        Codec codec = getCodec(obj.getClass());
        if (codec != null) {
            return codec.serialize(obj);
        }

        throw new SerializationException(String.format("No codec for class %s", obj.getClass().getName()));
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Codec codec = getCodec(clazz);

        if (codec != null) {
            return codec.deserialize(bytes);
        }

        throw new DeserializationException(String.format("No codec for class %s", clazz.getClass().getName()));
    }
}
