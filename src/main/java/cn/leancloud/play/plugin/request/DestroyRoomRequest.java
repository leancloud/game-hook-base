package cn.leancloud.play.plugin.request;

import clojure.lang.Keyword;

import java.util.Map;

public final class DestroyRoomRequest extends AbstractRequest {
    public DestroyRoomRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String toString() {
        return "DestroyRoomRequest{" +
                "roomName=" + getRoomName() +
                "}";
    }
}
