<div align="center">

# ⚡ TEST-AutoMATEs

### Smart Automation Framework for Workflow Optimization & Task Execution

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![JUnit5](https://img.shields.io/badge/JUnit-5.10-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![CI](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)](https://github.com/mahesh001-sys/TEST-AutoMATEs/actions)


<br/>

> A production-grade Java automation framework that orchestrates multi-step workflows,
> handles retries gracefully, and delivers rich HTML execution reports — all with zero dependencies on third-party runners.

<br/>

[Features](#-features) · [Architecture](#-architecture) · [Quick Start](#-quick-start) · [Usage](#-usage) · [Tests](#-tests) · [Author](#-author)

</div>

---

## 📋 Overview

**TEST-AutoMATEs** is an extensible Java-based automation engine designed for teams that need repeatable, observable, and maintainable automation pipelines. It follows clean-architecture principles — separating task definitions, execution logic, configuration, and reporting into well-defined packages.

The framework ships with five built-in tasks and an easy-to-extend `AutomationTask` base class, so adding your own workflows takes minutes.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔁 **Retry Logic** | Configurable retry attempts per task with exponential-style delay |
| 📊 **HTML Reports** | Auto-generated dark-themed execution report saved to `outputs/` |
| 🔧 **Config-Driven** | Centralised `config.properties` controls retries, timeouts, channels |
| 🧩 **Extensible** | Extend `AutomationTask` to add any custom workflow in minutes |
| 📝 **Structured Logging** | Thread-safe singleton logger writes to stdout + `logs/automates.log` |
| ✅ **Full Test Suite** | 17 JUnit 5 tests covering unit, parameterized, and end-to-end scenarios |
| 🚀 **CI/CD Ready** | GitHub Actions workflow included — runs on every push |

---

## 🏗 Architecture

```
TEST-AutoMATEs/
├── src/
│   ├── main/java/com/automates/
│   │   ├── core/                    # Engine, task base, result model
│   │   │   ├── AutoMATEsEngine.java      ← Orchestrator & entry point
│   │   │   ├── AutomationTask.java       ← Abstract base for all tasks
│   │   │   ├── TaskResult.java           ← Immutable result value object
│   │   │   └── TaskStatus.java           ← PASSED / FAILED / SKIPPED enum
│   │   ├── tasks/                   # Task registry & executor
│   │   │   ├── TaskScheduler.java        ← Registers & orders tasks
│   │   │   ├── TaskExecutor.java         ← Runs tasks with retry logic
│   │   │   └── ConcreteTasksImpl.java    ← 5 built-in automation tasks
│   │   ├── config/
│   │   │   └── ConfigLoader.java         ← Properties-file configuration
│   │   ├── utils/
│   │   │   ├── Logger.java               ← Thread-safe singleton logger
│   │   │   └── StringUtils.java          ← String helper utilities
│   │   └── report/
│   │       └── ReportGenerator.java      ← HTML report writer
│   ├── main/resources/
│   │   └── config.properties             ← Runtime configuration
│   └── test/java/com/automates/
│       └── AutoMATEsEngineTest.java      ← 17 JUnit 5 tests
├── outputs/
│   ├── report.html                       ← Execution report (auto-generated)
│   └── test-execution-output.txt         ← Console output sample
├── .github/workflows/ci.yml              ← GitHub Actions CI pipeline
├── pom.xml                               ← Maven build descriptor
└── README.md
```

### Execution Flow

```
AutoMATEsEngine.run()
        │
        ├─► ConfigLoader.load()          — loads config.properties
        │
        ├─► TaskScheduler.registerDefaultTasks()
        │        ├── FileCleanupTask
        │        ├── DataValidationTask
        │        ├── WorkflowTriggerTask
        │        ├── NotificationTask
        │        └── ReportSnapshotTask
        │
        ├─► TaskExecutor.execute(task)   — for each task (with retry)
        │        ├── task.setUp()
        │        ├── task.execute()  ──► TaskResult
        │        └── task.tearDown()
        │
        └─► ReportGenerator.generate()  — writes outputs/report.html
```

---

## 🚀 Quick Start

### Prerequisites

| Tool | Version |
|---|---|
| Java (JDK) | 17 or higher |
| Apache Maven | 3.8+ |
| Git | Any recent version |

### Clone & Build

```bash
# 1. Clone the repository
git clone https://github.com/mahesh001-sys/TEST-AutoMATEs.git
cd TEST-AutoMATEs

# 2. Compile and package
mvn clean package -DskipTests

# 3. Run all tests
mvn test

# 4. Execute the automation engine
java -jar target/automates-1.0.0.jar
```

---

## 🔧 Usage

### Running the Engine

```bash
java -jar target/automates-1.0.0.jar
```

**Expected console output:**

```
[2025-05-26 14:32:10.001] [INFO ] === AutoMATEs Engine Initializing ===
[2025-05-26 14:32:10.003] [INFO ] Configuration loaded from: src/main/resources/config.properties
[2025-05-26 14:32:10.006] [INFO ] Engine ready. Tasks registered: 5
[2025-05-26 14:32:10.007] [INFO ] Starting automation workflow...
[2025-05-26 14:32:10.131] [INFO ]   Status: PASSED | Duration: 124ms
...
[2025-05-26 14:32:10.642] [INFO ] === AutoMATEs Workflow Completed ===

Summary → Total: 5 | Passed: 5 | Failed: 0
```

The HTML report is written to `outputs/report.html` automatically.

### Adding a Custom Task

```java
// 1. Extend AutomationTask
public class MyCustomTask extends AutomationTask {

    public MyCustomTask() {
        super("My Custom Task", "Does something useful");
    }

    @Override
    public TaskResult execute() {
        long start = System.currentTimeMillis();

        // ── your logic here ──
        boolean success = doWork();

        long duration = System.currentTimeMillis() - start;
        return success
            ? TaskResult.passed(getName(), "Completed successfully.", duration)
            : TaskResult.failed(getName(), "Something went wrong.", duration);
    }
}

// 2. Register it before calling engine.run()
TaskScheduler scheduler = new TaskScheduler();
scheduler.register(new MyCustomTask());
```

### Configuration

Edit `src/main/resources/config.properties`:

```properties
task.max.retries=2          # retries before marking FAILED
task.retry.delay.ms=500     # delay between retries
report.output.dir=outputs   # where report.html is saved
log.level=INFO              # INFO | DEBUG | WARN | ERROR
notify.channels=email,slack # notification targets
```

---

## ✅ Tests

Run the full JUnit 5 suite:

```bash
mvn test
```

**Test results:**

```
Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
Time elapsed: 4.218 s

BUILD SUCCESS
```

| # | Test | Coverage Area |
|---|---|---|
| 1–3 | `testTaskResult*` | TaskResult factory methods & status |
| 4–5 | `testExecutor*` | Executor pass/fail with retry |
| 6–8 | `testScheduler*` | Task registration, immutability, clear |
| 9–13 | `testIsBlank`, `testTruncate`, `testPadRight`, `testConfig*` | Utilities & config |
| 14 | `testReportGeneration` | HTML report file creation |
| 15 | `testEngineEndToEnd` | Full integration run |

---

## 📸 Sample Report

The auto-generated `outputs/report.html` provides a dark-themed dashboard:

- **Summary cards** — total tasks, passed, failed, skipped, total time
- **Results table** — per-task status badges, duration, and message
- Clean, self-contained HTML — no external dependencies

---

## 🛣 Roadmap

- [ ] GUI dashboard for live task monitoring
- [ ] Parallel task execution (thread pool)
- [ ] Cloud deployment (AWS Lambda / GCP Cloud Run)
- [ ] Advanced CI/CD integration with Allure reports
- [ ] Plugin architecture for third-party task providers
- [ ] REST API for remote task triggering

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'feat: add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

Please follow the existing code style and add tests for any new functionality.

---

## 👤 Author

<div align="center">

**Banoth Mahesh Kumar**

[![GitHub](https://img.shields.io/badge/GitHub-mahesh001--sys-181717?style=for-the-badge&logo=github)](https://github.com/mahesh001-sys)

*Built with ☕ Java and a passion for clean automation.*

</div>

---

<div align="center">
<sub>⭐ If this project helped you, please give it a star!</sub>
</div>
