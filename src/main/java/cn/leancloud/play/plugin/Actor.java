package cn.leancloud.play.plugin;

import cn.leancloud.play.collection.PlayObject;

/**
 * 每一个加入房间的玩家都是一个 Actor，本接口用于获取 Actor 在房间内的信息
 */
public interface Actor {
    /**
     * 获取玩家 Actor Id
     *
     * @return 玩家 Actor Id
     */
    int getActorId();

    /**
     * 获取玩家 User Id
     *
     * @return 玩家 User Id
     */
    String getUserId();

    /**
     * 获取玩家在房间内的自定义属性。修改返回的属性无法实际修改玩家属性，请使用 {@link BoundRoom#updatePlayerProperty} 修改玩家属性。
     *
     * @return 玩家自定义属性
     */
    PlayObject getProperties();

    /**
     * 判断玩家是否完成加入房间 `fully-joined`。
     */
    boolean isFullyJoined();
}
