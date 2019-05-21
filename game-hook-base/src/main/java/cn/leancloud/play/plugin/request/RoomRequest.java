package cn.leancloud.play.plugin.request;

import clojure.lang.Keyword;

import java.util.Map;

public interface RoomRequest {
    /**
     * 获取本次请求所属的房间名
     *
     * @return 房间名
     */
    String getRoomName();

    /**
     * 获取触发本次请求的玩家 User Id
     *
     * @return 返回玩家 User Id
     */
    String getUserId();

    /**
     * 获取所有请求参数，返回结果为不可变 Map，如要修改某个参数请使用参数对应的 setters 方法
     *
     * @return 所有请求参数
     */
    Map<Keyword, Object> getAllParameters();

    /**
     * 内部使用，设置 Read Only 后本请求不能再修改请求参数
     */
    void setReadOnly();
}
