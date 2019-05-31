package cn.leancloud.play.codec;

import cn.leancloud.play.collection.PlayArray;
import cn.leancloud.play.proto.GenericCollection;
import cn.leancloud.play.proto.GenericCollectionValue;
import com.google.protobuf.InvalidProtocolBufferException;

import static cn.leancloud.play.codec.GenericCollectionValueCodec.deserializeValue;
import static cn.leancloud.play.codec.GenericCollectionValueCodec.serializeValue;

public final class PlayArrayCodec implements Codec {
    @Override
    public byte[] serialize(Object obj) {
        PlayArray array = (PlayArray) obj;

        GenericCollection.Builder collection = GenericCollection.newBuilder();
        for (Object element: array) {
            collection.addListValue(serializeValue(element));
        }

        return collection.build().toByteArray();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] bytes) {
        try {
            PlayArray m = new PlayArray();
            GenericCollection collection = GenericCollection.parseFrom(bytes);
            for (GenericCollectionValue v : collection.getListValueList()) {
                m.add(deserializeValue(v));
            }
            return (T) m;
        } catch (InvalidProtocolBufferException ex) {
            throw new DeserializationException("Can't deserialize bytes to PlayArray", ex);
        }
    }
}
