package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.hook.Reason;
import cn.leancloud.play.hook.request.CloseRoomRequest;
import cn.leancloud.play.utils.Log;

import java.util.concurrent.CompletableFuture;

public final class CloseRoomContext extends AbstractOperationContext<CloseRoomRequest> {
    public CloseRoomContext(CloseRoomRequest req,
                            HookedRoom hookedRoom,
                            CompletableFuture<HookResponse<CloseRoomRequest>> future) {
        super(req, hookedRoom, future);
    }

    @Override
    public String getHookName() {
        return "CloseRoom";
    }

    @Override
    public void rejectProcess(Reason reason) {
        Log.warn("reject close room with reason {} is not allowed. will continue process anyway", reason);
        super.continueProcess();
    }
}


