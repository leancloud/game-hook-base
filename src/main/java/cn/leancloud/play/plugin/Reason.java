package cn.leancloud.play.plugin;

public final class Reason {
    public static final Reason EMPTY_REASON = new Reason();

    private final int appCode;
    private final String msg;

    private Reason() {
        this.appCode = -1;
        this.msg = "";
    }

    private Reason(int appCode) {
        if (appCode < 0) {
            throw new IllegalArgumentException("appCode should be a non-negative integer");
        }

        this.appCode = appCode;
        this.msg = "";
    }

    private Reason(int appCode, String msg) {
        if (appCode < 0) {
            throw new IllegalArgumentException("appCode should be a non-negative integer");
        }

        if (msg != null && msg.length() > 512) {
            throw new IllegalArgumentException("msg should shorter than 512 characters");
        }

        this.appCode = appCode;
        this.msg = msg == null ? "" : msg;
    }

    /**
     * 创建带自定义 appCode 的 Reason。appCode 为 Hook 实现者自定义的 code，比如可以用于在 Hook 拒绝某玩家的
     * 请求等时候，通过发送 Reason 给玩家让玩家知道请求为什么被拒绝
     *
     * @param appCode Hook 实现者自定义 code
     * @return 返回新创建的 Reason
     */
    public static Reason of(int appCode) {
        return new Reason(appCode);
    }

    /**
     * 创建带自定义 appCode 和 Message 的 Reason。appCode 和 Message 均为 Hook 实现者自定义实现。比如可以用于
     * 在 Hook 拒绝某玩家的请求等时候，通过发送 Reason 给玩家让玩家知道请求为什么被拒绝
     *
     * @param appCode Hook 实现者自定义的 code
     * @param message Hook 实现者自定义的文本 message
     * @return 新创建的 Reason
     */
    public static Reason of(int appCode, String message) {
        return new Reason(appCode, message);
    }

    /**
     * 获取 Hook 实现者定义的 appCode
     *
     * @return appCode
     */
    public int getAppCode() {
        return appCode;
    }

    /**
     * 获取 Hook 实现者自定义的 Message 信息
     *
     * @return message
     */
    public String getMessage() {
        return msg;
    }

    @Override
    public String toString() {
        return "Reason{" +
                "appCode=" + appCode +
                ", message='" + msg + '\'' +
                '}';
    }
}
