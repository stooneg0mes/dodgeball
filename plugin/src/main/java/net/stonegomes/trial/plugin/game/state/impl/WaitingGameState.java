package net.stonegomes.trial.plugin.game.state.impl;

import fr.mrmicky.fastboard.FastBoard;
import net.stonegomes.trial.plugin.util.SyncTeleport;
import net.stonegomes.trial.plugin.util.time.TimeFormatter;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.scoreboard.ScoreboardCache;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.state.GameStateContext;
import net.stonegomes.trial.core.team.GameTeamType;
import net.stonegomes.trial.plugin.DodgeballPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WaitingGameState implements GameState {

    private final DodgeballPlugin plugin;

    private final ScoreboardCache scoreboardCache;

    public WaitingGameState(DodgeballPlugin plugin) {
        this.plugin = plugin;
        this.scoreboardCache = plugin.getScoreboardCache();
    }

    @Override
    public String getName() {
        return "Waiting for Players";
    }

    @Override
    public GameState getNextState() {
        return new StartingGameState(plugin);
    }

    @Override
    public GameState getPreviousState() {
        return null;
    }

    @Override
    public void onGameUpdate(GameStateContext context) {
        final Game game = context.getGame();
        if (game.isAbleToStart()) {
            context.advanceState();
        }
    }

    @Override
    public void onPlayerEnter(GameStateContext context) {
        final Player player = context.getBukkitPlayer();
        player.sendTitle(
            "§6§lWAITING FOR PLAYERS",
            "§7Waiting for more players to join...",
            10,
            20,
            10
        );

        final Location spawnLocation = context.getGame().getWaitingLobby().getSpawnLocation();
        SyncTeleport.teleport(plugin, player, spawnLocation);

        final Inventory inventory = player.getInventory();
        inventory.setItem(3, new ItemStack(Material.RED_WOOL, 1));
        inventory.setItem(5, new ItemStack(Material.BLUE_WOOL, 1));
    }

    @Override
    public void onPlayerExit(GameStateContext context) {
        final Inventory inventory = context.getBukkitPlayer().getInventory();
        inventory.clear();
    }

    @Override
    public void onPlayerScoreboardUpdate(GameStateContext context) {
        final Game game = context.getGame();
        final GamePlayer gamePlayer = context.getPlayer();

        final FastBoard fastBoard = scoreboardCache.getScoreboard(context.getPlayer().getUniqueId());
        fastBoard.updateTitle("§6§lDODGEBALL");
        fastBoard.updateLines(
            "§7" + TimeFormatter.formatDate() + "  §8" + game.getName(),
            "",
            " Stage: §eWaiting for Players",
            " Players: §e" + game.getPlayerMap().size() + "/" + game.getMaxPlayers(),
            "",
            " Team: " + (gamePlayer.hasTeam() ? gamePlayer.getTeam().getColoredMessage() : "§eNone"),
            "",
            "§edodgeball.net"
        );
    }

    @Override
    public void onPlayerInteract(GameStateContext context, PlayerInteractEvent event) {
        final ItemStack itemStack = event.getItem();
        if (itemStack == null) return;

        final Material itemType = itemStack.getType();
        if (itemType != Material.RED_WOOL && itemType != Material.BLUE_WOOL) return;

        final GamePlayer gamePlayer = context.getPlayer();
        if (itemStack.getType() == Material.RED_WOOL) {
            gamePlayer.setTeam(GameTeamType.RED);
        } else {
            gamePlayer.setTeam(GameTeamType.BLUE);
        }

        final GameTeamType teamType = gamePlayer.getTeam();
        gamePlayer.getBukkitPlayer().sendMessage("§aYou selected the §f" + teamType.getName() + " §ateam successfully.");
    }

    @Override
    public void onEntityHit(GameStateContext context, GamePlayer attacker, EntityDamageByEntityEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockBreak(GameStateContext context, BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockPlace(GameStateContext context, BlockPlaceEvent event) {
        event.setCancelled(true);
    }

}
