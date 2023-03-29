package net.stonegomes.trial.plugin.lobby;

import lombok.AllArgsConstructor;
import net.stonegomes.trial.core.lobby.Lobby;
import org.bukkit.Location;

@AllArgsConstructor
public class DodgeballLobby implements Lobby {

    private Location spawnLocation;

    @Override
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public void setSpawnLocation(Location location) {
        this.spawnLocation = location;
    }

}
