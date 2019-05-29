package cn.leancloud.play.codec;

import cn.leancloud.play.collection.GameArray;
import cn.leancloud.play.collection.GameMap;

import java.util.IdentityHashMap;
import java.util.Objects;

public final class CodecsManager {
    private static final CodecsManager instance = new CodecsManager();
    public static CodecsManager getInstance() {
        return instance;
    }

    private final IdentityHashMap<Class<?>, Codec> registeredCodec = new IdentityHashMap<>();

    static {
        CodecsManager.getInstance().registerCodec(GameMap.class, new GameMapCodec());
        CodecsManager.getInstance().registerCodec(GameArray.class, new GameArrayCodec());
    }

    public void registerCodec(Class<?> type, Codec codec) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(codec);

        registeredCodec.put(type, codec);
    }

    public Codec getCodec(Class<?> type) {
        return registeredCodec.get(type);
    }

    public byte[] serialize(Object obj) {
        Codec codec = registeredCodec.get(obj.getClass());
        if (codec != null) {
            return codec.serialize(obj);
        }

        throw new SerializationException(String.format("No codec for class %s", obj.getClass().getName()));
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Codec codec = registeredCodec.get(clazz);

        if (codec != null) {
            return codec.deserialize(bytes);
        }

        throw new DeserializationException(String.format("No codec for class %s", clazz.getClass().getName()));
    }
}
