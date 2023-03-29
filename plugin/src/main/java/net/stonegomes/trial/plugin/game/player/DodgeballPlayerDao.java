package net.stonegomes.trial.plugin.game.player;

import com.mongodb.client.MongoCollection;
import net.stonegomes.trial.plugin.storage.MongoConnection;
import net.stonegomes.trial.plugin.util.task.Task;
import net.stonegomes.trial.core.player.GamePlayerDao;
import org.bson.conversions.Bson;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class DodgeballPlayerDao implements GamePlayerDao<DodgeballPlayer> {

    private final MongoCollection<DodgeballPlayer> mongoCollection;

    public DodgeballPlayerDao(MongoConnection mongoConnection) {
        this.mongoCollection = mongoConnection.getCollection("players", DodgeballPlayer.class);
    }

    @Override
    public void replace(DodgeballPlayer game) {
        Task.runAsync(() -> {
            final Bson query = eq("uniqueId", game.getUniqueId().toString());
            if (mongoCollection.find(query).first() != null) {
                mongoCollection.replaceOne(query, game);
            } else {
                mongoCollection.insertOne(game);
            }
        });
    }

    @Override
    public DodgeballPlayer find(UUID uniqueId) {
        final Bson query = eq("uniqueId", uniqueId.toString());
        return mongoCollection.find(query).first();
    }

    @Override
    public void delete(DodgeballPlayer game) {
        Task.runAsync(() -> {
            final Bson query = eq("uniqueId", game.getUniqueId().toString());
            mongoCollection.deleteOne(query);
        });
    }

    @Override
    public Collection<DodgeballPlayer> find() {
        final Set<DodgeballPlayer> mongoPlayers = new HashSet<>();
        for (DodgeballPlayer player : mongoCollection.find()) {
            mongoPlayers.add(player);
        }

        return mongoPlayers;
    }

}
