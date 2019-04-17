package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.HookResponse;
import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.hook.request.RaiseRpcRequest;

import java.util.concurrent.CompletableFuture;

public final class BeforeRaiseRpcContext extends AbstractDeferrableContext<RaiseRpcRequest> {
    public BeforeRaiseRpcContext(RaiseRpcRequest req,
                                 HookedRoom hookedRoom,
                                 CompletableFuture<HookResponse<RaiseRpcRequest>> future) {
        super(req, hookedRoom, future);
    }

    @Override
    public String getHookName() {
        return "BeforeRaiseRpc";
    }
}
