package net.stonegomes.trial.plugin.game;

import com.mongodb.client.MongoCollection;
import net.stonegomes.trial.common.storage.MongoConnection;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameDao;
import net.stonegomes.trial.core.GameMap;
import org.bson.conversions.Bson;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class DodgeballGameDao implements GameDao<DodgeballGame> {

    private final MongoCollection<DodgeballGame> mongoCollection;

    public DodgeballGameDao(MongoConnection mongoConnection) {
        this.mongoCollection = mongoConnection.getCollection("games", DodgeballGame.class);
    }

    @Override
    public void replace(DodgeballGame game) {
        final Bson query = eq("uniqueId", game.getUniqueId().toString());

        if (mongoCollection.find(query).first() != null) {
            mongoCollection.replaceOne(query, game);
        } else {
            mongoCollection.insertOne(game);
        }
    }

    @Override
    public DodgeballGame find(UUID uniqueId) {
        final Bson query = eq("uniqueId", uniqueId.toString());
        return mongoCollection.find(query).first();
    }

    @Override
    public void delete(DodgeballGame game) {
        final Bson query = eq("uniqueId", game.getUniqueId().toString());
        mongoCollection.deleteOne(query);
    }

    @Override
    public Collection<DodgeballGame> find() {
        final Set<DodgeballGame> mongoGames = new HashSet<>();
        for (DodgeballGame game : mongoCollection.find()) {
            mongoGames.add(game);
        }

        return mongoGames;
    }

    @Override
    public void loadAll(GameMap gameMap) {
        for (Game game : find()) {
            gameMap.putGame(game.getUniqueId(), game);
        }
    }

}
