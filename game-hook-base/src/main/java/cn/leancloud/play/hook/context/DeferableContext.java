package cn.leancloud.play.hook.context;

import cn.leancloud.play.hook.request.RoomRequest;

public interface DeferableContext<T extends RoomRequest> extends Context<T> {
    /**
     * 延迟处理本次请求
     */
    void deferProcess();
}
