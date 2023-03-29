package net.stonegomes.trial.core.player;

import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.team.GameTeamType;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface GamePlayer {

    /**
     * Get the player unique id.
     *
     * @return the unique id.
     */
    UUID getUniqueId();

    /**
     * Get the type of the player.
     *
     * @return the type.
     */
    GamePlayerType getType();

    /**
     * Set the type of the player.
     *
     * @param type the type.
     */
    void setType(GamePlayerType type);

    /**
     * Get the player current game.
     *
     * @return the game.
     */
    Game getGame();

    /**
     * Check if the player is in a game.
     *
     * @return true if the player is in a game.
     */
    boolean hasGame();

    void setGame(Game game);

    /**
     * Get the team of the player.
     *
     * @return the team.
     */
    GameTeamType getTeam();

    /**
     * Check if the player has a team.
     *
     * @return true if the player has a team.
     */
    boolean hasTeam();

    /**
     * Set the team of the player.
     *
     * @param team the team.
     */
    void setTeam(GameTeamType team);

    /**
     * Get the player monthly kills.
     *
     * @return the monthly kills.
     */
    int getMonthlyKills();

    /**
     * Set the player monthly kills.
     *
     * @param monthlyKills the monthly kills.
     */
    void setMonthlyKills(int monthlyKills);

    /**
     * Get the player weekly kills.
     *
     * @return the weekly kills.
     */
    int getWeeklyKills();

    /**
     * Set the player weekly kills.
     *
     * @param weeklyKills the weekly kills.
     */
    void setWeeklyKills(int weeklyKills);

    /**
     * Get the player game kills.
     *
     * @return the game kills.
     */
    int getGameKills();

    /**
     * Set the player game kills.
     *
     * @param gameKills the game kills.
     */
    void setGameKills(int gameKills);

    /**
     * Get the player total kills.
     *
     * @return the total kills.
     */
    int getTotalKills();

    /**
     * Increment all kills.
     */
    void incrementKills();

    /**
     * Get the player monthly deaths.
     *
     * @return the monthly deaths.
     */
    int getMonthlyDeaths();

    /**
     * Set the player monthly deaths.
     *
     * @param monthlyDeaths the monthly deaths.
     */
    void setMonthlyDeaths(int monthlyDeaths);

    /**
     * Get the player weekly deaths.
     *
     * @return the weekly deaths.
     */
    int getWeeklyDeaths();

    /**
     * Set the player weekly deaths.
     *
     * @param weeklyDeaths the weekly deaths.
     */
    void setWeeklyDeaths(int weeklyDeaths);

    /**
     * Get the player total deaths.
     *
     * @return the total deaths.
     */
    int getTotalDeaths();

    /**
     * Increment all deaths.
     */
    void incrementDeaths();

    /**
     * Get the player monthly wins.
     *
     * @return the monthly wins.
     */
    int getMonthlyWins();

    /**
     * Set the player monthly wins.
     *
     * @param monthlyWins the monthly wins.
     */
    void setMonthlyWins(int monthlyWins);

    /**
     * Get the player weekly wins.
     *
     * @return the weekly wins.
     */
    int getWeeklyWins();

    /**
     * Set the player weekly wins.
     *
     * @param weeklyWins the weekly wins.
     */
    void setWeeklyWins(int weeklyWins);

    /**
     * Get the player total wins.
     *
     * @return the total wins.
     */
    int getTotalWins();

    /**
     * Increment all wins.
     */
    void incrementWins();

    /**
     * Get the player monthly defeats.
     *
     * @return the monthly defeats.
     */
    int getMonthlyDefeats();

    /**
     * Set the player monthly defeats.
     *
     * @param monthlyDefeats the monthly defeats.
     */
    void setMonthlyDefeats(int monthlyDefeats);

    /**
     * Get the player weekly defeats.
     *
     * @return the weekly defeats.
     */
    int getWeeklyDefeats();

    /**
     * Set the player weekly defeats.
     *
     * @param weeklyDefeats the weekly defeats.
     */
    void setWeeklyDefeats(int weeklyDefeats);

    /**
     * Get the player total defeats.
     *
     * @return the total defeats.
     */
    int getTotalDefeats();

    /**
     * Increment all defeats.
     */
    void incrementDefeats();

    /**
     * Get the bukkit player.
     *
     * @return the bukkit player.
     */
    Player getBukkitPlayer();

    /**
     * Get the name of the player.
     *
     * @return the name.
     */
    String getName();

    /**
     * Check if the player is in the same game as another player.
     *
     * @param gamePlayer the player to check.
     * @return true if the player is in the same game.
     */
    boolean isInSameGame(GamePlayer gamePlayer);

    /**
     * Check if the player is in the same team as another player.
     *
     * @param gamePlayer the player to check.
     * @return true if the player is in the same team.
     */
    boolean isInSameTeam(GamePlayer gamePlayer);

}
