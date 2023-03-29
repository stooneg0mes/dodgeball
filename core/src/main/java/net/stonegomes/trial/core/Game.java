package net.stonegomes.trial.core;

import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerMap;
import net.stonegomes.trial.core.region.CuboidRegion;
import net.stonegomes.trial.core.state.GameState;
import net.stonegomes.trial.core.team.GameTeamType;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public interface Game {

    /**
     * Get the unique id of the game.
     *
     * @return the unique id of the game.
     */
    UUID getUniqueId();

    /**
     * Get the name of the game.
     *
     * @return the name of the game.
     */
    String getName();

    /**
     * Get the game player map.
     *
     * @return the game player map.
     */
    GamePlayerMap getPlayerMap();

    /**
     * Get the maximum amount players of the game.
     *
     * @return the max players.
     */
    int getMaxPlayers();

    /**
     * Get the minimum amount players of the game.
     *
     * @return the min players.
     */
    int getMinPlayers();

    /**
     * Get the waiting lobby.
     *
     * @return the waiting lobby.
     */
    Lobby getWaitingLobby();

    /**
     * Set the waiting lobby.
     *
     * @param lobby the waiting lobby.
     */
    void setWaitingLobby(Lobby lobby);

    /**
     * Get the spectating lobby.
     *
     * @return the spectating lobby.
     */
    Lobby getSpectatingLobby();

    /**
     * Set the spectating lobby.
     *
     * @param lobby the spectating lobby.
     */
    void setSpectatingLobby(Lobby lobby);

    /**
     * Get the current game state.
     *
     * @return the game state.
     */
    GameState getCurrentState();

    /**
     * Set the current game state.
     *
     * @param gameState the game state.
     */
    void setCurrentState(GameState gameState);

    /**
     * Get the red team cuboid region.
     *
     * @return the red team cuboid.
     */
    CuboidRegion getRedTeamCuboid();

    /**
     * Set the red team cuboid region.
     *
     * @param cuboid the cuboid.
     */
    void setRedTeamCuboid(CuboidRegion cuboid);

    /**
     * Get the blue team cuboid region.
     *
     * @return the blue team cuboid.
     */
    CuboidRegion getBlueTeamCuboid();

    /**
     * Set the blue team cuboid region.
     *
     * @param cuboid the cuboid.
     */
    void setBlueTeamCuboid(CuboidRegion cuboid);

    /**
     * Get the red team players.
     *
     * @return the red team players.
     */
    Collection<GamePlayer> getRedTeamPlayers();

    /**
     * Get the blue team players.
     *
     * @return the blue team players.
     */
    Collection<GamePlayer> getBlueTeamPlayers();

    /**
     * Get the players of a team type.
     *
     * @param teamType the team type.
     * @return the players of the team type.
     */
    Collection<GamePlayer> getPlayers(GameTeamType teamType);

    /**
     * Get the winner team.
     *
     * @return the winner team.
     */
    GameTeamType getWinnerTeam();

    /**
     * Checks if the game has a winner team.
     * If the winner team is null it is considered that the game is not ended.
     *
     * @return
     */
    boolean hasWinnerTeam();

    /**
     * Set the winner team.
     *
     * @param teamType the winner team.
     */
    void setWinnerTeam(GameTeamType teamType);

    /**
     * Pick a winning team.
     * It will return the team with more players or random if it is even.
     *
     * @return  the winner team.
     */
    default GameTeamType pickWinner() {
        return pickTeam(false);
    }

    /**
     * Pick the lowest members team.
     * It will return the team with less players or random if it is even.
     *
     * @return the lowest members team.
     */
    default GameTeamType pickLowestTeam() {
        return pickTeam(true);
    }

    /**
     * Pick the lowest members team or the highest members team.
     *
     * @param lowest if true it will pick the lowest members team, otherwise it will pick the highest members team.
     * @return the team type.
     */
    private GameTeamType pickTeam(boolean lowest) {
        final int redTeamSize = getRedTeamPlayers().size();
        final int blueTeamSize = getBlueTeamPlayers().size();

        if (redTeamSize < blueTeamSize) {
            return lowest ? GameTeamType.RED : GameTeamType.BLUE;
        } else if (blueTeamSize < redTeamSize) {
            return lowest ? GameTeamType.BLUE : GameTeamType.RED;
        } else {
            final boolean randomIsBlue = ThreadLocalRandom.current().nextBoolean();
            return randomIsBlue ? GameTeamType.BLUE : GameTeamType.RED;
        }
    }

    /**
     * Checks if the game is full.
     *
     * @return true if the game is full.
     */
    default boolean isFull() {
        return getPlayerMap().size() >= getMaxPlayers();
    }

    /**
     * Checks if the game is able to start.
     * If the player size is between the min and max players and the player size
     * it is considered able to start.
     *
     * @return true if the game is able to start.
     */
    default boolean isAbleToStart() {
        final int playersSize = getPlayerMap().size();
        if (playersSize >= getMinPlayers() && playersSize <= getMaxPlayers()) {
            return playersSize % 2 == 0;
        }

        return false;
    }

}
