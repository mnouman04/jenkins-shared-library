package org.yourteam

class NotificationService implements Serializable {
  private final def script

  NotificationService(def script) {
    this.script = script
  }

  void sendSlack(String webhookUrl, String message) {
    if (!webhookUrl?.trim()) {
      script.error('NotificationService.sendSlack requires webhookUrl')
    }
    if (!message?.trim()) {
      script.error('NotificationService.sendSlack requires message')
    }

    String escapedMessage = message
      .replace('\\', '\\\\')
      .replace('"', '\\"')

    script.sh """#!/bin/bash
set +x
payload=\"{\\\"text\\\":\\\"${escapedMessage}\\\"}\"
curl -sS -X POST -H \"Content-type: application/json\" --data \"\$payload\" \"${webhookUrl}\" >/dev/null
"""
  }

  void sendEmail(String to, String subject, String body) {
    if (!to?.trim()) {
      script.error('NotificationService.sendEmail requires to')
    }
    if (!subject?.trim()) {
      script.error('NotificationService.sendEmail requires subject')
    }
    if (!body?.trim()) {
      script.error('NotificationService.sendEmail requires body')
    }

    script.mail to: to, subject: subject, body: body
  }
}
