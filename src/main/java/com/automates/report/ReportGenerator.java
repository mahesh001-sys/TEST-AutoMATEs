package com.automates.report;

import com.automates.core.TaskResult;
import com.automates.core.TaskStatus;
import com.automates.utils.Logger;
import com.automates.utils.StringUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Generates a human-readable HTML execution report from a list of {@link TaskResult} objects.
 *
 * <p>The report is written to {@code outputs/report.html}.</p>
 *
 * @author Banoth Mahesh Kumar
 */
public class ReportGenerator {

    private static final String OUTPUT_PATH = "outputs/report.html";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Logger logger = Logger.getInstance();

    /**
     * Generates and writes the HTML report.
     *
     * @param results list of completed task results
     */
    public void generate(List<TaskResult> results) {
        new java.io.File("outputs").mkdirs();

        long passed  = results.stream().filter(r -> r.getStatus() == TaskStatus.PASSED).count();
        long failed  = results.stream().filter(r -> r.getStatus() == TaskStatus.FAILED).count();
        long skipped = results.stream().filter(r -> r.getStatus() == TaskStatus.SKIPPED).count();
        long totalMs = results.stream().mapToLong(TaskResult::getDurationMs).sum();

        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_PATH))) {
            pw.println(buildHtml(results, passed, failed, skipped, totalMs));
            logger.info("Report written → " + OUTPUT_PATH);
        } catch (IOException e) {
            logger.error("Failed to write report: " + e.getMessage());
        }
    }

    private String buildHtml(List<TaskResult> results,
                              long passed, long failed, long skipped, long totalMs) {
        StringBuilder sb = new StringBuilder();
        String now = LocalDateTime.now().format(FMT);

        sb.append("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8"/>
                  <title>AutoMATEs Execution Report</title>
                  <style>
                    body{font-family:system-ui,sans-serif;background:#0f172a;color:#e2e8f0;margin:0;padding:2rem}
                    h1{color:#38bdf8;font-size:1.8rem;margin-bottom:.25rem}
                    .meta{color:#94a3b8;font-size:.85rem;margin-bottom:1.5rem}
                    .cards{display:flex;gap:1rem;margin-bottom:2rem;flex-wrap:wrap}
                    .card{background:#1e293b;border-radius:.75rem;padding:1.25rem 2rem;min-width:120px;text-align:center}
                    .card .num{font-size:2rem;font-weight:700}
                    .card .lbl{font-size:.75rem;color:#94a3b8;margin-top:.25rem}
                    .pass{color:#4ade80}.fail{color:#f87171}.skip{color:#fbbf24}.dur{color:#60a5fa}
                    table{width:100%;border-collapse:collapse;background:#1e293b;border-radius:.75rem;overflow:hidden}
                    th{background:#0f172a;padding:.75rem 1rem;text-align:left;font-size:.8rem;
                       text-transform:uppercase;letter-spacing:.05em;color:#94a3b8}
                    td{padding:.75rem 1rem;font-size:.875rem;border-top:1px solid #334155}
                    .badge{display:inline-block;padding:.2rem .6rem;border-radius:.375rem;font-size:.75rem;font-weight:600}
                    .badge-pass{background:#14532d;color:#4ade80}
                    .badge-fail{background:#450a0a;color:#f87171}
                    .badge-skip{background:#422006;color:#fbbf24}
                    footer{margin-top:2rem;font-size:.75rem;color:#64748b;text-align:center}
                  </style>
                </head>
                <body>
                """);

        sb.append("<h1>⚡ AutoMATEs — Execution Report</h1>\n");
        sb.append("<p class='meta'>Generated: ").append(now)
          .append(" &nbsp;|&nbsp; AutoMATEs v1.0.0</p>\n");

        sb.append("<div class='cards'>\n");
        sb.append(card(String.valueOf(results.size()), "Total Tasks", "dur"));
        sb.append(card(String.valueOf(passed),  "Passed",  "pass"));
        sb.append(card(String.valueOf(failed),  "Failed",  "fail"));
        sb.append(card(String.valueOf(skipped), "Skipped", "skip"));
        sb.append(card(totalMs + "ms",          "Total Time", "dur"));
        sb.append("</div>\n");

        sb.append("<table>\n<thead><tr>");
        for (String h : List.of("#", "Task Name", "Status", "Duration", "Message"))
            sb.append("<th>").append(h).append("</th>");
        sb.append("</tr></thead>\n<tbody>\n");

        int i = 1;
        for (TaskResult r : results) {
            String badge = switch (r.getStatus()) {
                case PASSED  -> "<span class='badge badge-pass'>✔ PASSED</span>";
                case FAILED  -> "<span class='badge badge-fail'>✘ FAILED</span>";
                case SKIPPED -> "<span class='badge badge-skip'>— SKIPPED</span>";
                default      -> r.getStatus().name();
            };
            sb.append("<tr>")
              .append("<td>").append(i++).append("</td>")
              .append("<td>").append(r.getTaskName()).append("</td>")
              .append("<td>").append(badge).append("</td>")
              .append("<td>").append(r.getDurationMs()).append("ms</td>")
              .append("<td>").append(StringUtils.truncate(r.getMessage(), 80)).append("</td>")
              .append("</tr>\n");
        }
        sb.append("</tbody></table>\n");
        sb.append("<footer>AutoMATEs © 2025 Banoth Mahesh Kumar · github.com/mahesh001-sys</footer>\n");
        sb.append("</body></html>");
        return sb.toString();
    }

    private String card(String num, String label, String cls) {
        return String.format("""
                <div class='card'>
                  <div class='num %s'>%s</div>
                  <div class='lbl'>%s</div>
                </div>
                """, cls, num, label);
    }
}
