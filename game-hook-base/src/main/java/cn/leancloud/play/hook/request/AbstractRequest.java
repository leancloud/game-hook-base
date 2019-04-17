package cn.leancloud.play.hook.request;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Keyword;
import clojure.lang.RT;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequest implements RoomRequest{
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

    @Override
    public String getRoomName() {
        return getParameter(cid_k);
    }

    @Override
    public String getUserId() {
        return getParameter(pid_k);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Keyword, Object> getAllParameters() {
        if (modifiedParams.isEmpty()) {
            return originParams;
        } else {
            return (Map<Keyword, Object>) merge_map_fn.invoke(originParams, modifiedParams);
        }
    }

    @Override
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
