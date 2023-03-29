package net.stonegomes.trial.core.process;

import java.util.Collection;
import java.util.UUID;

public interface ProcessCache {

    /**
     * Put a player process in the cache.
     *
     * @param uuid the unique id of the player.
     * @param process the process.
     */
    void putProcess(UUID uuid, Process process);

    /**
     * Get a process by a player unique id.
     *
     * @param uuid the unique id of the player.
     * @return the process.
     */
    Process getProcess(UUID uuid);

    /**
     * Check if a player has a process in the cache.
     *
     * @param uuid the unique id of the player.
     * @return true if the player has a process.
     */
    boolean hasProcess(UUID uuid);

    /**
     * Remove a process player from the cache.
     *
     * @param uuid the unique id of the player.
     */
    void removeProcess(UUID uuid);

    /**
     * Get all processes in the cache.
     *
     * @return the processes.
     */
    Collection<Process> getProcesses();
}
