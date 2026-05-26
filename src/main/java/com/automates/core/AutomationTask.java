package com.automates.core;

/**
 * Abstract base class for all automation tasks in the framework.
 *
 * <p>Every concrete task must extend this class and implement {@link #execute()}.</p>
 *
 * @author Banoth Mahesh Kumar
 */
public abstract class AutomationTask {

    private final String name;
    private final String description;
    private       int    retryCount;

    protected AutomationTask(String name, String description) {
        this.name        = name;
        this.description = description;
        this.retryCount  = 0;
    }

    /**
     * Core execution logic — implemented by each concrete task.
     *
     * @return {@link TaskResult} containing status, message, and metrics
     */
    public abstract TaskResult execute();

    /**
     * Pre-execution hook (setup/teardown pattern).
     * Override to add task-specific setup.
     */
    public void setUp() {}

    /**
     * Post-execution hook for cleanup.
     * Override to add task-specific teardown.
     */
    public void tearDown() {}

    public String getName()        { return name; }
    public String getDescription() { return description; }
    public int    getRetryCount()  { return retryCount; }
    public void   incrementRetry() { this.retryCount++; }

    @Override
    public String toString() {
        return String.format("AutomationTask[name='%s', retries=%d]", name, retryCount);
    }
}
