package net.stonegomes.trial.plugin.lobby;

import lombok.Getter;
import net.stonegomes.trial.core.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
public class DodgeballWorldLobby implements Lobby {

    private final String world;

    public DodgeballWorldLobby(String world) {
        this.world = world;
    }

    public DodgeballWorldLobby() {
        this.world = Bukkit.getWorlds().stream()
            .map(World::getName)
            .findFirst()
            .orElse(null);
    }

    @Override
    public Location getSpawnLocation() {
        final World world = Bukkit.getWorld(this.world);
        if (world == null) return null;

        return world.getSpawnLocation();
    }

    @Override
    public void setSpawnLocation(Location location) {
        final World world = Bukkit.getWorld(this.world);
        if (world == null) return;

        world.setSpawnLocation(location);
    }

}
