package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.HookResponse;
import cn.leancloud.play.plugin.request.JoinRoomRequest;

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
