package cn.leancloud.play.hook.request;

import clojure.lang.Keyword;
import clojure.lang.PersistentVector;
import clojure.lang.RT;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RaiseRpcRequest extends AbstractRequest {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final Keyword cacheOptionK = (Keyword) RT.keyword(null, "cache-option");
    private static final Keyword receiverGroupK = (Keyword) RT.keyword(null, "receiver-group");
    private static final Keyword toActorsK = (Keyword) RT.keyword(null, "to-actors");
    private static final Keyword binK = (Keyword) RT.keyword(null, "bin");
    private static final Keyword dataK = (Keyword) RT.keyword(null, "data");
    private static final Keyword eventIdK = (Keyword) RT.keyword(null, "event-id");
    private static final Keyword fromActorIdK = (Keyword) RT.keyword(null, "from-actor-id");

    public RaiseRpcRequest(Map<Keyword, Object> requestParams) {
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
    public RaiseRpcRequest setCacheOption(CacheOption opt) {
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
     */
    public RaiseRpcRequest setReceiverGroup(ReceiverGroup group) {
        Objects.requireNonNull(group);

        setParameter(receiverGroupK, (long) group.getCode());
        return this;
    }

    /**
     * 获取事件目标接收玩家 Actor Id 列表
     *
     * @return 目标玩家 Actor Id 列表
     */
    public List<Integer> getToActorIds() {
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
    public RaiseRpcRequest setToActorIds(List<Integer> actorIds) {
        Objects.requireNonNull(actorIds);

        setParameter(toActorsK, PersistentVector.create(actorIds));
        return this;
    }

    /**
     * 获取事件内容
     *
     * @return 事件内容
     */
    public byte[] getData() {
        Boolean isBin = getParameter(binK);
        Object data = getParameter(dataK);
        if (data == null) {
            return EMPTY_BYTE_ARRAY;
        } else {
            if (isBin != null && isBin) {
                return (byte[]) data;
            } else {
                return ((String) data).getBytes(StandardCharsets.UTF_8);
            }
        }
    }

    /**
     * 设置事件内容
     *
     * @param data 事件内容
     * @return this
     */
    public RaiseRpcRequest setData(byte[] data) {
        Boolean isBin = getParameter(binK);
        if (isBin != null && isBin) {
            setParameter(dataK, data);
        } else {
            setParameter(dataK, new String(data, StandardCharsets.UTF_8));
        }
        return this;
    }

    /**
     * 获取事件自定义 Id
     *
     * @return 自定义 Id
     */
    public int getEventId() {
        Number id = getParameter(eventIdK);
        if (id != null) {
            return id.intValue();
        }

        return -1;
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

        return -1;
    }
}
