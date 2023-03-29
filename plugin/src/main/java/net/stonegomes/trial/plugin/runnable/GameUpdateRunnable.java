package net.stonegomes.trial.plugin.runnable;

import lombok.RequiredArgsConstructor;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerCache;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.state.GameStateContext;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class GameUpdateRunnable extends BukkitRunnable {

    private final GamePlayerCache gamePlayerCache;
    private final GameManager gameManager;

    private double halfSecond = 0;

    @Override
    public void run() {
        halfSecond += 0.5;

        for (Game game : gameManager.getGameMap().getGames()) {
            final GameState currentState = game.getCurrentState();

            final GameStateContext gameContext = gameManager.createContext(game);
            if (hasPassedSecond()) {
                currentState.onGameUpdate(gameContext);
            }

            for (GamePlayer gamePlayer : game.getPlayerMap().getPlayers()) {
                final GameStateContext playerContext = gameManager.createContext(gamePlayer, game);
                if (hasPassedSecond()) {
                    currentState.onPlayerUpdate(playerContext);
                }

                switch (gamePlayer.getType()) {
                    case PLAYING -> currentState.onPlayerScoreboardUpdate(playerContext);
                    case SPECTATING -> currentState.onSpectatorScoreboardUpdate(playerContext);
                }
            }
        }

        if (hasPassedSecond()) {
            halfSecond = 0;
        }
    }

    private boolean hasPassedSecond() {
        return halfSecond >= 1;
    }

}
