package works.emregumus.xwelcome.Listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.plugin.Plugin;
import works.emregumus.xwelcome.XWelcome;
import works.emregumus.xwelcome.Api.Utils.XString;

public class UpdateInventoryTitleListener extends PacketAdapter {
    private XWelcome plugin;

    public UpdateInventoryTitleListener(XWelcome plugin) {
        super((Plugin)plugin, ListenerPriority.NORMAL, new PacketType[] { PacketType.Play.Server.OPEN_WINDOW });
        this.plugin = plugin;
    }

    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        if (packet.getType() == PacketType.Play.Server.OPEN_WINDOW) {
            WrappedChatComponent title = (WrappedChatComponent)packet.getChatComponents().read(0);
            BaseComponent[] titleCmp = ComponentSerializer.parse(title.getJson());
            StringBuilder invTitle = new StringBuilder();
            for (BaseComponent baseComponent : titleCmp)
                invTitle.append(baseComponent.toLegacyText());
            if (invTitle.toString().contains("placeholder;")) {
                String titleStr = invTitle.toString().replace("placeholder;", "");
                String normalizeTitle = PlaceholderAPI.setPlaceholders(event.getPlayer(), XString.colorize(titleStr));
                packet.getChatComponents().write(0, WrappedChatComponent.fromJson(ComponentSerializer.toString(normalizeTitle)));
            }
        }
    }
}
