package net.stonegomes.trial.plugin.process;

import net.stonegomes.trial.core.process.ProcessContext;

import java.util.HashMap;
import java.util.Map;

public class DodgeballProcessContext implements ProcessContext {

    private final Map<String, Object> context = new HashMap<>();

    @Override
    public void set(String key, Object value) {
        context.put(key, value);
    }

    @Override
    public <T> T get(String key) {
        return (T) context.get(key);
    }

}
