package net.stonegomes.trial.plugin.game.player;

import com.google.common.cache.*;
import lombok.RequiredArgsConstructor;
import net.stonegomes.trial.core.player.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DodgeballPlayerCache implements GamePlayerCache {

    private final LoadingCache<UUID, GamePlayer> playerCache;

    public DodgeballPlayerCache(GamePlayerFactory gamePlayerFactory, GamePlayerDao<DodgeballPlayer> gamePlayerDao) {
        playerCache = CacheBuilder.newBuilder()
            .expireAfterAccess(20, TimeUnit.MINUTES)
            .removalListener(new PlayerRemovalListener(gamePlayerDao))
            .build(new PlayerCacheLoader(gamePlayerFactory, gamePlayerDao));
    }

    @Override
    public GamePlayer getPlayer(UUID uuid) {
        return playerCache.getUnchecked(uuid);
    }

    @Override
    public void removePlayer(UUID uuid) {
        playerCache.invalidate(uuid);
    }

    @Override
    public boolean containsPlayer(UUID uuid) {
        return playerCache.asMap().containsKey(uuid);
    }

    @Override
    public Collection<GamePlayer> getCachedPlayers() {
        return playerCache.asMap().values();
    }

}

@RequiredArgsConstructor
class PlayerCacheLoader extends CacheLoader<UUID, GamePlayer> {

    private final GamePlayerFactory gamePlayerFactory;
    private final GamePlayerDao<DodgeballPlayer> gamePlayerDao;

    @Override
    public @NotNull GamePlayer load(@NotNull UUID playerId) {
        final GamePlayer gamePlayer = (GamePlayer) gamePlayerDao.find(playerId);
        if (gamePlayer != null) {
            return gamePlayer;
        }

        return gamePlayerFactory.createPlayer(playerId, GamePlayerType.NONE);
    }

}

@RequiredArgsConstructor
class PlayerRemovalListener implements RemovalListener<UUID, GamePlayer> {

    private final GamePlayerDao<DodgeballPlayer> gamePlayerDao;

    @Override
    public void onRemoval(@NotNull RemovalNotification<UUID, GamePlayer> notification) {
        gamePlayerDao.replace((DodgeballPlayer) notification.getValue());
    }

}