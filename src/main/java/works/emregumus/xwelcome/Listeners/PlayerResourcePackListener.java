package works.emregumus.xwelcome.Listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import works.emregumus.xwelcome.Api.XWelcomeListener;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerResourcePackListener extends XWelcomeListener {
    public static Map<UUID, ItemStack[]> getCachePlayerStorages() {
        return cachePlayerStorages;
    }

    private static Map<UUID, ItemStack[]> cachePlayerStorages = (Map)new HashMap<>();

    public PlayerResourcePackListener() {
        this.plugin.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)this.plugin, new PacketType[] { PacketType.Play.Server.CHAT }) {
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                if (PlayerResourcePackListener.cachePlayerStorages.containsKey(player.getUniqueId()))
                    event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ConfigurationSection settingSection = this.plugin.getSection("welcome-screen");
        boolean isEnabled = settingSection.getBoolean("enable", false);
        if (isEnabled) {
            Player player = event.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 99));
            player.setWalkSpeed(0.0F);
        }
    }

    @EventHandler
    public void onResourcePackStatusChange(PlayerResourcePackStatusEvent event) {
        ConfigurationSection settingSection = this.plugin.getSection("welcome-screen");
        boolean isEnabled = settingSection.getBoolean("enable", false);
        if (!isEnabled)
            return;
        if (event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
            Player player = event.getPlayer();
            String title = PlaceholderAPI.setPlaceholders(player, settingSection.getString("background-title"));
            String welcomeTitle = PlaceholderAPI.setPlaceholders(player, settingSection.getString("welcome-title"));
            Title titleCmp = Title.title(
                    (Component)Component.text(title),
                    (Component)Component.text(""),
                    Title.Times.times(
                            Duration.ofSeconds(0L),
                            Duration.ofDays(1L),
                            Duration.ofSeconds(0L)));
            Inventory inv = Bukkit.createInventory(null, 54, (Component)Component.text(welcomeTitle));
            cachePlayerStorages.put(player.getUniqueId(), player.getInventory().getStorageContents());
            player.openInventory(inv);
            player.showTitle(titleCmp);
            player.setWalkSpeed(0.2F);
            player.getInventory().setStorageContents(new ItemStack[0]);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (isWelcomeInventory(event.getView())) {
            Player player = (Player)event.getPlayer();
            UUID uuid = player.getUniqueId();
            player.resetTitle();
            if (cachePlayerStorages.containsKey(uuid)) {
                player.getInventory().setStorageContents(cachePlayerStorages.get(uuid));
                player.removePotionEffect(PotionEffectType.BLINDNESS);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 99));
                cachePlayerStorages.remove(uuid);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                List<String> joinMotdMessages = (List<String>)this.plugin.getConfig().get("join-motd");
                joinMotdMessages.forEach(msg -> {
                    String parseMessage = PlaceholderAPI.setPlaceholders(player, msg);
                    player.sendMessage(parseMessage);
                });
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (isWelcomeInventory(event.getView()))
            event.setCancelled(true);
    }

    public boolean isWelcomeInventory(InventoryView view) {
        ConfigurationSection settingSection = this.plugin.getSection("welcome-screen");
        boolean isEnabled = settingSection.getBoolean("enable", false);
        if (!isEnabled)
            return false;
        Player player = (Player)view.getPlayer();
        String welcomeTitle = PlaceholderAPI.setPlaceholders(player, settingSection.getString("welcome-title"));
        if (welcomeTitle.equalsIgnoreCase(view.getTitle()))
            return true;
        return false;
    }
}
