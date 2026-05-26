package com.automates.core;

/**
 * Enumeration of possible automation task execution statuses.
 *
 * @author Banoth Mahesh Kumar
 */
public enum TaskStatus {
    /** Task completed successfully. */
    PASSED,

    /** Task encountered an error and did not complete. */
    FAILED,

    /** Task was intentionally bypassed (e.g., due to dependency failure). */
    SKIPPED,

    /** Task is queued but has not started. */
    PENDING
}
