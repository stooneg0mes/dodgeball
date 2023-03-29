package net.stonegomes.trial.plugin;

import com.mongodb.MongoClient;
import fr.mrmicky.fastboard.FastBoard;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.ViewFrame;
import net.stonegomes.trial.common.storage.MongoConnection;
import net.stonegomes.trial.core.GameDao;
import net.stonegomes.trial.core.GameFactory;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerCache;
import net.stonegomes.trial.core.player.GamePlayerDao;
import net.stonegomes.trial.core.player.GamePlayerFactory;
import net.stonegomes.trial.core.process.ProcessCache;
import net.stonegomes.trial.core.scoreboard.ScoreboardCache;
import net.stonegomes.trial.plugin.codec.DodgeballGameCodec;
import net.stonegomes.trial.plugin.codec.DodgeballPlayerCodec;
import net.stonegomes.trial.plugin.command.GameMainCommand;
import net.stonegomes.trial.plugin.command.subcommand.GameCreateSubCommand;
import net.stonegomes.trial.plugin.command.subcommand.GameDeleteSubCommand;
import net.stonegomes.trial.plugin.command.subcommand.GameLeaveSubCommand;
import net.stonegomes.trial.plugin.game.DodgeballGame;
import net.stonegomes.trial.plugin.game.DodgeballGameDao;
import net.stonegomes.trial.plugin.game.DodgeballGameFactory;
import net.stonegomes.trial.plugin.game.DodgeballGameManager;
import net.stonegomes.trial.plugin.game.player.DodgeballPlayer;
import net.stonegomes.trial.plugin.game.player.DodgeballPlayerCache;
import net.stonegomes.trial.plugin.game.player.DodgeballPlayerDao;
import net.stonegomes.trial.plugin.game.player.DodgeballPlayerFactory;
import net.stonegomes.trial.plugin.listener.GameListener;
import net.stonegomes.trial.plugin.listener.ProcessListener;
import net.stonegomes.trial.plugin.listener.TrafficListener;
import net.stonegomes.trial.plugin.placeholder.DodgeballGamePlaceholder;
import net.stonegomes.trial.plugin.process.DodgeballProcessCache;
import net.stonegomes.trial.plugin.runnable.GameUpdateRunnable;
import net.stonegomes.trial.plugin.scoreboard.DodgeballScoreboardCache;
import net.stonegomes.trial.plugin.view.GameMainView;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class DodgeballPlugin extends JavaPlugin {

    public static DodgeballPlugin getInstance() {
        return getPlugin(DodgeballPlugin.class);
    }

    private MongoConnection mongoConnection;

    private ProcessCache processCache;

    private ScoreboardCache scoreboardCache;

    private GamePlayerFactory gamePlayerFactory;
    private GamePlayerDao<DodgeballPlayer> gamePlayerDao;
    private GamePlayerCache gamePlayerCache;

    private GameFactory gameFactory;
    private GameDao<DodgeballGame> gameDao;
    private GameManager gameManager;

    private ViewFrame viewFrame;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Storage connection
        if (!setupMongo()) {
            getLogger().warning("Failed to connect to the database. Disabling plugin...");
            return;
        }

        // Process
        processCache = new DodgeballProcessCache();

        // Scoreboard
        scoreboardCache = new DodgeballScoreboardCache();

        // Game & player
        gamePlayerFactory = new DodgeballPlayerFactory();
        gamePlayerDao = new DodgeballPlayerDao(mongoConnection);
        gamePlayerCache = new DodgeballPlayerCache(gamePlayerFactory, gamePlayerDao);

        gameManager = new DodgeballGameManager(scoreboardCache, this);
        gameFactory = new DodgeballGameFactory(this);
        gameDao = new DodgeballGameDao(mongoConnection);
        gameDao.loadAll(gameManager.getGameMap());

        // View
        viewFrame = ViewFrame.of(this);
        viewFrame.with(new GameMainView(gamePlayerCache, gameManager))
            .register();

        // Commands, listeners runnable and placeholder
        registerCommands();
        registerListeners();
        registerRunnable();
        registerPlaceholder();
    }

    @Override
    public void onDisable() {
        for (GamePlayer gamePlayer : gamePlayerCache.getCachedPlayers()) {
            if (!gamePlayer.hasGame()) continue;

            gameManager.kickPlayer(gamePlayer);
        }

        for (FastBoard fastBoard : scoreboardCache.getScoreboards()) {
            fastBoard.delete();
        }
    }

    private void registerCommands() {
        final BukkitFrame bukkitFrame = new BukkitFrame(this);
        bukkitFrame.registerCommands(
            new GameMainCommand(gamePlayerCache, viewFrame),
            new GameCreateSubCommand(processCache, gameManager, gameFactory, gameDao),
            new GameDeleteSubCommand(gameDao, gameManager),
            new GameLeaveSubCommand(gamePlayerCache, gameManager)
        );
    }

    private void registerListeners() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ProcessListener(processCache), this);
        pluginManager.registerEvents(new TrafficListener(gamePlayerCache, gameManager), this);
        pluginManager.registerEvents(new GameListener(gamePlayerCache, gameManager), this);
    }

    private void registerRunnable() {
        new GameUpdateRunnable(gamePlayerCache, gameManager).runTaskTimerAsynchronously(this, 0L, 10L);
    }

    private void registerPlaceholder() {
        final PluginManager pluginManager = Bukkit.getPluginManager();
        if (pluginManager.getPlugin("PlaceholderAPI") == null) return;

        new DodgeballGamePlaceholder(gamePlayerCache).register();
    }

    private boolean setupMongo() {
        final ConfigurationSection storageSection = getConfig().getConfigurationSection("storage");
        if (storageSection == null) return false;

        final String connectionString = storageSection.getString("connection");
        if (connectionString == null) return false;

        final String database = storageSection.getString("database");
        if (database == null) return false;

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
            MongoClient.getDefaultCodecRegistry(),
            CodecRegistries.fromCodecs(
                new DodgeballGameCodec(this),
                new DodgeballPlayerCodec(this)
            )
        );

        mongoConnection = new MongoConnection(connectionString, database);
        mongoConnection.startConnection(codecRegistry);

        return true;
    }

}
