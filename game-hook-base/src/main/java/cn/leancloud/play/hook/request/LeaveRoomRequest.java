package cn.leancloud.play.hook.request;

import clojure.lang.Keyword;
import clojure.lang.RT;

import java.util.Map;

public final class LeaveRoomRequest extends AbstractRequest {
    private static final Keyword is_by_master_k = (Keyword)RT.keyword(null, "by-master?");

    public LeaveRoomRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    /**
     * 玩家是否因为被 Master 踢出房间
     *
     * @return 是否被 Master 踢出房间
     */
    public boolean byMaster(){
        Boolean byMaster = getParameter(is_by_master_k);
        return byMaster != null && byMaster;
    }
}
