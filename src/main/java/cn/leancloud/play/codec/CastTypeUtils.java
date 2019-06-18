package cn.leancloud.play.codec;

import clojure.lang.Keyword;
import cn.leancloud.play.collection.PlayArray;
import cn.leancloud.play.collection.PlayObject;
import cn.leancloud.play.proto.GenericCollectionValue;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Base64.getDecoder;

public class CastTypeUtils {

    public static String castToString(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Keyword) {
            return ((Keyword) value).getName();
        }

        return value.toString();
    }

    public static Byte castToByte(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).byteValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            return Byte.parseByte(strVal);
        }
        throw new CastTypeException("can not cast to byte, value : " + value);
    }

    public static Character castToChar(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        }

        throw new CastTypeException("can not cast to char, value : " + value);
    }

    public static Short castToShort(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).shortValue();
        }

        throw new CastTypeException("can not cast to short, value : " + value);
    }

    public static Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }

        throw new CastTypeException("can not cast to float, value : " + value);
    }

    public static Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        throw new CastTypeException("can not cast to double, value : " + value);
    }

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch == '+' || ch == '-') {
                if (i != 0) {
                    return false;
                }
            } else if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    public static Long castToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        throw new CastTypeException("can not cast to long, value : " + value);
    }

    public static Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        throw new CastTypeException("can not cast to int, value : " + value);
    }

    public static byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        if (value instanceof String) {
            return getDecoder().decode((String) value);
        }

        throw new CastTypeException("can not cast to int, value : " + value);
    }

    public static Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue() == 1;
        }

        throw new CastTypeException("can not cast to boolean, value : " + value);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static PlayObject castToPlayObject(Object value) {
        if (value instanceof PlayObject) {
            return (PlayObject) value;
        }

        if (value instanceof Map) {
            return PlayObject.toPlayObject((Map) value);
        }

        if (value instanceof CollectionThunk) {
            CollectionThunk thunk = (CollectionThunk)value;
            return thunk.getPlayObject();
        }

        if (value instanceof byte[]) {
            return CodecsManager.getInstance().deserialize((byte[])value, PlayObject.class);
        }

        throw new CastTypeException("can not cast to PlayObject, value : '" + value + "'");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static PlayArray castToPlayArray(Object value){
        if (value instanceof PlayArray) {
            return (PlayArray) value;
        }

        if (value instanceof List) {
            return PlayArray.toPlayArray((List)value);
        }

        if (value instanceof CollectionThunk) {
            CollectionThunk thunk = (CollectionThunk)value;
            return thunk.getPlayArray();
        }

        if (value instanceof byte[]) {
            return CodecsManager.getInstance().deserialize((byte[])value, PlayArray.class);
        }

        throw new CastTypeException("can not cast to PlayArray, value : '" + value + "'");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T cast(Object obj, Class<T> clazz) {
        Objects.requireNonNull(clazz);

        if (obj == null) {
            if (clazz == int.class) {
                return (T) Integer.valueOf(0);
            } else if (clazz == long.class) {
                return (T) Long.valueOf(0);
            } else if (clazz == short.class) {
                return (T) Short.valueOf((short) 0);
            } else if (clazz == byte.class) {
                return (T) Byte.valueOf((byte) 0);
            } else if (clazz == float.class) {
                return (T) Float.valueOf(0);
            } else if (clazz == double.class) {
                return (T) Double.valueOf(0);
            } else if (clazz == boolean.class) {
                return (T) Boolean.FALSE;
            }
            return null;
        }

        if (clazz == obj.getClass()) {
            return (T) obj;
        }
        if (obj instanceof Map) {
            if (clazz == Map.class) {
                return (T) obj;
            }

            if (clazz == PlayObject.class) {
                return (T) PlayObject.toPlayObject((Map) obj);
            }

            // ignore other kind of Map
        }

        if (obj instanceof List) {
            if (clazz == List.class) {
                return (T) obj;
            }

            if (clazz == PlayArray.class) {
                return (T) PlayArray.toPlayArray((List) obj);
            }

            // ignore other kind of List
        }

        if (clazz.isArray()) {
            if (obj instanceof Collection) {
                Collection collection = (Collection) obj;
                int index = 0;
                Object array = Array.newInstance(clazz.getComponentType(), collection.size());
                for (Object item : collection) {
                    Object value = cast(item, clazz.getComponentType());
                    Array.set(array, index, value);
                    index++;
                }
                return (T) array;
            }
            if (clazz == byte[].class) {
                return (T) castToBytes(obj);
            }
        }

        if (clazz.isAssignableFrom(obj.getClass())) {
            return (T) obj;
        }

        Codec codec = CodecsManager.getInstance().getCodec(clazz);
        if (codec != null) {
            if (obj instanceof ObjectThunk) {
                ObjectThunk thunk = (ObjectThunk)obj;
                if (CodecsManager.getInstance().getObjectTypeId(clazz) == thunk.getObjectTypeId()) {
                    return thunk.resolve(codec);
                }
            }
        }

        if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) castToBoolean(obj);
        }
        if (clazz == byte.class || clazz == Byte.class) {
            return (T) castToByte(obj);
        }
        if (clazz == char.class || clazz == Character.class) {
            return (T) castToChar(obj);
        }
        if (clazz == short.class || clazz == Short.class) {
            return (T) castToShort(obj);
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) castToInt(obj);
        }
        if (clazz == long.class || clazz == Long.class) {
            return (T) castToLong(obj);
        }
        if (clazz == float.class || clazz == Float.class) {
            return (T) castToFloat(obj);
        }
        if (clazz == double.class || clazz == Double.class) {
            return (T) castToDouble(obj);
        }
        if (clazz == String.class) {
            return (T) castToString(obj);
        }

        throw new CastTypeException("can not cast to: " + clazz.getName());
    }
}

