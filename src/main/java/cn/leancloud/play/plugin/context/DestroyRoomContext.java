package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.HookResponse;
import cn.leancloud.play.plugin.Reason;
import cn.leancloud.play.plugin.request.DestroyRoomRequest;
import cn.leancloud.play.utils.Log;

import java.util.concurrent.CompletableFuture;

public final class DestroyRoomContext extends AbstractOperationContext<DestroyRoomRequest> {
    public DestroyRoomContext(DestroyRoomRequest req,
                              CompletableFuture<HookResponse<DestroyRoomRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "DestroyRoom";
    }

    @Override
    public void rejectProcess(Reason reason) {
        Log.warn("reject destroy room with reason {} is not allowed. will continue process anyway", reason);
        super.continueProcess();
    }
}


