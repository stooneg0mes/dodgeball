package net.stonegomes.trial.core.state;

import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.player.GamePlayer;
import org.bukkit.entity.Player;

public interface GameStateContext {

    /**
     * Get the game manager
     *
     * @return the manager
     */
    GameManager getManager();

    /**
     * Get the game player
     *
     * @return the player
     */
    GamePlayer getPlayer();

    /**
     * Get the game
     *
     * @return the game
     */
    Game getGame();

    /**
     * Get the bukkit player
     *
     * @return the bukkit player
     */
    Player getBukkitPlayer();

    /**
     * Get the current game state
     *
     * @return the state
     */
    GameState getGameState();

    /**
     * Go to the previous game state
     */
    void backState();

    /**
     * Go to the next game state
     */
    void advanceState();

    /**
     * Check if the player is playing
     *
     * @return true if the player is playing
     */
    default boolean isPlaying() {
        final GamePlayer player = getPlayer();
        if (player == null) return false;

        return player.hasGame();
    }

}