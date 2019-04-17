package cn.leancloud.play.hook.request;

import clojure.lang.Keyword;

import java.util.Map;

public final class CloseRoomRequest extends AbstractRequest {
    public CloseRoomRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }
}
