package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.SetPlayerPropertiesRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeSetPlayerPropertiesContext extends AbstractDeferrableContext<SetPlayerPropertiesRequest> {
    public BeforeSetPlayerPropertiesContext(SetPlayerPropertiesRequest req,
                                            CompletableFuture<HookResponse<SetPlayerPropertiesRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "BeforeSetPlayerProperties";
    }
}
