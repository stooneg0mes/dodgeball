package net.stonegomes.trial.core;

import java.util.Collection;
import java.util.UUID;

public interface GameDao<T> {

    /**
     * Replace the game in the database.
     *
     * @param game the game to replace.
     */
    void replace(T game);

    /**
     * Find a game in the database by its unique id.
     *
     * @param uniqueId the unique id of the game.
     * @return the game.
     */
    Game find(UUID uniqueId);

    /**
     * Delete a game from the database.
     *
     * @param game the game to delete.
     */
    void delete(T game);

    /**
     * Find all games in the database.
     *
     * @return the games.
     */
    Collection<T> find();

    /**
     * Load all games from the database in a game map.
     *
     * @param gameMap the game map.
     */
    void loadAll(GameMap gameMap);

}
