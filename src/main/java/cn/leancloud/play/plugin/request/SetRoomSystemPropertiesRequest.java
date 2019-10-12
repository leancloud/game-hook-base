package cn.leancloud.play.plugin.request;


import clojure.lang.Keyword;
import clojure.lang.PersistentHashMap;
import clojure.lang.RT;

import java.util.*;

public final class SetRoomSystemPropertiesRequest extends AbstractRequest {
    private static final Keyword sysAttrK = RT.keyword(null, "sys-attr");

    public SetRoomSystemPropertiesRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    /**
     * 获取请求内是否设置房间开闭属性。房间默认为开放状态，如果房间关闭则表示不能有新用户加入房间。
     *
     * @return 房间开闭属性选项。使用 Optional 是因为请求内可能没有设置房间开闭属性，需要通过判断确认。
     */
    public Optional<OpenRoomProperty> getOpenRoomProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Boolean valueToSet = (Boolean) oldProps.get(OpenRoomProperty.propertyKey);

        if (valueToSet != null) {
            return Optional.of(OpenRoomProperty.set(valueToSet));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 修改本次设置房间系统属性请求，设置房间开闭属性。房间默认为开放状态，如果房间关闭则表示不能有新用户加入房间。
     *
     * @param property 待设置的房间开闭属性，不能为 null
     * @return this
     */
    public SetRoomSystemPropertiesRequest setOpenRoomProperty(OpenRoomProperty property) {
        Objects.requireNonNull(property);

        if (property.getPropertyValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(OpenRoomProperty.propertyKey, property.getSerializedPropertyValue());
            setProperties(oldProps);
        }

        return this;
    }

    /**
     * 获取请求内是否设置房间可见性属性。房间默认为可见状态，如果房间不可见则表示新用户不能通过在大厅自动匹配房间方式加入房间。
     *
     * @return 房间可见性属性选项。使用 Optional 是因为请求内可能没有设置房间可见性属性，需要通过判断确认。
     */
    public Optional<ExposeRoomProperty> getExposeRoomProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Boolean valueToSet = (Boolean) oldProps.get(ExposeRoomProperty.propertyKey);

        if (valueToSet != null) {
            return Optional.of(ExposeRoomProperty.set(valueToSet));
        } else {
            return Optional.empty();
        }
    }

    /**
     * 修改本次设置房间系统属性请求，设置房间可见性属性。房间默认为可见状态，如果房间不可见则表示新用户不能通过在大厅
     * 自动匹配房间方式加入房间。
     *
     * @param property 待设置的房间可见性属性，不能为 null
     * @return this
     */
    public SetRoomSystemPropertiesRequest setExposeRoomProperty(ExposeRoomProperty property) {
        Objects.requireNonNull(property);

        if (property.getPropertyValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(ExposeRoomProperty.propertyKey, property.getSerializedPropertyValue());
            setProperties(oldProps);
        }

        return this;
    }

    /**
     * 获取请求内是否设置邀请好友属性。
     *
     * @return 邀请好友属性选项。使用 Optional 是因为请求内可能没有设置房间邀请好友属性，需要通过判断确认。
     */
    @SuppressWarnings("unchecked")
    public Optional<ExpectedUserIdsProperty> getExpectedUserIdsProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Map<Keyword, Collection<String>> valueToSet = (Map<Keyword, Collection<String>>) oldProps.get(ExpectedUserIdsProperty.propertyKey);

        if (valueToSet != null && !valueToSet.isEmpty()) {
            Map.Entry<Keyword, Collection<String>> entry = valueToSet.entrySet().iterator().next();
            Operator op = Operator.findOperator(entry.getKey());
            ExpectedUserIdsProperty property = new ExpectedUserIdsProperty(op, new HashSet<>(entry.getValue()));
            return Optional.of(property);
        }

        return Optional.empty();
    }

    /**
     * 修改本次设置房间系统属性请求，设置房间邀请好友 ID 列表。
     *
     * @param property 待设置的邀请好友 ID 列表属性，不能为 null
     * @return this
     */
    public SetRoomSystemPropertiesRequest setExpectedUserIdsProperty(ExpectedUserIdsProperty property) {
        Objects.requireNonNull(property);

        if (property.getPropertyValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(ExpectedUserIdsProperty.propertyKey, property.getSerializedPropertyValue());
            setProperties(oldProps);
        }

        return this;
    }

    /**
     * 修改本次设置房间系统属性请求，设置房间最大玩家数量属性。房间内玩家数量达到限制后，新玩家无法再加入本房间。
     *
     * @param property 待设置的房间最大玩家数量属性，不能为 null
     * @return this
     */
    public SetRoomSystemPropertiesRequest setMaxPlayerCountProperty(MaxPlayerCountProperty property) {
        Objects.requireNonNull(property);

        if (property.getPropertyValueToSet() != null) {
            HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
            oldProps.put(MaxPlayerCountProperty.propertyKey, property.getSerializedPropertyValue());
            setProperties(oldProps);
        }

        return this;
    }

    /**
     * 获取请求内是否设置房间最大玩家数量属性。
     *
     * @return 房间可见性属性选项。使用 Optional 是因为请求内可能没有设置房间最大玩家数量属性，需要通过判断确认。
     */
    public Optional<MaxPlayerCountProperty> getMaxPlayerCountProperty() {
        HashMap<Keyword, Object> oldProps = new HashMap<>(getProperties());
        Number valueToSet = (Number) oldProps.get(MaxPlayerCountProperty.propertyKey);

        if (valueToSet != null) {
            return Optional.of(MaxPlayerCountProperty.set(valueToSet.intValue()));
        } else {
            return Optional.empty();
        }
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

    /**
     * 房间邀请好友列表属性。加入邀请好友列表后，相当于帮好友在房间内做了 "占位"，即使房间满了好友也能加入房间。
     * <p>
     * 修改房间邀请好友列表我们支持四种操作，对应四种 {@link Operator}：
     * <p>
     * Operator.ADD 在现有房间邀请好友列表上增加新的待邀请好友。比如房间现有好友 ["a", "b", "c"]，
     * 执行 ADD ["c", "d"] 后，房间邀请好友列表结果为 ["a", "b", "c", "d"]
     * <p>
     * Operator.REMOVE 在现有房间邀请好友列表上删除一组好友 ID。比如房间现有好友 ["a", "b", "c"]，
     * 执行 REMOVE ["c", "d"] 后，房间邀请好友列表结果为 ["a", "b"]
     * <p>
     * Operator.SET 用新的邀请列表替换现有房间邀请好友列表。比如房间现有好友 ["a", "b", "c"]，执行
     * SET ["c", "d"] 后，房间邀请好友列表结果为 ["c", "d"]
     * <p>
     * Operator.DROP 清空现有房间邀请好友列表。比如房间现有好友 ["a", "b", "c"]，执行
     * DROP 后，房间邀请好友列表结果为 [ ]
     */
    public final static class ExpectedUserIdsProperty implements RoomSystemProperty<Set<String>> {
        private static Keyword propertyKey = RT.keyword(null, "expectMembers");
        private final Operator operator;
        private final Set<String> valueToSet;

        private ExpectedUserIdsProperty(Operator op, Set<String> valueToSet) {
            this.operator = op;
            this.valueToSet = Collections.unmodifiableSet(new HashSet<>(valueToSet));
        }

        /**
         * 在现有房间邀请好友列表上增加新的待邀请好友。比如房间现有好友 ["a", "b", "c"]，
         * 参数为 ["c", "d"]，执行后房间邀请好友列表结果为 ["a", "b", "c", "d"]
         *
         * @param valueToSet 待添加新的邀请好友 ID 列表
         * @return this
         */
        public static ExpectedUserIdsProperty add(Set<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedUserIdsProperty(Operator.ADD, valueToSet);
        }

        /**
         * 在现有房间邀请好友列表中删除一组好友 ID。比如房间现有好友 ["a", "b", "c"]，
         * 参数为 ["c", "d"]，执行后房间邀请好友列表结果为 ["a", "b"]
         *
         * @param valueToSet 待删除好友 ID 列表
         * @return this
         */
        public static ExpectedUserIdsProperty remove(Set<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedUserIdsProperty(Operator.REMOVE, valueToSet);
        }

        /**
         * 用新的邀请列表替换现有房间邀请好友列表。比如房间现有好友 ["a", "b", "c"]，
         * 参数为 ["c", "d"]，执行后房间邀请好友列表结果为 ["c", "d"]
         *
         * @param valueToSet 待删除好友 ID 列表
         * @return this
         */
        public static ExpectedUserIdsProperty set(Set<String> valueToSet) {
            Objects.requireNonNull(valueToSet);

            return new ExpectedUserIdsProperty(Operator.SET, valueToSet);
        }

        /**
         * 清空现有房间邀请好友列表。比如房间现有好友 ["a", "b", "c"]，执行后房间邀请好友列
         * 表结果为 [ ]
         *
         * @return this
         */
        public static ExpectedUserIdsProperty drop() {
            return new ExpectedUserIdsProperty(Operator.DROP, Collections.emptySet());
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public Set<String> getPropertyValueToSet() {
            return valueToSet;
        }

        /**
         * 获取邀请好友列表操作数，可以是 ADD，REMOVE，SET，DROP 之一。
         *
         * @return 邀请好友列表操作数
         */
        public Operator getOperator() {
            return operator;
        }

        @Override
        public Object getSerializedPropertyValue() {
            Map<Keyword, Set<String>> m = new HashMap<>();
            m.put(operator.key, valueToSet);
            return m;
        }

        @Override
        public String toString() {
            return "operator=" + getOperator().name() +
                    ", expectedUserIds=" + valueToSet;
        }
    }

    /**
     * 房间可见性属性。房间默认为可见状态，如果房间不可见则表示新用户不能通过在大厅自动匹配房间方式加入房间。
     */
    public final static class ExposeRoomProperty implements RoomSystemProperty<Boolean> {
        private static Keyword propertyKey = RT.keyword(null, "visible");
        private final boolean valueToSet;

        private ExposeRoomProperty(Boolean valueToSet) {
            Objects.requireNonNull(valueToSet);

            this.valueToSet = valueToSet;
        }

        /**
         * 设置房间可见性属性。
         *
         * @param valueToSet true 表示房间在大厅可见，false 表示房间在大厅不可见
         * @return this
         */
        public static ExposeRoomProperty set(boolean valueToSet) {

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

        @Override
        public String toString() {
            return "visible=" + valueToSet;
        }
    }

    /**
     * 房间开闭属性。房间默认为开放状态，如果房间关闭则表示不能有新用户加入房间。
     */
    public final static class OpenRoomProperty implements RoomSystemProperty<Boolean> {
        private static Keyword propertyKey = RT.keyword(null, "open");
        private final boolean valueToSet;

        private OpenRoomProperty(Boolean valueToSet) {
            Objects.requireNonNull(valueToSet);

            this.valueToSet = valueToSet;
        }

        /**
         * 设置房间开闭属性。
         *
         * @param valueToSet true 表示开放房间，false 表示关闭房间
         * @return this
         */
        public static OpenRoomProperty set(boolean valueToSet) {
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

        @Override
        public String toString() {
            return "openRoom=" + valueToSet;
        }
    }

    /**
     * 房间最大玩家数量属性。房间玩家数量达到上限后则房间无法再加入新玩家。
     */
    public final static class MaxPlayerCountProperty implements RoomSystemProperty<Integer> {
        private static Keyword propertyKey = RT.keyword(null, "maxMembers");
        private final int valueToSet;

        private MaxPlayerCountProperty(int valueToSet) {
            this.valueToSet = valueToSet;
        }

        /**
         * 设置房间最大玩家数量属性。
         *
         * @param playerCount 待设置的最大玩家数量
         * @return this
         */
        public static MaxPlayerCountProperty set(int playerCount) {
            return new MaxPlayerCountProperty(playerCount);
        }

        @Override
        public Keyword getPropertyKey() {
            return propertyKey;
        }

        @Override
        public Integer getPropertyValueToSet() {
            return valueToSet;
        }

        @Override
        public String toString() {
            return "maxPlayerCount=" + valueToSet;
        }
    }

    @Override
    public String toString() {
        final StringBuilder propertyStrBuilder = new StringBuilder();
        for (Optional<? extends RoomSystemProperty<?>> property :
                Arrays.asList(
                        getOpenRoomProperty(),
                        getExposeRoomProperty(),
                        getExpectedUserIdsProperty(),
                        getMaxPlayerCountProperty()
                )) {
            if (property.isPresent()) {
                propertyStrBuilder.append(", ");
                propertyStrBuilder.append(property.get());
            }
        }

        return "SetRoomSystemPropertiesRequest{" +
                "roomName=" + getRoomName() +
                ", userId=" + getUserId() +
                propertyStrBuilder.toString() +
                "}";
    }
}