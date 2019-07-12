package cn.leancloud.play.codec;

import cn.leancloud.play.collection.PlayArray;
import cn.leancloud.play.collection.PlayObject;
import cn.leancloud.play.proto.GenericCollectionValue;
import com.google.protobuf.ByteString;

final class CollectionThunk {
    private final GenericCollectionValue.Type type;
    private final ByteString byteString;

    CollectionThunk(GenericCollectionValue.Type type, ByteString byteString) {
        this.type = type;
        this.byteString = byteString;
    }

    ByteString getByteString() {
        return byteString;
    }

    GenericCollectionValue.Type getType() {
        return type;
    }

    PlayObject getPlayObject() {
        if (getType() == GenericCollectionValue.Type.MAP) {
            return CodecsManager.deserialize(getByteString().toByteArray(), PlayObject.class);
        } else {
            throw new CastTypeException("can not cast to PlayObject, actual collection type: '" + getType().name() + "'");
        }
    }

    PlayArray getPlayArray() {
        if (getType() == GenericCollectionValue.Type.ARRAY) {
            return CodecsManager.deserialize(getByteString().toByteArray(), PlayArray.class);
        } else {
            throw new CastTypeException("can not cast to PlayArray, actual collection type: '" + getType().name() + "'");
        }
    }
}
