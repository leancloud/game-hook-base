package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.hook.request.LeaveRoomRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeLeaveRoomContext extends AbstractOperationContext<LeaveRoomRequest> {
    public BeforeLeaveRoomContext(LeaveRoomRequest req,
                                  HookedRoom hookedRoom,
                                  CompletableFuture<HookResponse<LeaveRoomRequest>> future) {
        super(req, hookedRoom, future);
    }

    @Override
    public String getHookName() {
        return "BeforeLeaveRoom";
    }
}
