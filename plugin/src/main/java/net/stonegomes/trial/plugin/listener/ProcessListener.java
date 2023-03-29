package net.stonegomes.trial.plugin.listener;

import lombok.RequiredArgsConstructor;
import net.stonegomes.trial.core.process.Process;
import net.stonegomes.trial.core.process.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

@RequiredArgsConstructor
public class ProcessListener implements Listener {

    private final ProcessCache processCache;

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final Process process = processCache.getProcess(player.getUniqueId());
        if (process == null) return;

        final String message = event.getMessage();
        if (process.onCancel(player, message)) {
            event.setCancelled(true);
            return;
        }

        final ProcessPhase processPhase = process.getCurrentPhase();
        if (processPhase == null || processPhase.getType() != ProcessPhaseType.CHAT) return;

        final ProcessContext context = process.getContext();
        if (processPhase.handleInput(context).test(event.getMessage(), player)) {
            if (process.isOnLastPhase()) {
                process.onFinish(player, context);
                processCache.removeProcess(player.getUniqueId());
            } else {
                process.advancePhase(player);
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        final Block block = event.getClickedBlock();
        if (block == null) return;

        final Player player = event.getPlayer();
        final Process process = processCache.getProcess(player.getUniqueId());
        if (process == null) return;

        final ProcessPhase processPhase = process.getCurrentPhase();
        if (processPhase == null || processPhase.getType() != ProcessPhaseType.INTERACT_BLOCK) return;

        final ProcessContext context = process.getContext();
        if (processPhase.handleInput(context).test(block.getLocation(), player)) {
            if (process.isOnLastPhase()) {
                process.onFinish(player, context);
                processCache.removeProcess(player.getUniqueId());
            } else {
                process.advancePhase(player);
            }

            event.setCancelled(true);
        }
    }

}
