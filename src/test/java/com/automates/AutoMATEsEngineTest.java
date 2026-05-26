package com.automates;

import com.automates.core.*;
import com.automates.tasks.TaskExecutor;
import com.automates.tasks.TaskScheduler;
import com.automates.utils.StringUtils;
import com.automates.config.ConfigLoader;
import com.automates.report.ReportGenerator;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit and integration test suite for the AutoMATEs framework.
 *
 * <p>Covers engine lifecycle, task scheduling, executor retry logic,
 * utility helpers, and report generation.</p>
 *
 * @author Banoth Mahesh Kumar
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AutoMATEsEngineTest {

    // ─── Fixtures ───────────────────────────────────────────

    private static AutomationTask passingTask() {
        return new AutomationTask("PassingTask", "Always succeeds") {
            @Override public TaskResult execute() {
                return TaskResult.passed(getName(), "OK", 10);
            }
        };
    }

    private static AutomationTask failingTask() {
        return new AutomationTask("FailingTask", "Always fails") {
            @Override public TaskResult execute() {
                return TaskResult.failed(getName(), "Intentional failure", 5);
            }
        };
    }

    // ─── TaskResult Tests ───────────────────────────────────

    @Test @Order(1)
    @DisplayName("TaskResult.passed() sets PASSED status")
    void testTaskResultPassed() {
        TaskResult r = TaskResult.passed("T1", "All good", 42);
        assertEquals(TaskStatus.PASSED, r.getStatus());
        assertTrue(r.isPassed());
        assertEquals(42, r.getDurationMs());
    }

    @Test @Order(2)
    @DisplayName("TaskResult.failed() sets FAILED status")
    void testTaskResultFailed() {
        TaskResult r = TaskResult.failed("T2", "Error", 10);
        assertEquals(TaskStatus.FAILED, r.getStatus());
        assertFalse(r.isPassed());
    }

    @Test @Order(3)
    @DisplayName("TaskResult.skipped() sets SKIPPED status with zero duration")
    void testTaskResultSkipped() {
        TaskResult r = TaskResult.skipped("T3", "Dependency failed");
        assertEquals(TaskStatus.SKIPPED, r.getStatus());
        assertEquals(0, r.getDurationMs());
    }

    // ─── TaskExecutor Tests ──────────────────────────────────

    @Test @Order(4)
    @DisplayName("Executor returns PASSED for a succeeding task")
    void testExecutorPassingTask() {
        TaskResult result = new TaskExecutor().execute(passingTask());
        assertEquals(TaskStatus.PASSED, result.getStatus());
    }

    @Test @Order(5)
    @DisplayName("Executor returns FAILED after retries exhausted")
    void testExecutorFailingTask() {
        TaskResult result = new TaskExecutor().execute(failingTask());
        assertEquals(TaskStatus.FAILED, result.getStatus());
        assertTrue(result.getMessage().contains("attempts"));
    }

    // ─── TaskScheduler Tests ─────────────────────────────────

    @Test @Order(6)
    @DisplayName("Scheduler registers default tasks correctly")
    void testSchedulerDefaultTasks() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.registerDefaultTasks();
        assertEquals(5, scheduler.getTaskCount());
    }

    @Test @Order(7)
    @DisplayName("Scheduler returns immutable task list")
    void testSchedulerImmutableList() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.registerDefaultTasks();
        List<AutomationTask> tasks = scheduler.getTasks();
        assertThrows(UnsupportedOperationException.class, () -> tasks.add(passingTask()));
    }

    @Test @Order(8)
    @DisplayName("Scheduler clear() removes all tasks")
    void testSchedulerClear() {
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.registerDefaultTasks();
        scheduler.clear();
        assertEquals(0, scheduler.getTaskCount());
    }

    // ─── StringUtils Tests ───────────────────────────────────

    @Test @Order(9)
    @DisplayName("StringUtils.isBlank() handles null and whitespace")
    void testIsBlank() {
        assertTrue(StringUtils.isBlank(null));
        assertTrue(StringUtils.isBlank("   "));
        assertFalse(StringUtils.isBlank("hello"));
    }

    @ParameterizedTest @Order(10)
    @DisplayName("StringUtils.truncate() truncates long strings")
    @ValueSource(ints = {5, 10, 20})
    void testTruncate(int maxLen) {
        String input  = "AutoMATEs Smart Automation";
        String result = StringUtils.truncate(input, maxLen);
        assertTrue(result.length() <= maxLen);
    }

    @Test @Order(11)
    @DisplayName("StringUtils.padRight() produces exact width")
    void testPadRight() {
        assertEquals(10, StringUtils.padRight("hi", 10).length());
        assertEquals(10, StringUtils.padRight("1234567890xx", 10).length());
    }

    // ─── ConfigLoader Tests ──────────────────────────────────

    @Test @Order(12)
    @DisplayName("ConfigLoader loads defaults when file is absent")
    void testConfigDefaults() {
        ConfigLoader.load("nonexistent.properties");
        assertEquals("AutoMATEs", ConfigLoader.get("app.name"));
        assertEquals("1.0.0",     ConfigLoader.get("app.version"));
        assertTrue(ConfigLoader.isLoaded());
    }

    @Test @Order(13)
    @DisplayName("ConfigLoader.get() returns fallback for missing key")
    void testConfigFallback() {
        String val = ConfigLoader.get("does.not.exist", "fallback");
        assertEquals("fallback", val);
    }

    // ─── ReportGenerator Tests ───────────────────────────────

    @Test @Order(14)
    @DisplayName("ReportGenerator creates output file without exception")
    void testReportGeneration() {
        List<TaskResult> results = List.of(
                TaskResult.passed ("R1", "OK",      100),
                TaskResult.failed ("R2", "Error",    50),
                TaskResult.skipped("R3", "Skipped")
        );
        assertDoesNotThrow(() -> new ReportGenerator().generate(results));
    }

    // ─── Full Engine Integration Test ────────────────────────

    @Test @Order(15)
    @DisplayName("End-to-end: Engine runs all tasks and returns results list")
    void testEngineEndToEnd() {
        AutoMATEsEngine engine = new AutoMATEsEngine();
        List<TaskResult> results = engine.run();
        assertNotNull(results);
        assertFalse(results.isEmpty());
        results.forEach(r -> assertNotNull(r.getStatus()));
    }
}
