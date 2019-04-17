package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.request.RoomRequest;

public interface SkipableContext<T extends RoomRequest> extends Context<T> {
    /**
     * 拒绝本次请求，但不返回任何信息给发送请求的玩家
     */
    void skipProcess();
}

