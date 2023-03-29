package net.stonegomes.trial.core.player;

import java.util.Collection;
import java.util.UUID;

public interface GamePlayerCache {

    /**
     * Get a player from the cache by its unique id.
     *
     * @param uuid the unique id of the player.
     * @return the player.
     */
    GamePlayer getPlayer(UUID uuid);

    /**
     * Remove a player from the cache.
     *
     * @param uuid the unique id of the player.
     */
    void removePlayer(UUID uuid);

    /**
     * Check if the cache contains a player.
     *
     * @param uuid the unique id of the player.
     * @return true if the cache contains the player.
     */
    boolean containsPlayer(UUID uuid);

    /**
     * Get all players in the cache.
     *
     * @return the players.
     */
    Collection<GamePlayer> getCachedPlayers();

}
