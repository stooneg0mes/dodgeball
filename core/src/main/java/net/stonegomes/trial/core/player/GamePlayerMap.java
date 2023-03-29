package net.stonegomes.trial.core.player;

import java.util.Collection;

public interface GamePlayerMap {

    /**
     * Get the players in the map.
     *
     * @param includeSpectators whether to include spectators.
     * @return the players.
     */
    Collection<GamePlayer> getPlayers(boolean includeSpectators);

    /**
     * Get the players in the map (including spectators).
     *
     * @return the players.
     */
    default Collection<GamePlayer> getPlayers() {
        return getPlayers(true);
    }

    /**
     * Get the size of the map.
     *
     * @param includeSpectators whether to include spectators.
     * @return the size.
     */
    int size(boolean includeSpectators);

    /**
     * Get the size of the map (including spectators).
     *
     * @return the size.
     */
    default int size() {
        return size(true);
    }

    /**
     * Checks if the map is empty.
     *
     * @param includeSpectators whether to include spectators.
     * @return true if the map is empty.
     */
    boolean isEmpty(boolean includeSpectators);

    /**
     * Checks if the map is empty (including spectators).
     *
     * @return true if the map is empty.
     */
    default boolean isEmpty() {
        return isEmpty(true);
    }

    /**
     * Add a player to the map.
     *
     * @param player the player.
     */
    void addPlayer(GamePlayer player);

    /**
     * Remove a player from the map.
     *
     * @param player the player.
     */
    void removePlayer(GamePlayer player);

    /**
     * Check if the map contains a player.
     *
     * @param player the player.
     * @return true if the map contains the player.
     */
    boolean containsPlayer(GamePlayer player);

    /**
     * Clear the map.
     */
    void clear();

}
