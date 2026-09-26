package com.automates.tasks.impl;

import com.automates.tasks.TaskResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DataValidationTaskTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldValidateValidJson() throws Exception {

        Path jsonFile =
                tempDirectory.resolve("valid.json");

        Files.write(
                jsonFile,
                "{\"name\":\"Mahesh\",\"role\":\"QA\"}"
                        .getBytes(StandardCharsets.UTF_8));

        DataValidationTask task =
                new DataValidationTask(
                        jsonFile.toString());

        TaskResult result =
                task.execute(null);

        assertTrue(result.isSuccess());

        assertEquals(
                "Data Validation",
                result.getTaskName());

        assertTrue(
                result.getMessage()
                        .contains("Valid JSON file"));
    }

    @Test
    void shouldRejectInvalidJson() throws Exception {

        Path jsonFile =
                tempDirectory.resolve("invalid.json");

        Files.write(
                jsonFile,
                "{\"name\":\"Mahesh\""
                        .getBytes(StandardCharsets.UTF_8));

        DataValidationTask task =
                new DataValidationTask(
                        jsonFile.toString());

        TaskResult result =
                task.execute(null);

        assertFalse(result.isSuccess());

        assertTrue(
                result.getMessage()
                        .contains("Invalid JSON"));
    }

    @Test
    void shouldFailWhenJsonFileDoesNotExist() {

        Path missingFile =
                tempDirectory.resolve("missing.json");

        DataValidationTask task =
                new DataValidationTask(
                        missingFile.toString());

        TaskResult result =
                task.execute(null);

        assertFalse(result.isSuccess());

        assertTrue(
                result.getMessage()
                        .contains("JSON file does not exist"));
    }
}
