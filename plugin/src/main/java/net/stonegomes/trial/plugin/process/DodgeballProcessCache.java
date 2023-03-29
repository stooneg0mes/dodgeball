package net.stonegomes.trial.plugin.process;

import net.stonegomes.trial.core.process.Process;
import net.stonegomes.trial.core.process.ProcessCache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DodgeballProcessCache implements ProcessCache {

    private final Map<UUID, Process> processMap = new HashMap<>();

    @Override
    public void putProcess(UUID uuid, Process process) {
        processMap.put(uuid, process);

        final Player player = Bukkit.getPlayer(uuid);
        process.startProcess(player);
    }

    @Override
    public Process getProcess(UUID uuid) {
        return processMap.get(uuid);
    }

    @Override
    public boolean hasProcess(UUID uuid) {
        return processMap.containsKey(uuid);
    }

    @Override
    public void removeProcess(UUID uuid) {
        processMap.remove(uuid);
    }

    @Override
    public Collection<Process> getProcesses() {
        return processMap.values();
    }

}
