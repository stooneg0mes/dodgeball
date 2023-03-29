package net.stonegomes.trial.plugin.game.player;

import lombok.Getter;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerMap;
import net.stonegomes.trial.core.player.GamePlayerType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class DodgeballPlayerMap implements GamePlayerMap {

    private final Map<UUID, GamePlayer> playerMap = new HashMap<>();

    @Override
    public Collection<GamePlayer> getPlayers(boolean includeSpectators) {
        if (includeSpectators) {
            return playerMap.values();
        }

        return playerMap.values().stream()
            .filter(player -> player.getType() == GamePlayerType.PLAYING)
            .collect(Collectors.toSet());
    }

    @Override
    public int size(boolean includeSpectators) {
        if (!includeSpectators) {
            return getPlayers(false).size();
        }

        return playerMap.size();
    }

    @Override
    public boolean isEmpty(boolean includeSpectators) {
        if (!includeSpectators) {
            return getPlayers(false).isEmpty();
        }

        return playerMap.isEmpty();
    }

    @Override
    public void addPlayer(GamePlayer player) {
        if (containsPlayer(player)) {
            return;
        }

        playerMap.put(player.getUniqueId(), player);
    }

    @Override
    public void removePlayer(GamePlayer player) {
        if (!containsPlayer(player)) {
            return;
        }

        playerMap.remove(player.getUniqueId());
    }

    @Override
    public boolean containsPlayer(GamePlayer player) {
        return playerMap.containsKey(player.getUniqueId());
    }

    @Override
    public void clear() {
        playerMap.clear();
    }

}
