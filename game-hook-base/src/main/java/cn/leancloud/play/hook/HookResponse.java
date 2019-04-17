package cn.leancloud.play.hook;

import cn.leancloud.play.hook.context.ContextStatus;
import cn.leancloud.play.hook.request.AbstractRequest;

import java.util.Objects;

public final class HookResponse<T extends AbstractRequest> {
    private final T req;
    private final Reason reason;
    private final ContextStatus status;

    private HookResponse(T req, Reason reason, ContextStatus status) {
        this.req = req;
        this.reason = reason;
        this.status = status;
    }

    public static <T extends AbstractRequest> HookResponse<T> success(T req) {
        Objects.requireNonNull(req);

        return new HookResponse<>(req, Reason.EMPTY_REASON, ContextStatus.CONTINUED);
    }

    public static <T extends AbstractRequest> HookResponse<T> defer(T req) {
        Objects.requireNonNull(req);

        return new HookResponse<>(req, Reason.EMPTY_REASON, ContextStatus.DEFERRED);
    }

    public static <T extends AbstractRequest> HookResponse<T> skip(T req) {
        Objects.requireNonNull(req);

        return new HookResponse<>(req, Reason.EMPTY_REASON, ContextStatus.SKIPPED);
    }

    public static <T extends AbstractRequest> HookResponse<T> reject(Reason reason) {
        Objects.requireNonNull(reason);

        return new HookResponse<>(null, reason, ContextStatus.REJECTED);
    }

    public T getRequest() {
        return req;
    }

    public Reason getReason() {
        return reason;
    }

    public ContextStatus getStatus() {
        return status;
    }
}
