package com.automates.core;

import com.automates.report.ReportGenerator;
import com.automates.tasks.AutomationTask;
import com.automates.tasks.TaskExecutor;
import com.automates.tasks.TaskResult;
import com.automates.tasks.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Main entry point for the AutoMATEs automation framework.
 *
 * The engine coordinates task scheduling, task execution
 * and report generation.
 */
public class AutoMATEsEngine {

    private static final Logger logger =
            LoggerFactory.getLogger(AutoMATEsEngine.class);

    private final TaskScheduler taskScheduler;
    private final TaskExecutor taskExecutor;
    private final ReportGenerator reportGenerator;

    public AutoMATEsEngine(
            TaskScheduler taskScheduler,
            TaskExecutor taskExecutor,
            ReportGenerator reportGenerator) {

        if (taskScheduler == null) {
            throw new IllegalArgumentException(
                    "TaskScheduler cannot be null");
        }

        if (taskExecutor == null) {
            throw new IllegalArgumentException(
                    "TaskExecutor cannot be null");
        }

        if (reportGenerator == null) {
            throw new IllegalArgumentException(
                    "ReportGenerator cannot be null");
        }

        this.taskScheduler = taskScheduler;
        this.taskExecutor = taskExecutor;
        this.reportGenerator = reportGenerator;
    }

    /**
     * Executes all registered tasks and generates a report.
     *
     * @return list of task results
     */
    public List<TaskResult> run() {

        List<TaskResult> results = new ArrayList<>();

        logger.info(
                "Starting AutoMATEs engine with {} task(s)",
                taskScheduler.getTaskCount());

        for (AutomationTask<?> task :
                taskScheduler.getTasks()) {

            TaskResult result = executeTask(task);

            results.add(result);
        }

        reportGenerator.generate(results);

        logger.info(
                "AutoMATEs engine completed. Total tasks: {}",
                results.size());

        return results;
    }

    /**
     * Executes a single task.
     */
    private TaskResult executeTask(
            AutomationTask<?> task) {

        return executeWithExecutor(task);
    }

    /**
     * Uses the executor while keeping generic task handling
     * inside the framework.
     */
    @SuppressWarnings("unchecked")
    private TaskResult executeWithExecutor(
            AutomationTask<?> task) {

        AutomationTask<Object> executableTask =
                (AutomationTask<Object>) task;

        return taskExecutor.execute(
                executableTask,
                null);
    }

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public ReportGenerator getReportGenerator() {
        return reportGenerator;
    }
}
