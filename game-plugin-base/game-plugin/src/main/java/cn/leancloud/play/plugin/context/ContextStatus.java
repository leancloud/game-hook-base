package cn.leancloud.play.plugin.context;

/**
 * Hook Context 状态
 */
public enum ContextStatus {
    /**
     * Hook 请求还未被处理
     */
    NEW((byte)0),
    /**
     * Hook 请求被延迟处理
     */
    DEFERRED((byte)1),
    /**
     * Hook 请求被通过，已经在处理中
     */
    CONTINUED((byte)10),
    /**
     * Hook 请求被拒绝，会返回信息给发送请求的玩家
     */
    REJECTED((byte)11),
    /**
     * Hook 请求被拒绝，不会返回信息给发送请求的玩家
     */
    SKIPPED((byte)12);


    private byte code;

    ContextStatus(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
