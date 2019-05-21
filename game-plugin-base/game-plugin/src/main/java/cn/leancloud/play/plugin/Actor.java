package cn.leancloud.play.plugin;

import java.util.Map;

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
     * 获取玩家在房间内的自定义属性
     *
     * @return 玩家自定义属性
     */
    Map<String, Object> getProperties();
}
