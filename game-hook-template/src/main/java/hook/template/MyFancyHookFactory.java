package hook.template;

import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.utils.Log;
import cn.leancloud.play.hook.GameHook;
import cn.leancloud.play.hook.HookFactory;

import java.util.Map;

public class MyFancyHookFactory implements HookFactory {
    @Override
    public GameHook create(HookedRoom room, String hookName, Map<String, String> initConfigs) {
        if ("fancy-hook".equals(hookName)) {
            return new MyFancyGameHook(initConfigs);
        } else {
            Log.error("unknown hook name {}", hookName);
            return null;
        }
    }
}
