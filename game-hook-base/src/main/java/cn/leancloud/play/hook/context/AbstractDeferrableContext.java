package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.RoomRequest;

import java.util.concurrent.CompletableFuture;

abstract class AbstractDeferrableContext<T extends RoomRequest>
        extends AbstractOperationContext<T>
        implements DeferableContext<T>, SkipableContext<T>{

    AbstractDeferrableContext(T req, CompletableFuture<HookResponse<T>> future) {
        super(req, future);
    }

    @Override
    public void skipProcess() {
        updateStatusWhenNotProcessed(ContextStatus.SKIPPED);
        completeHookFuture(HookResponse.skip(getRequest()));
    }

    @Override
    public void deferProcess() {
        updateStatusWhenNotProcessed(ContextStatus.DEFERRED);
    }
}
