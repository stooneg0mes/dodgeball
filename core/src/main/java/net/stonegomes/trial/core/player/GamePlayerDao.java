package net.stonegomes.trial.core.player;

import java.util.Collection;
import java.util.UUID;

public interface GamePlayerDao<T> {

    /**
     * Replace a player in the database.
     *
     * @param gamePlayer the player.
     */
    void replace(T gamePlayer);

    /**
     * Find a player in the database by its unique id.
     *
     * @param uniqueId the unique id of the player.
     * @return the player.
     */
    T find(UUID uniqueId);

    /**
     * Delete a player in the database.
     *
     * @param gamePlayer the player.
     */
    void delete(T gamePlayer);

    /**
     * Find all players in the database.
     *
     * @return the players.
     */
    Collection<T> find();

}
