package net.stonegomes.trial.plugin.command;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.ViewFrame;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerCache;
import net.stonegomes.trial.plugin.view.GameMainView;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GameMainCommand {

    private final GamePlayerCache gamePlayerCache;

    private final ViewFrame viewFrame;

    @Command(
        name = "game",
        permission = "trial.command.games",
        target = CommandTarget.PLAYER
    )
    public void handleGamesCommand(Context<Player> context) {
        final Player player = context.getSender();
        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(player.getUniqueId());
        if (gamePlayer == null) {
            return;
        }

        viewFrame.open(GameMainView.class, player, ImmutableMap.of("gamePlayer", gamePlayer));
    }

}
