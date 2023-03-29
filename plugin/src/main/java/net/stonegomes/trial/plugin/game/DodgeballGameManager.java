package net.stonegomes.trial.plugin.game;

import lombok.Getter;
import net.stonegomes.trial.common.SyncGameMode;
import net.stonegomes.trial.common.SyncTeleport;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.GameMap;
import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerMap;
import net.stonegomes.trial.core.player.GamePlayerType;
import net.stonegomes.trial.core.scoreboard.ScoreboardCache;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.state.GameStateContext;
import net.stonegomes.trial.plugin.DodgeballPlugin;
import net.stonegomes.trial.plugin.game.state.DodgeballStateContext;
import net.stonegomes.trial.plugin.game.state.impl.WaitingGameState;
import net.stonegomes.trial.plugin.lobby.DodgeballWorldLobby;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Getter
public class DodgeballGameManager implements GameManager {

    private final ScoreboardCache scoreboardCache;
    private final DodgeballPlugin plugin;

    private final GameMap gameMap;

    private final Lobby mainLobby;

    public DodgeballGameManager(ScoreboardCache scoreboardCache, DodgeballPlugin plugin) {
        this.scoreboardCache = scoreboardCache;
        this.plugin = plugin;

        this.gameMap = new DodgeballGameMap();
        this.mainLobby = new DodgeballWorldLobby();
    }

    @Override
    public GameStateContext createContext(GamePlayer player) {
        return new DodgeballStateContext(this, player);
    }

    @Override
    public GameStateContext createContext(Game game) {
        return new DodgeballStateContext(this, game);
    }

    @Override
    public GameStateContext createContext(GamePlayer player, Game game) {
        return new DodgeballStateContext(this, player, game);
    }

    @Override
    public void resetGame(Game game) {
        final GameState currentState = game.getCurrentState();
        if (currentState.isFirstState() || !game.hasWinnerTeam()) return;

        for (GamePlayer player : game.getPlayerMap().getPlayers()) {
            kickPlayer(player);
        }

        game.getPlayerMap().clear();
        game.setWinnerTeam(null);
        game.setCurrentState(new WaitingGameState(plugin));
    }

    @Override
    public void sendPlayer(GamePlayer player, Game game) {
        final GameState currentState = game.getCurrentState();
        if (game.isFull() || !currentState.isFirstState()) return;

        player.setType(GamePlayerType.PLAYING);
        player.setGame(game);

        game.getPlayerMap().addPlayer(player);
        game.getCurrentState().onPlayerEnter(createContext(player));
        broadcastPlayerJoin(player);

        final Player bukkitPlayer = player.getBukkitPlayer();
        SyncGameMode.setGameMode(plugin, bukkitPlayer, GameMode.SURVIVAL);
    }

    @Override
    public void sendSpectatingPlayer(GamePlayer player, Game game) {
        player.setType(GamePlayerType.SPECTATING);
        player.setGame(game);

        final GamePlayerMap playerMap = game.getPlayerMap();
        if (playerMap.containsPlayer(player)) {
            broadcastPlayerEliminated(player);
        } else {
            playerMap.addPlayer(player);
        }

        game.getCurrentState().onSpectatorEnter(createContext(player));

        final Player bukkitPlayer = player.getBukkitPlayer();
        SyncGameMode.setGameMode(plugin, bukkitPlayer, GameMode.SPECTATOR);
    }

    @Override
    public void kickPlayer(GamePlayer player) {
        scoreboardCache.removeScoreboard(player.getUniqueId());

        final Game game = player.getGame();
        if (!game.hasWinnerTeam()) {
            game.getPlayerMap().removePlayer(player);
        }

        final GameState currentState = game.getCurrentState();
        final GameStateContext context = createContext(player);
        switch (player.getType()) {
            case PLAYING -> currentState.onPlayerExit(context);
            case SPECTATING -> currentState.onSpectatorExit(context);
        }

        broadcastPlayerQuit(player);
        player.setType(GamePlayerType.NONE);
        player.setGameKills(0);
        player.setGame(null);
        player.setTeam(null);

        final Player bukkitPlayer = player.getBukkitPlayer();
        SyncGameMode.setGameMode(plugin, bukkitPlayer, GameMode.SURVIVAL);
        SyncTeleport.teleport(plugin, bukkitPlayer, mainLobby.getSpawnLocation());
    }

    @Override
    public void broadcastMessage(Game game, String message) {
        for (GamePlayer player : game.getPlayerMap().getPlayers()) {
            player.getBukkitPlayer().sendMessage(message);
        }
    }

    private void broadcastPlayerJoin(GamePlayer player) {
        final Game game = player.getGame();
        final int currentPlayers = game.getPlayerMap().getPlayers().size();
        final int maxPlayers = game.getMaxPlayers();

        final String currentAndMaxPlayers = "§b" + currentPlayers + "§e/§b" + maxPlayers;
        final String playerName = player.getName();

        broadcastMessage(game, "§7" + playerName + "§e joined the game! (" + currentAndMaxPlayers + "§e)");
    }

    private void broadcastPlayerQuit(GamePlayer player) {
        final String playerName = player.getName();
        broadcastMessage(player.getGame(), "§c" + playerName + " has left the game.");
    }

    private void broadcastPlayerEliminated(GamePlayer player) {
        final String playerName = player.getName();
        broadcastMessage(player.getGame(), "§c" + playerName + " has been eliminated.");
    }

}
