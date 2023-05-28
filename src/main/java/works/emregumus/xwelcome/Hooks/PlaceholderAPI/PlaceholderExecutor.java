package works.emregumus.xwelcome.Hooks.PlaceholderAPI;

import org.bukkit.entity.Player;
import works.emregumus.xwelcome.Api.XWelcomePlugin;

public abstract class PlaceholderExecutor extends XWelcomePlugin {
    public static String NULL_KEY = "UNDEFINED_KEY";

    protected String identify;

    public PlaceholderExecutor(String identify) {
        this.identify = identify;
    }

    public abstract String execute(Player paramPlayer, String paramString);
}
