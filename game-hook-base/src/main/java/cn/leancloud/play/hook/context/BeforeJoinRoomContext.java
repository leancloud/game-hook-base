package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.hook.Reason;
import cn.leancloud.play.hook.request.JoinRoomRequest;
import cn.leancloud.play.utils.Log;

import java.util.concurrent.CompletableFuture;

public final class BeforeJoinRoomContext extends AbstractOperationContext<JoinRoomRequest> {
    public BeforeJoinRoomContext(JoinRoomRequest req,
                                 HookedRoom hookedRoom,
                                 CompletableFuture<HookResponse<JoinRoomRequest>> future) {
        super(req, hookedRoom, future);
    }

    @Override
    public String getHookName() {
        return "BeforeJoinRoom";
    }
}
