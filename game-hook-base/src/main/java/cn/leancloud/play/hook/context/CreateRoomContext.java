package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.request.CreateRoomRequest;

import java.util.concurrent.CompletableFuture;

public final class CreateRoomContext extends AbstractOperationContext<CreateRoomRequest> {
    public CreateRoomContext(CreateRoomRequest req,
                             CompletableFuture<HookResponse<CreateRoomRequest>> future) {
        super(req, future);
    }

    @Override
    public String getHookName() {
        return "CreateRoom";
    }
}
