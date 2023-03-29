package net.stonegomes.trial.plugin.process.impl;

import net.stonegomes.trial.common.location.cuboid.CuboidRegion;
import net.stonegomes.trial.common.string.RandomString;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameDao;
import net.stonegomes.trial.core.GameFactory;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.lobby.Lobby;
import net.stonegomes.trial.core.process.Process;
import net.stonegomes.trial.core.process.ProcessContext;
import net.stonegomes.trial.core.process.ProcessPhase;
import net.stonegomes.trial.core.process.ProcessPhaseType;
import net.stonegomes.trial.plugin.game.DodgeballGame;
import net.stonegomes.trial.plugin.lobby.DodgeballLobby;
import net.stonegomes.trial.plugin.process.DodgeballProcessContext;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.BiPredicate;

public class GameCreateProcess extends Process {

    private final GameManager gameManager;
    private final GameFactory gameFactory;
    private final GameDao<DodgeballGame> gameDao;

    public GameCreateProcess(GameManager gameManager, GameFactory gameFactory, GameDao<DodgeballGame> gameDao) {
        super(new DodgeballProcessContext());
        this.gameManager = gameManager;
        this.gameFactory = gameFactory;
        this.gameDao = gameDao;

        addPhases(
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[]{
                        "",
                        "§aType the name of the game you want to create.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.CHAT;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof String message) {
                            final String finalName;
                            if (message.equalsIgnoreCase("random")) {
                                finalName = new RandomString(5).nextString();
                            } else {
                                finalName = message;
                            }

                            context.set("name", finalName);
                            return true;
                        }

                        return false;
                    };
                }
            },
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[]{
                        "",
                        "§aGo to the §fwaiting lobby§a location and type §f'done'§a in the chat.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.CHAT;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof final String message) {
                            if (!message.equalsIgnoreCase("done")) {
                                return false;
                            }

                            final Location location = player.getLocation();
                            context.set("waitingLobby", location);
                            return true;
                        }

                        return false;
                    };
                }
            },
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[]{
                        "",
                        "§aGo to the §fspectator lobby§a location and type §f'done'§a in the chat.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.CHAT;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof final String message) {
                            if (!message.equalsIgnoreCase("done")) {
                                return false;
                            }

                            final Location location = player.getLocation();
                            context.set("spectatorLobby", location);
                            return true;
                        }

                        return false;
                    };
                }
            },
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[]{
                        "",
                        "§aGo to the §fblue team§a spawn location and type §f'done'§a in the chat.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.CHAT;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof final String message) {
                            if (!message.equalsIgnoreCase("done")) {
                                return false;
                            }

                            final Location location = player.getLocation();
                            context.set("blueTeamSpawn", location);
                            return true;
                        }

                        return false;
                    };
                }
            },
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[]{
                        "",
                        "§aGo to §fred team§a spawn location and type §f'done'§a in the chat.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.CHAT;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof final String message) {
                            if (!message.equalsIgnoreCase("done")) {
                                return false;
                            }

                            final Location location = player.getLocation();
                            context.set("redTeamSpawn", location);
                            return true;
                        }

                        return false;
                    };
                }
            },
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[]{
                        "",
                        "§aSelect the §ffirst position§a of the §fblue team§a area.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.INTERACT_BLOCK;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof Location location) {
                            context.set("blueFirstPosition", location);
                            return true;
                        }

                        return false;
                    };
                }
            },
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[] {
                        "",
                        "§aSelect the §fsecond position§a of the §fblue team§a area.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.INTERACT_BLOCK;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof Location location) {
                            context.set("blueSecondPosition", location);
                            return true;
                        }

                        return false;
                    };
                }
            },
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[]{
                        "",
                        "§aSelect the §ffirst position§a of the §fred team§a area.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.INTERACT_BLOCK;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof Location location) {
                            context.set("redFirstPosition", location);
                            return true;
                        }

                        return false;
                    };
                }
            },
            new ProcessPhase() {
                @Override
                public String[] getStartMessage() {
                    return new String[] {
                        "",
                        "§aSelect the §fsecond position§a of the §fred team§a area.",
                        "§7(You can cancel this process at any time by typing 'cancel')",
                        ""
                    };
                }

                @Override
                public ProcessPhaseType getType() {
                    return ProcessPhaseType.INTERACT_BLOCK;
                }

                @Override
                public BiPredicate<Object, Player> handleInput(ProcessContext context) {
                    return (input, player) -> {
                        if (input instanceof Location location) {
                            context.set("redSecondPosition", location);
                            return true;
                        }

                        return false;
                    };
                }
            }
        );
    }

    @Override
    public void onFinish(Player player, ProcessContext context) {
        final String name = context.get("name");

        final Lobby waitingLobby = new DodgeballLobby(context.get("waitingLobby"));
        final Lobby spectatorLobby = new DodgeballLobby(context.get("spectatorLobby"));

        final CuboidRegion blueTeamCuboid = new CuboidRegion(
            context.get("blueFirstPosition"),
            context.get("blueSecondPosition"),
            context.get("blueTeamSpawn")
        );
        final CuboidRegion redTeamCuboid = new CuboidRegion(
            context.get("redFirstPosition"),
            context.get("redSecondPosition"),
            context.get("redTeamSpawn")
        );

        final Game game = gameFactory.createGame(
            name,
            waitingLobby,
            spectatorLobby,
            blueTeamCuboid,
            redTeamCuboid
        );

        gameManager.getGameMap().putGame(game.getUniqueId(), game);
        gameDao.replace((DodgeballGame) game);

        player.sendMessage("§aYou created the game §f'" + name + "'§a successfully!");
    }

    @Override
    public boolean onCancel(Player player, String message) {
        if (message.equalsIgnoreCase("cancel")) {
            player.sendMessage("§cYou cancelled the creation process.");
            return true;
        }

        return false;
    }

}
