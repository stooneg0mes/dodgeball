package net.stonegomes.trial.plugin.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import net.stonegomes.trial.core.scoreboard.ScoreboardCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DodgeballScoreboardCache implements ScoreboardCache {

    private final Map<UUID, FastBoard> scoreboardMap = new HashMap<>();

    @Override
    public void putScoreboard(UUID uuid, FastBoard fastBoard) {
        scoreboardMap.put(uuid, fastBoard);
    }

    @Override
    public FastBoard getScoreboard(UUID uuid) {
        final FastBoard fastBoard = scoreboardMap.get(uuid);
        if (fastBoard == null) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                return null;
            }

            final FastBoard newBoard = new FastBoard(player);
            putScoreboard(uuid, newBoard);

            return newBoard;
        }

        return fastBoard;
    }

    @Override
    public boolean hasScoreboard(UUID uuid) {
        return scoreboardMap.containsKey(uuid);
    }

    @Override
    public void removeScoreboard(UUID uuid) {
        if (!hasScoreboard(uuid)) {
            return;
        }

        final FastBoard fastBoard = scoreboardMap.remove(uuid);
        fastBoard.delete();
    }

    @Override
    public Collection<FastBoard> getScoreboards() {
        return scoreboardMap.values();
    }

}