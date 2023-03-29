package net.stonegomes.trial.plugin.placeholder;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerCache;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public class DodgeballGamePlaceholder extends PlaceholderExpansion {
    
    private final GamePlayerCache gamePlayerCache;

    @Override
    public @NotNull String getIdentifier() {
        return "game";
    }

    @Override
    public @NotNull String getAuthor() {
        return "stoneg0mes";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(player.getUniqueId());

        return switch (params) {
            case "total_kills" -> String.valueOf(gamePlayer.getTotalKills());
            case "monthly_kills" -> String.valueOf(gamePlayer.getMonthlyKills());
            case "weekly_kills" -> String.valueOf(gamePlayer.getWeeklyKills());
            case "total_deaths" -> String.valueOf(gamePlayer.getTotalDeaths());
            case "monthly_deaths" -> String.valueOf(gamePlayer.getMonthlyDeaths());
            case "weekly_deaths" -> String.valueOf(gamePlayer.getWeeklyDeaths());
            case "total_wins" -> String.valueOf(gamePlayer.getTotalWins());
            case "monthly_wins" -> String.valueOf(gamePlayer.getMonthlyWins());
            case "weekly_wins" -> String.valueOf(gamePlayer.getWeeklyWins());
            case "total_defeats" -> String.valueOf(gamePlayer.getTotalDefeats());
            case "monthly_defeats" -> String.valueOf(gamePlayer.getMonthlyDefeats());
            case "weekly_defeats" -> String.valueOf(gamePlayer.getWeeklyDefeats());
            default -> null;
        };

    }
    
}
