package net.stonegomes.trial.plugin.game.state.impl;

import fr.mrmicky.fastboard.FastBoard;
import net.stonegomes.trial.common.SyncTeleport;
import net.stonegomes.trial.common.location.cuboid.CuboidRegion;
import net.stonegomes.trial.common.time.TimeFormatter;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerType;
import net.stonegomes.trial.core.scoreboard.ScoreboardCache;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.state.GameStateContext;
import net.stonegomes.trial.core.team.GameTeamType;
import net.stonegomes.trial.plugin.DodgeballPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.stream.Collectors;

public class OngoingGameState implements GameState {

    private final DodgeballPlugin plugin;

    private final ScoreboardCache scoreboardCache;

    private int timeToLive;

    public OngoingGameState(DodgeballPlugin plugin) {
        this.plugin = plugin;
        this.scoreboardCache = plugin.getScoreboardCache();

        // 2 minutes
        this.timeToLive = (60 * 2);
    }

    @Override
    public String getName() {
        return "In Game";
    }

    @Override
    public GameState getNextState() {
        return new EndingGameState(plugin);
    }

    @Override
    public GameState getPreviousState() {
        return new StartingGameState(plugin);
    }

    @Override
    public void onGameUpdate(GameStateContext context) {
        timeToLive--;

        final Game game = context.getGame();

        final Collection<GamePlayer> redTeamPlayers = game.getRedTeamPlayers().stream()
            .filter(player -> player.getType() == GamePlayerType.PLAYING)
            .collect(Collectors.toSet());

        final Collection<GamePlayer> blueTeamPlayers = game.getBlueTeamPlayers().stream()
            .filter(player -> player.getType() == GamePlayerType.PLAYING)
            .collect(Collectors.toSet());

        if (redTeamPlayers.isEmpty() || blueTeamPlayers.isEmpty()) {
            pickWinnerAndAdvanceState(context);
        } else if (timeToLive <= 0) {
            pickWinnerAndAdvanceState(context);
        }
    }


    @Override
    public void onPlayerEnter(GameStateContext context) {
        final GamePlayer gamePlayer = context.getPlayer();
        final Game game = context.getGame();

        final Player player = context.getBukkitPlayer();
        player.getInventory().setItem(4, new ItemStack(Material.DIAMOND_HOE));

        final Location spawnLocation;
        if (gamePlayer.getTeam() == GameTeamType.RED) {
            spawnLocation = game.getRedTeamCuboid().getSpawnLocation();
        } else {
            spawnLocation = game.getBlueTeamCuboid().getSpawnLocation();
        }

        SyncTeleport.teleport(plugin, player, spawnLocation);
    }

    @Override
    public void onSpectatorEnter(GameStateContext context) {
        final Game game = context.getGame();
        final Player player = context.getBukkitPlayer();
        SyncTeleport.teleport(plugin, player, game.getSpectatingLobby().getSpawnLocation());
    }

    @Override
    public void onPlayerExit(GameStateContext context) {
        final Player player = context.getBukkitPlayer();
        player.getInventory().clear();
    }

    @Override
    public void onPlayerScoreboardUpdate(GameStateContext context) {
        final Game game = context.getGame();
        final GamePlayer gamePlayer = context.getPlayer();

        final FastBoard fastBoard = scoreboardCache.getScoreboard(gamePlayer.getUniqueId());
        fastBoard.updateTitle("§6§lDODGEBALL");
        fastBoard.updateLines(
            "§7" + TimeFormatter.formatDate() + "  §8" + game.getName(),
            "",
            " Stage: §eIn Game",
            " Players: §e" + game.getPlayerMap().size() + "/" + game.getMaxPlayers(),
            " Time to End: §e" + TimeFormatter.formatSeconds(timeToLive),
            "",
            " Team: " + (gamePlayer.hasTeam() ? gamePlayer.getTeam().getColoredMessage() : "§eNone"),
            " Score: §e" + gamePlayer.getGameKills(),
            "",
            "§edodgeball.net"
        );
    }

    @Override
    public void onPlayerInteract(GameStateContext context, PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = event.getItem();
        if (itemStack == null) return;

        final Material itemType = itemStack.getType();
        if (itemType != Material.DIAMOND_HOE) return;

        final Snowball snowball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
        snowball.setShooter(player);
        snowball.setVelocity(player.getLocation().getDirection().multiply(1.5));
    }

    @Override
    public void onEntityHit(GameStateContext context, GamePlayer attackerPlayer, EntityDamageByEntityEvent event) {
        final GamePlayer victimPlayer = context.getPlayer();
        final Entity damagerEntity = event.getDamager();

        final GameTeamType victimTeam = victimPlayer.getTeam();
        final GameTeamType attackerTeam = attackerPlayer.getTeam();

        if (damagerEntity instanceof Snowball) {
            if (victimTeam == attackerTeam) {
                event.setCancelled(true);
            }

            attackerPlayer.incrementKills();
            attackerPlayer.toDatabase();

            context.getManager().sendSpectatingPlayer(victimPlayer, context.getGame());
            victimPlayer.incrementDeaths();
            victimPlayer.toDatabase();
        }

        if ((damagerEntity instanceof Player) && (victimTeam == attackerTeam)) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onPlayerMove(GameStateContext context, PlayerMoveEvent event) {
        final Location toLocation = event.getTo();
        if (toLocation == null) return;

        final Game game = context.getGame();
        final CuboidRegion redTeamCuboid = game.getRedTeamCuboid();
        final CuboidRegion blueTeamCuboid = game.getBlueTeamCuboid();

        final GameTeamType teamType = context.getPlayer().getTeam();
        if (teamType == GameTeamType.RED && redTeamCuboid.contains(toLocation)
            || teamType == GameTeamType.BLUE && blueTeamCuboid.contains(toLocation)
        ) {
            return;
        }

        final Player player = event.getPlayer();
        player.sendMessage("§cYou cannot leave your team's area!");

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

    private void pickWinnerAndAdvanceState(GameStateContext context) {
        final Game game = context.getGame();
        final GameTeamType winnerTeam = game.pickWinner();

        if (winnerTeam != null && !game.hasWinnerTeam()) {
            game.setWinnerTeam(winnerTeam);
            context.advanceState();
        }
    }

}
