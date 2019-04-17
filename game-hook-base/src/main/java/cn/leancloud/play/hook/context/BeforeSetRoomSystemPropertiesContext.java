package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.SetRoomSystemPropertiesRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeSetRoomSystemPropertiesContext extends AbstractOperationContext<SetRoomSystemPropertiesRequest> {
    public BeforeSetRoomSystemPropertiesContext(SetRoomSystemPropertiesRequest req, CompletableFuture<HookResponse<SetRoomSystemPropertiesRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "BeforeSetRoomSystemProperties";
    }
}
