package cn.leancloud.play.collection;

import cn.leancloud.play.utils.CastTypeException;

import java.lang.reflect.Type;
import java.util.IdentityHashMap;
import java.util.Objects;

public class CodecsManager {
    private final IdentityHashMap <Type, Codec> registeredCodec = new IdentityHashMap<>();

    public void registerCode(Type type, Codec codec) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(codec);

        registeredCodec.put(type, codec);
    }

    public byte[] serialize(Object obj) {
        Codec codec = registeredCodec.get(obj.getClass());
        if (codec != null) {
            return codec.serialize(obj);
        }

        throw new CastTypeException();
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz){
        Codec codec = registeredCodec.get(clazz);

        return codec.deserialize(bytes);
    }
}
