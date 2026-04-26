import org.yourteam.DockerHelper

def call(Map config = [:]) {
  if (!config.name) {
    error('buildAndPushImage requires name')
  }
  if (!config.tag) {
    error('buildAndPushImage requires tag')
  }

  def docker = new DockerHelper(this)
  docker.buildImage(config.name as String, config.tag as String)
  docker.pushImage(config.name as String, config.tag as String)
}
