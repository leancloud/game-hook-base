package cn.leancloud.play.hook.request;

import cn.leancloud.play.Builder;

import java.util.Objects;

/**
 * 发送事件附带选项
 */
public final class RaiseRpcOptions {
    /**
     * 静态空 option
     */
    public static final RaiseRpcOptions emptyOption = RaiseRpcOptions.builder().build();

    /**
     * 创建 RaiseRpcOptions 的 Builder
     *
     * @return RaiseRpcOptions 的 Builder
     */
    public static RaiseRpcOptionsBuilder builder() {
        return new RaiseRpcOptionsBuilder();
    }

    private final Byte eventId;
    private final CacheOption cacheOption;

    private RaiseRpcOptions(RaiseRpcOptionsBuilder builder) {
        this.eventId = builder.eventId;
        this.cacheOption = builder.cacheOption;
    }

    /**
     * 获取发送事件带着自定义事件 Id
     *
     * @return 自定义事件 Id
     */
    public byte getEventId() {
        return eventId;
    }

    /**
     * 获取发送事件带着缓存方式选项
     *
     * @return 缓存方式选项
     */
    public CacheOption getCacheOption() {
        return cacheOption;
    }

    public static class RaiseRpcOptionsBuilder implements Builder<RaiseRpcOptions> {
        private Byte eventId = null;
        private CacheOption cacheOption = CacheOption.NO_CACHE;

        /**
         * 发送事件带着自定义事件 Id
         *
         * @param eventId 自定义事件 Id
         * @return this
         */
        public RaiseRpcOptionsBuilder withEventId(byte eventId) {
            this.eventId = eventId;
            return this;
        }

        /**
         * 发送事件带着缓存方式选项
         *
         * @param option 配置的缓存方式
         * @return this
         */
        public RaiseRpcOptionsBuilder withCacheOption(CacheOption option) {
            this.cacheOption= Objects.requireNonNull(option);
            return this;
        }

        @Override
        public RaiseRpcOptions build() {
            return new RaiseRpcOptions(this);
        }
    }
}
