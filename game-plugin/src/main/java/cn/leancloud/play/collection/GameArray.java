package cn.leancloud.play.collection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static cn.leancloud.play.collection.GameMap.toGameMap;
import static cn.leancloud.play.collection.TypeUtils.*;

public class GameArray implements List<Object>, Cloneable, RandomAccess, Serializable {
    public static final GameArray EMPTY_ARRAY = new GameArray(Collections.emptyList());

    private static final long  serialVersionUID = 1L;
    private final List<Object> list;

    public GameArray(){
        this.list = new ArrayList<>();
    }

    public GameArray(List<Object> list){
        this.list = list;
    }

    public GameArray(int initialCapacity){
        this.list = new ArrayList<>(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    public static GameArray toGameArray(List<Object> list) {
        if (list == null) {
            return GameArray.EMPTY_ARRAY;
        }

        GameArray array = new GameArray(list.size());

        for (Object v: list) {
            if (v instanceof Map) {
                v = toGameMap((Map<Object, Object>)v);
            } else if (v instanceof List) {
                v = toGameArray((List<Object>)v);
            }

            array.add(v);
        }

        return array;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<Object> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(Object o) {
        return list.add(o);
    }

    public GameArray fluentAdd(Object o) {
        list.add(o);
        return this;
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    public GameArray fluentRemove(Object o) {
        list.remove(o);
        return this;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<?> c) {
        return list.addAll(c);
    }

    public GameArray fluentAddAll(Collection<?> c) {
        list.addAll(c);
        return this;
    }

    @Override
    public boolean addAll(int index, Collection<?> c) {
        return list.addAll(index, c);
    }

    public GameArray fluentAddAll(int index, Collection<?> c){
        list.addAll(index, c);
        return this;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public GameArray fluentRemoveAll(Collection<?> c) {
        list.removeAll(c);
        return this;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public GameArray fluentRetainAll(Collection<?> c) {
        list.retainAll(c);
        return this;
    }

    @Override
    public void clear() {
        list.clear();
    }

    public GameArray fluentClear() {
        list.clear();
        return this;
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Object> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<Object> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public Object get(int index) {
        return list.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        if (index == -1) {
            list.add(element);
            return null;
        }

        if (list.size() <= index) {
            for (int i = list.size(); i < index; ++i) {
                list.add(null);
            }
            list.add(element);
            return null;
        }

        return list.set(index, element);
    }

    public GameArray fluentSet(int index, Object element) {
        set(index, element);
        return this;
    }

    @Override
    public void add(int index, Object element) {
        list.add(index, element);
    }

    public GameArray fluentAdd(int index, Object element) {
        list.add(index, element);
        return this;
    }

    @Override
    public Object remove(int index) {
        return list.remove(index);
    }

    public GameArray fluentRemove(int index) {
        list.remove(index);
        return this;
    }

    @Override
    public Object clone() {
        return new GameArray(new ArrayList<>(list));
    }

    public boolean equals(Object obj) {
        return this.list.equals(obj);
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    @SuppressWarnings("unchecked")
    public GameMap getGameMap(int index) {
        Object value = list.get(index);
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

    @SuppressWarnings("unchecked")
    public GameArray getGameArray(int index) {
        Object value = list.get(index);

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

//    public <T> T getObject(int index, Class<T> clazz) {
//        Object obj = list.get(index);
//        return TypeUtils.castToJavaBean(obj, clazz);
//    }
//
//    public <T> T getObject(int index, Type type) {
//        Object obj = list.get(index);
//        if (type instanceof Class) {
//            return (T) TypeUtils.castToJavaBean(obj, (Class) type);
//        } else {
//            String json = JSON.toJSONString(obj);
//            return (T) JSON.parseObject(json, type);
//        }
//    }

    public Boolean getBoolean(int index) {
        Object value = get(index);

        if (value == null) {
            return null;
        }

        return castToBoolean(value);
    }

    public boolean getBooleanValue(int index) {
        Object value = get(index);

        if (value == null) {
            return false;
        }

        return castToBoolean(value);
    }

    public Byte getByte(int index) {
        Object value = get(index);

        return castToByte(value);
    }

    public byte getByteValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0;
        }

        return castToByte(value);
    }

    public Short getShort(int index) {
        Object value = get(index);

        return castToShort(value);
    }

    public short getShortValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0;
        }

        return castToShort(value);
    }

    public Integer getInteger(int index) {
        Object value = get(index);

        return castToInt(value);
    }

    public int getIntValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0;
        }

        return castToInt(value);
    }

    public Long getLong(int index) {
        Object value = get(index);

        return castToLong(value);
    }

    public long getLongValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0L;
        }

        return castToLong(value);
    }

    public Float getFloat(int index) {
        Object value = get(index);

        return castToFloat(value);
    }

    public float getFloatValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0F;
        }

        return castToFloat(value);
    }

    public Double getDouble(int index) {
        Object value = get(index);

        return castToDouble(value);
    }

    public double getDoubleValue(int index) {
        Object value = get(index);

        if (value == null) {
            return 0D;
        }

        return castToDouble(value);
    }

    public BigDecimal getBigDecimal(int index) {
        Object value = get(index);

        return castToBigDecimal(value);
    }

    public BigInteger getBigInteger(int index) {
        Object value = get(index);

        return castToBigInteger(value);
    }

    public String getString(int index) {
        Object value = get(index);

        return castToString(value);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> toJavaList(Class<T> clazz) {
        List<T> list = new ArrayList<T>(this.size());

        for (Object item : this) {
            T classItem = (T) TypeUtils.cast(item, clazz);
            list.add(classItem);
        }

        return list;
    }

    public List<Object> getInnerList() {
        return list;
    }

    @Override
    public String toString() {
        return "GameArray{" +
                "list=" + list +
                '}';
    }
}
