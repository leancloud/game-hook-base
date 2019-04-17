package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.SetRoomPropertiesRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeSetRoomPropertiesContext extends AbstractDeferrableContext<SetRoomPropertiesRequest> {
    public BeforeSetRoomPropertiesContext(SetRoomPropertiesRequest req, CompletableFuture<HookResponse<SetRoomPropertiesRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "BeforeSetRoomProperties";
    }
}
