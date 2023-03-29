package net.stonegomes.trial.plugin.storage;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Getter
public class MongoConnection {

    private final String connectionString;
    private final String database;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    public void startConnection(CodecRegistry codecRegistry) {
        final Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.WARNING);

        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .codecRegistry(codecRegistry)
            .build();

        this.mongoClient = MongoClients.create(mongoClientSettings);
        this.mongoDatabase = mongoClient.getDatabase(database);
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }

    public <T> MongoCollection<T> getCollection(String collectionName, Class<T> clazz) {
        return mongoDatabase.getCollection(collectionName, clazz);
    }

}
