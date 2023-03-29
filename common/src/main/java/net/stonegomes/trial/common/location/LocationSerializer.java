package net.stonegomes.trial.common.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer {

    public String serialize(Location value) {
        if (value == null) return "Empty";

        return value.getWorld().getName()
            + ";"
            + value.getX()
            + ";"
            + value.getY()
            + ";"
            + value.getZ()
            + ";"
            + value.getYaw()
            + ";"
            + value.getPitch();
    }

    public Location deserialize(String key) {
        if (key.equals("Empty")) return null;

        final String[] splitKey = key.split(";");
        final World world = Bukkit.getWorld(splitKey[0]);

        return new Location(world,
            Double.parseDouble(splitKey[1]),
            Double.parseDouble(splitKey[2]),
            Double.parseDouble(splitKey[3]),
            Float.parseFloat(splitKey[4]),
            Float.parseFloat(splitKey[5])
        );
    }

}