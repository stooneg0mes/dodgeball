package net.stonegomes.trial.plugin.game.player;

import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerFactory;
import net.stonegomes.trial.core.player.GamePlayerType;

import java.util.UUID;

public class DodgeballPlayerFactory implements GamePlayerFactory {

    @Override
    public GamePlayer createPlayer(UUID uuid, Game game, GamePlayerType type) {
        return DodgeballPlayer.builder()
            .uniqueId(uuid)
            .game(game)
            .type(type)
            .team(null)
            .totalKills(0)
            .weeklyKills(0)
            .monthlyKills(0)
            .gameKills(0)
            .totalDeaths(0)
            .weeklyDeaths(0)
            .monthlyDeaths(0)
            .totalWins(0)
            .weeklyWins(0)
            .monthlyWins(0)
            .totalDefeats(0)
            .weeklyDefeats(0)
            .monthlyDefeats(0)
            .build();
    }

    @Override
    public GamePlayer createPlayer(UUID uuid, GamePlayerType type) {
        return createPlayer(uuid, null, type);
    }

    @Override
    public GamePlayer createPlayer(
        UUID uuid,
        int totalKills,
        int monthlyKills,
        int weeklyKills,
        int totalDeaths,
        int monthlyDeaths,
        int weeklyDeaths,
        int totalWins,
        int monthlyWins,
        int weeklyWins,
        int totalDefeats,
        int monthlyDefeats,
        int weeklyDefeats
    ) {
        return DodgeballPlayer.builder()
            .uniqueId(uuid)
            .game(null)
            .type(GamePlayerType.NONE)
            .team(null)
            .totalKills(totalKills)
            .weeklyKills(weeklyKills)
            .monthlyKills(monthlyKills)
            .gameKills(0)
            .totalDeaths(totalDeaths)
            .weeklyDeaths(weeklyDeaths)
            .monthlyDeaths(monthlyDeaths)
            .totalWins(totalWins)
            .weeklyWins(weeklyWins)
            .monthlyWins(monthlyWins)
            .totalDefeats(totalDefeats)
            .weeklyDefeats(weeklyDefeats)
            .monthlyDefeats(monthlyDefeats)
            .build();
    }

}
