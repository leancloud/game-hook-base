package cn.leancloud.play.collection;

import cn.leancloud.play.codec.CodecsManager;
import cn.leancloud.play.codec.CastTypeException;
import cn.leancloud.play.codec.CastTypeUtils;

import java.io.Serializable;
import java.util.*;

import static cn.leancloud.play.codec.CastTypeUtils.*;

public final class PlayArray implements List<Object>, Cloneable, RandomAccess, Serializable {
    public static final PlayArray EMPTY_ARRAY = new PlayArray(Collections.emptyList());

    private static final long serialVersionUID = 1L;
    private final List<Object> list;

    public PlayArray() {
        this.list = new ArrayList<>();
    }

    public PlayArray(List<Object> list) {
        if (list == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = list;
        }
    }

    public PlayArray(int initialCapacity) {
        this.list = new ArrayList<>(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    public static PlayArray toPlayArray(List<Object> list) {
        if (list == null) {
            return null;
        }

        return new PlayArray(list);
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

    public PlayArray fluentAdd(Object o) {
        list.add(o);
        return this;
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    public PlayArray fluentRemove(Object o) {
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

    public PlayArray fluentAddAll(Collection<?> c) {
        list.addAll(c);
        return this;
    }

    @Override
    public boolean addAll(int index, Collection<?> c) {
        return list.addAll(index, c);
    }

    public PlayArray fluentAddAll(int index, Collection<?> c) {
        list.addAll(index, c);
        return this;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public PlayArray fluentRemoveAll(Collection<?> c) {
        list.removeAll(c);
        return this;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public PlayArray fluentRetainAll(Collection<?> c) {
        list.retainAll(c);
        return this;
    }

    @Override
    public void clear() {
        list.clear();
    }

    public PlayArray fluentClear() {
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

    public PlayArray fluentSet(int index, Object element) {
        set(index, element);
        return this;
    }

    @Override
    public void add(int index, Object element) {
        list.add(index, element);
    }

    public PlayArray fluentAdd(int index, Object element) {
        list.add(index, element);
        return this;
    }

    @Override
    public Object remove(int index) {
        return list.remove(index);
    }

    public PlayArray fluentRemove(int index) {
        list.remove(index);
        return this;
    }

    @Override
    public Object clone() {
        return new PlayArray(new ArrayList<>(list));
    }

    public boolean equals(Object obj) {
        return this.list.equals(obj);
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    @SuppressWarnings("unchecked")
    public PlayObject getPlayObject(int index) {
        Object value = list.get(index);
        if (value == null) {
            return null;
        }

        if (value instanceof PlayObject) {
            return (PlayObject) value;
        }

        if (value instanceof Map) {
            PlayObject m = PlayObject.toPlayObject((Map) value);
            set(index, m);
            return m;
        }

        if (value instanceof byte[]) {
            PlayObject m = CodecsManager.getInstance().deserialize((byte[])value, PlayObject.class);
            set(index, m);
            return m;
        }

        throw new CastTypeException("can not cast to PlayObject, value : '" + value + "'");
    }

    @SuppressWarnings("unchecked")
    public PlayArray getPlayArray(int index) {
        Object value = list.get(index);

        if (value == null) {
            return null;
        }

        if (value instanceof PlayArray) {
            return (PlayArray) value;
        }

        if (value instanceof List) {
            PlayArray array = toPlayArray(list);
            set(index, array);
            return array;
        }

        if (value instanceof byte[]) {
            PlayArray array = CodecsManager.getInstance().deserialize((byte[])value, PlayArray.class);
            set(index, array);
            return array;
        }

        throw new CastTypeException("can not cast to PlayArray, value : '" + value + "'");
    }

    public <T> T getObject(int index, Class<T> clazz) {
        Object value = list.get(index);

        return CastTypeUtils.cast(value, clazz);
    }

    public Boolean getBoolean(int index) {
        Object value = get(index);

        if (value == null) {
            return null;
        }

        return castToBoolean(value);
    }

    public boolean getBooleanValue(int index) {
        Object value = get(index);

        Boolean boolV = castToBoolean(value);
        if (boolV == null) {
            return false;
        }

        return boolV;
    }

    public Byte getByte(int index) {
        Object value = get(index);

        return castToByte(value);
    }

    public byte getByteValue(int index) {
        Object value = get(index);

        Byte bValue = castToByte(value);
        if (value == null) {
            return 0;
        }

        return bValue;
    }

    public byte[] getBytes(int index) {
        Object value = get(index);

        if (value == null) {
            return null;
        }

        return castToBytes(value);
    }

    public Short getShort(int index) {
        Object value = get(index);

        return castToShort(value);
    }

    public short getShortValue(int index) {
        Object value = get(index);

        Short sValue = castToShort(value);
        if (sValue == null) {
            return 0;
        }

        return sValue;
    }

    public Integer getInteger(int index) {
        Object value = get(index);

        return castToInt(value);
    }

    public int getIntValue(int index) {
        Object value = get(index);

        Integer intValue = castToInt(value);
        if (intValue == null) {
            return 0;
        }

        return intValue;
    }

    public Long getLong(int index) {
        Object value = get(index);

        return castToLong(value);
    }

    public long getLongValue(int index) {
        Object value = get(index);

        Long lVal = castToLong(value);
        if (lVal == null) {
            return 0L;
        }

        return lVal;
    }

    public Float getFloat(int index) {
        Object value = get(index);

        return castToFloat(value);
    }

    public float getFloatValue(int index) {
        Object value = get(index);

        Float fVal = castToFloat(value);
        if (fVal == null) {
            return 0F;
        }

        return fVal;
    }

    public Double getDouble(int index) {
        Object value = get(index);

        return castToDouble(value);
    }

    public double getDoubleValue(int index) {
        Object value = get(index);

        Double dVal = castToDouble(value);
        if (dVal == null) {
            return 0D;
        }

        return dVal;
    }

    public String getString(int index) {
        Object value = get(index);

        return castToString(value);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> toJavaList(Class<T> clazz) {
        List<T> list = new ArrayList<T>(this.size());

        for (Object item : this) {
            T classItem = (T) CastTypeUtils.cast(item, clazz);
            list.add(classItem);
        }

        return list;
    }

    public List<Object> getInnerList() {
        return list;
    }

    @Override
    public String toString() {
        return "PlayArray{" +
                list +
                '}';
    }
}
