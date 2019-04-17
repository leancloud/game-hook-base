package cn.leancloud.play.hook;

import java.util.Map;

public interface HookFactory {
    /**
     * 创建 Hook 工厂
     *
     * @param room 与新 Hook 绑定的房间实例
     * @param hookName 创建的 Hook 名称
     * @param initConfigs 创建 Hook 的初始化配置参数
     * @return 新创建的 Hook
     */
    GameHook create(HookedRoom room, String hookName, Map<String, String> initConfigs);
}
