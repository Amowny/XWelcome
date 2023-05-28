package works.emregumus.xwelcome.Hooks.PlaceholderAPI;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import works.emregumus.xwelcome.Hooks.PlaceholderAPI.executors.ResourcePackPlaceholder;
import works.emregumus.xwelcome.XWelcome;

import java.util.HashMap;
import java.util.Map;

public class XWelcomeExpansion extends PlaceholderExpansion {
    private XWelcome plugin;

    private static Map<String, PlaceholderExecutor> executors = new HashMap<>();

    public XWelcomeExpansion(XWelcome plugin) {
        this.plugin = plugin;
        addExecutors(new PlaceholderExecutor[] {
                (PlaceholderExecutor)new ResourcePackPlaceholder()
        });
    }

    private void addExecutor(PlaceholderExecutor executor) {
        executors.put(executor.identify, executor);
    }

    private void addExecutors(PlaceholderExecutor... executorList) {
        for (PlaceholderExecutor placeholderExecutor : executorList)
            addExecutor(placeholderExecutor);
    }

    @NotNull
    public String getIdentifier() {
        return "xwelcome";
    }

    @NotNull
    public String getAuthor() {
        return "Amownyy";
    }

    @NotNull
    public String getVersion() {
        return "1.0.0-BETA";
    }

    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (params.contains(":") && (params.startsWith("get") || params.startsWith("has"))) {
            String[] data = params.split(":");
            String identify = data[0];
            if (executors.containsKey(identify)) {
                PlaceholderExecutor placeholderHandler = executors.get(identify);
                String executorParams = params.replace(identify + ":", "");
                return placeholderHandler.execute(player, executorParams);
            }
        }
        return "UNDEFINED_PLACEHOLDER";
    }
}
