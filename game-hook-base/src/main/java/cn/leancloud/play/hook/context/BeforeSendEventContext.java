package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.SendEventRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeSendEventContext extends AbstractDeferrableContext<SendEventRequest> {
    public BeforeSendEventContext(SendEventRequest req,
                                  CompletableFuture<HookResponse<SendEventRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "BeforeSendEvent";
    }
}
