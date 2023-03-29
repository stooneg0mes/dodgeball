package net.stonegomes.trial.plugin.view;

import me.saiintbrisson.minecraft.PaginatedView;
import me.saiintbrisson.minecraft.PaginatedViewSlotContext;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewItem;
import net.stonegomes.trial.common.item.ItemBuilder;
import net.stonegomes.trial.core.Game;
import net.stonegomes.trial.core.GameManager;
import net.stonegomes.trial.core.player.GamePlayer;
import net.stonegomes.trial.core.player.GamePlayerCache;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class GameMainView extends PaginatedView<Game> {

    private static final Integer[] DECORATION_SLOTS = new Integer[]{
        0, 1, 2, 3, 4, 5, 6, 7, 8,
        9, 17, 18, 26, 27, 35,
        36, 37, 38, 39, 40, 41, 42, 43, 44
    };

    private final GamePlayerCache gamePlayerCache;
    private final GameManager gameManager;

    public GameMainView(GamePlayerCache gamePlayerCache, GameManager gameManager) {
        super(5 * 9, "List of Games");

        this.gamePlayerCache = gamePlayerCache;
        this.gameManager = gameManager;

        setLayout(
            "XXXXXXXXX",
            "XOOOOOOOX",
            "XOOOOOOOX",
            "XOOOOOOOX",
            "XXX<X>XXX"
        );

        setNextPageItem((context, item) -> {
            item.withItem(new ItemBuilder(Material.ARROW)
                .name("<#2cd42c>&lNEXT PAGE")
                .lore("§7Click to go to the next page")
                .build()
            ).onClick(handler -> {
                context.paginated().switchToNextPage();
            });
        });

        setPreviousPageItem((context, item) -> {
            item.withItem(new ItemBuilder(Material.ARROW)
                .name("<#b82d23>&lPREVIOUS PAGE")
                .lore("§7Click to go to the previous page")
                .build()
            ).onClick(handler -> {
                context.paginated().switchToPreviousPage();
            });
        });

        setCancelOnClick(true);
    }

    @Override
    protected void onRender(@NotNull ViewContext context) {
        context.paginated().setSource(gameManager.getGameMap().getGames().stream().toList());

        for (int slot : DECORATION_SLOTS) {
            context.slot(slot).withItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());
        }

        if (context.paginated().getSource().isEmpty()) {
            context.slot(22).withItem(
                new ItemBuilder(Material.COBWEB)
                    .name("§c§lNO GAMES FOUND")
                    .lore("§7There are no games available at the moment.")
                    .build()
            );
        }

        context.slot(40).withItem(new ItemBuilder(Material.BELL)
            .name("§6§lLIST OF GAMES")
            .lore(
                "§7Here you can see all the games available.",
                "§7Click on one of them to join."
            )
            .build()
        );
    }

    @Override
    protected void onItemRender(@NotNull PaginatedViewSlotContext<Game> context, @NotNull ViewItem viewItem, @NotNull Game value) {
        final GamePlayer gamePlayer = gamePlayerCache.getPlayer(context.getPlayer().getUniqueId());

        viewItem.withItem(new ItemBuilder(Material.PAPER)
            .name("§aGame '" + value.getName() + "'")
            .lore(
                "§7Players: §f" + value.getPlayerMap().size() + "/" + value.getMaxPlayers(),
                "§7Status: §f" + value.getCurrentState().getName()
            )
            .build()
        ).onClick(handler -> {
            if (value.isFull() || !value.getCurrentState().isFirstState()) {
                handler.getPlayer().sendMessage("§cThis game is full or already started.");
                return;
            }

            gameManager.sendPlayer(gamePlayer, value);
        });
    }

}
