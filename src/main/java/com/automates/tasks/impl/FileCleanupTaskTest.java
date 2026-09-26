package com.automates.tasks.impl;

import com.automates.tasks.TaskResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class FileCleanupTaskTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldDeleteOldFiles() throws Exception {

        Path oldFile =
                tempDirectory.resolve("old-file.txt");

        Path recentFile =
                tempDirectory.resolve("recent-file.txt");

        Files.write(
                oldFile,
                "old data".getBytes());

        Files.write(
                recentFile,
                "recent data".getBytes());

        Files.setLastModifiedTime(
                oldFile,
                FileTime.from(
                        Instant.now()
                                .minus(10, ChronoUnit.DAYS)));

        Files.setLastModifiedTime(
                recentFile,
                FileTime.from(
                        Instant.now()));

        FileCleanupTask task =
                new FileCleanupTask(
                        tempDirectory.toString(),
                        7);

        TaskResult result =
                task.execute(null);

        assertTrue(result.isSuccess());

        assertFalse(
                Files.exists(oldFile),
                "Old file should be deleted");

        assertTrue(
                Files.exists(recentFile),
                "Recent file should remain");
    }

    @Test
    void shouldFailWhenDirectoryDoesNotExist() {

        Path missingDirectory =
                tempDirectory.resolve("missing");

        FileCleanupTask task =
                new FileCleanupTask(
                        missingDirectory.toString(),
                        7);

        TaskResult result =
                task.execute(null);

        assertFalse(result.isSuccess());

        assertTrue(
                result.getMessage()
                        .contains("Directory does not exist"));
    }
}
