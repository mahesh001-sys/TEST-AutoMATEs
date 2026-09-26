package com.automates.tasks.impl;

import com.automates.tasks.AutomationTask;
import com.automates.tasks.TaskResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Validates that a JSON file exists and contains valid JSON data.
 */
public class DataValidationTask implements AutomationTask<Void> {

    private static final Logger logger =
            LoggerFactory.getLogger(DataValidationTask.class);

    private final Path jsonFile;
    private final ObjectMapper objectMapper;

    public DataValidationTask(String filePath) {

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "JSON file path cannot be empty");
        }

        this.jsonFile = Paths.get(filePath);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getTaskName() {
        return "Data Validation";
    }

    @Override
    public TaskResult execute(Void input) {

        long startTime = System.currentTimeMillis();

        if (!Files.exists(jsonFile)) {

            return TaskResult.failure(
                    getTaskName(),
                    "JSON file does not exist: " + jsonFile,
                    System.currentTimeMillis() - startTime);
        }

        if (!Files.isRegularFile(jsonFile)) {

            return TaskResult.failure(
                    getTaskName(),
                    "Path is not a regular file: " + jsonFile,
                    System.currentTimeMillis() - startTime);
        }

        try {

            JsonNode jsonNode =
                    objectMapper.readTree(jsonFile.toFile());

            if (jsonNode == null) {

                return TaskResult.failure(
                        getTaskName(),
                        "JSON file is empty: " + jsonFile,
                        System.currentTimeMillis() - startTime);
            }

            logger.info(
                    "JSON validation successful: {}",
                    jsonFile);

            return TaskResult.success(
                    getTaskName(),
                    "Valid JSON file: " + jsonFile,
                    System.currentTimeMillis() - startTime);

        } catch (IOException e) {

            logger.error(
                    "JSON validation failed: {}",
                    jsonFile,
                    e);

            return TaskResult.failure(
                    getTaskName(),
                    "Invalid JSON: " + e.getMessage(),
                    System.currentTimeMillis() - startTime);
        }
    }
}
