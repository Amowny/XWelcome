package works.emregumus.xwelcome.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import works.emregumus.xwelcome.Api.Utils.AdventureUtil;
import works.emregumus.xwelcome.XWelcome;

import javax.annotation.ParametersAreNonnullByDefault;

public class XWelcomeCommand implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                AdventureUtil.sendMessage(player, "<green>Running <aqua>XWelcome <green>by <gray>Amownyy <green>for <dark_aqua>Commissions");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("xwelcome.reload")) {
                XWelcome.getInstance().saveConfig();
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    AdventureUtil.sendMessage(player, "<green>Reloaded <aqua>XWelcome <green>by <gray>Amownyy <green>for <dark_aqua>Commissions");
                } else {
                    XWelcome.getInstance().saveConfig();
                    AdventureUtil.consoleMessage("<green>Reloaded <aqua>XWelcome <green>by <gray>Amownyy <green>for <dark_aqua>Commissions");
                }
                return true;
            }
            return true;
        }
        return false;
    }
}
