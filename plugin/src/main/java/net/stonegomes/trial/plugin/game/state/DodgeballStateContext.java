package net.stonegomes.trial.plugin.game.state;

import lombok.Getter;
import lombok.experimental.Delegate;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.state.GameStateContext;
import org.bukkit.entity.Player;

@Getter
public class DodgeballStateContext implements GameStateContext  {

    @Delegate
    private final GameManager manager;

    private final GamePlayer player;
    private final Game game;

    public DodgeballStateContext(GameManager manager, GamePlayer player, Game game) {
        this.manager = manager;
        this.player = player;
        this.game = game;
    }

    public DodgeballStateContext(GameManager manager, GamePlayer player) {
        this(manager, player, player.getGame());
    }

    public DodgeballStateContext(GameManager manager, Game game) {
        this(manager, null, game);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public Player getBukkitPlayer() {
        return player.getBukkitPlayer();
    }

    @Override
    public GameState getGameState() {
        return game.getCurrentState();
    }

    @Override
    public void backState() {
        final GameState previousState = game.getCurrentState().getPreviousState();
        handleStateChange(previousState);
    }

    @Override
    public void advanceState() {
        final GameState nextState = game.getCurrentState().getNextState();
        handleStateChange(nextState);
    }

    private void handleStateChange(GameState otherState) {
        if (game == null) return;

        final GameState currentState = game.getCurrentState();
        if (currentState == null) return;

        final GameStateContext gameContext = manager.createContext(game);
        currentState.onGameStop(gameContext);

        for (GamePlayer gamePlayer : game.getPlayerMap().getPlayers()) {
            final GameStateContext playerContext = manager.createContext(gamePlayer);
            currentState.onPlayerExit(playerContext);

            if (otherState != null) {
                switch (gamePlayer.getType()) {
                    case SPECTATING -> otherState.onSpectatorScoreboardUpdate(playerContext);
                    case PLAYING -> otherState.onPlayerScoreboardUpdate(playerContext);
                }

                otherState.onPlayerEnter(playerContext);
                game.setCurrentState(otherState);
            }
        }

        if (otherState != null) {
            otherState.onGameStart(gameContext);
        }
    }

}
