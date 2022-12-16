def PROJECT_NAME = "API-REST-JAVA"
def LABEL_ID = "js-slave-${PROJECT_NAME}-${UUID.randomUUID().toString()}"
def GIT_COMMIT
def TAG = "activity"
def DEPLOY_CREDENTIALS = 'credencial'
def AWS_CREDENTIALS_ID = 'aws-jenkins-dev'
podTemplate(label: LABEL_ID, yaml: """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: docker-container
    image: docker
    command: ['cat']
    tty: true
    env:
    - name: REGISTRY
      value: nome registry
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
  - name: sdk-container
    image: imagem jdk8
    command: ['cat']
    tty: true
  - name: ansible-container
    image: imagem sdk:ansible
    command: ['cat']
    tty: true
  - name: selenium-container
    image: selenium/standalone-chrome:102.0-chromedriver-102.0
    ports:
      - containerPort: 4444
  - name: sonar-container
    image: imagem sonar:jdk8
    command: ['cat']
    tty: true
    env:
    - name: TAG
      value: "${TAG}"
  nodeSelector:
    nodegroup-type: jenkins-nodes
  tolerations:
  - key: "jenkins-nodes"
    operator: "Exists"
    effect: "NoSchedule"
  resources:
    requests:
      memory: "4Gi"
      cpu: "2"
  imagePullSecrets:
  - name: dockerhub
  volumes:
    - name: dockersock
      hostPath:
        path: /var/run/docker.sock
"""
) {
  node(LABEL_ID){
    checkout scm
    currentBuild.displayName += "${env.TAG}"
    currentBuild.description = "${env.TAG}"
    stage('Integration-tests'){
      notifyStarted()
      try {
        container('sdk-container'){
        sh "mvn clean test -Dtest=TestRunner -D\"cucumber.options= -t @${env.TAG}\""
        }

              // cucumber reports collection
               cucumber buildStatus: null, fileIncludePattern: '**/Report.json', jsonReportDirectory: 'target', sortingMethod: 'ALPHABETICAL'
               publishHTML(target:[
                              		    			allowMissing: true,
                              					alwaysLinkToLastBuild: true,
                              					keepAll: true,
                              					reportDir: "${WORKSPACE}",
                              					reportFiles: 'target/cucumber-reports/Report.json',
                              					reportName: 'cucumber-reports',
                              					reportTitles: 'cucumber-reports'
                              					])
        notifySuccessful()
      }
      catch(e) {
        notifyFailed()
      }
    }
  }//fim node label
}//fim pod template
def notifyStarted() {
    slackSend (channel: '#reports', color: '#FFFF00', message: "STARTING API/REST Tests : TAG:${env.TAG} \n By ${env.BUILD_USER}")
}
def notifySuccessful() {
    slackSend (channel: '#reports', color: '#00FF00', message: "SUCCESSFUL API/REST Tests : TAG: ${env.TAG} \n ${env.BUILD_URL} \n Build Report JSON: ${env.BUILD_URL}cucumber-reports/ \n Cucumber Report: ${env.BUILD_URL}cucumber-html-reports/overview-features.html\n Cucumber steps: ${env.BUILD_URL}cucumber-html-reports/overview-steps.html")
}
def notifyFailed() {
	slackSend (channel: '#reports', color: '#FF0000', message: "FAILED API/REST Tests : TAG: ${env.TAG} \n ${env.BUILD_URL} \n Build Report JSON: ${env.BUILD_URL}cucumber-reports/ \n Cucumber Report: ${env.BUILD_URL}cucumber-html-reports/overview-features.html\n Cucumber steps: ${env.BUILD_URL}cucumber-html-reports/overview-steps.html")
}