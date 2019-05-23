package cn.leancloud.play.collection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static cn.leancloud.play.collection.TypeUtils.*;

public class GameMap implements Map<String, Object>, Cloneable, Serializable {
    public static final GameMap EMPTY_MAP = new GameMap(Collections.emptyMap());

    private static final long         serialVersionUID         = 1L;
    private static final int          DEFAULT_INITIAL_CAPACITY = 16;


    private final Map<String, Object> map;

    public GameMap(){
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public GameMap(Map<Object, Object> map){
        this.map = toGameMap(map);
    }

    public GameMap(int initialCapacity){
        map = new HashMap<>(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    public static GameMap toGameMap(Map<Object, Object> inputMap) {
        if (inputMap == null) {
            return GameMap.EMPTY_MAP;
        }

        GameMap map = new GameMap(inputMap.size());

        for (Map.Entry<Object, Object> entry : inputMap.entrySet()) {
            Object key = entry.getKey();
            String keyInStr = castToString(key);
            Object v = entry.getValue();
            if (v instanceof Map) {
                v = toGameMap((Map<Object, Object>)v);
            } else if (v instanceof List) {
                v = GameArray.toGameArray((List<Object>)v);
            }

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

    public GameMap fluentPut(String key, Object value) {
        map.put(key, value);
        return this;
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    public GameMap fluentRemove(Object key) {
        map.remove(key);
        return this;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        map.putAll(m);
    }

    public GameMap fluentPutAll(Map<? extends String, ?> m) {
        map.putAll(m);
        return this;
    }

    @Override
    public void clear() {
        map.clear();
    }

    public GameMap fluentClear() {
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
        return new GameMap(new HashMap<>(map));
    }

    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    @SuppressWarnings("unchecked")
    public GameMap getGameMap(String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof GameMap) {
            return (GameMap) value;
        }

        if (value instanceof Map) {
            return new GameMap((Map)value);
        }

        if (value instanceof byte[]) {
            // todo deserialize bytes to GameMap
        }

        throw new SerializationException("can not cast to GameMap, value : '" + value +"'");
    }

    public GameArray getGameArray(String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof GameArray) {
            return (GameArray) value;
        }

        if (value instanceof byte[]) {
            // todo deserialize to GameArray
        }

        throw new SerializationException("can not cast to GameArray, value : '" + value +"'");
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

    public BigDecimal getBigDecimal(String key) {
        Object value = get(key);

        return castToBigDecimal(value);
    }

    public BigInteger getBigInteger(String key) {
        Object value = get(key);

        return castToBigInteger(value);
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
        return "GameMap{" +
                "map=" + map +
                '}';
    }
}
