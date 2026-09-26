package com.automates.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskExecutorTest {

    @Test
    void shouldExecuteSuccessfulTask() {

        AutomationTask<Void> task =
                new AutomationTask<Void>() {

                    @Override
                    public String getTaskName() {
                        return "Test Task";
                    }

                    @Override
                    public TaskResult execute(Void input) {
                        return TaskResult.success(
                                getTaskName(),
                                "Task completed",
                                10);
                    }
                };

        TaskExecutor executor =
                new TaskExecutor(3);

        TaskResult result =
                executor.execute(task, null);

        assertTrue(result.isSuccess());
        assertEquals("Test Task", result.getTaskName());
        assertEquals(
                "Task completed",
                result.getMessage());
    }

    @Test
    void shouldRetryFailedTask() {

        AutomationTask<Void> task =
                new AutomationTask<Void>() {

                    private int attempts = 0;

                    @Override
                    public String getTaskName() {
                        return "Retry Task";
                    }

                    @Override
                    public TaskResult execute(Void input) {

                        attempts++;

                        if (attempts < 3) {
                            return TaskResult.failure(
                                    getTaskName(),
                                    "Temporary failure",
                                    10);
                        }

                        return TaskResult.success(
                                getTaskName(),
                                "Task succeeded after retry",
                                10);
                    }
                };

        TaskExecutor executor =
                new TaskExecutor(3);

        TaskResult result =
                executor.execute(task, null);

        assertTrue(result.isSuccess());
        assertEquals(
                "Task succeeded after retry",
                result.getMessage());
    }

    @Test
    void shouldRejectInvalidMaxAttempts() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new TaskExecutor(0));
    }
}
