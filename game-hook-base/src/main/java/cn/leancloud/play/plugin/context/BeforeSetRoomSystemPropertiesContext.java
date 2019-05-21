package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.HookResponse;
import cn.leancloud.play.plugin.request.SetRoomSystemPropertiesRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeSetRoomSystemPropertiesContext extends AbstractOperationContext<SetRoomSystemPropertiesRequest> {
    public BeforeSetRoomSystemPropertiesContext(SetRoomSystemPropertiesRequest req,
                                                CompletableFuture<HookResponse<SetRoomSystemPropertiesRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "BeforeSetRoomSystemProperties";
    }
}
