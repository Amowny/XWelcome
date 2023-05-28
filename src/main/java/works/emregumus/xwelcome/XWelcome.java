package works.emregumus.xwelcome;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import works.emregumus.xwelcome.Commands.XWelcomeCommand;
import works.emregumus.xwelcome.Commands.XWelcomeTabCompleter;
import works.emregumus.xwelcome.Hooks.PlaceholderAPI.XWelcomeExpansion;
import works.emregumus.xwelcome.Listeners.PlayerResourcePackListener;
import works.emregumus.xwelcome.Listeners.UpdateInventoryTitleListener;

import java.util.Objects;

public final class XWelcome extends JavaPlugin {

    private static XWelcome instance;
    private ProtocolManager protocolManager;
    private XWelcomeExpansion expansion;
    private static BukkitAudiences adventure;

    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        instance = this;

        adventure = BukkitAudiences.create(this);

        Objects.requireNonNull(Bukkit.getPluginCommand("xwelcome")).setExecutor(new XWelcomeCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("xwelcome")).setTabCompleter(new XWelcomeTabCompleter());

        this.expansion = new XWelcomeExpansion(this);
        this.expansion.register();

        this.protocolManager.addPacketListener((PacketListener)new UpdateInventoryTitleListener(this));
        new PlayerResourcePackListener();

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        reloadConfig();
    }

    @Override
    public void onDisable() {
        this.expansion.unregister();
        PlaceholderAPIPlugin.getInstance().reloadConfig();
    }

    public static XWelcome getInstance() {
        return instance;
    }
    public ProtocolManager getProtocolManager() {
        return this.protocolManager;
    }
    public ConfigurationSection getSection(String path) {
        return this.getConfig().getConfigurationSection(path);
    }
    public static BukkitAudiences getAdventure() {
        return adventure;
    }
}
