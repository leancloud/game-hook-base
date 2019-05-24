package cn.leancloud.play.plugin;

import java.util.Map;

public interface PluginFactory {
    /**
     * 创建 Hook 工厂
     *
     * @param room 与新 Plugin 绑定的房间实例
     * @param pluginName 创建的 Plugin 名称
     * @param initConfigs 创建 Plugin 的初始化配置参数
     * @return 新创建的 Plugin
     */
    Plugin create(BoundRoom room, String pluginName, Map<String, Object> initConfigs);
}
