package com.automates.tasks;

/**
 * Represents the result of an automation task execution.
 */
public class TaskResult {

    private final String taskName;
    private final boolean success;
    private final String message;
    private final long executionTimeMs;

    private TaskResult(
            String taskName,
            boolean success,
            String message,
            long executionTimeMs) {

        this.taskName = taskName;
        this.success = success;
        this.message = message;
        this.executionTimeMs = executionTimeMs;
    }

    public static TaskResult success(
            String taskName,
            String message,
            long executionTimeMs) {

        return new TaskResult(
                taskName,
                true,
                message,
                executionTimeMs);
    }

    public static TaskResult failure(
            String taskName,
            String message,
            long executionTimeMs) {

        return new TaskResult(
                taskName,
                false,
                message,
                executionTimeMs);
    }

    public String getTaskName() {
        return taskName;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskName='" + taskName + '\'' +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", executionTimeMs=" + executionTimeMs +
                '}';
    }
}
