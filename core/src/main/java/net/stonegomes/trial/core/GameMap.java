package net.stonegomes.trial.core;

import java.util.Collection;
import java.util.UUID;

public interface GameMap {

    /**
     * Put a game in the map.
     *
     * @param id the id of the game.
     * @param game the game.
     */
    void putGame(UUID id, Game game);

    /**
     * Remove a game from the map.
     *
     * @param id the id of the game.
     */
    void removeGame(UUID id);

    /**
     * Get a game by its id.
     *
     * @param id the id of the game.
     * @return the game.
     */
    Game getGame(UUID id);

    /**
     * Get a game by its name.
     *
     * @param name the name of the game.
     * @return the game.
     */
    Game getGameByName(String name);

    /**
     * Check if the game map contains a game.
     *
     * @param id the id of the game.
     * @return true if the game map contains the game.
     */
    boolean containsGame(UUID id);

    /**
     * Get all games in the map.
     * @return the games.
     */
    Collection<Game> getGames();

}