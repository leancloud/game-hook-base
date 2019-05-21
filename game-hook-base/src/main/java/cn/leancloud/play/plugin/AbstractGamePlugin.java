package cn.leancloud.play.plugin;

import cn.leancloud.play.plugin.context.*;

public abstract class AbstractGamePlugin implements GamePlugin {
    private final BindingRoom room;

    public AbstractGamePlugin(BindingRoom room) {
        this.room = room;
    }

    @Override
    public BindingRoom getHookedRoom() {
        return room;
    }

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
    public void onBeforeSendEvent(BeforeSendEventContext ctx) {
        ctx.continueProcess();
    }
}
