package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.LeaveRoomRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeLeaveRoomContext extends AbstractOperationContext<LeaveRoomRequest> {
    public BeforeLeaveRoomContext(LeaveRoomRequest req, CompletableFuture<HookResponse<LeaveRoomRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "BeforeLeaveRoom";
    }
}
