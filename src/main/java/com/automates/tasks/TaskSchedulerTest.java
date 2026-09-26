package com.automates.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskSchedulerTest {

    @Test
    void shouldAddAndCountTasks() {

        TaskScheduler scheduler =
                new TaskScheduler();

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
                                "Success",
                                10);
                    }
                };

        scheduler.addTask(task);

        assertEquals(
                1,
                scheduler.getTaskCount());

        assertEquals(
                task,
                scheduler.getTasks().get(0));
    }

    @Test
    void shouldClearAllTasks() {

        TaskScheduler scheduler =
                new TaskScheduler();

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
                                "Success",
                                10);
                    }
                };

        scheduler.addTask(task);

        assertEquals(1, scheduler.getTaskCount());

        scheduler.clearTasks();

        assertEquals(
                0,
                scheduler.getTaskCount());
    }

    @Test
    void shouldRejectNullTask() {

        TaskScheduler scheduler =
                new TaskScheduler();

        assertThrows(
                IllegalArgumentException.class,
                () -> scheduler.addTask(null));
    }
}
