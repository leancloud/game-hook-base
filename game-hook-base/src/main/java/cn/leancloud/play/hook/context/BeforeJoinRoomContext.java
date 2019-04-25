package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.JoinRoomRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeJoinRoomContext extends AbstractOperationContext<JoinRoomRequest> {
    public BeforeJoinRoomContext(JoinRoomRequest req,
                                 CompletableFuture<HookResponse<JoinRoomRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "BeforeJoinRoom";
    }
}
