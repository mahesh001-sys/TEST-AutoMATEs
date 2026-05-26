package com.automates.core;

import java.time.Instant;

/**
 * Immutable value object capturing the outcome of a single automation task.
 *
 * @author Banoth Mahesh Kumar
 */
public final class TaskResult {

    private final String     taskName;
    private final TaskStatus status;
    private final String     message;
    private final long       durationMs;
    private final Instant    timestamp;

    public TaskResult(String taskName, TaskStatus status, String message, long durationMs) {
        this.taskName   = taskName;
        this.status     = status;
        this.message    = message;
        this.durationMs = durationMs;
        this.timestamp  = Instant.now();
    }

    /* ── Convenience factory methods ── */

    public static TaskResult passed(String taskName, String message, long durationMs) {
        return new TaskResult(taskName, TaskStatus.PASSED, message, durationMs);
    }

    public static TaskResult failed(String taskName, String message, long durationMs) {
        return new TaskResult(taskName, TaskStatus.FAILED, message, durationMs);
    }

    public static TaskResult skipped(String taskName, String reason) {
        return new TaskResult(taskName, TaskStatus.SKIPPED, reason, 0);
    }

    /* ── Getters ── */

    public String     getTaskName()   { return taskName; }
    public TaskStatus getStatus()     { return status; }
    public String     getMessage()    { return message; }
    public long       getDurationMs() { return durationMs; }
    public Instant    getTimestamp()  { return timestamp; }
    public boolean    isPassed()      { return status == TaskStatus.PASSED; }

    @Override
    public String toString() {
        return String.format("[%s] %s — %s (%dms)", status, taskName, message, durationMs);
    }
}
