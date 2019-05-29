package cn.leancloud.play.plugin;

import cn.leancloud.play.collection.GameMap;
import cn.leancloud.play.plugin.request.ReceiverGroup;
import cn.leancloud.play.plugin.request.RoomSystemProperty;
import cn.leancloud.play.plugin.request.SendEventOptions;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 与 Plugin 绑定的房间实例，用于获取房间信息以及对房间进行操作。
 */
public interface BoundRoom {
    /**
     * 获取一个 ScheduledExecutorService 用于将某个任务交给与当前房间绑定的线程来执行。在该线程上能保证任务运行期间，房间
     * 属性不会发生变化，但需要任务尽快执行完以避免过长时间的阻塞线程。
     * <p>
     * 因为性能原因，返回的 ScheduledExecutorService 时间精度为 20ms，比如布置一个任务 30ms 后执行则实际
     * 执行时间在 30ms ~ 50ms 之间，即不会早于预期执行时间且与预期执行时间最大偏差为 20ms。
     * <p>
     * 返回的 ScheduledExecutorService 不能被 shutdown，执行 shutdown 没有任何作用。
     *
     * @return ScheduledExecutorService
     */
    ScheduledExecutorService getScheduler();

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
     * 获取房间自定义属性。修改返回的属性无法实际修改房间属性，请使用 {@link #updateRoomProperty} 修改房间属性。
     *
     * @return 房间自定义属性
     */
    GameMap getRoomProperties();

    /**
     * 获取房间所有玩家列表。
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
     * 更改房间玩家自定义属性。
     *
     * @param targetActorId 目标玩家 Actor Id，如果目标 Actor Id 并不在房间内则会抛出 IllegalArgumentException
     * @param valuesToSet   待修改的玩家自定义属性，不能为 null
     */
    void updatePlayerProperty(int targetActorId, GameMap valuesToSet);

    /**
     * 更改房间玩家自定义属性
     *
     * @param targetActorId  目标玩家 Actor Id，如果目标 Actor Id 并不在房间内则会抛出 IllegalArgumentException
     * @param valuesToSet    待修改的玩家自定义属性，不能为 null
     * @param expectedValues 不能为 null。设置 CAS 操作用于匹配的玩家自定义属性。设置了用于匹配的玩家自定义属性后，只有当玩家自定义属性符合
     *                       匹配的值后更新玩家自定义属性操作才会生效。
     */
    void updatePlayerProperty(int targetActorId, GameMap valuesToSet, GameMap expectedValues);

    /**
     * 更改房间自定义属性
     *
     * @param valuesToSet 待修改的房间自定义属性, 不能为 null
     */
    void updateRoomProperty(GameMap valuesToSet);

    /**
     * 更改房间自定义属性
     *
     * @param valuesToSet    待修改的房间自定义属性, 不能为 null
     * @param expectedValues 不能为 null。进行 CAS 操作时使用，用于匹配的房间自定义属性。设置了用于匹配的自定义属性后，只有当房间自定义属性符合
     *                       匹配的值后更新房间自定义属性操作才会生效。
     */
    void updateRoomProperty(GameMap valuesToSet, GameMap expectedValues);

    /**
     * 更改房间系统属性
     *
     * @param property 待修改的系统属性，不能为 null
     * @param <V> 具体修改的房间系统属性类型
     */
    <V> void updateRoomSystemProperty(RoomSystemProperty<V> property);

    /**
     * 将某玩家从房间移除
     *
     * @param actorId 待移除的玩家 Actor Id，如果目标 Actor Id 并不在房间内则本次请求无效果
     */
    void removeActor(int actorId);

    /**
     * 发送事件给目标 Actor。
     *
     * @param toActorsIds 目标 Actor Id 列表，不能为 null
     * @param fromActorId 发送事件的 Actor Id，为 0 表示由系统发出
     * @param eventId     自定义事件 Id
     * @param eventData   事件数据，不能为 null
     * @param options     发送事件配置选项，不能为 null，无配置选项请填写 SendEventOptions.emptyOption
     */
    void sendEventToActors(List<Integer> toActorsIds, int fromActorId, byte eventId, GameMap eventData, SendEventOptions options);

    /**
     * 发送事件给目标接收组
     *
     * @param toReceiverGroup 目标接收组，不能为 null
     * @param fromActorId     发送事件的 Actor Id，为 0 表示由系统发出
     * @param eventId         自定义事件 Id
     * @param eventData       事件数据，不能为 null
     * @param options         发送事件配置选项，不能为 null，无配置选项请填写 SendEventOptions.emptyOption
     */
    void sendEventToReceiverGroup(ReceiverGroup toReceiverGroup, int fromActorId, byte eventId, GameMap eventData, SendEventOptions options);
}
