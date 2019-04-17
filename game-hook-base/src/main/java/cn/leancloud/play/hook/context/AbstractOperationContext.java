package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.hook.Reason;
import cn.leancloud.play.hook.request.RoomRequest;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

abstract class AbstractOperationContext<T extends RoomRequest> implements Context<T> {
    private final CompletableFuture<HookResponse<T>> future;
    private final T req;
    private final HookedRoom hookedRoom;

    private ContextStatus status;

    AbstractOperationContext(T req, HookedRoom hookedRoom, CompletableFuture<HookResponse<T>> future) {
        Objects.requireNonNull(req);
        Objects.requireNonNull(future);

        this.req = req;
        this.future = future;
        this.status = ContextStatus.NEW;
        this.hookedRoom = hookedRoom;
    }

    @Override
    public ContextStatus getStatus() {
        return status;
    }

    @Override
    public T getRequest() {
        return req;
    }

    @Override
    public HookedRoom getHookedRoom() {
        return hookedRoom;
    }

    @Override
    public void continueProcess() {
        updateStatusWhenNotProcessed(ContextStatus.CONTINUED);
        completeHookFuture(HookResponse.success(req));
    }

    @Override
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
