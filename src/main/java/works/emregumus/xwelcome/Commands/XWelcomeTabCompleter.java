package works.emregumus.xwelcome.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XWelcomeTabCompleter implements TabCompleter {

    @Override
    @ParametersAreNonnullByDefault
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission("beylandsauthy.admin")) {
            if (1 == args.length) {
                return Collections.singletonList("reload");
            }
        }
        return null;
    }
}
