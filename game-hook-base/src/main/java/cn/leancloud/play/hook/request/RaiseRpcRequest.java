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
    private static final Keyword cache_option_k = (Keyword) RT.keyword(null, "cache-option");
    private static final Keyword receiver_group_k = (Keyword) RT.keyword(null, "receiver-group");
    private static final Keyword to_actors_k = (Keyword) RT.keyword(null, "to-actors");
    private static final Keyword bin_k = (Keyword) RT.keyword(null, "bin");
    private static final Keyword data_k = (Keyword) RT.keyword(null, "data");
    private static final Keyword event_id_k = (Keyword) RT.keyword(null, "event-id");
    private static final Keyword from_actor_id_k = (Keyword) RT.keyword(null, "from-actor-id");

    public RaiseRpcRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    /**
     * 获取事件缓存选项
     *
     * @return Event 缓存选项
     */
    public CacheOption getCacheOption() {
        Number opt = getParameter(cache_option_k);
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

        setParameter(cache_option_k, (long) opt.getCode());
        return this;
    }

    /**
     * 获取事件目标接收组
     *
     * @return 事件目标接收组
     */
    public ReceiverGroup getReceiverGroup() {
        Number gp = getParameter(receiver_group_k);
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

        setParameter(receiver_group_k, (long) group.getCode());
        return this;
    }

    /**
     * 获取事件目标接收玩家 Actor Id 列表
     *
     * @return 目标玩家 Actor Id 列表
     */
    public List<Integer> getToActorIds() {
        List<Number> actors = getParameter(to_actors_k);
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

        setParameter(to_actors_k, PersistentVector.create(actorIds));
        return this;
    }

    /**
     * 获取事件内容
     *
     * @return 事件内容
     */
    public byte[] getData() {
        Boolean isBin = getParameter(bin_k);
        Object data = getParameter(data_k);
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
        Boolean isBin = getParameter(bin_k);
        if (isBin != null && isBin) {
            setParameter(data_k, data);
        } else {
            setParameter(data_k, new String(data, StandardCharsets.UTF_8));
        }
        return this;
    }

    /**
     * 获取事件自定义 Id
     *
     * @return 自定义 Id
     */
    public int getEventId() {
        Number id = getParameter(event_id_k);
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
        Number id = getParameter(from_actor_id_k);
        if (id != null) {
            return id.intValue();
        }

        return -1;
    }
}
