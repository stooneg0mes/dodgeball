package net.stonegomes.trial.plugin.game.player;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerType;
import net.stonegomes.trial.core.team.GameTeamType;
import net.stonegomes.trial.plugin.DodgeballPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor
@Builder
public class DodgeballPlayer implements GamePlayer {

    @Getter(AccessLevel.NONE)
    private static DodgeballPlugin plugin = DodgeballPlugin.getInstance();

    private final UUID uniqueId;

    private Game game;

    private GamePlayerType type;
    private GameTeamType team;

    private int totalKills, weeklyKills, monthlyKills, gameKills;
    private int totalDeaths, weeklyDeaths, monthlyDeaths;
    private int totalWins, weeklyWins, monthlyWins;
    private int totalDefeats, weeklyDefeats, monthlyDefeats;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public GamePlayerType getType() {
        return type;
    }

    @Override
    public void setType(GamePlayerType type) {
        this.type = type;
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public boolean hasGame() {
        return game != null;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public GameTeamType getTeam() {
        return team;
    }

    @Override
    public boolean hasTeam() {
        return team != null;
    }

    @Override
    public void setTeam(GameTeamType team) {
        this.team = team;
    }

    @Override
    public int getMonthlyKills() {
        return monthlyKills;
    }

    @Override
    public void setMonthlyKills(int monthlyKills) {
        this.monthlyKills = monthlyKills;
    }

    @Override
    public int getWeeklyKills() {
        return weeklyKills;
    }

    @Override
    public void setWeeklyKills(int weeklyKills) {
        this.weeklyKills = weeklyKills;
    }

    @Override
    public int getGameKills() {
        return gameKills;
    }

    @Override
    public void setGameKills(int gameKills) {
        this.gameKills = gameKills;
    }

    @Override
    public int getTotalKills() {
        return totalKills;
    }

    @Override
    public void incrementKills() {
        totalKills++;
        weeklyKills++;
        monthlyKills++;
        gameKills++;
    }

    @Override
    public int getMonthlyDeaths() {
        return monthlyDeaths;
    }

    @Override
    public void setMonthlyDeaths(int monthlyDeaths) {
        this.monthlyDeaths = monthlyDeaths;
    }

    @Override
    public int getWeeklyDeaths() {
        return weeklyDeaths;
    }

    @Override
    public void setWeeklyDeaths(int weeklyDeaths) {
        this.weeklyKills = weeklyDeaths;
    }

    @Override
    public int getTotalDeaths() {
        return totalDeaths;
    }

    @Override
    public void incrementDeaths() {
        totalDeaths++;
        weeklyDeaths++;
        monthlyDeaths++;
    }

    @Override
    public int getMonthlyWins() {
        return monthlyWins;
    }

    @Override
    public void setMonthlyWins(int monthlyWins) {
        this.monthlyWins = monthlyWins;
    }

    @Override
    public int getWeeklyWins() {
        return weeklyWins;
    }

    @Override
    public void setWeeklyWins(int weeklyWins) {
        this.weeklyWins = weeklyWins;
    }

    @Override
    public int getTotalWins() {
        return totalWins;
    }

    @Override
    public void incrementWins() {
        totalWins++;
        weeklyWins++;
        monthlyWins++;
    }

    @Override
    public int getMonthlyDefeats() {
        return monthlyDefeats;
    }

    @Override
    public void setMonthlyDefeats(int monthlyDefeats) {
        this.monthlyDefeats = monthlyDefeats;
    }

    @Override
    public int getWeeklyDefeats() {
        return monthlyDefeats;
    }

    @Override
    public void setWeeklyDefeats(int weeklyDefeats) {
        this.weeklyDefeats = weeklyDefeats;
    }

    @Override
    public int getTotalDefeats() {
        return totalDefeats;
    }

    @Override
    public void incrementDefeats() {
        totalDefeats++;
        weeklyDefeats++;
        monthlyDefeats++;
    }

    @Override
    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

    @Override
    public String getName() {
        final Player player = getBukkitPlayer();
        if (player != null) {
            return player.getName();
        }

        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uniqueId);
        if (offlinePlayer.hasPlayedBefore()) {
            return offlinePlayer.getName();
        }

        return null;
    }

    @Override
    public boolean isInSameGame(GamePlayer gamePlayer) {
        if (!hasGame()) return false;

        return gamePlayer.getGame().getUniqueId().equals(game.getUniqueId());
    }

    @Override
    public boolean isInSameTeam(GamePlayer gamePlayer) {
        if (!hasTeam()) return false;

        return gamePlayer.getTeam() == team;
    }

    @Override
    public void toDatabase() {
        plugin.getGamePlayerDao().replace(this);
    }

}
