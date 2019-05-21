package cn.leancloud.play.plugin.context;

import cn.leancloud.play.plugin.request.RoomRequest;

public interface DeferableContext<T extends RoomRequest> extends Context<T> {
    /**
     * 延迟处理本次请求
     */
    void deferProcess();
}
