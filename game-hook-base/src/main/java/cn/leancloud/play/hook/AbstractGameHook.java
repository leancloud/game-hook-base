package cn.leancloud.play.hook;

import cn.leancloud.play.hook.context.*;

public abstract class AbstractGameHook implements GameHook{
    @Override
    public void onCreateRoom(CreateRoomContext ctx) {
        ctx.continueProcess();
    }

    @Override
    public void onCloseRoom(CloseRoomContext ctx) {
        ctx.continueProcess();
    }

    @Override
    public void onBeforeJoinRoom(BeforeJoinRoomContext ctx) {
        ctx.continueProcess();
    }

    @Override
    public void onBeforeLeaveRoom(BeforeLeaveRoomContext ctx) {
        ctx.continueProcess();
    }

    @Override
    public void onBeforeSetRoomProperties(BeforeSetRoomPropertiesContext ctx) {
        ctx.continueProcess();
    }

    @Override
    public void onBeforeSetRoomSystemProperties(BeforeSetRoomSystemPropertiesContext ctx) {
        ctx.continueProcess();
    }

    @Override
    public void onBeforeSetPlayerProperties(BeforeSetPlayerPropertiesContext ctx) {
        ctx.continueProcess();
    }

    @Override
    public void onBeforeRaiseRpc(BeforeRaiseRpcContext ctx) {
        ctx.continueProcess();
    }
}
