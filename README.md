<div align="center">

# ⚡ TEST-AutoMATEs

### Java-Based Automation Framework for Workflow Optimization & Task Execution

[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![JUnit5](https://img.shields.io/badge/JUnit-5.10-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![CI](https://img.shields.io/badge/CI-GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)](https://github.com/mahesh001-sys/TEST-AutoMATEs/actions)

<br/>

> A Java-based automation framework designed to execute repeatable workflows, manage task execution and failures, generate structured results, and produce HTML execution reports with automated testing and CI/CD support.

<br/>

[Features](#-features) · [Architecture](#-architecture) · [Quick Start](#-quick-start) · [Usage](#-usage) · [Testing](#-testing) · [AI Development](#-ai-assisted-development) · [Author](#-author)

</div>

---

## 📋 Overview

**TEST-AutoMATEs** is an extensible Java-based automation framework designed to execute and monitor repeatable workflow tasks in a structured and maintainable way.

The project separates task definitions, scheduling, execution, configuration, logging, result handling, and reporting into dedicated components.

It includes five built-in demonstration tasks and provides an `AutomationTask` abstraction that can be extended to create custom automation workflows.

The framework also includes automated JUnit 5 testing, Maven-based build management, HTML reporting, and GitHub Actions CI.

---

## 🎯 Project Goals

- Automate repeatable workflow tasks
- Provide a reusable task execution architecture
- Handle task failures and retry attempts
- Capture structured execution results
- Generate readable HTML execution reports
- Support automated testing with JUnit 5
- Maintain a clean and extensible Java architecture
- Integrate the project with CI/CD using GitHub Actions

---

## ✨ Features

| Feature | Description |
|---|---|
| ⚙️ **Task Automation** | Executes multiple workflow tasks through a central automation engine |
| 🔁 **Retry Handling** | Supports retry attempts when task execution fails |
| 📊 **HTML Reporting** | Generates an HTML report containing task status, duration, and messages |
| 🔧 **Configuration** | Uses `config.properties` for centralized application settings |
| 🧩 **Extensible Architecture** | Extend `AutomationTask` to create custom workflow tasks |
| 📝 **Structured Logging** | Thread-safe singleton logger with console and file logging |
| 🗂️ **Task Scheduling** | Registers and manages tasks before execution |
| ✅ **JUnit 5 Testing** | Automated tests covering core components and workflow behavior |
| 🚀 **CI/CD Ready** | GitHub Actions workflow for automated build and testing |
| 🛡️ **Result Handling** | Immutable `TaskResult` model with PASSED, FAILED, and SKIPPED states |
| 📁 **File I/O** | Generates reports and maintains execution logs |
| ☕ **Java 17** | Built using modern Java features and object-oriented principles |

---

## 🏗 Architecture

<pre>
TEST-AutoMATEs/
├── src/
│   ├── main/
│   │   ├── java/com/automates/
│   │   │   ├── core/
│   │   │   │   ├── AutoMATEsEngine.java
│   │   │   │   ├── AutomationTask.java
│   │   │   │   ├── TaskResult.java
│   │   │   │   └── TaskStatus.java
│   │   │   │
│   │   │   ├── tasks/
│   │   │   │   ├── TaskScheduler.java
│   │   │   │   ├── TaskExecutor.java
│   │   │   │   └── ConcreteTasksImpl.java
│   │   │   │
│   │   │   ├── config/
│   │   │   │   └── ConfigLoader.java
│   │   │   │
│   │   │   ├── utils/
│   │   │   │   ├── Logger.java
│   │   │   │   └── StringUtils.java
│   │   │   │
│   │   │   └── report/
│   │   │       └── ReportGenerator.java
│   │   │
│   │   └── resources/
│   │       └── config.properties
│   │
│   └── test/
│       └── java/com/automates/
│           └── AutoMATEsEngineTest.java
│
├── outputs/
│   ├── report.html
│   └── test-execution-output.txt
│
├── logs/
│   └── automates.log
│
├── .github/
│   └── workflows/
│       └── ci.yml
│
├── pom.xml
└── README.md
</pre>

---

## 🔄 Execution Flow

<pre>
                    ┌──────────────────────┐
                    │   AutoMATEsEngine    │
                    │     Entry Point      │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    ConfigLoader      │
                    │ Load Configuration   │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    TaskScheduler     │
                    │ Register Tasks       │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    TaskExecutor      │
                    │ Execute Each Task    │
                    └──────────┬───────────┘
                               │
                    ┌──────────┴──────────┐
                    ▼                     ▼
             ┌─────────────┐       ┌─────────────┐
             │  setUp()    │       │   Retry     │
             └──────┬──────┘       │  Handling   │
                    │              └──────┬──────┘
                    ▼                     │
             ┌─────────────┐              │
             │  execute()  │◄────────────┘
             └──────┬──────┘
                    │
                    ▼
             ┌─────────────┐
             │ TaskResult  │
             │ PASS/FAIL/  │
             │   SKIPPED   │
             └──────┬──────┘
                    │
                    ▼
             ┌─────────────┐
             │ tearDown()  │
             └──────┬──────┘
                    │
                    ▼
          ┌─────────────────────┐
          │  ReportGenerator    │
          │ Generate HTML       │
          └──────────┬──────────┘
                     │
                     ▼
             outputs/report.html
</pre>

---

## 🧱 Core Components

### `AutoMATEsEngine`

The main orchestration component.

Responsibilities:

- Loads configuration
- Registers tasks
- Starts task execution
- Collects task results
- Generates the final HTML report

### `AutomationTask`

Abstract base class for automation tasks.

Provides:

- Task name
- Task description
- `setUp()`
- `execute()`
- `tearDown()`

Custom tasks can extend this class and implement their own business logic.

### `TaskScheduler`

Responsible for:

- Registering tasks
- Maintaining task order
- Managing the task collection
- Providing tasks to the execution layer

### `TaskExecutor`

Responsible for:

- Executing individual tasks
- Calling setup and cleanup methods
- Measuring execution time
- Handling task failures
- Supporting retry attempts
- Producing `TaskResult` objects

### `TaskResult`

Immutable result object containing information such as:

- Task name
- Task status
- Execution duration
- Result message

Supported statuses:

- `PASSED`
- `FAILED`
- `SKIPPED`

### `ConfigLoader`

Loads application settings from:

`src/main/resources/config.properties`

### `Logger`

A thread-safe singleton logger that supports:

- INFO
- WARN
- ERROR
- DEBUG

Logging is available through console output and the project log file.

### `ReportGenerator`

Generates a self-contained HTML execution report containing:

- Total tasks
- Passed tasks
- Failed tasks
- Skipped tasks
- Total execution time
- Individual task status
- Task duration
- Execution messages

---

## 🤖 Built-In Automation Tasks

The project contains five built-in demonstration/simulated tasks:

| Task | Purpose |
|---|---|
| 🧹 **File Cleanup** | Demonstrates cleanup-style automation |
| 🔍 **Data Validation** | Demonstrates validation workflow execution |
| ⚡ **Workflow Trigger** | Demonstrates workflow/action triggering |
| 🔔 **Notification Dispatch** | Demonstrates notification-style processing |
| 📸 **Report Snapshot** | Demonstrates report/snapshot processing |

> **Note:** These tasks are demonstration implementations used to show the framework architecture. They do not represent live integrations with external production systems.

The architecture can be extended later with real integrations such as APIs, databases, cloud services, notification platforms, or other automation targets.

---

## 🚀 Quick Start

### Prerequisites

| Tool | Version |
|---|---|
| Java JDK | 17 or higher |
| Apache Maven | 3.8+ |
| Git | Recent version |

### 1. Clone the Repository

    git clone https://github.com/mahesh001-sys/TEST-AutoMATEs.git
    cd TEST-AutoMATEs

### 2. Run Tests

    mvn clean test

### 3. Build the Project

    mvn clean package

### 4. Run the Executable JAR

    java -jar target/automates-1.0.0.jar

### Alternative Build Without Tests

    mvn clean package -DskipTests

---

## 🔧 Usage

### Running the Automation Engine

    java -jar target/automates-1.0.0.jar

The engine loads the configuration, registers the default tasks, executes them, collects their results, and generates the HTML report.

### Typical Execution Flow

<pre>
Configuration
     ↓
Task Registration
     ↓
Task Execution
     ↓
Setup → Execute → Tear Down
     ↓
Task Result
     ↓
Retry if Required
     ↓
Collect Results
     ↓
Generate HTML Report
</pre>

---

## 🧩 Creating a Custom Task

A custom workflow can be created by extending `AutomationTask`.

Example:

<pre><code>public class MyCustomTask extends AutomationTask {

    public MyCustomTask() {
        super("My Custom Task", "Performs a custom automation workflow");
    }

    @Override
    public TaskResult execute() {

        long start = System.currentTimeMillis();

        boolean success = doWork();

        long duration = System.currentTimeMillis() - start;

        if (success) {
            return TaskResult.passed(
                getName(),
                "Completed successfully.",
                duration
            );
        }

        return TaskResult.failed(
            getName(),
            "Something went wrong.",
            duration
        );
    }

    private boolean doWork() {
        // Custom automation logic
        return true;
    }
}</code></pre>

Register the task with the scheduler:

<pre><code>TaskScheduler scheduler = new TaskScheduler();

scheduler.register(new MyCustomTask());</code></pre>

This design makes the framework extensible without modifying the core execution engine.

---

## ⚙️ Configuration

Configuration is maintained in:

`src/main/resources/config.properties`

Example:

<pre><code>app.name=AutoMATEs
app.version=1.0.0
app.environment=production

task.max.retries=2
task.retry.delay.ms=500
task.timeout.ms=30000

report.output.dir=outputs
report.format=html
report.include.timestamp=true

log.level=INFO
log.file=logs/automates.log
log.max.file.size.mb=10

notify.channels=email,slack
notify.email=team@automates.dev</code></pre>

> **Configuration note:** The properties file centralizes application settings, but not every property is necessarily consumed by every current implementation component. Retry handling currently supports retry attempts and delay behavior; future versions can make additional settings fully runtime-driven.

---

## 🧪 Testing

The project uses **JUnit 5** for automated testing.

Run the complete test suite with:

    mvn test

Tests cover areas such as:

- `TaskResult` creation and status handling
- Task execution behavior
- Retry scenarios
- Task scheduling
- Utility methods
- Configuration loading
- HTML report generation
- End-to-end engine execution
- Parameterized test scenarios

### Testing Approach

<pre>
Unit Tests
    │
    ├── Core Components
    ├── Task Results
    ├── Scheduler
    ├── Executor
    ├── Utilities
    └── Configuration
             │
             ▼
Integration / End-to-End
             │
             ▼
     Automation Engine
             │
             ▼
       HTML Report
</pre>

---

## 📊 HTML Execution Report

After running the automation engine, the framework generates:

`outputs/report.html`

The report contains:

- 📌 Total number of tasks
- ✅ Passed tasks
- ❌ Failed tasks
- ⏭️ Skipped tasks
- ⏱️ Total execution time
- 📋 Individual task results
- ⏱️ Task execution duration
- 💬 Execution messages

The generated report is self-contained HTML and does not require external reporting software.

---

## 📝 Logging

The framework includes a custom thread-safe singleton logger.

Supported log levels:

    INFO
    WARN
    ERROR
    DEBUG

Logs can be written to:

    Console
    logs/automates.log

This provides visibility into the automation execution flow and helps during debugging.

---

## 🔄 Retry Handling

`TaskExecutor` supports retry attempts when task execution fails.

The execution pattern is:

<pre>
Task Starts
    ↓
Execute
    ↓
Success? ───── Yes ───► PASSED
    │
    No
    ↓
Retry Attempt
    ↓
Execute Again
    ↓
Success? ───── Yes ───► PASSED
    │
    No
    ↓
Continue Until Retry Limit
    ↓
FAILED
</pre>

Retry behavior is implemented in the execution layer so individual tasks do not need to manage retry loops themselves.

---

## 🔄 CI/CD

The project includes a GitHub Actions workflow:

`.github/workflows/ci.yml`

The CI workflow is configured to:

- Trigger on pushes to the configured branches
- Run on pull requests to the configured branch
- Set up JDK 17
- Use Maven
- Build the project
- Run automated tests
- Upload relevant test/execution artifacts

This helps verify that changes can be built and tested automatically.

---

## 🧠 Java Concepts Demonstrated

This project demonstrates several important Java concepts:

| Concept | Usage |
|---|---|
| **OOP** | Overall framework design |
| **Abstraction** | `AutomationTask` |
| **Inheritance** | Custom tasks extending `AutomationTask` |
| **Polymorphism** | Different task implementations |
| **Encapsulation** | Controlled task/result state |
| **Enums** | `TaskStatus` |
| **Collections** | Task and result management |
| **Immutable Objects** | `TaskResult` |
| **Exception Handling** | Task/configuration error handling |
| **File I/O** | Logs and HTML report generation |
| **Properties** | Application configuration |
| **Streams & Lambdas** | Collection processing |
| **Date/Time API** | Execution/report information |
| **Singleton Pattern** | Logger implementation |
| **JUnit 5** | Automated testing |
| **Maven** | Build and dependency management |

---

## 🤖 AI-Assisted Development

AI tools were used as **development assistants** during the development and improvement of this project.

Typical uses included:

- Understanding Java concepts and APIs
- Exploring different implementation approaches
- Debugging compilation and runtime errors
- Generating initial test-case ideas
- Identifying edge cases
- Improving code readability
- Reviewing implementation logic
- Improving technical documentation
- Understanding unfamiliar framework concepts

### AI Development Approach

AI was used as an assistant rather than as a replacement for understanding the code.

The development workflow was:

<pre>
Problem / Requirement
        ↓
Understand the Requirement
        ↓
Use AI for Ideas / Guidance
        ↓
Implement or Adapt the Solution
        ↓
Review the Generated Logic
        ↓
Compile the Project
        ↓
Run Automated Tests
        ↓
Debug / Improve
        ↓
Validate Final Implementation
</pre>

> AI-generated suggestions were reviewed, modified where necessary, and validated through compilation and testing rather than being blindly copied into the project.

This approach helped improve development speed while keeping the implementation understandable and testable.

---

## 🛡️ Engineering Practices

The project focuses on:

- Separation of responsibilities
- Reusable components
- Object-oriented design
- Immutable result objects
- Centralized configuration
- Structured logging
- Automated testing
- Error handling
- Execution reporting
- CI/CD integration
- Maintainable package structure

---

## 📁 Project Outputs

| Output | Description |
|---|---|
| `outputs/report.html` | Generated HTML execution report |
| `outputs/test-execution-output.txt` | Execution output/sample log |
| `logs/automates.log` | Runtime application log |
| `target/automates-1.0.0.jar` | Packaged executable JAR |
| `target/surefire-reports/` | Maven Surefire test reports |

---

## 🛣️ Roadmap

Future improvements may include:

- [ ] Fully configuration-driven retry and timeout behavior
- [ ] Parallel task execution using thread pools
- [ ] Task priorities and dependencies
- [ ] Real external API integrations
- [ ] Database automation tasks
- [ ] Email and Slack integrations
- [ ] REST API for remote task triggering
- [ ] Live monitoring dashboard
- [ ] Advanced reporting
- [ ] Allure reporting integration
- [ ] Plugin architecture
- [ ] Cloud deployment
- [ ] Additional integration and failure-path tests

---

## 🧰 Technology Stack

| Technology | Purpose |
|---|---|
| ☕ **Java 17** | Core programming language |
| 📦 **Maven** | Build and dependency management |
| 🧪 **JUnit 5** | Automated testing |
| 🪵 **SLF4J** | Logging API/dependency |
| 🔄 **Jackson** | JSON processing dependency |
| 🐙 **Git** | Version control |
| 🚀 **GitHub Actions** | CI/CD automation |
| 🌐 **HTML** | Execution reporting |

---

## 📌 Why This Project?

TEST-AutoMATEs was built to demonstrate practical software engineering concepts through a reusable automation framework.

The project combines:

**Java + Automation + Testing + OOP + Configuration + Reporting + CI/CD + AI-Assisted Development**

It demonstrates how a software automation problem can be broken into smaller components and implemented using maintainable Java architecture.

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a feature branch

    git checkout -b feature/amazing-feature

3. Make your changes
4. Add or update tests
5. Commit your changes

    git commit -m "feat: add amazing feature"

6. Push the branch

    git push origin feature/amazing-feature

7. Open a Pull Request

Please follow the existing code structure and add appropriate tests for new functionality.

---

## 👤 Author

<div align="center">

### **Banoth Mahesh Kumar**

Java | Automation | QA & Testing | Software Development

[![GitHub](https://img.shields.io/badge/GitHub-mahesh001--sys-181717?style=for-the-badge&logo=github)](https://github.com/mahesh001-sys)

[![Portfolio](https://img.shields.io/badge/Portfolio-Visit-0A66C2?style=for-the-badge&logo=googlechrome&logoColor=white)](https://mahesh001-sys.github.io/portfolio)

</div>

---

## 🔗 Project Links

**Repository:**  
https://github.com/mahesh001-sys/TEST-AutoMATEs

**GitHub Profile:**  
https://github.com/mahesh001-sys

**Portfolio:**  
https://mahesh001-sys.github.io/portfolio

---

<div align="center">

### ⭐ If you find this project useful, consider giving it a star!

**Built with ☕ Java, automation, testing, and continuous learning.**

</div>
