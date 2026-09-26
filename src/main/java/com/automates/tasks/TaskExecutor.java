package com.automates.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes automation tasks with retry support.
 */
public class TaskExecutor {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskExecutor.class);

    private final int maxAttempts;

    /**
     * Creates a TaskExecutor.
     *
     * @param maxAttempts maximum number of attempts
     */
    public TaskExecutor(int maxAttempts) {

        if (maxAttempts < 1) {
            throw new IllegalArgumentException(
                    "maxAttempts must be at least 1");
        }

        this.maxAttempts = maxAttempts;
    }

    /**
     * Executes a task with retry support.
     *
     * @param task task to execute
     * @param input input required by the task
     * @param <T> input type
     * @return final TaskResult
     */
    public <T> TaskResult execute(
            AutomationTask<T> task,
            T input) {

        TaskResult lastResult = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            long startTime = System.currentTimeMillis();

            try {
                logger.info(
                        "Executing task '{}' - attempt {}/{}",
                        task.getTaskName(),
                        attempt,
                        maxAttempts);

                TaskResult result = task.execute(input);

                long executionTime =
                        System.currentTimeMillis() - startTime;

                if (result.isSuccess()) {

                    logger.info(
                            "Task '{}' completed successfully on attempt {}",
                            task.getTaskName(),
                            attempt);

                    return TaskResult.success(
                            result.getTaskName(),
                            result.getMessage(),
                            executionTime);
                }

                lastResult = TaskResult.failure(
                        result.getTaskName(),
                        result.getMessage(),
                        executionTime);

                logger.warn(
                        "Task '{}' failed on attempt {}: {}",
                        task.getTaskName(),
                        attempt,
                        result.getMessage());

            } catch (Exception e) {

                long executionTime =
                        System.currentTimeMillis() - startTime;

                lastResult = TaskResult.failure(
                        task.getTaskName(),
                        e.getMessage(),
                        executionTime);

                logger.error(
                        "Task '{}' threw an exception on attempt {}",
                        task.getTaskName(),
                        attempt,
                        e);
            }
        }

        logger.error(
                "Task '{}' failed after {} attempts",
                task.getTaskName(),
                maxAttempts);

        return lastResult;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }
}
