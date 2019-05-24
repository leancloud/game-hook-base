package cn.leancloud.play.utils;

import clojure.lang.Keyword;
import cn.leancloud.play.codec.Codec;
import cn.leancloud.play.codec.CodecsManager;
import cn.leancloud.play.collection.GameMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

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

    public static BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }

        if (value instanceof Map && ((Map) value).size() == 0) {
            return null;
        }

        throw new CastTypeException("can not cast to BigDecimal, value : " + value);
    }

    public static BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if (value instanceof Float || value instanceof Double) {
            return BigInteger.valueOf(((Number) value).longValue());
        }

        throw new CastTypeException("can not cast to BigInteger, value : " + value);
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
            return Base64.getDecoder().decode((String) value);
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

    public static <A extends Annotation> A getAnnotation(Class<?> clazz, Class<A> annotationClass) {
        A a = clazz.getAnnotation(annotationClass);
        if (a != null) {
            return a;
        }

        if (clazz.getAnnotations().length > 0) {
            for (Annotation annotation : clazz.getAnnotations()) {
                a = annotation.annotationType().getAnnotation(annotationClass);
                if (a != null) {
                    return a;
                }
            }
        }
        return null;
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

            if (clazz == GameMap.class) {
                return (T) GameMap.toGameMap((Map) obj);
            }

            // ignore other kind of Map
        }

        if (obj instanceof byte[]) {
            Codec codec = CodecsManager.getInstance().getCodec(clazz);
            if (codec != null) {
                return codec.deserialize((byte[]) obj);
            }
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
        if (clazz == BigDecimal.class) {
            return (T) castToBigDecimal(obj);
        }
        if (clazz == BigInteger.class) {
            return (T) castToBigInteger(obj);
        }

        throw new CastTypeException("can not cast to : " + clazz.getName());
    }
}

