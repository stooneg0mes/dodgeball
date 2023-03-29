package net.stonegomes.trial.core.state;

import net.stonegomes.trial.core.player.GamePlayer;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public interface GameState {

    /**
     * Get the name of the game state.
     *
     * @return the name of the game state.
     */
    String getName();

    /**
     * Get the next game state.
     *
     * @return the next game state.
     */
    GameState getNextState();

    /**
     * Get the previous game state.
     *
     * @return the previous game state.
     */
    GameState getPreviousState();

    /**
     * Check if the game state is the first state.
     *
     * @return true if the game state is the first state.
     */
    default boolean isFirstState() {
        return getPreviousState() == null;
    }

    /**
     * Check if the game state is the last state.
     *
     * @return true if the game state is the last state.
     */
    default boolean isLastState() {
        return getNextState() == null;
    }

    /**
     * Called once when the game state is started.
     * @param context the game state context.
     */
    default void onGameStart(GameStateContext context) {
    }

    /**
     * Called to update a game in the game state.
     *
     * @param context the game state context.
     */
    default void onGameUpdate(GameStateContext context) {
    }

    /**
     * Called once when the game state is stopped.
     * @param context the game state context.
     */
    default void onGameStop(GameStateContext context) {
    }

    /**
     * Called when a player enters the game state.
     *
     * @param context the game state context.
     */
    default void onPlayerEnter(GameStateContext context) {
    }

    /**
     * Called when a spectator enters the game state.
     *
     * @param context the game state context.
     */
    default void onSpectatorEnter(GameStateContext context) {
    }

    /**
     * Called to update a player in the game state.
     *
     * @param context the game state context.
     */
    default void onPlayerUpdate(GameStateContext context) {
    }

    /**
     * Called when a player exits the game state.
     *
     * @param context the game state context.
     */
    default void onPlayerExit(GameStateContext context) {
    }

    /**
     * Called when a spectator exits the game state.
     *
     * @param context the game state context.
     */
    default void onSpectatorExit(GameStateContext context) {
    }

    /**
     * Called to update the scoreboard of a player in the game state.
     *
     * @param context the game state context.
     */
    default void onPlayerScoreboardUpdate(GameStateContext context) {
    }

    /**
     * Called every 10 ticks (0.5 seconds) to update the scoreboard of a spectator in the game state.
     *
     * @param context the game state context.
     */
    default void onSpectatorScoreboardUpdate(GameStateContext context) {
        onPlayerScoreboardUpdate(context);
    }

    /**
     * Called when a player is damaged by another entity.
     *
     * @param context the game state context.
     * @param attacker the player who attacked.
     * @param event the entity damage by entity event.
     */
    default void onEntityHit(GameStateContext context, GamePlayer attacker, EntityDamageByEntityEvent event) {
    }

    /**
     * Called when a player interacts with an entity.
     *
     * @param context the game state context.
     * @param event the player interact event.
     */
    default void onPlayerInteract(GameStateContext context, PlayerInteractEvent event) {
    }

    /**
     * Called when a player dies.
     *
     * @param context the game state context.
     * @param event the player death event.
     */
    default void onPlayerDeath(GameStateContext context, PlayerDeathEvent event) {
    }

    /**
     * Called when a player breaks a block.
     *
     * @param context the game state context.
     * @param event the block break event.
     */
    default void onBlockBreak(GameStateContext context, BlockBreakEvent event) {
    }

    /**
     * Called when a player places a block.
     *
     * @param context the game state context.
     * @param event the block place event.
     */
    default void onBlockPlace(GameStateContext context, BlockPlaceEvent event) {
    }

    /**
     * Called when a player moves.
     *
     * @param context the game state context.
     * @param event the player move event.
     */
    default void onPlayerMove(GameStateContext context, PlayerMoveEvent event) {
    }

}