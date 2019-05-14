package cn.leancloud.play.hook.request;

import cn.leancloud.play.Builder;

import java.util.Objects;

/**
 * 发送事件附带选项
 */
public final class SendEventOptions {
    /**
     * 空 option
     */
    public static final SendEventOptions emptyOption = SendEventOptions.builder().build();

    /**
     * 创建 SendEventOptions 的 Builder
     *
     * @return SendEventOptions 的 Builder
     */
    public static SendEventOptionsBuilder builder() {
        return new SendEventOptionsBuilder();
    }

    private final CacheOption cacheOption;

    private SendEventOptions(SendEventOptionsBuilder builder) {
        this.cacheOption = builder.cacheOption;
    }

    /**
     * 获取发送事件带着缓存方式选项
     *
     * @return 缓存方式选项
     */
    public CacheOption getCacheOption() {
        return cacheOption;
    }

    public static class SendEventOptionsBuilder implements Builder<SendEventOptions> {
        private CacheOption cacheOption = CacheOption.NO_CACHE;

        /**
         * 发送事件带着缓存方式选项
         *
         * @param option 配置的缓存方式
         * @return this
         */
        public SendEventOptionsBuilder withCacheOption(CacheOption option) {
            this.cacheOption= Objects.requireNonNull(option);
            return this;
        }

        @Override
        public SendEventOptions build() {
            return new SendEventOptions(this);
        }
    }
}
