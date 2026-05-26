package com.automates.tasks;

import com.automates.core.AutomationTask;
import com.automates.core.TaskResult;
import com.automates.core.TaskStatus;
import com.automates.utils.Logger;

/**
 * Responsible for executing a single {@link AutomationTask} with retry logic,
 * timing, and structured error handling.
 *
 * @author Banoth Mahesh Kumar
 */
public class TaskExecutor {

    private static final int    MAX_RETRIES     = 2;
    private static final long   RETRY_DELAY_MS  = 500;
    private final Logger logger = Logger.getInstance();

    /**
     * Executes a task with up to {@value #MAX_RETRIES} retries on failure.
     *
     * @param task the automation task to execute
     * @return {@link TaskResult} reflecting final status after all attempts
     */
    public TaskResult execute(AutomationTask task) {
        task.setUp();
        long start = System.currentTimeMillis();

        for (int attempt = 1; attempt <= MAX_RETRIES + 1; attempt++) {
            try {
                TaskResult result = task.execute();
                long duration = System.currentTimeMillis() - start;

                if (result.isPassed()) {
                    task.tearDown();
                    return TaskResult.passed(task.getName(), result.getMessage(), duration);
                }

                logger.warn("Task '" + task.getName() + "' failed on attempt " + attempt);

            } catch (Exception ex) {
                logger.error("Unexpected exception in '" + task.getName() + "': " + ex.getMessage());
            }

            if (attempt <= MAX_RETRIES) {
                task.incrementRetry();
                sleep(RETRY_DELAY_MS);
            }
        }

        long duration = System.currentTimeMillis() - start;
        task.tearDown();
        return TaskResult.failed(task.getName(), "Failed after " + (MAX_RETRIES + 1) + " attempts", duration);
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
