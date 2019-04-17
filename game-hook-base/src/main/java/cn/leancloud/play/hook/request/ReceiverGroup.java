package cn.leancloud.play.hook.request;

import static java.lang.String.format;

/**
 * 事件接收组
 */
public enum ReceiverGroup {
    /**
     * 房间内除了事件发送者外都为事件接收人
     */
    OTHERS((byte)0),
    /**
     * 房间内所有玩家均是事件接收人，包括事件发送者
     */
    ALL((byte)1),
    /**
     * 只有房间 Master 为事件接收人
     */
    MASTER((byte)2);

    private byte code;

    ReceiverGroup(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public static ReceiverGroup of(byte code) {
        for(ReceiverGroup opt : ReceiverGroup.values()) {
            if (opt.code == code) {
                return opt;
            }
        }

        throw new IllegalArgumentException(format("unknown code: %s", code));
    }
}
