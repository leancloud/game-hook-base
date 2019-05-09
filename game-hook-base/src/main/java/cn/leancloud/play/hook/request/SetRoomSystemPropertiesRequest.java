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

        if (property.getPropertyValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(OpenRoomProperty.propertyKey, property.getSerializedPropertyValue());
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

        if (property.getPropertyValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(ExposeRoomProperty.propertyKey, property.getSerializedPropertyValue());
            setProperties(oldProps);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    public Optional<ExpectedMembersProperty> getExpectedMembersProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Map<Keyword, Collection<String>> valueToSet = (Map<Keyword, Collection<String>>) oldProps.get(ExpectedMembersProperty.propertyKey);

        if (valueToSet != null && !valueToSet.isEmpty()) {
            Map.Entry<Keyword, Collection<String>> entry = valueToSet.entrySet().iterator().next();
            Operator op = Operator.findOperator(entry.getKey());
            ExpectedMembersProperty property = new ExpectedMembersProperty(op, new HashSet<>(entry.getValue()));
            return Optional.of(property);
        }

        return Optional.empty();
    }

    public SetRoomSystemPropertiesRequest setExpectedMembersProperty(ExpectedMembersProperty property) {
        Objects.requireNonNull(property);

        if (property.getPropertyValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(ExpectedMembersProperty.propertyKey, property.getSerializedPropertyValue());
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
        ADD(RT.keyword(null, "$add")),
        REMOVE(RT.keyword(null, "$remove")),
        SET(RT.keyword(null, "$set")),
        DROP(RT.keyword(null, "$drop"));

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

    public final static class ExpectedMembersProperty implements RoomSystemProperty<Set<String>> {
        private static Keyword propertyKey = (Keyword) RT.keyword(null, "expectMembers");
        private final Operator operator;
        private final Set<String> valueToSet;

        private ExpectedMembersProperty(Operator op, Set<String> valueToSet) {
            this.operator = op;
            this.valueToSet = Collections.unmodifiableSet(new HashSet<>(valueToSet));
        }

        public static ExpectedMembersProperty add(Set<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedMembersProperty(Operator.ADD, valueToSet);
        }

        public static ExpectedMembersProperty remove(Set<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedMembersProperty(Operator.REMOVE, valueToSet);
        }

        public static ExpectedMembersProperty set(Set<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedMembersProperty(Operator.SET, valueToSet);
        }

        public static ExpectedMembersProperty drop() {
            return new ExpectedMembersProperty(Operator.DROP, Collections.emptySet());
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public Set<String> getPropertyValueToSet() {
            return valueToSet;
        }

        public Operator getOperator() {
            return operator;
        }

        @Override
        public Object getSerializedPropertyValue() {
            Map<Keyword, Set<String>> m = new HashMap<>();
            m.put(operator.key, valueToSet);
            return m;
        }
    }

    public final static class ExposeRoomProperty implements RoomSystemProperty<Boolean> {
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
        public Boolean getPropertyValueToSet() {
            return valueToSet;
        }
    }

    public final static class OpenRoomProperty implements RoomSystemProperty<Boolean> {
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
        public Boolean getPropertyValueToSet() {
            return valueToSet;
        }
    }
}