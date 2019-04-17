package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.AbstractRequest;

import java.util.concurrent.CompletableFuture;

abstract class AbstractDeferrableContext<T extends AbstractRequest> extends AbstractOperationContext<T> {
    AbstractDeferrableContext(T req, CompletableFuture<HookResponse<T>> future) {
        super(req, future);
    }

    /**
     * 拒绝本次请求，但不返回任何信息给发送请求的玩家
     */
    public void skipProcess() {
        updateStatusWhenNotProcessed(ContextStatus.SKIPPED);
        completeHookFuture(HookResponse.skip(getRequest()));
    }

    /**
     * 延迟处理本次请求
     *
     */
    public void deferProcess() {
        updateStatusWhenNotProcessed(ContextStatus.DEFERRED);
    }
}
