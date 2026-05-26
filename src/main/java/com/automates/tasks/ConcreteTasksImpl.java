package com.automates.tasks;

import com.automates.core.AutomationTask;
import com.automates.core.TaskResult;

// ─────────────────────────────────────────────
//  1. FileCleanupTask
// ─────────────────────────────────────────────
class FileCleanupTask extends AutomationTask {

    FileCleanupTask() {
        super("File Cleanup", "Removes stale temp files older than 24h from /tmp");
    }

    @Override
    public TaskResult execute() {
        // Simulated: in production this scans the filesystem
        long start = System.currentTimeMillis();
        simulateWork(120);
        int filesRemoved = 47; // demo value
        return TaskResult.passed(getName(),
                "Removed " + filesRemoved + " stale files successfully.",
                System.currentTimeMillis() - start);
    }

    private void simulateWork(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

// ─────────────────────────────────────────────
//  2. DataValidationTask
// ─────────────────────────────────────────────
class DataValidationTask extends AutomationTask {

    DataValidationTask() {
        super("Data Validation", "Validates incoming data payloads against schema rules");
    }

    @Override
    public TaskResult execute() {
        long start = System.currentTimeMillis();
        simulateWork(200);
        int records = 1_024, valid = 1_019, invalid = 5;
        String msg = String.format("Validated %d records → %d valid, %d invalid.", records, valid, invalid);
        return TaskResult.passed(getName(), msg, System.currentTimeMillis() - start);
    }

    private void simulateWork(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

// ─────────────────────────────────────────────
//  3. WorkflowTriggerTask
// ─────────────────────────────────────────────
class WorkflowTriggerTask extends AutomationTask {

    WorkflowTriggerTask() {
        super("Workflow Trigger", "Fires downstream workflow hooks via internal event bus");
    }

    @Override
    public TaskResult execute() {
        long start = System.currentTimeMillis();
        simulateWork(85);
        return TaskResult.passed(getName(),
                "3 downstream workflows triggered successfully.",
                System.currentTimeMillis() - start);
    }

    private void simulateWork(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

// ─────────────────────────────────────────────
//  4. NotificationTask
// ─────────────────────────────────────────────
class NotificationTask extends AutomationTask {

    NotificationTask() {
        super("Notification Dispatch", "Sends email/Slack alerts upon workflow completion");
    }

    @Override
    public TaskResult execute() {
        long start = System.currentTimeMillis();
        simulateWork(60);
        return TaskResult.passed(getName(),
                "Notifications dispatched to 3 channels.",
                System.currentTimeMillis() - start);
    }

    private void simulateWork(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

// ─────────────────────────────────────────────
//  5. ReportSnapshotTask
// ─────────────────────────────────────────────
class ReportSnapshotTask extends AutomationTask {

    ReportSnapshotTask() {
        super("Report Snapshot", "Captures and persists HTML execution report to /outputs");
    }

    @Override
    public TaskResult execute() {
        long start = System.currentTimeMillis();
        simulateWork(150);
        return TaskResult.passed(getName(),
                "Report snapshot saved → outputs/report.html",
                System.currentTimeMillis() - start);
    }

    private void simulateWork(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
