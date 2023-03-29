package net.stonegomes.trial.plugin.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerCache;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class GameLeaveSubCommand {

    private final GamePlayerCache gamePlayerCache;
    private final GameManager gameManager;

    @Command(
        name = "leave",
        target = CommandTarget.PLAYER,
        permission = "trial.command.game.leave"
    )
    public void handleCommand(Context<Player> context) {
        final UUID playerId = context.getSender().getUniqueId();
        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(playerId);
        if (!gamePlayer.hasGame()) {
            context.sendMessage("§cYou are not in a game.");
            return;
        }

        gameManager.kickPlayer(gamePlayer);
        context.sendMessage("§aYou left the game successfully.");
    }

}
