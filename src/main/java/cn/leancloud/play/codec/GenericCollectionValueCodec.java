package cn.leancloud.play.codec;

import cn.leancloud.play.collection.GameArray;
import cn.leancloud.play.collection.GameMap;
import cn.leancloud.play.proto.GenericCollectionValue;
import com.google.protobuf.ByteString;

final class GenericCollectionValueCodec {
    static GenericCollectionValue serializeValue(Object inputObject) {
        GenericCollectionValue.Builder v = GenericCollectionValue.newBuilder();

        if (inputObject == null) {
            v.setType(GenericCollectionValue.Type.NULL);
        } else if (inputObject instanceof byte[]) {
            v.setType(GenericCollectionValue.Type.BYTES);
            v.setBytesValue(ByteString.copyFrom((byte[]) inputObject));
        } else if (inputObject instanceof Byte) {
            v.setType(GenericCollectionValue.Type.BYTE);
            v.setIntValue((Byte) inputObject);
        } else if (inputObject instanceof Short) {
            v.setType(GenericCollectionValue.Type.SHORT);
            v.setIntValue((Short) inputObject);
        } else if (inputObject instanceof Integer) {
            v.setType(GenericCollectionValue.Type.INT);
            v.setIntValue((Integer) inputObject);
        } else if (inputObject instanceof Long) {
            v.setType(GenericCollectionValue.Type.LONG);
            v.setLongIntValue((Long) inputObject);
        } else if (inputObject instanceof Boolean) {
            v.setType(GenericCollectionValue.Type.BOOL);
            v.setBoolValue((boolean) inputObject);
        } else if (inputObject instanceof Float) {
            v.setType(GenericCollectionValue.Type.FLOAT);
            v.setFloatValue((Float) inputObject);
        } else if (inputObject instanceof Double) {
            v.setType(GenericCollectionValue.Type.DOUBLE);
            v.setDoubleValue((Double) inputObject);
        } else if (inputObject instanceof String) {
            v.setType(GenericCollectionValue.Type.STRING);
            v.setStringValue((String) inputObject);
        } else if (inputObject instanceof GameMap) {
            v.setType(GenericCollectionValue.Type.MAP);
            v.setBytesValue(ByteString.copyFrom(CodecsManager.getInstance().serialize(inputObject)));
        } else if (inputObject instanceof GameArray) {
            v.setType(GenericCollectionValue.Type.ARRAY);
            v.setBytesValue(ByteString.copyFrom(CodecsManager.getInstance().serialize(inputObject)));
        } else {
            v.setType(GenericCollectionValue.Type.OBJECT);
            v.setBytesValue(ByteString.copyFrom(CodecsManager.getInstance().serialize(inputObject)));
        }

        return v.build();
    }

    static Object deserializeValue(GenericCollectionValue value) {
        switch (value.getType()) {
            case NULL:
                return null;
            case BYTES:
                return value.getBytesValue().toByteArray();
            case BYTE:
                return (byte) value.getIntValue();
            case SHORT:
                return (short) value.getIntValue();
            case INT:
                return value.getIntValue();
            case LONG:
                return value.getLongIntValue();
            case BOOL:
                return value.getBoolValue();
            case FLOAT:
                return value.getFloatValue();
            case DOUBLE:
                return value.getDoubleValue();
            case OBJECT:
                return value.getBytesValue().toByteArray();
            case STRING:
                return value.getStringValue();
            case MAP:
                return CodecsManager.getInstance().deserialize(value.getBytesValue().toByteArray(), GameMap.class);
            case ARRAY:
                return CodecsManager.getInstance().deserialize(value.getBytesValue().toByteArray(), GameArray.class);
            default:
                throw new DeserializationException("Unknown GenericCollectionValue type: " + value.getType());
        }
    }

}
