package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.hook.request.CreateRoomRequest;
import cn.leancloud.play.hook.HookResponse;

import java.util.concurrent.CompletableFuture;

public final class CreateRoomContext extends AbstractOperationContext<CreateRoomRequest> {
    public CreateRoomContext(CreateRoomRequest req,
                             HookedRoom hookedRoom,
                             CompletableFuture<HookResponse<CreateRoomRequest>> future) {
        super(req, hookedRoom, future);
    }

    @Override
    public String getHookName() {
        return "CreateRoom";
    }
}
