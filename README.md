# ⚡ TEST-AutoMATEs

### Java 11 Automation Framework

A reusable Java 11 automation framework for task execution, scheduling, retry handling, validation, reporting, automated testing, and CI/CD.

## 🚀 Key Features

- Task-based automation architecture
- Task scheduling and execution
- Configurable retry handling
- File cleanup automation
- JSON data validation
- HTML execution reports
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
| `.github/workflows/` | GitHub Actions CI/CD workflow |
| `src/main/java/com/automates/config/` | Framework configuration |
| `src/main/java/com/automates/core/` | Main automation engine |
| `src/main/java/com/automates/report/` | HTML report generation |
| `src/main/java/com/automates/tasks/` | Task interfaces and execution components |
| `src/main/java/com/automates/tasks/impl/` | Automation task implementations |
| `src/main/resources/` | Configuration and logging files |
| `src/test/java/` | JUnit 5 test cases |
| `pom.xml` | Maven configuration and dependencies |
| `README.md` | Project documentation |


## 🧩 Core Components

### AutoMATEsEngine
Central component that coordinates task scheduling, execution, result collection, and report generation.

### TaskScheduler
Registers and manages automation tasks before execution.

### TaskExecutor
Executes tasks with configurable retry support, exception handling, logging, and execution-time tracking.

### AutomationTask
Generic interface that provides a common contract for automation tasks.

### TaskResult
Stores task name, success status, execution message, and execution time.

### FileCleanupTask
Identifies and deletes files older than the configured age.

### DataValidationTask
Validates JSON files using Jackson.

### ConfigLoader
Loads framework configuration from `config.properties`.

### ReportGenerator
Generates an HTML report containing task status, messages, and execution time.

## 🔁 Retry Handling

The framework supports configurable retry execution through `TaskExecutor`.

Example:

Attempt 1 → Failed  
Attempt 2 → Failed  
Attempt 3 → Success

The maximum number of attempts can be configured when creating the executor.

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java 11 | Application development |
| Maven | Build & dependency management |
| JUnit 5 | Automated testing |
| Jackson | JSON validation |
| SLF4J + Log4j2 | Structured logging |
| Java NIO | File operations |
| Git | Version control |
| GitHub | Source control |
| GitHub Actions | CI/CD |

## 🧪 Test Results

Latest GitHub Actions execution:

Tests run: 18  
Failures: 0  
Errors: 0  
Skipped: 0  

BUILD SUCCESS

| Metric | Result |
|---|---:|
| Tests | 18 |
| Failures | 0 |
| Errors | 0 |
| Skipped | 0 |
| Build | ✅ SUCCESS |

## 🚀 How to Run

### Prerequisites

- JDK 11
- Maven
- Git

### Run Tests

`mvn clean test`

## 🔄 CI/CD

GitHub Actions automatically:

1. Checks out the source code
2. Sets up Java 11
3. Runs Maven tests
4. Uploads Surefire test reports

Workflow:

`.github/workflows/maven-test.yml`

## 🤖 AI-Assisted Development

AI tools were used as a development-support resource during the project.

AI assistance was used for:

- Understanding Java and automation concepts
- Exploring implementation approaches
- Reviewing code structure
- Identifying compilation issues
- Debugging test failures
- Improving documentation
- Generating initial implementation ideas

All AI-generated suggestions were reviewed, modified, tested, and integrated based on the actual project requirements.

The final implementation was validated through automated tests and GitHub Actions.

## 📈 Future Enhancements

- Parallel task execution
- Additional automation task implementations
- Database validation tasks
- REST API automation tasks
- Enhanced HTML dashboards
- Environment-specific configuration
- Extended CI/CD reporting

## 👨‍💻 Author

**Mahesh Kumar Banoth**

B.Tech – Information Technology

**Focus:** Java • QA Automation • Software Testing • Automation Frameworks • CI/CD

---

⭐ Explore the source code and automated test implementation.
