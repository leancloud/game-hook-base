package cn.leancloud.play.collection;

import cn.leancloud.play.codec.*;

import java.io.Serializable;
import java.util.*;

import static cn.leancloud.play.codec.CastTypeUtils.*;

public final class PlayObject implements Map<String, Object>, Cloneable, Serializable {
    public static final PlayObject EMPTY_OBJECT = new PlayObject(Collections.emptyMap());

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;


    private final Map<String, Object> map;

    public PlayObject() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public PlayObject(Map<Object, Object> map) {
        if (map == null) {
            this.map = new PlayObject();
        } else {
            this.map = toPlayObject(map);
        }
    }

    public PlayObject(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    public static PlayObject toPlayObject(Map<Object, Object> inputMap) {
        if (inputMap == null) {
            return null;
        }

        PlayObject map = new PlayObject(inputMap.size());

        for (Map.Entry<Object, Object> entry : inputMap.entrySet()) {
            Object key = entry.getKey();
            String keyInStr = castToString(key);
            Object v = entry.getValue();

            map.put(keyInStr, v);
        }

        return map;
    }


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public PlayObject fluentPut(String key, Object value) {
        map.put(key, value);
        return this;
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    public PlayObject fluentRemove(Object key) {
        map.remove(key);
        return this;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        map.putAll(m);
    }

    public PlayObject fluentPutAll(Map<? extends String, ?> m) {
        map.putAll(m);
        return this;
    }

    @Override
    public void clear() {
        map.clear();
    }

    public PlayObject fluentClear() {
        map.clear();
        return this;
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Object clone() {
        return new PlayObject(new HashMap<>(map));
    }

    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    @SuppressWarnings("unchecked")
    public PlayObject getPlayObject(String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof PlayObject) {
            return (PlayObject) value;
        }

        if (value instanceof Map) {
            PlayObject m = toPlayObject((Map) value);
            put(key, m);
            return m;
        }

        if (value instanceof byte[]) {
            PlayObject m = CodecsManager.getInstance().deserialize((byte[])value, PlayObject.class);
            put(key, m);
            return m;
        }

        throw new CastTypeException("can not cast to PlayObject, value : '" + value + "'");
    }

    @SuppressWarnings("unchecked")
    public PlayArray getPlayArray(String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof PlayArray) {
            return (PlayArray) value;
        }

        if (value instanceof List) {
            PlayArray array = PlayArray.toPlayArray((List) value);
            put(key, array);
            return array;
        }

        if (value instanceof byte[]) {
            PlayArray array = CodecsManager.getInstance().deserialize((byte[])value, PlayArray.class);
            put(key, array);
            return array;
        }

        throw new CastTypeException("can not cast to PlayArray, value : '" + value + "'");
    }

    public <T> T getObject(String key, Class<T> clazz) {
        Object obj = map.get(key);
        return CastTypeUtils.cast(obj, clazz);
    }

    public Boolean getBoolean(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return castToBoolean(value);
    }

    public byte[] getBytes(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return castToBytes(value);
    }

    public boolean getBooleanValue(String key) {
        Object value = get(key);

        Boolean booleanVal = castToBoolean(value);
        if (booleanVal == null) {
            return false;
        }

        return booleanVal;
    }

    public Byte getByte(String key) {
        Object value = get(key);

        return castToByte(value);
    }

    public byte getByteValue(String key) {
        Object value = get(key);

        Byte byteVal = castToByte(value);
        if (byteVal == null) {
            return 0;
        }

        return byteVal;
    }

    public Short getShort(String key) {
        Object value = get(key);

        return castToShort(value);
    }

    public short getShortValue(String key) {
        Object value = get(key);

        Short shortVal = castToShort(value);
        if (shortVal == null) {
            return 0;
        }

        return shortVal;
    }

    public Integer getInteger(String key) {
        Object value = get(key);

        return castToInt(value);
    }

    public int getIntValue(String key) {
        Object value = get(key);

        Integer intVal = castToInt(value);
        if (intVal == null) {
            return 0;
        }

        return intVal;
    }

    public Long getLong(String key) {
        Object value = get(key);

        return castToLong(value);
    }

    public long getLongValue(String key) {
        Object value = get(key);

        Long longVal = castToLong(value);
        if (longVal == null) {
            return 0L;
        }

        return longVal;
    }

    public Float getFloat(String key) {
        Object value = get(key);

        return castToFloat(value);
    }

    public float getFloatValue(String key) {
        Object value = get(key);

        Float floatValue = castToFloat(value);
        if (floatValue == null) {
            return 0F;
        }

        return floatValue;
    }

    public Double getDouble(String key) {
        Object value = get(key);

        return castToDouble(value);
    }

    public double getDoubleValue(String key) {
        Object value = get(key);

        Double doubleValue = castToDouble(value);
        if (doubleValue == null) {
            return 0D;
        }

        return doubleValue;
    }

    public String getString(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public Map<String, Object> getInnerMap() {
        return map;
    }

    @Override
    public String toString() {
        return "PlayObject{" + map + '}';
    }
}
