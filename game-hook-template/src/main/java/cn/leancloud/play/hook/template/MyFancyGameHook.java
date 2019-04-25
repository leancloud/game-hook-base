package cn.leancloud.play.hook.template;

import cn.leancloud.play.hook.AbstractGameHook;
import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.hook.context.*;
import cn.leancloud.play.hook.request.*;

import java.util.Map;

public class MyFancyGameHook extends AbstractGameHook {
    public MyFancyGameHook(HookedRoom room, Map<String, String> initConfigs) {
        super(room);
        // do some constructor stuff
    }

    @Override
    public void onCreateRoom(CreateRoomContext ctx) {
        CreateRoomRequest req = ctx.getRequest();

        // do something with req

        // process create room
        ctx.continueProcess();

        // do something after processing create room
    }

    @Override
    public void onBeforeJoinRoom(BeforeJoinRoomContext ctx) {
        JoinRoomRequest req = ctx.getRequest();

        // do something with req

        // process join room
        ctx.continueProcess();

        // do something after processing join room
    }

    @Override
    public void onBeforeLeaveRoom(BeforeLeaveRoomContext ctx) {
        LeaveRoomRequest req = ctx.getRequest();
        // do something with req

        // process leave room
        ctx.continueProcess();

        // do something after processing leave room
    }

    @Override
    public void onBeforeSetRoomProperties(BeforeSetRoomPropertiesContext ctx) {
        SetRoomPropertiesRequest req = ctx.getRequest();

        // do something with req

        // process set room properties
        ctx.continueProcess();

        // do something after processing set room properties
    }

    @Override
    public void onBeforeSetPlayerProperties(BeforeSetPlayerPropertiesContext ctx) {
        SetPlayerPropertiesRequest req = ctx.getRequest();

        // do something with req

        // process set player properties
        ctx.continueProcess();

        // do something after processing set player properties
    }

    @Override
    public void onBeforeSetRoomSystemProperties(BeforeSetRoomSystemPropertiesContext ctx) {
        SetRoomSystemPropertiesRequest req = ctx.getRequest();

        // do something with req

        // process set room system properties
        ctx.continueProcess();

        // do something after processing set room system properties
    }

    @Override
    public void onBeforeRaiseRpc(BeforeRaiseRpcContext ctx) {
        RaiseRpcRequest req = ctx.getRequest();

        // do something with req

        // process raise rpc
        ctx.continueProcess();

        // do something after processing raise rpc
    }

    @Override
    public void onCloseRoom(CloseRoomContext ctx) {
        CloseRoomRequest req = ctx.getRequest();

        // do something with req

        // process close room
        ctx.continueProcess();

        // do something after processing close room
    }
}
