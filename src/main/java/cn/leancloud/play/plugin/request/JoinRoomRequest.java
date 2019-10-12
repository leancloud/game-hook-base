package cn.leancloud.play.plugin.request;

import clojure.lang.Keyword;
import clojure.lang.PersistentVector;
import clojure.lang.RT;
import cn.leancloud.play.collection.PlayObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class JoinRoomRequest extends AbstractRequest {
    private static final Keyword expectMK = RT.keyword(null, "expect-m");
    private static final Keyword isRejoinK = RT.keyword(null, "rejoin?");
    private static final Keyword expectAttrK = RT.keyword(null, "expect-attr");
    private static final Keyword attrK = RT.keyword(null, "attr");

    public JoinRoomRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    /**
     * 获取请求中用于「占位」的玩家 ID 列表。
     *
     * @return 用于「占位」的玩家 ID 列表
     */
    public List<String> getExpectedUserIds() {
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
    public PlayObject getMatchProperties() {
        return getParameter(expectAttrK, PlayObject.EMPTY_OBJECT);
    }

    /**
     * 获取房间玩家自定义属性
     *
     * @return 返回房间玩家自定义属性，是不可变 Map
     */
    public PlayObject getPlayerCustomRoomProperties() {
        return getParameter(attrK, PlayObject.EMPTY_OBJECT);
    }

    /**
     * 设置房间玩家自定义属性
     *
     * @param attr 房间玩家自定义属性，不能是空也不能是 null。attr 会被拷贝一份后存入请求内，
     *             所以本方法返回后再修改 attr 不会影响已存入请求内的房间玩家自定义属性参数
     * @return this
     */
    public JoinRoomRequest setPlayerCustomRoomProperties(PlayObject attr) {
        Objects.requireNonNull(attr);
        if (attr.isEmpty()) throw new IllegalArgumentException();

        setParameter(attrK, attr.clone());
        return this;
    }

    @Override
    public String toString() {
        return "JoinRoomRequest{" +
                ", roomName=" + getRoomName() +
                ", userId=" + getUserId() +
                ", expectUserIds=" + getExpectedUserIds() +
                ", rejoin=" + isRejoin() +
                ", matchProperties=" + getMatchProperties() +
                ", playerCustomRoomProperties=" + getPlayerCustomRoomProperties() +
                "}";
    }
}
