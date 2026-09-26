package com.automates.report;

import com.automates.tasks.TaskResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldGenerateHtmlReport() throws Exception {

        List<TaskResult> results = Arrays.asList(

                TaskResult.success(
                        "File Cleanup",
                        "Deleted files: 2, Failed deletions: 0",
                        25),

                TaskResult.failure(
                        "Data Validation",
                        "Invalid JSON",
                        15)
        );

        ReportGenerator generator =
                new ReportGenerator(
                        tempDirectory.toString(),
                        "automation-report.html");

        Path reportPath =
                generator.generate(results);

        assertTrue(
                Files.exists(reportPath),
                "Report file should be generated");

        String reportContent =
                new String(
                        Files.readAllBytes(reportPath),
                        StandardCharsets.UTF_8);

        assertTrue(
                reportContent.contains(
                        "AutoMATEs Automation Report"));

        assertTrue(
                reportContent.contains(
                        "File Cleanup"));

        assertTrue(
                reportContent.contains(
                        "Data Validation"));

        assertTrue(
                reportContent.contains("PASSED"));

        assertTrue(
                reportContent.contains("FAILED"));

        assertTrue(
                reportContent.contains("Total Tasks: 2"));

        assertTrue(
                reportContent.contains("Passed: 1"));

        assertTrue(
                reportContent.contains("Failed: 1"));
    }

    @Test
    void shouldRejectNullResults() {

        ReportGenerator generator =
                new ReportGenerator(
                        tempDirectory.toString(),
                        "report.html");

        assertThrows(
                IllegalArgumentException.class,
                () -> generator.generate(null));
    }
}
