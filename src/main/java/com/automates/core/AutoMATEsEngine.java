package com.automates.core;

import com.automates.tasks.TaskExecutor;
import com.automates.tasks.TaskScheduler;
import com.automates.utils.Logger;
import com.automates.report.ReportGenerator;
import com.automates.config.ConfigLoader;

import java.util.List;
import java.util.ArrayList;

/**
 * AutoMATEsEngine — Core orchestrator for the TEST-AutoMATEs framework.
 *
 * <p>Responsible for initializing the automation pipeline, managing task
 * execution lifecycle, and generating final reports.</p>
 *
 * @author  Banoth Mahesh Kumar
 * @version 1.0.0
 * @since   2025-01-01
 */
public class AutoMATEsEngine {

    private final TaskScheduler scheduler;
    private final TaskExecutor  executor;
    private final ReportGenerator reporter;
    private final Logger logger;

    public AutoMATEsEngine() {
        this.logger    = Logger.getInstance();
        this.scheduler = new TaskScheduler();
        this.executor  = new TaskExecutor();
        this.reporter  = new ReportGenerator();
    }

    /**
     * Bootstraps the engine: loads config, registers tasks, and starts execution.
     */
    public void initialize() {
        logger.info("=== AutoMATEs Engine Initializing ===");
        ConfigLoader.load("src/main/resources/config.properties");
        scheduler.registerDefaultTasks();
        logger.info("Engine ready. Tasks registered: " + scheduler.getTaskCount());
    }

    /**
     * Runs all registered automation tasks sequentially and collects results.
     *
     * @return list of {@link TaskResult} objects for each executed task
     */
    public List<TaskResult> run() {
        initialize();
        logger.info("Starting automation workflow...");

        List<TaskResult> results = new ArrayList<>();
        for (AutomationTask task : scheduler.getTasks()) {
            logger.info("Executing task: " + task.getName());
            TaskResult result = executor.execute(task);
            results.add(result);
            logger.info("  Status: " + result.getStatus() + " | Duration: " + result.getDurationMs() + "ms");
        }

        reporter.generate(results);
        logger.info("=== AutoMATEs Workflow Completed ===");
        return results;
    }

    public static void main(String[] args) {
        AutoMATEsEngine engine = new AutoMATEsEngine();
        List<TaskResult> results = engine.run();
        long passed = results.stream().filter(r -> r.getStatus() == TaskStatus.PASSED).count();
        long failed = results.stream().filter(r -> r.getStatus() == TaskStatus.FAILED).count();
        System.out.printf("%nSummary → Total: %d | Passed: %d | Failed: %d%n",
                results.size(), passed, failed);
    }
}
