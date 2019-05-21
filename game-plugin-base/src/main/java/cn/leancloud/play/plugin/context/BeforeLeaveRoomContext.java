package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.HookResponse;
import cn.leancloud.play.plugin.request.LeaveRoomRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeLeaveRoomContext extends AbstractOperationContext<LeaveRoomRequest> {
    public BeforeLeaveRoomContext(LeaveRoomRequest req,
                                  CompletableFuture<HookResponse<LeaveRoomRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "BeforeLeaveRoom";
    }
}
