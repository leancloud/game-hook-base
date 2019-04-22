package cn.leancloud.play.hook.template;

import cn.leancloud.play.hook.HookedRoom;
import cn.leancloud.play.utils.Log;
import cn.leancloud.play.hook.GameHook;
import cn.leancloud.play.hook.HookFactory;

import java.util.Map;

public class MyFancyHookFactory implements HookFactory {
    @Override
    public GameHook create(HookedRoom room, String hookName, Map<String, String> initConfigs) {
        if (hookName != null && hookName.length() > 0) {
            switch (hookName) {
                case "fancy-hook":
                    return new MyFancyGameHook(initConfigs);
                case "master is watching you hook":
                    return new MasterIsWatchingYouHook();
            }
        }

        Log.error("unknown hook name {}", hookName);
        return null;
    }
}
