package cn.leancloud.play.hook.request;

import static java.lang.String.format;

/**
 * 事件缓存选项
 */
public enum CacheOption {
    /**
     * 不缓存事件
     */
    NO_CACHE((byte)0),
    /**
     * 仅缓存事件到某 Actor 下
     */
    CACHE_ON_ACTOR((byte)1),
    /**
     * 缓存事件到整个房间
     */
    CACHE_ON_ROOM((byte)2);

    private byte code;

    CacheOption(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public static CacheOption of(byte code) {
        for(CacheOption opt : CacheOption.values()) {
            if (opt.code == code) {
                return opt;
            }
        }

        throw new IllegalArgumentException(format("unknown code: %s", code));
    }
}
