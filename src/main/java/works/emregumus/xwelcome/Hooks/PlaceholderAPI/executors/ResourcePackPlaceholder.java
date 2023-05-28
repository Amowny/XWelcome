package works.emregumus.xwelcome.Hooks.PlaceholderAPI.executors;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import works.emregumus.xwelcome.Hooks.PlaceholderAPI.PlaceholderExecutor;

public class ResourcePackPlaceholder extends PlaceholderExecutor {
    public ResourcePackPlaceholder() {
        super("getRspStatus");
    }

    public String execute(Player player, String params) {
        if (player.getResourcePackStatus() != PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED){
            return "false";
        }else {
            return "true";
        }
    }
}
