package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.Reason;
import cn.leancloud.play.hook.request.AbstractRequest;
import cn.leancloud.play.hook.HookResponse;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * 每个 Hook 被调用时都会传入一个 Context。Context 内封装着 Hook 的名称、触发 Hook 调用的玩家
 * 请求参数，以及当前 Hook 请求的处理状态。Hook 处理结果也是通过 Context 通知给 Game Server。可以将
 * Context 当做 Hook 与 Game Server 之间通信、交互的桥梁。
 *
 */
abstract class AbstractOperationContext<T extends AbstractRequest> {
    private final CompletableFuture<HookResponse<T>> future;
    private final T req;

    private ContextStatus status;

    AbstractOperationContext(T req, CompletableFuture<HookResponse<T>> future) {
        Objects.requireNonNull(req);
        Objects.requireNonNull(future);

        this.req = req;
        this.future = future;
        this.status = ContextStatus.NEW;
    }

    /**
     * 获取当前 Context 状态
     *
     * @return 返回 Context 状态
     */
    public ContextStatus getStatus() {
        return status;
    }

    /**
     * 获取与当前 Context 绑定的 Hook 请求参数实例
     *
     * @return 返回 Hook 请求参数实例
     */
    public T getRequest() {
        return req;
    }

    /**
     * 获取当前 Context 所属 Hook 名称
     *
     * @return 返回 Hook 名称
     */
    public abstract String getHookName();

    /**
     * 同意本次请求，Game Server 会执行 Hook 之后的操作
     */
    public void continueProcess() {
        updateStatusWhenNotProcessed(ContextStatus.CONTINUED);
        completeHookFuture(HookResponse.success(req));
    }

    /**
     * 拒绝本次请求，并发送 Reason 信息给发送请求的玩家
     *
     * @param reason 拒绝原因
     */
    public void rejectProcess(Reason reason) {
        updateStatusWhenNotProcessed(ContextStatus.REJECTED);
        completeHookFuture(HookResponse.reject(reason));
    }

    void updateStatusWhenNotProcessed(ContextStatus newStatus) {
        if (status == ContextStatus.NEW || status == ContextStatus.DEFERRED) {
            status = newStatus;
        } else {
            throw new IllegalStateException("request already processed");
        }
    }

    void completeHookFuture(HookResponse<T> response) {
        req.setReadOnly();
        future.complete(response);
    }
}
