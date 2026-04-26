def call(Map config = [:]) {
  if (!config.projectKey) {
    error('runSonarScan requires projectKey')
  }
  if (!config.sonarHostUrl) {
    error('runSonarScan requires sonarHostUrl')
  }
  if (!config.sonarTokenCredentialId) {
    error('runSonarScan requires sonarTokenCredentialId')
  }

  String sources = (config.sources ?: '.') as String

  withCredentials([string(credentialsId: config.sonarTokenCredentialId as String, variable: 'SONAR_TOKEN')]) {
    sh """#!/bin/bash
set -e
sonar-scanner \\
  -Dsonar.projectKey=${config.projectKey} \\
  -Dsonar.sources=${sources} \\
  -Dsonar.host.url=${config.sonarHostUrl} \\
  -Dsonar.token=\$SONAR_TOKEN
"""
  }
}
