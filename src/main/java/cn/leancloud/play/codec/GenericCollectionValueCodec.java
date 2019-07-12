package cn.leancloud.play.codec;

import cn.leancloud.play.collection.PlayArray;
import cn.leancloud.play.collection.PlayObject;
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
        } else if (inputObject instanceof PlayObject) {
            v.setType(GenericCollectionValue.Type.MAP);
            v.setBytesValue(ByteString.copyFrom(CodecsManager.serialize(inputObject)));
        } else if (inputObject instanceof PlayArray) {
            v.setType(GenericCollectionValue.Type.ARRAY);
            v.setBytesValue(ByteString.copyFrom(CodecsManager.serialize(inputObject)));
        } else if (inputObject instanceof CollectionThunk){
            CollectionThunk thunk = (CollectionThunk)inputObject;
            v.setType(thunk.getType());
            v.setBytesValue(thunk.getByteString());
        } else if (inputObject instanceof ObjectThunk) {
            v.setType(GenericCollectionValue.Type.OBJECT);
            ObjectThunk thunk = (ObjectThunk)inputObject;
            v.setBytesValue(thunk.getObjectInByteString());
            v.setObjectTypeId(thunk.getObjectTypeId());
        } else {
            v.setType(GenericCollectionValue.Type.OBJECT);
            v.setBytesValue(ByteString.copyFrom(CodecsManager.serialize(inputObject)));
            v.setObjectTypeId(CodecsManager.getObjectTypeId(inputObject.getClass()));
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
            case STRING:
                return value.getStringValue();
            case OBJECT:
                return new ObjectThunk((byte)value.getObjectTypeId(), value.getBytesValue());
            case MAP:
                return new CollectionThunk(GenericCollectionValue.Type.MAP, value.getBytesValue());
            case ARRAY:
                return new CollectionThunk(GenericCollectionValue.Type.ARRAY, value.getBytesValue());
            default:
                throw new DeserializationException("Unknown GenericCollectionValue type: " + value.getType());
        }
    }

}
