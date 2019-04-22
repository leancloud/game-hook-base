package cn.leancloud.play.hook.template;

import cn.leancloud.play.hook.AbstractGameHook;
import cn.leancloud.play.hook.Actor;
import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.hook.context.BeforeRaiseRpcContext;
import cn.leancloud.play.hook.request.RaiseRpcOptions;
import cn.leancloud.play.hook.request.RaiseRpcRequest;
import cn.leancloud.play.hook.request.ReceiverGroup;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MasterIsWatchingYouHook extends AbstractGameHook {
    @Override
    public void onBeforeRaiseRpc(BeforeRaiseRpcContext ctx) {
        RaiseRpcRequest req = ctx.getRequest();
        HookedRoom room = ctx.getHookedRoom();
        Actor master = room.getMaster();
        if (master == null) {
            // no master in this room
            // reject and swallow this request
            ctx.skipProcess();
            return;
        }

        boolean masterIsInTargets = true;
        List<Integer> targetActors = req.getToActorIds();
        if (!targetActors.isEmpty() &&
                targetActors.stream().noneMatch(actorId -> actorId == master.getActorId())) {
            masterIsInTargets = false;

            ArrayList<Integer> newTargets = new ArrayList<>(targetActors);
            newTargets.add(master.getActorId());
            req.setToActorIds(newTargets);
        }

        ctx.continueProcess();

        if (!masterIsInTargets) {
            String msg = String.format("actor %d is sending sneaky rpc", req.getFromActorId());
            room.raiseRpcToReceiverGroup(ReceiverGroup.ALL,
                    master.getActorId(),
                    msg.getBytes(StandardCharsets.UTF_8),
                    RaiseRpcOptions.emptyOption);
        }
    }
}
