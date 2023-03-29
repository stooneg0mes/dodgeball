package net.stonegomes.trial.core;

import net.stonegomes.trial.core.region.CuboidRegion;
import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.core.player.GamePlayerMap;
import net.stonegomes.trial.core.state.GameState;

import java.util.UUID;

public interface GameFactory {

    /**
     * Create a new game.
     *
     * @param uniqueId the unique id of the game.
     * @param name the name of the game.
     * @param waitingLobby the waiting lobby.
     * @param spectatingLobby the spectating lobby.
     * @param blueTeamCuboid the blue team cuboid.
     * @param redTeamCuboid the red team cuboid.
     * @param initialState the initial state of the game.
     * @param playerSet the player set.
     * @return the created game.
     */
    Game createGame(
        UUID uniqueId,
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid,
        GameState initialState,
        GamePlayerMap playerSet
    );

    /**
     * Create a new game.
     *
     * @param name the name of the game.
     * @param waitingLobby the waiting lobby.
     * @param spectatingLobby the spectating lobby.
     * @param blueTeamCuboid the blue team cuboid.
     * @param redTeamCuboid the red team cuboid.
     * @param initialState the initial state of the game.
     * @param playerSet the player set.
     * @return the created game.
     */
    Game createGame(
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid,
        GameState initialState,
        GamePlayerMap playerSet
    );

    /**
     * Create a new game.
     *
     * @param name the name of the game.
     * @param waitingLobby the waiting lobby.
     * @param spectatingLobby the spectating lobby.
     * @param blueTeamCuboid the blue team cuboid.
     * @param redTeamCuboid the red team cuboid.
     * @param initialState the initial state of the game.
     * @return the created game.
     */
    Game createGame(
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid,
        GameState initialState
    );

    /**
     * Create a new game.
     *
     * @param uuid the unique id of the game.
     * @param name the name of the game.
     * @param waitingLobby the waiting lobby.
     * @param spectatingLobby the spectating lobby.
     * @param blueTeamCuboid the blue team cuboid.
     * @param redTeamCuboid the red team cuboid.
     * @return the created game.
     */
    Game createGame(
        UUID uuid,
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid
    );

    /**
     * Create a new game.
     *
     * @param name the name of the game.
     * @param waitingLobby the waiting lobby.
     * @param spectatingLobby the spectating lobby.
     * @param blueTeamCuboid the blue team cuboid.
     * @param redTeamCuboid the red team cuboid.
     * @return the created game.
     */
    Game createGame(
        String name,
        Lobby waitingLobby,
        Lobby spectatingLobby,
        CuboidRegion blueTeamCuboid,
        CuboidRegion redTeamCuboid
    );

}
