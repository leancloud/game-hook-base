package cn.leancloud.play.plugin.request;

import clojure.lang.Keyword;
import clojure.lang.PersistentVector;
import clojure.lang.RT;
import cn.leancloud.play.collection.PlayObject;
import cn.leancloud.play.utils.Log;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class SendEventRequest extends AbstractRequest {
    private static final Keyword cacheOptionK = RT.keyword(null, "cache-option");
    private static final Keyword receiverGroupK = RT.keyword(null, "receiver-group");
    private static final Keyword toActorsK = RT.keyword(null, "to-actors");
    private static final Keyword dataK = RT.keyword(null, "data");
    private static final Keyword eventIdK = RT.keyword(null, "event-id");
    private static final Keyword fromActorIdK = RT.keyword(null, "from-actor-id");

    public SendEventRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    /**
     * 获取事件缓存选项
     *
     * @return Event 缓存选项
     */
    public CacheOption getCacheOption() {
        Number opt = getParameter(cacheOptionK);
        if (opt != null) {
            return CacheOption.of(opt.byteValue());
        } else {
            return CacheOption.NO_CACHE;
        }
    }

    /**
     * 设置事件缓存选项
     *
     * @param opt 缓存选项
     * @return this
     */
    public SendEventRequest setCacheOption(CacheOption opt) {
        Objects.requireNonNull(opt);

        setParameter(cacheOptionK, (long) opt.getCode());
        return this;
    }

    /**
     * 获取事件目标接收组
     *
     * @return 事件目标接收组
     */
    public ReceiverGroup getReceiverGroup() {
        Number gp = getParameter(receiverGroupK);
        if (gp != null) {
            return ReceiverGroup.of(gp.byteValue());
        } else {
            return ReceiverGroup.OTHERS;
        }
    }

    /**
     * 设置事件目标接收组
     *
     * @param group 事件目标接收组
     * @return this
     */
    public SendEventRequest setReceiverGroup(ReceiverGroup group) {
        Objects.requireNonNull(group);

        setParameter(receiverGroupK, (long) group.getCode());
        return this;
    }

    /**
     * 获取事件目标接收玩家 Actor Id 列表
     *
     * @return 目标玩家 Actor Id 列表
     */
    public List<Integer> getTargetActorIds() {
        List<Number> actors = getParameter(toActorsK);
        if (actors != null) {
            return actors.stream().map(Number::intValue).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 设置事件目标接收玩家 Actor Id 列表
     *
     * @param actorIds 目标玩家 Actor Id 列表
     * @return this
     */
    public SendEventRequest setTargetActorIds(List<Integer> actorIds) {
        Objects.requireNonNull(actorIds);

        setParameter(toActorsK, PersistentVector.create(actorIds));
        return this;
    }

    /**
     * 获取事件内容
     *
     * @return 事件内容
     */
    public PlayObject getEventData() {
        Object data = getParameter(dataK);
        if (data == null) {
            return PlayObject.EMPTY_OBJECT;
        } else {
            return (PlayObject) data;
        }
    }

    /**
     * 设置事件内容
     *
     * @param data 事件内容
     * @return this
     */
    public SendEventRequest setEventData(PlayObject data) {
        if (data == null) {
            data = PlayObject.EMPTY_OBJECT;
        }

        setParameter(dataK, data);

        return this;
    }

    /**
     * 获取事件自定义 Id
     *
     * @return 自定义 Id
     */
    public byte getEventId() {
        Number id = getParameter(eventIdK);
        if (id != null) {
            return id.byteValue();
        }

        throw new IllegalArgumentException(String.format("eventId is required for %s", SendEventRequest.class));
    }

    /**
     * 获取事件发送者 Actor Id
     *
     * @return 发送者 Actor Id
     */
    public int getFromActorId() {
        Number id = getParameter(fromActorIdK);
        if (id != null) {
            return id.intValue();
        }

        Log.error("No from actor id in {}. current params={}", SendEventRequest.class.getSimpleName(), getAllInternalParameters());
        return -1;
    }

    @Override
    public String toString() {
        return "SendEventRequest{" +
                "roomName=" + getRoomName() +
                ", userId=" + getUserId() +
                ", eventId=" + getEventId() +
                ", fromActorId=" + getFromActorId() +
                ", targetActorIds=" + getTargetActorIds() +
                ", receiverGroup=" + getReceiverGroup().name() +
                ", cacheOptions=" + getCacheOption().name() +
                ", eventData=" + getEventData() +
                "}";
    }
}
