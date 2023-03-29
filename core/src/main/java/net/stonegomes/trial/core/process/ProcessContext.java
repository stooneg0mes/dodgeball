package net.stonegomes.trial.core.process;

public interface ProcessContext {


    /**
     * Sets a value in the process context.
     *
     * @param key   the key
     * @param value the value
     */
    void set(String key, Object value);

    /**
     * Gets a value from the process context
     *
     * @param key the key
     * @return the value
     */
    <T> T get(String key);

}
