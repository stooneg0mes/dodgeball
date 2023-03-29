package net.stonegomes.trial.core.team;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;

@RequiredArgsConstructor
@Getter
public enum GameTeamType {

    BLUE("Blue", ChatColor.BLUE),
    RED("Red", ChatColor.RED);

    private final String name;
    private final ChatColor color;

    public String getColoredMessage() {
        return color + name;
    }

    public GameTeamType getOpposite() {
        return this == BLUE ? RED : BLUE;
    }

}
