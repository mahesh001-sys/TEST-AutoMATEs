package com.automates.tasks.impl;

import com.automates.tasks.AutomationTask;
import com.automates.tasks.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Deletes files older than the configured age.
 */
public class FileCleanupTask implements AutomationTask<Void> {

    private static final Logger logger =
            LoggerFactory.getLogger(FileCleanupTask.class);

    private final Path directory;
    private final long maxAgeDays;

    public FileCleanupTask(
            String directory,
            long maxAgeDays) {

        if (directory == null || directory.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Directory cannot be empty");
        }

        if (maxAgeDays < 0) {
            throw new IllegalArgumentException(
                    "maxAgeDays cannot be negative");
        }

        this.directory = Paths.get(directory);
        this.maxAgeDays = maxAgeDays;
    }

    @Override
    public String getTaskName() {
        return "File Cleanup";
    }

    @Override
    public TaskResult execute(Void input) {

        long startTime = System.currentTimeMillis();

        if (!Files.exists(directory)) {

            return TaskResult.failure(
                    getTaskName(),
                    "Directory does not exist: " + directory,
                    System.currentTimeMillis() - startTime);
        }

        if (!Files.isDirectory(directory)) {

            return TaskResult.failure(
                    getTaskName(),
                    "Path is not a directory: " + directory,
                    System.currentTimeMillis() - startTime);
        }

        int deletedFiles = 0;
        int failedFiles = 0;

        Instant cutoffTime =
                Instant.now().minus(maxAgeDays, ChronoUnit.DAYS);

        try (DirectoryStream<Path> files =
                     Files.newDirectoryStream(directory)) {

            for (Path file : files) {

                if (!Files.isRegularFile(file)) {
                    continue;
                }

                FileTime lastModifiedTime =
                        Files.getLastModifiedTime(file);

                if (lastModifiedTime.toInstant()
                        .isBefore(cutoffTime)) {

                    try {
                        Files.delete(file);
                        deletedFiles++;

                        logger.info(
                                "Deleted old file: {}",
                                file);

                    } catch (IOException e) {

                        failedFiles++;

                        logger.warn(
                                "Unable to delete file: {}",
                                file,
                                e);
                    }
                }
            }

        } catch (IOException e) {

            return TaskResult.failure(
                    getTaskName(),
                    "Unable to process directory: "
                            + e.getMessage(),
                    System.currentTimeMillis() - startTime);
        }

        long executionTime =
                System.currentTimeMillis() - startTime;

        String message =
                "Deleted files: " + deletedFiles
                        + ", Failed deletions: " + failedFiles;

        if (failedFiles > 0) {

            return TaskResult.failure(
                    getTaskName(),
                    message,
                    executionTime);
        }

        return TaskResult.success(
                getTaskName(),
                message,
                executionTime);
    }
}
