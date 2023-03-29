package net.stonegomes.trial.plugin.game;

import lombok.RequiredArgsConstructor;
import net.stonegomes.trial.core.region.CuboidRegion;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameFactory;
import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.core.player.GamePlayerMap;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.plugin.DodgeballPlugin;
import net.stonegomes.trial.plugin.game.player.DodgeballPlayerMap;
import net.stonegomes.trial.plugin.game.state.impl.WaitingGameState;

import java.util.UUID;

@RequiredArgsConstructor
public class DodgeballGameFactory implements GameFactory {

    private final DodgeballPlugin plugin;

    @Override
    public Game createGame(
        UUID uniqueId,
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid,
        GameState initialState,
        GamePlayerMap playerSet
    ) {
        return DodgeballGame.builder()
            .uniqueId(UUID.randomUUID())
            .name(name)
            .waitingLobby(waitingLobby)
            .spectatingLobby(spectatingLobby)
            .blueTeamCuboid(blueTeamCuboid)
            .redTeamCuboid(redTeamCuboid)
            .currentState(initialState)
            .playerMap(playerSet)
            .winnerTeam(null)
            .build();
    }

    @Override
    public Game createGame(
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid,
        GameState initialState,
        GamePlayerMap playerSet
    ) {
        return createGame(
            UUID.randomUUID(),
            name,
            waitingLobby,
            spectatingLobby,
            blueTeamCuboid,
            redTeamCuboid,
            initialState,
            playerSet
        );
    }

    @Override
    public Game createGame(
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid,
        GameState initialState
    ) {
        return createGame(
            name,
            waitingLobby,
            spectatingLobby,
            blueTeamCuboid,
            redTeamCuboid,
            initialState,
            new DodgeballPlayerMap()
        );
    }

    @Override
    public Game createGame(
        UUID uuid,
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid
    ) {
        return createGame(
            uuid,
            name,
            waitingLobby,
            spectatingLobby,
            blueTeamCuboid,
            redTeamCuboid,
            new WaitingGameState(plugin),
            new DodgeballPlayerMap());
    }

    @Override
    public Game createGame(
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid
    ) {
        return createGame(
            UUID.randomUUID(),
            name,
            waitingLobby,
            spectatingLobby,
            blueTeamCuboid,
            redTeamCuboid
        );
    }

}
