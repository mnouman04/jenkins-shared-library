package org.yourteam

class DockerHelper implements Serializable {
  private final def script

  DockerHelper(def script) {
    this.script = script
  }

  void buildImage(String name, String tag) {
    validateImageParams(name, tag, 'buildImage')
    script.sh "docker build -t ${name}:${tag} ."
  }

  void pushImage(String name, String tag) {
    validateImageParams(name, tag, 'pushImage')
    script.sh "docker push ${name}:${tag}"
  }

  private void validateImageParams(String name, String tag, String methodName) {
    if (!name?.trim()) {
      script.error("DockerHelper.${methodName} requires name")
    }
    if (!tag?.trim()) {
      script.error("DockerHelper.${methodName} requires tag")
    }
  }
}
