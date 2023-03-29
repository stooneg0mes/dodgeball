package net.stonegomes.trial.core.player;

import net.stonegomes.trial.core.Game;

import java.util.UUID;

public interface GamePlayerFactory {

    /**
     * Create a new game player.
     *
     * @param uuid the unique id of the player.
     * @param type the type of the player.
     * @param game the game the player is in.
     * @return the created game player.
     */
    GamePlayer createPlayer(UUID uuid, Game game, GamePlayerType type);

    /**
     * Create a new game player.
     *
     * @param uuid the unique id of the player.
     * @param type the type of the player.
     * @return the created game player.
     */
    GamePlayer createPlayer(UUID uuid, GamePlayerType type);

    /**
     * Create a new game player.
     *
     * @param uuid the unique id of the player.
     *
     * @param totalKills the total kills of the player.
     * @param monthlyKills the monthly kills of the player.
     * @param weeklyKills the weekly kills of the player.
     *
     * @param totalDeaths the total deaths of the player.
     * @param monthlyDeaths the monthly deaths of the player.
     * @param weeklyDeaths the weekly deaths of the player.
     *
     * @param totalWins the total wins of the player.
     * @param monthlyWins the monthly wins of the player.
     * @param weeklyWins the weekly wins of the player.
     *
     * @param totalDefeats the total defeats of the player.
     * @param monthlyDefeats the monthly defeats of the player.
     * @param weeklyDefeats the weekly defeats of the player.
     */
    GamePlayer createPlayer(
        UUID uuid,
        int totalKills,
        int monthlyKills,
        int weeklyKills,
        int totalDeaths,
        int monthlyDeaths,
        int weeklyDeaths,
        int totalWins,
        int monthlyWins,
        int weeklyWins,
        int totalDefeats,
        int monthlyDefeats,
        int weeklyDefeats
    );

}
