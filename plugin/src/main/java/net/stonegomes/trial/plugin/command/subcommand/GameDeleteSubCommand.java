package net.stonegomes.trial.plugin.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameDao;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.plugin.game.DodgeballGame;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class GameDeleteSubCommand {

    private final GameDao<DodgeballGame> gameDao;
    private final GameManager gameManager;

    @Command(
        name = "game.delete",
        permission = "trial.command.game.delete",
        usage = "game delete <name>",
        target = CommandTarget.PLAYER
    )
    public void handleCommand(Context<Player> context, String name) {
        final Game game = gameManager.getGameMap().getGameByName(name);
        if (game == null) {
            context.sendMessage("§cGame not found.");
            return;
        }

        gameManager.getGameMap().removeGame(game.getUniqueId());
        gameDao.delete((DodgeballGame) game);

        context.sendMessage("§aYou deleted the game §f" + game.getName() + "§a successfully.");
    }

}
