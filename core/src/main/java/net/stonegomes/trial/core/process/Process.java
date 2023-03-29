package net.stonegomes.trial.core.process;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Process {

    private final List<ProcessPhase> phases;
    private final ProcessContext context;

    private int currentPhase;

    public Process(ProcessContext context) {
        this.phases = new ArrayList<>();
        this.context = context;
    }

    /**
     * Gets the current phase of the process.
     *
     * @return the current phase
     */
    public ProcessPhase getCurrentPhase() {
        return phases.get(currentPhase);
    }

    /**
     * Check if the process is on the last phase.
     *
     * @return if the process is on the last phase
     */
    public boolean isOnLastPhase() {
        return currentPhase == (phases.size() - 1);
    }

    /**
     * Add a phase to the process.
     *
     * @param processPhase the phase
     */
    public void addPhase(ProcessPhase processPhase) {
        phases.add(processPhase);
    }

    /**
     * Add multiple phases to the process.
     *
     * @param processPhases the phases
     */
    public void addPhases(ProcessPhase... processPhases) {
        this.phases.addAll(Arrays.asList(processPhases));
    }

    /**
     * Check if the process has a phase.
     *
     * @param processPhase the phase
     * @return if the process has the phase
     */
    public boolean hasPhase(ProcessPhase processPhase) {
        return phases.contains(processPhase);
    }

    /**
     * Remove a phase from the process
     *
     * @param processPhase the phase
     */
    public void removePhase(ProcessPhase processPhase) {
        phases.remove(processPhase);
    }

    /**
     * Start the process to a player.
     *
     * @param player the player
     */
    public void startProcess(Player player) {
        currentPhase = 0;
        startCurrentPhase(player);
    }

    /**
     * Start the previous phase of the process to a player.
     *
     * @param player the player
     */
    private void backPhase(Player player) {
        currentPhase--;
        startCurrentPhase(player);
    }

    /**
     * Start the next phase of the process to a player.
     *
     * @param player the player
     */
    public void advancePhase(Player player) {
        currentPhase++;
        startCurrentPhase(player);
    }

    /**
     * Start the current phase of the process to a player.
     *
     * @param player the player
     */
    protected void startCurrentPhase(Player player) {
        if (currentPhase < 0 || currentPhase >= phases.size()) {
            return;
        }

        final ProcessPhase processPhase = phases.get(currentPhase);
        if (processPhase == null) {
            return;
        }

        player.sendMessage(processPhase.getStartMessage());
    }

    /**
     * Finish the process to a player.
     *
     * @param player  the player
     * @param context the context
     */
    public abstract void onFinish(Player player, ProcessContext context);

    /**
     * Cancel the process to a player.
     *
     * @param player the player
     * @param message the message
     * @return if the process was cancelled
     */
    public abstract boolean onCancel(Player player, String message);

}
