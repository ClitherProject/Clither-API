package org.clitherproject.clither.api.plugin;

/**
 * A Scheduler can be used to schedule tasks to run in the future. The
 * asynchronous methods in this interface are preferred over their synchronous
 * counterparts; however, asynchronous tasks should generally not use any of the
 * Clither API, as it isn't guaranteed to be thread-safe.
 */
public interface Scheduler {

    /**
     * Runs a task synchronously (during the server tick) after a delay.
     *
     * @param plugin the owning plugin of this task
     * @param task the task to run
     * @param delay the delay in ticks (20 ticks = 1 second) before the task
     * executes
     * @return A task ID that can be used with {@link #cancelTask(int)}.
     */
    public int runTaskLater(Plugin plugin, Runnable task, long delay);

    /**
     * Runs a task asynchronously (outside of the server tick) after a delay.
     *
     * @param plugin the owning plugin of this task
     * @param task the task to run
     * @param delay the delay in ticks (20 ticks = 1 second) before the task
     * executes
     * @return A task ID that can be used with {@link #cancelTask(int)}.
     */
    public int runAsyncTaskLater(Plugin plugin, Runnable task, long delay);

    /**
     * Runs a task synchronously (during the server tick) on a timer.
     *
     * @param plugin the owning plugin of this task
     * @param task the task to run
     * @param delay the initial delay, in ticks (20 ticks = 1 second), before
     * the task executes for the first time
     * @param interval the interval in ticks (20 ticks = 1 second) at which the
     * task will repeat
     * @return A task ID that can be used with {@link #cancelTask(int)}.
     */
    public int runTaskTimer(Plugin plugin, Runnable task, long delay, long interval);

    /**
     * Runs a task asynchronously (outside of the server tick) on a timer.
     *
     * @param plugin the owning plugin of this task
     * @param task the task to run
     * @param delay the initial delay, in ticks (20 ticks = 1 second), before
     * the task executes for the first time
     * @param interval the interval in ticks (20 ticks = 1 second) at which the
     * task will repeat
     * @return A task ID that can be used with {@link #cancelTask(int)}.
     */
    public int runAsyncTaskTimer(Plugin plugin, Runnable task, long delay, long interval);

    /**
     * Cancels a scheduled task.
     *
     * @param id the task ID to cancel
     * @return true if the task was successfully canceled, false if the task ID
     * did not exist or the task was already canceled
     */
    public boolean cancelTask(int id);
}
