package net.stonegomes.trial.plugin.util;

import net.stonegomes.trial.plugin.util.task.Task;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SyncTeleport {

    public static void teleport(JavaPlugin plugin, Player player, Location location) {
        if (player == null || location == null) {
            return;
        }

        Task.runTask(plugin, () -> player.teleport(location));
    }

}
