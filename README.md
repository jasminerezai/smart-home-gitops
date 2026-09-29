# SmartHomeGitOps

An Android application that monitors a GitHub repository for security-related incidents and allows an operator to respond directly through the GitHub API.

The project was developed as part of the Mobile Application course at Kristianstad University and consists of two labs, evolving the application from passive monitoring into an interactive GitOps control system.

## What it does

### Lab 1 — Monitoring & Detection

The application continuously monitors GitHub Pull Requests and comments using the GitHub REST API.

- Polls the repository every 30 seconds
- Analyzes Pull Request comments for potentially deceptive content
- Displays a **Normal** state when no threat is detected
- Displays a **Security Alert** when a suspicious comment is detected
- Shows the detected message and confidence score

### Lab 2 — Interactive Control & Mitigation

Lab 2 extends the application with operator controls and GitHub write operations.

- **Force Reject** — closes a Pull Request using `PATCH`
- **Force Merge** — retrieves and updates `house_config.json` using `GET` + `PUT`, then closes the Pull Request
- Updates the configuration to:
  - `target_temperature: 17.0`
  - `last_updated_by: Android-Operator`
- Automatically returns the UI to the normal state after successful mitigation
- Handles API and network errors without crashing

## Architecture

```text
Jetpack Compose UI
        ↓
   ViewModel
        ↓
   Repository
        ↓
   Retrofit / GitHub REST API
        ↓
      GitHub
