package com.automates.core;

import com.automates.report.ReportGenerator;
import com.automates.tasks.AutomationTask;
import com.automates.tasks.TaskExecutor;
import com.automates.tasks.TaskResult;
import com.automates.tasks.TaskScheduler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutoMATEsEngineTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldExecuteAllTasksAndGenerateReport()
            throws Exception {

        TaskScheduler scheduler =
                new TaskScheduler();

        AutomationTask<Void> firstTask =
                new AutomationTask<Void>() {

                    @Override
                    public String getTaskName() {
                        return "First Task";
                    }

                    @Override
                    public TaskResult execute(Void input) {
                        return TaskResult.success(
                                getTaskName(),
                                "First task completed",
                                10);
                    }
                };

        AutomationTask<Void> secondTask =
                new AutomationTask<Void>() {

                    @Override
                    public String getTaskName() {
                        return "Second Task";
                    }

                    @Override
                    public TaskResult execute(Void input) {
                        return TaskResult.success(
                                getTaskName(),
                                "Second task completed",
                                20);
                    }
                };

        scheduler.addTask(firstTask);
        scheduler.addTask(secondTask);

        TaskExecutor executor =
                new TaskExecutor(2);

        ReportGenerator reportGenerator =
                new ReportGenerator(
                        tempDirectory.toString(),
                        "automation-report.html");

        AutoMATEsEngine engine =
                new AutoMATEsEngine(
                        scheduler,
                        executor,
                        reportGenerator);

        List<TaskResult> results =
                engine.run();

        assertEquals(
                2,
                results.size());

        assertTrue(
                results.get(0).isSuccess());

        assertTrue(
                results.get(1).isSuccess());

        Path reportPath =
                tempDirectory.resolve(
                        "automation-report.html");

        assertTrue(
                Files.exists(reportPath),
                "Automation report should be generated");
    }

    @Test
    void shouldRejectNullDependencies() {

        TaskScheduler scheduler =
                new TaskScheduler();

        TaskExecutor executor =
                new TaskExecutor(1);

        ReportGenerator reportGenerator =
                new ReportGenerator(
                        tempDirectory.toString(),
                        "report.html");

        assertThrows(
                IllegalArgumentException.class,
                () -> new AutoMATEsEngine(
                        null,
                        executor,
                        reportGenerator));

        assertThrows(
                IllegalArgumentException.class,
                () -> new AutoMATEsEngine(
                        scheduler,
                        null,
                        reportGenerator));

        assertThrows(
                IllegalArgumentException.class,
                () -> new AutoMATEsEngine(
                        scheduler,
                        executor,
                        null));
    }
}
