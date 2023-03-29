package net.stonegomes.trial.plugin.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import net.stonegomes.trial.common.location.cuboid.CuboidRegion;
import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerMap;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.team.GameTeamType;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
public class DodgeballGame implements net.stonegomes.trial.core.Game {

    private final UUID uniqueId;

    private final String name;

    private final GamePlayerMap playerMap;

    private GameState currentState;

    private Lobby waitingLobby, spectatingLobby;

    private CuboidRegion redTeamCuboid, blueTeamCuboid;

    private GameTeamType winnerTeam;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GamePlayerMap getPlayerMap() {
        return playerMap;
    }

    @Override
    public int getMaxPlayers() {
        return 20;
    }

    @Override
    public int getMinPlayers() {
        return 2;
    }

    @Override
    public Lobby getWaitingLobby() {
        return waitingLobby;
    }

    @Override
    public void setWaitingLobby(Lobby lobby) {
        this.waitingLobby = lobby;
    }

    @Override
    public Lobby getSpectatingLobby() {
        return spectatingLobby;
    }

    @Override
    public void setSpectatingLobby(Lobby lobby) {
        this.spectatingLobby = lobby;
    }

    @Override
    public GameState getCurrentState() {
        return currentState;
    }

    @Override
    public void setCurrentState(GameState gameState) {
        this.currentState = gameState;
    }

    @Override
    public CuboidRegion getRedTeamCuboid() {
        return redTeamCuboid;
    }

    @Override
    public void setRedTeamCuboid(CuboidRegion cuboid) {
        this.redTeamCuboid = cuboid;
    }

    @Override
    public CuboidRegion getBlueTeamCuboid() {
        return blueTeamCuboid;
    }

    @Override
    public void setBlueTeamCuboid(CuboidRegion cuboid) {
        this.blueTeamCuboid = cuboid;
    }

    @Override
    public Collection<GamePlayer> getRedTeamPlayers() {
        return playerMap.getPlayers(false).stream()
            .filter(player -> player.getTeam() == GameTeamType.RED)
            .collect(Collectors.toSet());
    }

    @Override
    public Collection<GamePlayer> getBlueTeamPlayers() {
        return playerMap.getPlayers(false).stream()
            .filter(player -> player.getTeam() == GameTeamType.BLUE)
            .collect(Collectors.toSet());
    }

    @Override
    public Collection<GamePlayer> getPlayers(GameTeamType teamType) {
        return switch (teamType) {
            case RED -> getRedTeamPlayers();
            case BLUE -> getBlueTeamPlayers();
        };
    }

    @Override
    public GameTeamType getWinnerTeam() {
        return winnerTeam;
    }

    @Override
    public boolean hasWinnerTeam() {
        return winnerTeam != null;
    }

    @Override
    public void setWinnerTeam(GameTeamType teamType) {
        this.winnerTeam = teamType;
    }

    public boolean isOnSetup() {
        return currentState == null
            || waitingLobby == null
            || spectatingLobby == null;
    }

}
