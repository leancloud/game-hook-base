package cn.leancloud.play.plugin.template;

import cn.leancloud.play.plugin.AbstractGamePlugin;
import cn.leancloud.play.plugin.Actor;
import cn.leancloud.play.plugin.BoundRoom;
import cn.leancloud.play.plugin.context.BeforeSendEventContext;
import cn.leancloud.play.plugin.request.ReceiverGroup;
import cn.leancloud.play.plugin.request.SendEventOptions;
import cn.leancloud.play.plugin.request.SendEventRequest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class MasterIsWatchingYouPlugin extends AbstractGamePlugin {
    public MasterIsWatchingYouPlugin(BoundRoom room) {
        super(room);
    }

    @Override
    public void onBeforeSendEvent(BeforeSendEventContext ctx) {
        SendEventRequest req = ctx.getRequest();
        BoundRoom room = getHookedRoom();
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
            room.sendEventToReceiverGroup(ReceiverGroup.ALL,
                    master.getActorId(),
                    req.getEventId(),
                    msg.getBytes(StandardCharsets.UTF_8),
                    SendEventOptions.emptyOption);
        }
    }
}
