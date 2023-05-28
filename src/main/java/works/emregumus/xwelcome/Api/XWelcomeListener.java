package works.emregumus.xwelcome.Api;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class XWelcomeListener extends XWelcomePlugin implements Listener {
    public XWelcomeListener() {
        this.plugin.getServer().getPluginManager().registerEvents(this, (Plugin)this.plugin);
    }
}
