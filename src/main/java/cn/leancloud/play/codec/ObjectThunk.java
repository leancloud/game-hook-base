package cn.leancloud.play.codec;

import com.google.protobuf.ByteString;

import java.util.Objects;

final class ObjectThunk {
    private final byte objectTypeId;
    private final ByteString originalObjectInByteString;
    private Object resolvedObject;

    ObjectThunk(byte objectTypeId, ByteString originalObjectInByteString) {
        this.objectTypeId = objectTypeId;
        this.originalObjectInByteString = originalObjectInByteString;
    }

    byte getObjectTypeId() {
        return objectTypeId;
    }

    ByteString getObjectInByteString() {
        if (resolvedObject == null) {
            return originalObjectInByteString;
        } else {
            return ByteString.copyFrom(CodecsManager.serialize(resolvedObject));
        }
    }

    @SuppressWarnings("unchecked")
    <T> T resolve(Codec codec) {
        if (resolvedObject == null) {
            resolvedObject = codec.deserialize(originalObjectInByteString.toByteArray());
        }

        return (T) resolvedObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectThunk that = (ObjectThunk) o;
        if (resolvedObject != null && that.resolvedObject != null) return resolvedObject.equals(that.resolvedObject);
        return getObjectTypeId() == that.getObjectTypeId() &&
                Objects.equals(originalObjectInByteString, that.originalObjectInByteString);
    }

    @Override
    public int hashCode() {
        if (resolvedObject != null) {
            return resolvedObject.hashCode();
        }

        return Objects.hash(getObjectTypeId(), originalObjectInByteString);
    }
}
