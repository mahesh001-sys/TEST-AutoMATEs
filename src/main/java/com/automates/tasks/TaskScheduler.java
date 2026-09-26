package com.automates.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages and executes a collection of automation tasks.
 */
public class TaskScheduler {

    private final List<AutomationTask<?>> tasks;

    public TaskScheduler() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the scheduler.
     *
     * @param task automation task
     */
    public void addTask(AutomationTask<?> task) {

        if (task == null) {
            throw new IllegalArgumentException(
                    "Task cannot be null");
        }

        tasks.add(task);
    }

    /**
     * Returns all registered tasks.
     *
     * @return unmodifiable task list
     */
    public List<AutomationTask<?>> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Returns the number of registered tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Removes all registered tasks.
     */
    public void clearTasks() {
        tasks.clear();
    }
}
