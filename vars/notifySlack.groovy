import org.yourteam.NotificationService

def call(Map config = [:]) {
  if (!config.webhookCredentialId) {
    error('notifySlack requires webhookCredentialId')
  }
  if (!config.message) {
    error('notifySlack requires message')
  }

  withCredentials([string(credentialsId: config.webhookCredentialId as String, variable: 'SLACK_WEBHOOK_URL')]) {
    def notifier = new NotificationService(this)
    notifier.sendSlack(env.SLACK_WEBHOOK_URL, config.message as String)
  }
}
