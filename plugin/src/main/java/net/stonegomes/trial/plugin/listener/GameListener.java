package net.stonegomes.trial.plugin.listener;

import lombok.RequiredArgsConstructor;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerCache;
import net.stonegomes.trial.core.state.GameStateContext;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

@RequiredArgsConstructor
public class GameListener implements Listener {

    private final GamePlayerCache gamePlayerCache;
    private final GameManager gameManager;

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;

        final Entity damager = event.getDamager();
        GamePlayer gameAttacker;
        if ((damager instanceof Snowball snowball) && (snowball.getShooter() instanceof Player shooter)) {
            gameAttacker = gamePlayerCache.getPlayer(shooter.getUniqueId());
        } else if (damager instanceof Player player) {
            gameAttacker = gamePlayerCache.getPlayer(player.getUniqueId());
        } else {
            return;
        }

        final GamePlayer gameVictim = gamePlayerCache.getPlayer(victim.getUniqueId());
        if (!gameAttacker.isInSameGame(gameVictim)) return;

        final Game game = gameAttacker.getGame();
        final GameStateContext context = gameManager.createContext(gameVictim);
        game.getCurrentState().onEntityHit(context, gameAttacker, event);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(event.getPlayer().getUniqueId());
        if (!gamePlayer.hasGame()) return;

        final Game game = gamePlayer.getGame();
        final GameStateContext context = gameManager.createContext(gamePlayer);
        game.getCurrentState().onPlayerInteract(context, event);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(event.getEntity().getUniqueId());
        if (!gamePlayer.hasGame()) return;

        final Game game = gamePlayer.getGame();
        final GameStateContext context = gameManager.createContext(gamePlayer);
        game.getCurrentState().onPlayerDeath(context, event);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(event.getPlayer().getUniqueId());
        if (!gamePlayer.hasGame()) return;

        final Game game = gamePlayer.getGame();
        final GameStateContext context = gameManager.createContext(gamePlayer);
        game.getCurrentState().onBlockBreak(context, event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(event.getPlayer().getUniqueId());
        if (!gamePlayer.hasGame()) return;

        final Game game = gamePlayer.getGame();
        final GameStateContext context = gameManager.createContext(gamePlayer);
        game.getCurrentState().onBlockPlace(context, event);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        final Location toLocation = event.getTo();
        if (toLocation == null) return;

        final Location fromLocation = event.getFrom();
        if (fromLocation.getBlockX() == toLocation.getBlockX()
            && fromLocation.getBlockZ() == toLocation.getBlockZ()
        ) return;

        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(event.getPlayer().getUniqueId());
        if (!gamePlayer.hasGame()) return;

        final Game game = gamePlayer.getGame();
        final GameStateContext context = gameManager.createContext(gamePlayer);
        game.getCurrentState().onPlayerMove(context, event);
    }

}
