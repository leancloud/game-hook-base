package cn.leancloud.play.hook;

import cn.leancloud.play.hook.request.RaiseRpcOptions;
import cn.leancloud.play.hook.request.ReceiverGroup;
import cn.leancloud.play.hook.request.RoomSystemProperty;

import java.util.List;
import java.util.Map;

/**
 * 与 Hook 绑定的房间实例，用于获取房间信息以及对房间进行操作。
 */
public interface HookedRoom {
    /**
     * 获取房间名称
     *
     * @return 返回房间名称
     */
    String getRoomName();

    /**
     * 获取房间 Master 玩家的 Actor 实例
     *
     * @return 返回 Master 玩家的 Actor 实例
     */
    Actor getMaster();

    /**
     * 获取房间自定义属性
     *
     * @return 房间自定义属性
     */
    Map<String, Object> getRoomProperties();

    /**
     * 获取房间所有玩家列表
     *
     * @return 房间所有玩家列表
     */
    List<Actor> getAllActors();

    /**
     * 根据 Actor Id 获取房间玩家列表
     *
     * @param actorIds 目标 Actor Id 列表，可以包含不在房间内的 Actor Id，但不存在的玩家不会在结果中
     * @return 返回查询到的目标玩家列表
     */
    List<Actor> getActorByActorIds(List<Integer> actorIds);

    /**
     * 获取房间最大玩家数量限制
     *
     * @return 最大玩家数量限制
     */
    int getMaxPlayerCount();

    /**
     * 获取没玩家在本房间时本房间最大保留时长
     *
     * @return 空房间保留时长，单位为秒
     */
    int getEmptyRoomTtlSecs();

    /**
     * 获取玩家允许的最大离线时间，默认为 0 表示玩家一旦断线就自动从房间离开
     *
     * @return 玩家允许的最大离线时间
     */
    int getPlayerTtlSecs();

    /**
     * 获取房间指定的玩家 ID 列表。这个参数主要用于为某些能加入到房间中的特定玩家「占位」。
     *
     * @return 指定的玩家 ID 列表
     */
    List<String> getExpectUsers();

    /**
     * 房间是否可见。默认为可见，即所有玩家都能在大厅上查看、自动匹配到本房间
     *
     * @return 房间是否可见
     */
    boolean isVisible();

    /**
     * 房间是否关闭。关闭后房间不允许新玩家加入
     *
     * @return 房间是否关闭
     */
    boolean isOpen();

    /**
     * 获取用于房间匹配的房间自定义属性键。不在本列表内的房间自定义属性不会用来做房间匹配
     *
     * @return 用于房间匹配的房间自定义属性键
     */
    List<String> getLobbyKeys();

    /**
     * 更改房间玩家自定义属性
     *
     * @param targetActorId 目标玩家 Actor Id
     * @param valuesToSet 待修改的玩家自定义属性
     * @param expectedValues 设置 CAS 操作用于匹配的玩家自定义属性。设置了用于匹配的玩家自定义属性后，只有当玩家自定义属性符合
     *                       匹配的值后更新玩家自定义属性操作才会生效。
     */
    void updatePlayerProperty(int targetActorId, Map<String, Object> valuesToSet, Map<String, Object> expectedValues);

    /**
     * 更改房间自定义属性
     *
     * @param valuesToSet 待修改的房间自定义属性
     * @param expectedValues 进行 CAS 操作时使用，用于匹配的房间自定义属性。设置了用于匹配的自定义属性后，只有当房间自定义属性符合
     *                       匹配的值后更新房间自定义属性操作才会生效。
     */
    void updateRoomProperty(Map<String, Object> valuesToSet, Map<String, Object> expectedValues);

    /**
     * 更改房间系统属性
     * @param property 待修改的系统属性
     * @param <K> 具体要改的系统属性
     * @param <V> 待修改后的值
     */
    <K, V> void updateRoomSystemProperty(RoomSystemProperty<K, V> property);

    /**
     * 将某玩家从房间移除
     *
     * @param actorId 待移除的玩家 Actor Id
     */
    void removeActor(int actorId);

    /**
     * 发送事件给目标 Actor。
     *
     * @param toActorsIds 目标 Actor Id 列表
     * @param fromActorId 发送事件的 Actor Id
     * @param data 事件数据
     * @param options 发送事件配置选项
     */
    void raiseRpcToActors(List<Integer> toActorsIds, int fromActorId, byte[] data, RaiseRpcOptions options);

    /**
     * 发送事件给目标接收组
     *
     * @param toReceiverGroup 目标接收组
     * @param fromActorId 发送事件的 Actor Id
     * @param data 事件数据
     * @param options 发送事件配置选项
     */
    void raiseRpcToReceiverGroup(ReceiverGroup toReceiverGroup, int fromActorId, byte[] data, RaiseRpcOptions options);
}