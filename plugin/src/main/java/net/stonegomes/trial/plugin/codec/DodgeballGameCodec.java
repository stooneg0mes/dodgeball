package net.stonegomes.trial.plugin.codec;

import net.stonegomes.trial.common.location.LocationSerializer;
import net.stonegomes.trial.common.location.cuboid.CuboidRegion;
import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.plugin.DodgeballPlugin;
import net.stonegomes.trial.plugin.game.DodgeballGame;
import net.stonegomes.trial.plugin.lobby.DodgeballLobby;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bukkit.Location;

import java.util.UUID;

public class DodgeballGameCodec implements Codec<DodgeballGame> {

    private final DodgeballPlugin plugin;
    private final LocationSerializer locationSerializer;

    public DodgeballGameCodec(DodgeballPlugin plugin) {
        this.plugin = plugin;
        this.locationSerializer = new LocationSerializer();
    }

    /**
     * Decodes a BSON value from the given reader into an instance of the type parameter {@code T}.
     *
     * @param reader         the BSON reader
     * @param decoderContext the decoder context
     * @return an instance of the type parameter {@code T}.
     */
    @Override
    public DodgeballGame decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        reader.readObjectId();

        final UUID uniqueId = UUID.fromString(reader.readString());
        final String name = reader.readString();

        final Lobby waitingLobby = new DodgeballLobby(locationSerializer.deserialize(reader.readString()));
        final Lobby spectatingLobby = new DodgeballLobby(locationSerializer.deserialize(reader.readString()));

        final CuboidRegion redTeamCuboid = readCuboid(reader);
        final CuboidRegion blueTeamCuboid = readCuboid(reader);

        reader.readEndDocument();

        return (DodgeballGame) plugin.getGameFactory().createGame(
            uniqueId,
            name,
            waitingLobby,
            spectatingLobby,
            redTeamCuboid,
            blueTeamCuboid
        );
    }

    /**
     * Encode an instance of the type parameter {@code T} into a BSON value.
     *
     * @param writer         the BSON writer to encode into
     * @param value          the value to encode
     * @param encoderContext the encoder context
     */
    @Override
    public void encode(BsonWriter writer, DodgeballGame value, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeName("uniqueId");
        writer.writeString(value.getUniqueId().toString());

        writer.writeName("name");
        writer.writeString(value.getName());

        writer.writeName("waitingLobby");
        writer.writeString(locationSerializer.serialize(value.getWaitingLobby().getSpawnLocation()));

        writer.writeName("spectatingLobby");
        writer.writeString(locationSerializer.serialize(value.getSpectatingLobby().getSpawnLocation()));

        final CuboidRegion redCuboid = value.getRedTeamCuboid();
        writer.writeName("redTeamFirstPos");
        writer.writeString(locationSerializer.serialize(redCuboid.getFirstPos()));

        writer.writeName("redTeamSecondPos");
        writer.writeString(locationSerializer.serialize(redCuboid.getSecondPos()));

        writer.writeName("redTeamSpawn");
        writer.writeString(locationSerializer.serialize(redCuboid.getSpawnLocation()));

        final CuboidRegion blueCuboid = value.getBlueTeamCuboid();
        writer.writeName("blueTeamFirstPos");
        writer.writeString(locationSerializer.serialize(blueCuboid.getFirstPos()));

        writer.writeName("blueTeamSecondPos");
        writer.writeString(locationSerializer.serialize(blueCuboid.getSecondPos()));

        writer.writeName("blueTeamSpawn");
        writer.writeString(locationSerializer.serialize(blueCuboid.getSpawnLocation()));

        writer.writeEndDocument();
   }

    /**
     * Returns the Class instance that this encodes. This is necessary because Java does not reify generic types.
     *
     * @return the Class instance that this encodes.
     */
    @Override
    public Class<DodgeballGame> getEncoderClass() {
        return DodgeballGame.class;
    }

    private CuboidRegion readCuboid(BsonReader reader) {
        final Location firstPos = locationSerializer.deserialize(reader.readString());
        final Location secondPos = locationSerializer.deserialize(reader.readString());
        final Location spawnLocation = locationSerializer.deserialize(reader.readString());

        return new CuboidRegion(firstPos, secondPos, spawnLocation);
    }

}
