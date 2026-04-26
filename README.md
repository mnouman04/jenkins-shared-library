# Jenkins Shared Library

This repository contains a reusable Jenkins Shared Library with global steps under `vars/` and Groovy classes under `src/`.

## Structure

```text
vars/
  notifySlack.groovy
  buildAndPushImage.groovy
  runSonarScan.groovy
src/org/yourteam/
  NotificationService.groovy
  DockerHelper.groovy
```

## Classes

### `org.yourteam.NotificationService`
- Constructor: receives Jenkins pipeline script context.
- Methods:
  - `sendSlack(webhookUrl, message)`
  - `sendEmail(to, subject, body)`

### `org.yourteam.DockerHelper`
- Constructor: receives Jenkins pipeline script context.
- Methods:
  - `buildImage(name, tag)`
  - `pushImage(name, tag)`

## Global Steps

### `notifySlack(Map config)`
Required keys:
- `webhookCredentialId`
- `message`

Behavior:
- Loads Slack webhook URL from Jenkins String credential.
- Sends message through `NotificationService.sendSlack`.

### `buildAndPushImage(Map config)`
Required keys:
- `name`
- `tag`

Behavior:
- Builds and pushes Docker image using `DockerHelper`.

### `runSonarScan(Map config)`
Required keys:
- `projectKey`
- `sonarHostUrl`
- `sonarTokenCredentialId`

Optional keys:
- `sources` (default: `.`)

Behavior:
- Executes Sonar scanner with token from Jenkins String credential.

## Minimal Usage Example

```groovy
@Library('your-lib') _

pipeline {
  agent any

  stages {
    stage('Notify') {
      steps {
        notifySlack(
          webhookCredentialId: 'slack-webhook',
          message: "Build started: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        )
      }
    }
  }
}
```
