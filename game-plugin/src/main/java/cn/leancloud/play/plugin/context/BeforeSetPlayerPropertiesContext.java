package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.HookResponse;
import cn.leancloud.play.plugin.request.SetPlayerPropertiesRequest;

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
