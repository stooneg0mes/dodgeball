package net.stonegomes.trial.plugin.storage.codec;

import net.stonegomes.trial.plugin.DodgeballPlugin;
import net.stonegomes.trial.plugin.game.player.DodgeballPlayer;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.UUID;

public class DodgeballPlayerCodec implements Codec<DodgeballPlayer> {

    private final DodgeballPlugin plugin;

    public DodgeballPlayerCodec(DodgeballPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Decodes a BSON value from the given reader into an instance of the type parameter {@code T}.
     *
     * @param reader         the BSON reader
     * @param decoderContext the decoder context
     * @return an instance of the type parameter {@code T}.
     */
    @Override
    public DodgeballPlayer decode(BsonReader reader, DecoderContext decoderContext) {
        reader.readStartDocument();
        reader.readObjectId();

        final UUID uniqueId = UUID.fromString(reader.readString());

        final int totalKills = reader.readInt32();
        final int weeklyKills = reader.readInt32();
        final int monthlyKills = reader.readInt32();

        final int totalDeaths = reader.readInt32();
        final int weeklyDeaths = reader.readInt32();
        final int monthlyDeaths = reader.readInt32();

        final int totalWins = reader.readInt32();
        final int weeklyWins = reader.readInt32();
        final int monthlyWins = reader.readInt32();

        final int totalDefeats = reader.readInt32();
        final int weeklyDefeats = reader.readInt32();
        final int monthlyDefeats = reader.readInt32();

        reader.readEndDocument();

        return (DodgeballPlayer) plugin.getGamePlayerFactory().createPlayer(
            uniqueId,
            totalKills,
            weeklyKills,
            monthlyKills,
            totalDeaths,
            weeklyDeaths,
            monthlyDeaths,
            totalWins,
            weeklyWins,
            monthlyWins,
            totalDefeats,
            weeklyDefeats,
            monthlyDefeats
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
    public void encode(BsonWriter writer, DodgeballPlayer value, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeString("uniqueId", value.getUniqueId().toString());

        writer.writeInt32("totalKills", value.getTotalKills());
        writer.writeInt32("weeklyKills", value.getWeeklyKills());
        writer.writeInt32("monthlyKills", value.getMonthlyKills());

        writer.writeInt32("totalDeaths", value.getTotalDeaths());
        writer.writeInt32("weeklyDeaths", value.getWeeklyDeaths());
        writer.writeInt32("monthlyDeaths", value.getMonthlyDeaths());

        writer.writeInt32("totalWins", value.getTotalWins());
        writer.writeInt32("weeklyWins", value.getWeeklyWins());
        writer.writeInt32("monthlyWins", value.getMonthlyWins());

        writer.writeInt32("totalDefeats", value.getTotalDefeats());
        writer.writeInt32("weeklyDefeats", value.getWeeklyDefeats());
        writer.writeInt32("monthlyDefeats", value.getMonthlyDefeats());
        
        writer.writeEndDocument();
   }

    /**
     * Returns the Class instance that this encodes. This is necessary because Java does not reify generic types.
     *
     * @return the Class instance that this encodes.
     */
    @Override
    public Class<DodgeballPlayer> getEncoderClass() {
        return DodgeballPlayer.class;
    }
}
