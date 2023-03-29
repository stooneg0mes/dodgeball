package net.stonegomes.trial.plugin.listener;

import lombok.RequiredArgsConstructor;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@RequiredArgsConstructor
public class TrafficListener implements Listener {

    private final GamePlayerCache gamePlayerCache;
    private final GameManager gameManager;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        final Lobby lobby = gameManager.getMainLobby();
        if (lobby == null) return;

        event.getPlayer().teleport(lobby.getSpawnLocation());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        final UUID playerId = event.getPlayer().getUniqueId();
        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(playerId);
        if (gamePlayer == null || !gamePlayer.hasGame()) return;

        final Game game = gamePlayer.getGame();
        gameManager.kickPlayer(gamePlayer);
    }

}
