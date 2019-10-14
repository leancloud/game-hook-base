package cn.leancloud.play.plugin.request;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Keyword;
import clojure.lang.RT;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractRequest implements RoomRequest {
    private static final Keyword cidK = RT.keyword(null, "cid");
    private static final Keyword pidK = RT.keyword(null, "pid");
    private static final IFn mergeMapFn = Clojure.var("clojure.core", "merge");

    private final Map<Keyword, Object> originParams;
    private final Map<Keyword, Object> modifiedParams;

    private boolean readOnly;
    private Map<Keyword, Object> mergedParams;

    AbstractRequest(Map<Keyword, Object> originParams) {
        this.originParams = originParams;
        this.modifiedParams = new HashMap<>();
        this.readOnly = false;
        this.mergedParams = null;
    }

    @Override
    public String getRoomName() {
        return getParameter(cidK);
    }

    @Override
    public String getUserId() {
        return getParameter(pidK);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Keyword, Object> getAllInternalParameters() {
        if (mergedParams == null) {
            if (modifiedParams.isEmpty()) {
                mergedParams = originParams;
            } else {
                mergedParams = (Map<Keyword, Object>) mergeMapFn.invoke(originParams, modifiedParams);
            }
        }

        return mergedParams;
    }

    @Override
    public void setReadOnly() {
        readOnly = true;
    }

    <T> T getParameter(Keyword key) {
        return getParameter(key, null);
    }

    @SuppressWarnings("unchecked")
    <T> T getParameter(Keyword key, T orElse) {
        Object o;
        if (mergedParams != null && (o = mergedParams.get(key)) != null) {
            return (T) o;
        }

        o = modifiedParams.get(key);
        if (o != null) {
            return (T) o;
        }

        o = originParams.get(key);
        if (o != null) {
            return (T) o;
        }

        return orElse;
    }

    void setParameter(Keyword key, Object v) {
        if (readOnly) {
            throw new IllegalStateException("this request is read only. maybe it has already been processed");
        } else {
            mergedParams = null;
            modifiedParams.put(key, v);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AbstractRequest that = (AbstractRequest) o;
        return Objects.equals(getAllInternalParameters(), that.getAllInternalParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAllInternalParameters());
    }
}
