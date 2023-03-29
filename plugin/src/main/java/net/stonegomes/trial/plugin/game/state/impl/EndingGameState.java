package net.stonegomes.trial.plugin.game.state.impl;

import fr.mrmicky.fastboard.FastBoard;
import net.stonegomes.trial.common.time.TimeFormatter;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.scoreboard.ScoreboardCache;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.state.GameStateContext;
import net.stonegomes.trial.core.team.GameTeamType;
import net.stonegomes.trial.plugin.DodgeballPlugin;
import net.stonegomes.trial.plugin.game.player.DodgeballPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.List;

public class EndingGameState implements GameState {

    private final DodgeballPlugin plugin;

    private final ScoreboardCache scoreboardCache;

    private int timeToLeave;

    public EndingGameState(DodgeballPlugin plugin) {
        this.plugin = plugin;
        this.scoreboardCache = plugin.getScoreboardCache();

        // 15 seconds
        this.timeToLeave = 15;
    }

    @Override
    public String getName() {
        return "Ending";
    }

    @Override
    public GameState getNextState() {
        return null;
    }

    @Override
    public GameState getPreviousState() {
        return new OngoingGameState(plugin);
    }

    @Override
    public void onGameUpdate(GameStateContext context) {
        timeToLeave--;

        if (timeToLeave <= 0) {
            context.advanceState();
        }
    }

    @Override
    public void onGameStop(GameStateContext context) {
        final List<String> victoryCommands = plugin.getConfig().getStringList("victory-commands");

        final Game game = context.getGame();
        final GameTeamType winnerTeam = game.getWinnerTeam();

        for (GamePlayer player : game.getPlayers(winnerTeam)) {
            final String playerName = player.getName();
            for (String command : victoryCommands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", playerName));
            }

            player.incrementWins();
            player.toDatabase();
        }

        final GameTeamType loserTeam = winnerTeam.getOpposite();
        for (GamePlayer player : game.getPlayers(loserTeam)) {
            player.incrementDefeats();
            player.toDatabase();
        }

        final GameManager gameManager = context.getManager();
        gameManager.resetGame(game);
    }

    @Override
    public void onPlayerUpdate(GameStateContext context) {
        final GameTeamType winnerTeam = context.getGame().getWinnerTeam();
        final GameTeamType playerTeam = context.getPlayer().getTeam();

        final Player player = context.getBukkitPlayer();
        if (playerTeam == winnerTeam) {
            player.sendTitle(
                "§a§lVICTORY!",
                "§7Congratulations, your team won the game!",
                0,
                30,
                0
            );

            final Color colorFromTeam = winnerTeam == GameTeamType.RED ? Color.RED : Color.BLUE;

            final Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
            final FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.addEffect(FireworkEffect.builder()
                .with(FireworkEffect.Type.STAR)
                .withColor(colorFromTeam)
                .trail(true)
                .build()
            );
            fireworkMeta.setPower(1);

            firework.setFireworkMeta(fireworkMeta);
        } else {
            player.sendTitle(
                "§c§lDEFEAT!",
                "§7Your team lost the game, better luck next time.",
                0,
                30,
                0
            );
        }
    }

    @Override
    public void onPlayerScoreboardUpdate(GameStateContext context) {
        final GamePlayer gamePlayer = context.getPlayer();
        final Game game = context.getGame();

        final FastBoard fastBoard = scoreboardCache.getScoreboard(gamePlayer.getUniqueId());
        fastBoard.updateTitle("§6§lDODGEBALL");
        fastBoard.updateLines(
            "§7" + TimeFormatter.formatDate() + "  §8" + game.getName(),
            "",
            " Stage: §eEnding",
            " Time to End: §e" + TimeFormatter.formatSeconds(timeToLeave),
            "",
            " Team: " + (gamePlayer.hasTeam() ? gamePlayer.getTeam().getColoredMessage() : "§eNone"),
            " Score: §e" + gamePlayer.getGameKills(),
            "",
            "§edodgeball.net"
        );
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
