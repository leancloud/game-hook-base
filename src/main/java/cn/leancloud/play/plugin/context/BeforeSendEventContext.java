package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.HookResponse;
import cn.leancloud.play.plugin.request.SendEventRequest;

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
