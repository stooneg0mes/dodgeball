package net.stonegomes.trial.common.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Task {

    private static final BukkitScheduler scheduler = Bukkit.getScheduler();
    private static final Executor executor = new ThreadPoolExecutor(
        1,
        3,
        20,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>()
    );

    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, executor).exceptionally(exception -> null);
    }

    public static <U> CompletableFuture<U> whenCompleteAsync(CompletableFuture<U> future, BiConsumer<U, Throwable> consumer) {
        return future.whenCompleteAsync(consumer);
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public static BukkitTask timerAsync(Plugin plugin, Runnable runnable, long delay, long period) {
        return scheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period);
    }

    public static BukkitTask timer(Plugin plugin, Runnable runnable, long delay, long period) {
        return scheduler.runTaskTimer(plugin, runnable, delay, period);
    }

    public static BukkitTask runTaskAsync(Plugin plugin, Runnable runnable) {
        return scheduler.runTaskAsynchronously(plugin, runnable);
    }

    public static BukkitTask runTask(Plugin plugin, Runnable runnable) {
        return scheduler.runTask(plugin, runnable);
    }

}