package com.automates.tasks;

import com.automates.core.AutomationTask;
import com.automates.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages registration and ordering of automation tasks before execution.
 *
 * <p>Tasks are stored in insertion order. Future versions will support
 * priority queues and dependency graphs.</p>
 *
 * @author Banoth Mahesh Kumar
 */
public class TaskScheduler {

    private final List<AutomationTask> tasks  = new ArrayList<>();
    private final Logger               logger = Logger.getInstance();

    /**
     * Registers the default set of built-in automation tasks.
     */
    public void registerDefaultTasks() {
        register(new FileCleanupTask());
        register(new DataValidationTask());
        register(new WorkflowTriggerTask());
        register(new NotificationTask());
        register(new ReportSnapshotTask());
        logger.info("Registered " + tasks.size() + " default tasks.");
    }

    /**
     * Adds a custom task to the execution queue.
     *
     * @param task the task to add
     */
    public void register(AutomationTask task) {
        tasks.add(task);
        logger.info("Registered task: " + task.getName());
    }

    public List<AutomationTask> getTasks()   { return Collections.unmodifiableList(tasks); }
    public int                  getTaskCount(){ return tasks.size(); }

    public void clear() { tasks.clear(); }
}
