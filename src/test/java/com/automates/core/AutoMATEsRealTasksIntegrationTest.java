package com.automates.core;

import com.automates.report.ReportGenerator;
import com.automates.tasks.TaskExecutor;
import com.automates.tasks.TaskResult;
import com.automates.tasks.TaskScheduler;
import com.automates.tasks.impl.DataValidationTask;
import com.automates.tasks.impl.FileCleanupTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutoMATEsRealTasksIntegrationTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldExecuteRealTasksSuccessfully()
            throws Exception {

        Path cleanupDirectory =
                tempDirectory.resolve("cleanup");

        Files.createDirectories(cleanupDirectory);

        Path oldFile =
                cleanupDirectory.resolve("old-file.txt");

        Files.write(
                oldFile,
                "old data".getBytes(StandardCharsets.UTF_8));

        Files.setLastModifiedTime(
                oldFile,
                FileTime.from(
                        Instant.now()
                                .minus(10, ChronoUnit.DAYS)));

        Path jsonFile =
                tempDirectory.resolve("sample-data.json");

        Files.write(
                jsonFile,
                "{\"name\":\"Mahesh\",\"role\":\"QA\"}"
                        .getBytes(StandardCharsets.UTF_8));

        TaskScheduler scheduler =
                new TaskScheduler();

        scheduler.addTask(
                new FileCleanupTask(
                        cleanupDirectory.toString(),
                        7));

        scheduler.addTask(
                new DataValidationTask(
                        jsonFile.toString()));

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

        assertFalse(
                Files.exists(oldFile),
                "Old file should have been deleted");

        assertTrue(
                Files.exists(jsonFile),
                "JSON file should remain");

        assertTrue(
                Files.exists(
                        tempDirectory.resolve(
                                "automation-report.html")),
                "Automation report should be generated");
    }
}
