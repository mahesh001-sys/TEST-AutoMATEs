package com.automates.tasks;

/**
 * Represents a unit of automation work.
 *
 * @param <T> input type required by the task
 */
public interface AutomationTask<T> {

    /**
     * Returns the task name.
     */
    String getTaskName();

    /**
     * Executes the task using the supplied input.
     */
    TaskResult execute(T input);
}
