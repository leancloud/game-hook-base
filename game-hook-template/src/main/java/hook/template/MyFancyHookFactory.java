package hook.template;

import cn.leancloud.play.utils.Log;
import cn.leancloud.play.hook.GameHook;
import cn.leancloud.play.hook.HookFactory;
import cn.leancloud.play.hook.HookRoom;

import java.util.Map;

public class MyFancyHookFactory implements HookFactory {
    @Override
    public GameHook create(HookRoom room, String hookName, Map<String, String> initConfigs) {
        if ("fancy-hook".equals(hookName)) {
            return new MyFancyGameHook(initConfigs);
        } else {
            Log.error("unknown hook name {}", hookName);
            return null;
        }
    }
}
