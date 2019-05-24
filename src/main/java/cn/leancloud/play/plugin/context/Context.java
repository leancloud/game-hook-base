package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.Reason;
import cn.leancloud.play.plugin.request.RoomRequest;

/**
 * 每个 Hook 被调用时都会传入一个 Context。Context 内封装着 Hook 的名称、触发 Hook 调用的玩家
 * 请求参数，以及当前 Hook 请求的处理状态。Hook 处理结果也是通过 Context 通知给 Game Server。可以将
 * Context 当做 Hook 与 Game Server 之间通信、交互的桥梁。
 */
public interface Context <T extends RoomRequest>{
    /**
     * 获取当前 Context 状态
     *
     * @return Context 状态
     */
    ContextStatus getStatus();

    /**
     * 获取与当前 Context 绑定的 Hook 请求参数实例
     *
     * @return Hook 请求参数实例
     */
    T getRequest();

    /**
     * 获取当前 Context 所属 Hook 名称
     *
     * @return Hook 名称
     */
    String getHookName();

    /**
     * 当前 Hook 请求是否已经被处理过，即状态为 CONTINUED, SKIPPED, REJECTED
     * @return Hook 请求是否已经被处理过
     */
    boolean isProcessed();

    /**
     * 同意本次请求，Game Server 会执行 Hook 之后的操作
     */
    void continueProcess();

    /**
     * 拒绝本次请求，并发送 Reason 信息给发送请求的玩家
     *
     * @param reason 拒绝原因
     */
    void rejectProcess(Reason reason);
}
