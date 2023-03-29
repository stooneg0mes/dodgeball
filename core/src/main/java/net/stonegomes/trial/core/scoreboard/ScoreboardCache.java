package net.stonegomes.trial.core.scoreboard;

import fr.mrmicky.fastboard.FastBoard;

import java.util.Collection;
import java.util.UUID;

public interface ScoreboardCache {

    /**
     * Put a scoreboard player in the cache.
     *
     * @param uuid the unique id of the player.
     * @param fastBoard the scoreboard.
     */
    void putScoreboard(UUID uuid, FastBoard fastBoard);

    /**
     * Get a scoreboard player from the cache.
     *
     * @param uuid the unique id of the player.
     * @return the scoreboard.
     */
    FastBoard getScoreboard(UUID uuid);

    /**
     * Check if the scoreboard cache contains a scoreboard player.
     *
     * @param uuid the unique id of the player.
     * @return true if the scoreboard cache contains the scoreboard player.
     */
    boolean hasScoreboard(UUID uuid);

    /**
     * Remove a scoreboard player from the cache.
     *
     * @param uuid the unique id of the player.
     */
    void removeScoreboard(UUID uuid);

    /**
     * Get all scoreboards in the cache.
     *
     * @return the scoreboards.
     */
    Collection<FastBoard> getScoreboards();

}