package net.stonegomes.trial.plugin.util;

import net.stonegomes.trial.plugin.util.task.Task;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SyncGameMode {

    public static void setGameMode(JavaPlugin plugin, Player player, GameMode gameMode) {
        if (player == null || gameMode == null) {
            return;
        }

        Task.runTask(plugin, () -> player.setGameMode(gameMode));
    }

}
