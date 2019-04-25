package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.Reason;
import cn.leancloud.play.hook.request.RoomRequest;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

abstract class AbstractOperationContext<T extends RoomRequest> implements Context<T> {
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

    @Override
    public ContextStatus getStatus() {
        return status;
    }

    @Override
    public T getRequest() {
        return req;
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

    @Override
    public boolean isProcessed(){
        return status.getCode() > ContextStatus.DEFERRED.getCode();
    }

    void updateStatusWhenNotProcessed(ContextStatus newStatus) {
        if (isProcessed()) {
            throw new IllegalStateException("request already processed");
        } else {
            status = newStatus;
        }
    }

    void completeHookFuture(HookResponse<T> response) {
        req.setReadOnly();
        future.complete(response);
    }
}
