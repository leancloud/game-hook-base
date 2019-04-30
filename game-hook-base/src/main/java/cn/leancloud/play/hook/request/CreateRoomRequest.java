package cn.leancloud.play.hook.request;

import clojure.lang.Keyword;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentVector;
import clojure.lang.RT;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class CreateRoomRequest extends AbstractRequest {
    private static final Keyword maxMembersK = (Keyword) RT.keyword(null, "max-members");
    private static final Keyword emptyRoomTtlK = (Keyword) RT.keyword(null, "empty-room-ttl");
    private static final Keyword playerTtlK = (Keyword) RT.keyword(null, "player-ttl");
    private static final Keyword expectMK = (Keyword) RT.keyword(null, "expect-m");
    private static final Keyword isVisibleK = (Keyword) RT.keyword(null, "visible?");
    private static final Keyword lobbyKeysK = (Keyword) RT.keyword(null, "lobby-keys");
    private static final Keyword attrK = (Keyword) RT.keyword(null, "attr");

    public CreateRoomRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    /**
     * 获取最大房间人数参数
     *
     * @return 最大房间人数
     */
    public int getMaxPlayerCount() {
        return getParameter(maxMembersK, 10);
    }

    /**
     * 设置最大房间人数
     *
     * @param max 不能小于 0，如果设置的过大超过了 Game Server 的允许限制会被限制为 Game Server 最大允许值
     * @return this
     */
    public CreateRoomRequest setMaxPlayerCount(int max) {
        if (max <= 0) throw new IllegalArgumentException();
        setParameter(maxMembersK, max);
        return this;
    }

    /**
     * 获取空房间保留时间，默认为 0 表示房间内玩家全部离开后，房间会立即被销毁
     *
     * @return 返回空房间保留时间
     */
    public int getEmptyRoomTtlSecs() {
        return getParameter(emptyRoomTtlK, 0);
    }

    /**
     * 设置空房间保留时间，默认为 0 表示房间内玩家全部离开后，房间会立即被销毁
     *
     * @param ttl  空房间保留时间，不能小于 0，如果设置的过大超过了 Game Server 的允许限制会被限制
     *             为 Game Server 最大允许值
     * @param unit 空房间保留时间单位
     * @return this
     */
    public CreateRoomRequest setEmptyRoomTtl(long ttl, TimeUnit unit) {
        if (ttl <= 0) throw new IllegalArgumentException();
        setParameter(emptyRoomTtlK, (int) unit.toSeconds(ttl));
        return this;
    }

    /**
     * 获取玩家允许的最大离线时间，默认为 0 表示玩家一旦断线就自动从房间离开
     *
     * @return 返回玩家允许的最大离线时间
     */
    public int getPlayerTtlSecs() {
        return getParameter(playerTtlK, 0);
    }

    /**
     * 设置玩家允许的最大离线时间，默认为 0 表示玩家一旦断线就自动从房间离开
     *
     * @param ttl  最大离线时间，不能小于 0，如果设置的过大超过了 Game Server 的允许限制会被限制
     *             为 Game Server 最大允许值
     * @param unit 最大离线时间单位
     * @return this
     */
    public CreateRoomRequest setPlayerTtl(long ttl, TimeUnit unit) {
        if (ttl <= 0) throw new IllegalArgumentException();

        setParameter(playerTtlK, (int) unit.toSeconds(ttl));
        return this;
    }

    /**
     * 获取房间指定的玩家 ID 列表。这个参数主要用于为某些能加入到房间中的特定玩家「占位」。
     *
     * @return 房间指定的玩家 ID 列表
     */
    public List<String> getExpectUsers() {
        return getParameter(expectMK, Collections.emptyList());
    }

    /**
     * 设置房间指定的玩家 ID 列表。这个参数主要用于为某些能加入到房间中的特定玩家「占位」。
     *
     * @param expectUsers 指定的玩家 ID 列表，列表不能为空，不能是 null。expectUsers 参数会拷贝一份
     *                    后存入请求内，所以本方法返回后再修改 expectUsers 不会影响已存入请求内的列表
     * @return this
     */
    public CreateRoomRequest setExpectUsers(List<String> expectUsers) {
        Objects.requireNonNull(expectUsers);
        if (expectUsers.isEmpty()) throw new IllegalArgumentException();

        setParameter(expectMK, PersistentVector.create(expectUsers));
        return this;
    }

    /**
     * 房间是否可见。默认为可见，即所有玩家都能在大厅上查看、自动匹配到本房间
     *
     * @return 房间是否可见
     */
    public boolean isVisible() {
        return getParameter(isVisibleK, true);
    }

    /**
     * 隐藏房间。设置后只能通过房间名称加入本房间
     *
     * @return this
     */
    public CreateRoomRequest hideRoom() {
        setParameter(isVisibleK, false);
        return this;
    }

    /**
     * 暴露房间。设置后所有玩家都能在大厅上查看、自动匹配到本房间
     *
     * @return this
     */
    public CreateRoomRequest exposeRoom() {
        setParameter(isVisibleK, true);
        return this;
    }

    /**
     * 获取用于房间匹配的房间自定义属性键。不在本列表内的房间自定义属性不会用来做房间匹配
     *
     * @return 用于房间匹配的房间自定义属性键
     */
    public List<String> getLobbyKeys() {
        return getParameter(lobbyKeysK, Collections.emptyList());
    }

    /**
     * 设置用于房间匹配的房间自定义属性键。不在本列表内的房间自定义属性不会用来做房间匹配
     *
     * @param keys 用于房间匹配的房间自定义属性键，不能为空也不能是 null。keys 参数会拷贝
     *             一份后存入请求内，所以本方法返回后再修改 keys 不会影响已存入请求内的键列表
     * @return this
     */
    public CreateRoomRequest setLobbyKeys(List<String> keys) {
        Objects.requireNonNull(keys);
        if (keys.isEmpty()) throw new IllegalArgumentException();

        setParameter(lobbyKeysK, PersistentVector.create(keys));

        return this;
    }

    /**
     * 获取房间自定义属性
     *
     * @return 返回房间自定义属性，是不可变 Map
     */
    public Map<String, Object> getRoomProperties() {
        return getParameter(attrK, Collections.emptyMap());
    }

    /**
     * 设置房间自定义属性
     *
     * @param attr 房间自定义属性，不能是空也不能是 null。attr 会被拷贝一份后存入请求内，
     *             所以本方法返回后再修改 attr 不会影响已存入请求内的房间自定义属性参数
     * @return this
     */
    public CreateRoomRequest setRoomProperties(Map<String, Object> attr) {
        Objects.requireNonNull(attr);
        if (attr.isEmpty()) throw new IllegalArgumentException();

        setParameter(attrK, PersistentHashMap.create(attr));
        return this;
    }
}
