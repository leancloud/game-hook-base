package cn.leancloud.play.hook.request;

import clojure.lang.Keyword;
import clojure.lang.RT;
import cn.leancloud.play.hook.Reason;
import cn.leancloud.play.utils.Log;

import java.util.Map;

public final class LeaveRoomRequest extends AbstractRequest {
    private static final Keyword isByMasterK = (Keyword) RT.keyword(null, "by-master?");
    private static final Keyword fromActorIdK = (Keyword) RT.keyword(null, "init-by-actor");
    private static final Keyword targetActorIdK = (Keyword) RT.keyword(null, "target-actor-id");
    private static final Keyword appCodeK = (Keyword) RT.keyword(null, "app-code");
    private static final Keyword appMsgK = (Keyword) RT.keyword(null, "app-msg");

    public LeaveRoomRequest(Map<Keyword, Object> requestParams) {
        super(requestParams);
    }

    /**
     * 获取发起离开房间操作的 Actor Id。如果是某 Actor 自己要离开房间，则返回
     * 待离开房间 Actor 的 Id。如果是某 Actor 被踢，则这里获取的是发起踢人操作
     * 的 Actor 的 Id。
     *
     * @return 发起离开房间操作的 Actor Id
     */
    public int getFromActorId() {
        Number id = getParameter(fromActorIdK);
        if (id != null) {
            return id.intValue();
        }

        Log.error("No from actor id in LeaveRoomRequest. current params={}", getAllParameters());
        return -1;
    }

    /**
     * 获取将要离开本房间的 Actor Id。如果是某 Actor 自己要离开房间，则返回
     * 待离开房间 Actor 的 Id。如果是某 Actor 被踢，则这里获取的是被踢 Actor
     * 的 Id。
     *
     * @return 即将离开本房间的 Actor Id，返回 -1 表示程序有问题
     */
    public int getTargetActorId() {
        Number id = getParameter(targetActorIdK);
        if (id != null) {
            return id.intValue();
        }

        Log.error("No target actor id in LeaveRoomRequest. current params={}", getAllParameters());
        return -1;
    }

    /**
     * 获取自定义离开房间原因，只有在踢人场景下发起踢人请求一方可以提供踢人原因，Actor 自
     * 己主动离开房间或因为断线而离开房间时不提供原因。
     *
     * @return 自定义离开房间原因
     */
    public Reason getLeaveRoomReason(){
        Number appCode = getParameter(appCodeK);
        if (appCode != null) {
            String appMsg = getParameter(appMsgK);

            return Reason.of(appCode.intValue(), appMsg);
        }

        return Reason.EMPTY_REASON;
    }

    /**
     * 玩家是否因为被 Master 踢出房间
     *
     * @return 是否被 Master 踢出房间
     */
    public boolean byMaster() {
        Boolean byMaster = getParameter(isByMasterK);
        return byMaster != null && byMaster;
    }
}
