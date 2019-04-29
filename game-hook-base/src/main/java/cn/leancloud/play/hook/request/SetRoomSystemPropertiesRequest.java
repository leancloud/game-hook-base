package cn.leancloud.play.hook.request;


import clojure.lang.Keyword;
import clojure.lang.PersistentHashMap;
import clojure.lang.RT;

import java.util.*;

public final class SetRoomSystemPropertiesRequest extends AbstractRequest {
    private static final Keyword sysAttrK = (Keyword) RT.keyword(null, "sys-attr");
    private static final Keyword expectSysAttrK = (Keyword) RT.keyword(null, "expect-sys-attr");
    private static final Keyword expectMembersK = (Keyword) RT.keyword(null, "expectMembers");
    private static final Keyword visibleK = (Keyword) RT.keyword(null, "visible");
    private static final Keyword openK = (Keyword) RT.keyword(null, "open");

    public SetRoomSystemPropertiesRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    public Optional<OpenRoomProperty> getOpenRoomProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Boolean valueToSet = (Boolean) oldProps.get(OpenRoomProperty.propertyKey);

        HashMap<Keyword, Object> oldExpectedProps = new HashMap<>(getExpectedValues());
        Boolean expectedValue = (Boolean) oldExpectedProps.get(OpenRoomProperty.propertyKey);

        if (expectedValue != null) {
            assert valueToSet != null;
            return Optional.of(OpenRoomProperty.set(valueToSet, expectedValue));
        } else if (valueToSet != null) {
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

        if (property.getExpectedValue() != null) {
            HashMap<Keyword, Object> oldExpectedProps = new HashMap<>(getExpectedValues());
            oldExpectedProps.put(OpenRoomProperty.propertyKey, property.getExpectedValue());
            setExpectedValues(oldExpectedProps);
        }
        return this;
    }

    public Optional<ExposeRoomProperty> getExposeRoomProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Boolean valueToSet = (Boolean) oldProps.get(ExposeRoomProperty.propertyKey);

        HashMap<Keyword, Object> oldExpectedProps = new HashMap<>(getExpectedValues());
        Boolean expectedValue = (Boolean) oldExpectedProps.get(ExposeRoomProperty.propertyKey);

        if (expectedValue != null) {
            assert valueToSet != null;
            return Optional.of(ExposeRoomProperty.set(valueToSet, expectedValue));
        } else if (valueToSet != null) {
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

        if (property.getExpectedValue() != null) {
            HashMap<Keyword, Object> oldExpectedProps = new HashMap<>(getExpectedValues());
            oldExpectedProps.put(ExposeRoomProperty.propertyKey, property.getExpectedValue());
            setExpectedValues(oldExpectedProps);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public Optional<ExpectedMembersProperty> getExpectedMembersProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        List<String> valueToSet = (List<String>) oldProps.get(ExpectedMembersProperty.propertyKey);

        HashMap<Keyword, Object> oldExpectedProps = new HashMap<>(getExpectedValues());
        List<String> expectedValue = (List<String>) oldExpectedProps.get(ExpectedMembersProperty.propertyKey);

        if (expectedValue != null) {
            assert valueToSet != null;
            return Optional.of(ExpectedMembersProperty.set(valueToSet, expectedValue));
        } else if (valueToSet != null) {
            return Optional.of(ExpectedMembersProperty.set(valueToSet));
        } else {
            return Optional.empty();
        }
    }

    public SetRoomSystemPropertiesRequest setExpectedMembersProperty(ExpectedMembersProperty property) {
        Objects.requireNonNull(property);

        if (property.getValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(ExpectedMembersProperty.propertyKey, property.getValueToSet());
            setProperties(oldProps);
        }

        if (property.getExpectedValue() != null) {
            HashMap<Keyword, Object> oldExpectedProps = new HashMap<>(getExpectedValues());
            oldExpectedProps.put(ExpectedMembersProperty.propertyKey, property.getExpectedValue());
            setExpectedValues(oldExpectedProps);
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

    private Map<Keyword, Object> getExpectedValues() {
        return getParameter(expectSysAttrK, Collections.emptyMap());
    }

    private void setExpectedValues(Map<Keyword, Object> casAttr) {
        Objects.requireNonNull(casAttr);
        if (casAttr.isEmpty()) throw new IllegalArgumentException();

        setParameter(expectSysAttrK, PersistentHashMap.create(casAttr));
    }

    public final static class ExpectedMembersProperty implements RoomSystemProperty<Keyword, List<String>> {
        private static Keyword propertyKey = expectMembersK;
        private final List<String> valueToSet;
        private final List<String> expectedValue;

        private ExpectedMembersProperty(List<String> valueToSet, List<String> expectedValue) {
            this.valueToSet = Collections.unmodifiableList(new ArrayList<>(valueToSet));

            if (expectedValue != null) {
                this.expectedValue = Collections.unmodifiableList(new ArrayList<>(expectedValue));
            } else {
                this.expectedValue = null;
            }
        }

        public static ExpectedMembersProperty set(List<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedMembersProperty(valueToSet, null);
        }

        public static ExpectedMembersProperty set(List<String> valueToSet, List<String> expectedValue) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedMembersProperty(valueToSet, expectedValue);
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public List<String> getValueToSet() {
            return valueToSet;
        }

        @Override
        public List<String> getExpectedValue() {
            return expectedValue;
        }
    }

    public final static class ExposeRoomProperty implements RoomSystemProperty<Keyword, Boolean> {
        private static Keyword propertyKey = visibleK;
        private final Boolean valueToSet;
        private final Boolean expectedValue;

        private ExposeRoomProperty(Boolean valueToSet, Boolean expectedValue) {
            Objects.requireNonNull(valueToSet);

            this.valueToSet = valueToSet;
            this.expectedValue = expectedValue;
        }

        public static ExposeRoomProperty set(Boolean valueToSet) {

            return new ExposeRoomProperty(valueToSet, null);
        }

        public static ExposeRoomProperty set(Boolean valueToSet, Boolean expectedValue) {
            return new ExposeRoomProperty(valueToSet, expectedValue);
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public Boolean getValueToSet() {
            return valueToSet;
        }

        @Override
        public Boolean getExpectedValue() {
            return expectedValue;
        }
    }

    public final static class OpenRoomProperty implements RoomSystemProperty<Keyword, Boolean> {
        private static Keyword propertyKey = openK;
        private final Boolean valueToSet;
        private final Boolean expectedValue;

        private OpenRoomProperty(Boolean valueToSet, Boolean expectedValue) {
            Objects.requireNonNull(valueToSet);

            this.valueToSet = valueToSet;
            this.expectedValue = expectedValue;
        }

        public static OpenRoomProperty set(Boolean valueToSet) {
            return new OpenRoomProperty(valueToSet, null);
        }

        public static OpenRoomProperty set(Boolean valueToSet, Boolean expectedValue) {
            return new OpenRoomProperty(valueToSet, expectedValue);
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public Boolean getValueToSet() {
            return valueToSet;
        }

        @Override
        public Boolean getExpectedValue() {
            return expectedValue;
        }

    }
}