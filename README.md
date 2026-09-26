# ⚡ TEST-AutoMATEs

### Java 11 Automation Framework

A reusable Java 11 automation framework for task execution, scheduling, retry handling, validation, reporting, automated testing, and CI/CD.

## 🚀 Key Features

- Task-based automation architecture
- Task scheduling and execution
- Configurable retry handling
- File cleanup automation
- JSON data validation
- HTML execution reporting
- Structured logging with SLF4J + Log4j2
- Configuration using `config.properties`
- JUnit 5 automated testing
- GitHub Actions CI/CD
- AI-assisted development and debugging

## 🏗️ Architecture

AutoMATEsEngine
↓
TaskScheduler
↓
TaskExecutor
↓
AutomationTask
├── FileCleanupTask
└── DataValidationTask
↓
TaskResult
↓
ReportGenerator

## 📂 Project Structure

| Directory / File | Purpose |
|---|---|
| `.github/workflows/` | GitHub Actions CI/CD |
| `src/main/java/` | Framework source code |
| `src/main/resources/` | Configuration and logging |
| `src/test/java/` | JUnit 5 test cases |
| `outputs/` | Execution reports and test output |
| `outputs/report.html` | HTML execution report |
| `outputs/test-execution-output.txt` | Test execution output |
| `pom.xml` | Maven configuration and dependencies |
| `README.md` | Project documentation |

## 🧩 Core Components

### AutoMATEsEngine
Coordinates task scheduling, execution, result collection, and report generation.

### TaskScheduler
Registers and manages automation tasks.

### TaskExecutor
Executes tasks with configurable retry handling, exception handling, logging, and execution-time tracking.

### AutomationTask
Generic interface providing a common contract for automation tasks.

### TaskResult
Stores task name, execution status, message, and execution time.

### FileCleanupTask
Identifies and deletes files older than the configured age.

### DataValidationTask
Validates JSON files using Jackson.

### ReportGenerator
Generates an HTML report containing task status, messages, and execution time.

## 🔁 Retry Handling

The framework supports configurable retry execution through `TaskExecutor`.

Example:

Attempt 1 → Failed
Attempt 2 → Failed
Attempt 3 → Success

The maximum number of attempts is configurable.

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 11 | Application development |
| Maven | Build & dependency management |
| JUnit 5 | Automated testing |
| Jackson | JSON validation |
| SLF4J + Log4j2 | Logging |
| Java NIO | File operations |
| Git | Version control |
| GitHub Actions | CI/CD |

## 🧪 Test Results

Latest verified GitHub Actions execution:

**18 Tests | 0 Failures | 0 Errors | 0 Skipped**

**BUILD SUCCESS ✅**

## 🚀 How to Run

### Prerequisites

- JDK 11
- Maven
- Git

Run the test suite:

`mvn clean test`

## 📊 Execution Outputs

The `outputs/` directory contains execution artifacts such as:

- HTML automation report
- Test execution output

These artifacts provide visibility into task execution and test results.

## 🔄 CI/CD

GitHub Actions automatically:

1. Checks out the source code
2. Sets up Java 11
3. Runs Maven tests
4. Uploads Surefire test reports

Workflow:

`.github/workflows/maven-test.yml`

## 🤖 AI-Assisted Development

AI tools were used as a development-support resource for:

- Understanding Java and automation concepts
- Exploring implementation approaches
- Reviewing code structure
- Identifying compilation issues
- Debugging test failures
- Improving documentation

AI-generated suggestions were reviewed, modified, tested, and integrated based on the actual project requirements.

The final implementation was validated through automated tests and GitHub Actions.

## 📈 Future Enhancements

- Parallel task execution
- Additional automation tasks
- Database validation
- REST API automation
- Enhanced HTML dashboards
- Environment-specific configuration

## 👨‍💻 Author

**Mahesh Kumar Banoth**

B.Tech – Information Technology

**Focus:** Java • QA Automation • Software Testing • Automation Frameworks • CI/CD
