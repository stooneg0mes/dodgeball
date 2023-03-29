package net.stonegomes.trial.plugin.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.stonegomes.trial.core.GameDao;
import net.stonegomes.trial.core.GameFactory;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.process.ProcessCache;
import net.stonegomes.trial.plugin.process.impl.GameCreateProcess;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class GameCreateSubCommand {

    private final ProcessCache processCache;

    private final GameManager gameManager;
    private final GameFactory gameFactory;
    private final GameDao gameDao;

    @Command(
        name = "game.create",
        permission = "trial.command.game.create",
        target = CommandTarget.PLAYER
    )
    public void handleCommand(Context<Player> context) {
        final GameCreateProcess createProcess = new GameCreateProcess(gameManager, gameFactory, gameDao);
        final UUID playerId = context.getSender().getUniqueId();
        processCache.putProcess(playerId, createProcess);
    }

}
