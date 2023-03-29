package net.stonegomes.trial.core.lobby;

import org.bukkit.Location;

public interface Lobby {

    /**
     * Get the spawn location of the lobby.
     *
     * @return the spawn location.
     */
    Location getSpawnLocation();

    /**
     * Set the spawn location of the lobby.
     *
     * @param location the spawn location.
     */
    void setSpawnLocation(Location location);

}
