package com.automates.report;

import com.automates.tasks.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Generates an HTML report from automation task results.
 */
public class ReportGenerator {

    private static final Logger logger =
            LoggerFactory.getLogger(ReportGenerator.class);

    private final Path outputDirectory;
    private final String reportFileName;

    public ReportGenerator(
            String outputDirectory,
            String reportFileName) {

        if (outputDirectory == null
                || outputDirectory.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Output directory cannot be empty");
        }

        if (reportFileName == null
                || reportFileName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Report file name cannot be empty");
        }

        this.outputDirectory = Paths.get(outputDirectory);
        this.reportFileName = reportFileName;
    }

    /**
     * Generates an HTML report.
     *
     * @param results task execution results
     * @return path of the generated report
     */
    public Path generate(List<TaskResult> results) {

        if (results == null) {
            throw new IllegalArgumentException(
                    "Results cannot be null");
        }

        try {
            Files.createDirectories(outputDirectory);

            Path reportPath =
                    outputDirectory.resolve(reportFileName);

            String html = buildHtml(results);

            Files.write(
                    reportPath,
                    html.getBytes(StandardCharsets.UTF_8));

            logger.info(
                    "Automation report generated: {}",
                    reportPath);

            return reportPath;

        } catch (IOException e) {

            logger.error(
                    "Unable to generate automation report",
                    e);

            throw new IllegalStateException(
                    "Unable to generate automation report",
                    e);
        }
    }

    private String buildHtml(List<TaskResult> results) {

        int passed = 0;
        int failed = 0;

        StringBuilder rows = new StringBuilder();

        for (TaskResult result : results) {

            if (result.isSuccess()) {
                passed++;
            } else {
                failed++;
            }

            rows.append("<tr>")
                    .append("<td>")
                    .append(escapeHtml(result.getTaskName()))
                    .append("</td>")
                    .append("<td>")
                    .append(result.isSuccess()
                            ? "PASSED"
                            : "FAILED")
                    .append("</td>")
                    .append("<td>")
                    .append(escapeHtml(result.getMessage()))
                    .append("</td>")
                    .append("<td>")
                    .append(result.getExecutionTimeMs())
                    .append(" ms")
                    .append("</td>")
                    .append("</tr>");
        }

        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>AutoMATEs Automation Report</title>"
                + "<style>"
                + "body{font-family:Arial,sans-serif;margin:40px;}"
                + "table{border-collapse:collapse;width:100%;}"
                + "th,td{border:1px solid #ccc;padding:10px;text-align:left;}"
                + "th{background:#f2f2f2;}"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<h1>AutoMATEs Automation Report</h1>"
                + "<p>Total Tasks: "
                + results.size()
                + "</p>"
                + "<p>Passed: "
                + passed
                + "</p>"
                + "<p>Failed: "
                + failed
                + "</p>"
                + "<table>"
                + "<tr>"
                + "<th>Task</th>"
                + "<th>Status</th>"
                + "<th>Message</th>"
                + "<th>Execution Time</th>"
                + "</tr>"
                + rows
                + "</table>"
                + "</body>"
                + "</html>";
    }

    private String escapeHtml(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
