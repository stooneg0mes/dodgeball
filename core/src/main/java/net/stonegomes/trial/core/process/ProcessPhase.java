package net.stonegomes.trial.core.process;

import org.bukkit.entity.Player;

import java.util.function.BiPredicate;

public interface ProcessPhase {

    /**
     * Gets the start message of the phase.
     *
     * @return the start message
     */
    String[] getStartMessage();

    /**
     * Gets the type of the phase.
     *
     * @return the type
     */
    ProcessPhaseType getType();

    /**
     * Handles the input for the phase type
     *
     * @param context the process context
     * @return the predicate
     */
    BiPredicate<Object, Player> handleInput(ProcessContext context);

}
