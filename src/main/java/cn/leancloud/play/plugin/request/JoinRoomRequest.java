package cn.leancloud.play.plugin.request;

import clojure.lang.Keyword;
import clojure.lang.PersistentVector;
import clojure.lang.RT;
import cn.leancloud.play.collection.GameMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class JoinRoomRequest extends AbstractRequest {
    private static final Keyword expectMK = (Keyword) RT.keyword(null, "expect-m");
    private static final Keyword isRejoinK = (Keyword) RT.keyword(null, "rejoin?");
    private static final Keyword expectAttrK = (Keyword) RT.keyword(null, "expect-attr");
    private static final Keyword attrK = (Keyword) RT.keyword(null, "attr");

    public JoinRoomRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
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
    public JoinRoomRequest setExpectUsers(List<String> expectUsers) {
        Objects.requireNonNull(expectUsers);
        if (expectUsers.isEmpty()) throw new IllegalArgumentException();
        setParameter(expectMK, PersistentVector.create(expectUsers));
        return this;
    }

    /**
     * 是否是再次加入房间，即玩家之前在房间中，之后因断线而离开房间，此时再次重加入回原来所在房间
     *
     * @return 是否是再次加入房间
     */
    public boolean isRejoin() {
        return getParameter(isRejoinK, false);
    }

    /**
     * 获取匹配房间条件。传递该参数表示只有待加入房间自定义属性满足匹配条件时才能加入房间
     *
     * @return 匹配房间条件
     */
    public GameMap getMatchProperties() {
        return getParameter(expectAttrK, GameMap.EMPTY_MAP);
    }

    /**
     * 设置匹配房间条件。传递该参数表示只有待加入房间自定义属性满足匹配条件时才能加入房间
     *
     * @param attr 匹配房间条件，不能是空也不能是 null。attr 会被拷贝一份后存入请求内，
     *             所以本方法返回后再修改 attr 不会影响已存入请求内的匹配条件
     * @return this
     */
    public JoinRoomRequest setMatchProperties(GameMap attr) {
        Objects.requireNonNull(attr);
        if (attr.isEmpty()) throw new IllegalArgumentException();

        setParameter(expectAttrK, attr.clone());
        return this;
    }

    /**
     * 获取房间玩家自定义属性
     *
     * @return 返回房间玩家自定义属性，是不可变 Map
     */
    public GameMap getActorProperties() {
        return getParameter(attrK, GameMap.EMPTY_MAP);
    }

    /**
     * 设置房间玩家自定义属性
     *
     * @param attr 房间玩家自定义属性，不能是空也不能是 null。attr 会被拷贝一份后存入请求内，
     *             所以本方法返回后再修改 attr 不会影响已存入请求内的房间玩家自定义属性参数
     * @return this
     */
    public JoinRoomRequest setActorProperties(GameMap attr) {
        Objects.requireNonNull(attr);
        if (attr.isEmpty()) throw new IllegalArgumentException();

        setParameter(attrK, attr.clone());
        return this;
    }
}
