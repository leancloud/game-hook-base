package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.HookResponse;
import cn.leancloud.play.plugin.Reason;
import cn.leancloud.play.plugin.request.RoomRequest;

import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

abstract class AbstractOperationContext<T extends RoomRequest> implements Context<T> {
    private final CompletableFuture<HookResponse<T>> future;
    private final T req;
    private final Thread bindingThread;

    private ContextStatus status;

    AbstractOperationContext(T req, CompletableFuture<HookResponse<T>> future) {
        Objects.requireNonNull(req);
        Objects.requireNonNull(future);

        this.req = req;
        this.future = future;
        this.status = ContextStatus.NEW;
        this.bindingThread = Thread.currentThread();
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
        } else if (Thread.currentThread() != bindingThread) {
            String msg = "Context can only be operated by it's binding thread. Please use the scheduler in BoundRoom" +
                    "to submit a task to operate this Context.";
            throw new ConcurrentModificationException(msg);
        } else {
            status = newStatus;
        }
    }

    void completeHookFuture(HookResponse<T> response) {
        req.setReadOnly();
        future.complete(response);
    }
}
