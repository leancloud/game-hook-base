package cn.leancloud.play.hook;

import cn.leancloud.play.hook.context.*;

public interface GameHook {
    /**
     * 创建房间前调用
     *
     * @param ctx context
     */
    void onCreateRoom(CreateRoomContext ctx);

    /**
     * 房间关闭前调用
     *
     * @param ctx context
     */
    void onCloseRoom(CloseRoomContext ctx);

    /**
     * 加入房间前调用
     *
     * @param ctx context
     */
    void onBeforeJoinRoom(BeforeJoinRoomContext ctx);

    /**
     * 离开房间前调用
     *
     * @param ctx context
     */
    void onBeforeLeaveRoom(BeforeLeaveRoomContext ctx);

    /**
     * 设置房间自定义属性前调用
     *
     * @param ctx context
     */
    void onBeforeSetRoomProperties(BeforeSetRoomPropertiesContext ctx);

    /**
     * 设置房间玩家自定义属性前调用
     *
     * @param ctx context
     */
    void onBeforeSetPlayerProperties(BeforeSetPlayerPropertiesContext ctx);

    /**
     * 设置房间系统属性前调用
     *
     * @param ctx context
     */
    void onBeforeSetRoomSystemProperties(BeforeSetRoomSystemPropertiesContext ctx);

    /**
     * 发送事件前调用
     *
     * @param ctx context
     */
    void onBeforeRaiseRpc(BeforeRaiseRpcContext ctx);
}
