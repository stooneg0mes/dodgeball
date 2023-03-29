package net.stonegomes.trial.core;

import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.state.GameStateContext;

public interface GameManager {

    /**
     * Get the game map.
     * @return the game map.
     */
    GameMap getGameMap();

    /**
     * Get the main lobby.
     *
     * @return the main lobby.
     */
    Lobby getMainLobby();

    /**
     * Create a new game state context.
     *
     * @param player the player.
     * @return the context.
     */
    GameStateContext createContext(GamePlayer player);

    /**
     * Create a new game state context.
     *
     * @param game the game.
     * @return the context.
     */
    GameStateContext createContext(Game game);

    /**
     * Create a new game state context.
     *
     * @param player the player.
     * @param game the game.
     * @return the context.
     */
    GameStateContext createContext(GamePlayer player, Game game);

    /**
     * Reset a game.
     *
     * @param game the game.
     */
    void resetGame(Game game);

    /**
     * Send a player to a game.
     *
     * @param player the player.
     * @param game the game.
     */
    void sendPlayer(GamePlayer player, Game game);

    /**
     * Send a player to a game as a spectator.
     *
     * @param player the player.
     * @param game the game.
     */
    void sendSpectatingPlayer(GamePlayer player, Game game);

    /**
     * Kick a player from its current game.
     *
     * @param player the player.
     */
    void kickPlayer(GamePlayer player);

    /**
     * Broadcast a message to all players in a game.
     *
     * @param game the game.
     * @param message the message.
     */
    void broadcastMessage(Game game, String message);

}
