package net.stonegomes.trial.plugin.game;

import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DodgeballGameMap implements GameMap {

    private final Map<UUID, Game> gameMap = new HashMap<>();

    @Override
    public void putGame(UUID id, Game game) {
        gameMap.put(id, game);
    }

    @Override
    public void removeGame(UUID id) {
        gameMap.remove(id);
    }

    @Override
    public Game getGame(UUID id) {
        return gameMap.get(id);
    }

    @Override
    public Game getGameByName(String name) {
        return gameMap.values().stream()
            .filter(game -> game.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean containsGame(UUID id) {
        return gameMap.containsKey(id);
    }

    @Override
    public Collection<Game> getGames() {
        return gameMap.values();
    }

}