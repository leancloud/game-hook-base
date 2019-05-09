package cn.leancloud.play.hook.request;


import clojure.lang.Keyword;
import clojure.lang.PersistentHashMap;
import clojure.lang.RT;

import java.util.*;

public final class SetRoomSystemPropertiesRequest extends AbstractRequest {
    private static final Keyword sysAttrK = (Keyword) RT.keyword(null, "sys-attr");

    public SetRoomSystemPropertiesRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    public Optional<OpenRoomProperty> getOpenRoomProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Boolean valueToSet = (Boolean) oldProps.get(OpenRoomProperty.propertyKey);

        if (valueToSet != null) {
            return Optional.of(OpenRoomProperty.set(valueToSet));
        } else {
            return Optional.empty();
        }
    }

    public SetRoomSystemPropertiesRequest setOpenRoomProperty(OpenRoomProperty property) {
        Objects.requireNonNull(property);

        if (property.getValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(OpenRoomProperty.propertyKey, property.getValueToSet());
            setProperties(oldProps);
        }

        return this;
    }

    public Optional<ExposeRoomProperty> getExposeRoomProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Boolean valueToSet = (Boolean) oldProps.get(ExposeRoomProperty.propertyKey);

        if (valueToSet != null) {
            return Optional.of(ExposeRoomProperty.set(valueToSet));
        } else {
            return Optional.empty();
        }
    }

    public SetRoomSystemPropertiesRequest setExposeRoomProperty(ExposeRoomProperty property) {
        Objects.requireNonNull(property);

        if (property.getValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(ExposeRoomProperty.propertyKey, property.getValueToSet());
            setProperties(oldProps);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    public Optional<ExpectedMembersProperty> getExpectedMembersProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Map<Keyword, List<String>> valueToSet = (Map<Keyword, List<String>>) oldProps.get(ExpectedMembersProperty.propertyKey);

        if (valueToSet != null && !valueToSet.isEmpty()) {
            Map.Entry<Keyword,List<String>> entry = valueToSet.entrySet().iterator().next();
            Operator op = Operator.findOperator(entry.getKey());
            ExpectedMembersProperty property = new ExpectedMembersProperty(op, entry.getValue());
            return Optional.of(property);
        }

        return Optional.empty();
    }

    public SetRoomSystemPropertiesRequest setExpectedMembersProperty(ExpectedMembersProperty property) {
        Objects.requireNonNull(property);

        if (property.getValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            Map<Keyword, List<String>> valueToSet = new HashMap<>();
            ValueWithOperator<List<String>> expectMembers = property.getValueToSet();
            valueToSet.put(expectMembers.op.key, expectMembers.value);
            oldProps.put(ExpectedMembersProperty.propertyKey, valueToSet);
            setProperties(oldProps);
        }

        return this;
    }

    private void setProperties(Map<Keyword, Object> attr) {
        Objects.requireNonNull(attr);
        if (attr.isEmpty()) throw new IllegalArgumentException();

        setParameter(sysAttrK, PersistentHashMap.create(attr));
    }

    private Map<Keyword, Object> getProperties() {
        return getParameter(sysAttrK, Collections.emptyMap());
    }

    public enum Operator {
        ADD(Operator.addK),
        REMOVE(Operator.removeK),
        SET(Operator.setK),
        DROP(Operator.dropK);

        private static Keyword addK = (Keyword) RT.keyword(null, "$add");
        private static Keyword removeK = (Keyword) RT.keyword(null, "$remove");
        private static Keyword setK = (Keyword) RT.keyword(null, "$set");
        private static Keyword dropK = (Keyword) RT.keyword(null, "$drop");

        private final Keyword key;
        Operator(Keyword k) {
            this.key = k;
        }

        static Operator findOperator(Keyword k) {
            for (Operator op : Operator.values()) {
                if (op.key == k) {
                    return op;
                }
            }

            throw new IllegalArgumentException("No operator for keyword: " + k);
        }
    }

    public final static class ValueWithOperator<V> {
        private final Operator op;
        private final V value;

        ValueWithOperator(Operator op, V value) {
            this.op = op;
            this.value = value;
        }

        public Operator getOperator() {
            return op;
        }

        public V getValue() {
            return value;
        }

        public static <V> ValueWithOperator add(V valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ValueWithOperator<>(Operator.ADD, valueToSet);
        }

        public static <V> ValueWithOperator remove(V valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ValueWithOperator<>(Operator.REMOVE, valueToSet);
        }

        public static <V> ValueWithOperator set(V valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ValueWithOperator<>(Operator.SET, valueToSet);
        }

        public static <V> ValueWithOperator drop() {
            return new ValueWithOperator<>(Operator.DROP, null);
        }
    }

    public final static class ExpectedMembersProperty implements RoomSystemProperty<Keyword, ValueWithOperator<List<String>>> {
        private static Keyword propertyKey = (Keyword) RT.keyword(null, "expectMembers");
        private final ValueWithOperator<List<String>> valueToSet;

        private ExpectedMembersProperty(Operator op, List<String> valueToSet) {
            this.valueToSet = new ValueWithOperator<>(op, Collections.unmodifiableList(new ArrayList<>(valueToSet)));
        }

        public static ExpectedMembersProperty add(List<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedMembersProperty(Operator.ADD, valueToSet);
        }

        public static ExpectedMembersProperty remove(List<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedMembersProperty(Operator.REMOVE, valueToSet);
        }

        public static ExpectedMembersProperty set(List<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedMembersProperty(Operator.SET, valueToSet);
        }

        public static ExpectedMembersProperty drop() {
            return new ExpectedMembersProperty(Operator.DROP, Collections.emptyList());
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public ValueWithOperator<List<String>> getValueToSet() {
            return valueToSet;
        }
    }

    public final static class ExposeRoomProperty implements RoomSystemProperty<Keyword, Boolean> {
        private static Keyword propertyKey = (Keyword) RT.keyword(null, "visible");
        private final Boolean valueToSet;

        private ExposeRoomProperty(Boolean valueToSet) {
            Objects.requireNonNull(valueToSet);

            this.valueToSet = valueToSet;
        }

        public static ExposeRoomProperty set(Boolean valueToSet) {

            return new ExposeRoomProperty(valueToSet);
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public Boolean getValueToSet() {
            return valueToSet;
        }
    }

    public final static class OpenRoomProperty implements RoomSystemProperty<Keyword, Boolean> {
        private static Keyword propertyKey = (Keyword) RT.keyword(null, "open");
        private final Boolean valueToSet;

        private OpenRoomProperty(Boolean valueToSet) {
            Objects.requireNonNull(valueToSet);

            this.valueToSet = valueToSet;
        }

        public static OpenRoomProperty set(Boolean valueToSet) {
            return new OpenRoomProperty(valueToSet);
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public Boolean getValueToSet() {
            return valueToSet;
        }
    }
}