package net.stonegomes.trial.plugin.game.state.impl;

import fr.mrmicky.fastboard.FastBoard;
import lombok.RequiredArgsConstructor;
import net.stonegomes.trial.plugin.util.time.TimeFormatter;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.scoreboard.ScoreboardCache;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.state.GameStateContext;
import net.stonegomes.trial.core.team.GameTeamType;
import net.stonegomes.trial.plugin.DodgeballPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Set;

@RequiredArgsConstructor
public class StartingGameState implements GameState {

    private static final Set<Integer> NOTICING_TIMES = Set.of(30, 15, 10, 5, 4, 3, 2, 1);

    private final DodgeballPlugin plugin;

    private final ScoreboardCache scoreboardCache;

    private int timeToLive;

    public StartingGameState(DodgeballPlugin plugin) {
        this.plugin = plugin;
        this.scoreboardCache = plugin.getScoreboardCache();

        // 45 seconds
        this.timeToLive = 45;
    }

    @Override
    public String getName() {
        return "Starting Game";
    }

    @Override
    public GameState getNextState() {
        return new OngoingGameState(plugin);
    }

    @Override
    public GameState getPreviousState() {
        return new WaitingGameState(plugin);
    }

    @Override
    public void onGameUpdate(GameStateContext context) {
        timeToLive--;

        final Game game = context.getGame();
        if (game.isAbleToStart()) {
            if (timeToLive > 0) return;

            context.advanceState();
        } else {
            context.backState();
        }
    }

    @Override
    public void onPlayerEnter(GameStateContext context) {
        final Player player = context.getBukkitPlayer();
        player.sendTitle(
            "§6§lSTARTING GAME",
            "§7The game will start soon!",
            10,
            20,
            10
        );

        final GamePlayer gamePlayer = context.getPlayer();
        if (!gamePlayer.hasTeam()) {
            final Game game = context.getGame();
            final GameTeamType lowestTeam = game.pickLowestTeam();

            gamePlayer.setTeam(lowestTeam);
        }
    }

    @Override
    public void onPlayerUpdate(GameStateContext context) {
        if (NOTICING_TIMES.contains(timeToLive)) {
            final int fadeInAndOut = timeToLive <= 5 ? 0 : 10;
            final int stay = timeToLive <= 5 ? 30 : 20;

            final Player player = context.getBukkitPlayer();
            player.sendTitle(
                "§6§lSTARTING GAME",
                "§7The game will start in §6" + timeToLive + "§7 seconds.",
                fadeInAndOut,
                stay,
                fadeInAndOut
            );
        }
    }

    @Override
    public void onPlayerExit(GameStateContext context) {
        final Game game = context.getGame();
        if (game.isAbleToStart()) return;

        context.backState();
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
            " Stage: §eStarting Game",
            " Players: §e" + game.getPlayerMap().size() + "/" + game.getMaxPlayers(),
            " Time to Start: §e" + TimeFormatter.formatSeconds(timeToLive),
            "",
            " Team: " + (gamePlayer.hasTeam() ? gamePlayer.getTeam().getColoredMessage() : "§eNone"),
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
