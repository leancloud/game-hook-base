package cn.leancloud.play.hook.request;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Keyword;
import clojure.lang.RT;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequest {
    private static final Keyword cid_k = (Keyword)RT.keyword(null, "cid");
    private static final Keyword pid_k = (Keyword)RT.keyword(null, "pid");
    private static final IFn merge_map_fn = Clojure.var("clojure.core", "merge");

    private final Map<Keyword, Object> originParams;
    private final Map<Keyword, Object> modifiedParams;

    private boolean readOnly;

    AbstractRequest(Map<Keyword, Object> originParams) {
        this.originParams = originParams;
        this.modifiedParams = new HashMap<>();
        this.readOnly = false;
    }

    /**
     * 获取本次请求所属的房间名
     *
     * @return 房间名
     */
    public String getRoomName() {
        return getParameter(cid_k);
    }

    /**
     * 获取触发本次请求的玩家 User Id
     *
     * @return 返回玩家 User Id
     */
    public String getUserId() {
        return getParameter(pid_k);
    }

    /**
     * 获取所有请求参数，返回结果为不可变 Map，如要修改某个参数请使用参数对应的 setters 方法
     *
     * @return 所有请求参数
     */
    @SuppressWarnings("unchecked")
    public Map<Keyword, Object> getAllParameters() {
        if (modifiedParams.isEmpty()) {
            return originParams;
        } else {
            return (Map<Keyword, Object>) merge_map_fn.invoke(originParams, modifiedParams);
        }
    }

    /**
     * 内部使用，设置 Read Only 后本请求不能再修改请求参数
     */
    public void setReadOnly() {
        readOnly = true;
    }

    @SuppressWarnings("unchecked")
    <T> T getParameter(Keyword key) {
        return getParameter(key, null);
    }

    @SuppressWarnings("unchecked")
    <T> T getParameter(Keyword key, T orElse) {
        Object o = modifiedParams.get(key);
        if (o != null) {
            return (T)o;
        }

        o = originParams.get(key);
        if (o != null){
            return (T)o;
        }

        return orElse;
    }

    void setParameter(Keyword key, Object v) {
        if (readOnly) {
            throw new IllegalStateException("this request is read only. maybe it has already been processed");
        } else {
            modifiedParams.put(key, v);
        }
    }
}
