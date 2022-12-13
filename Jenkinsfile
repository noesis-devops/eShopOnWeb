pipeline {
  agent {
    kubernetes {
      yaml '''
      apiVersion: v1
      kind: Pod
      spec:
        containers:
        - name: dotnetcore
          image: mcr.microsoft.com/dotnet/sdk:6.0
          command:
          - sleep
          args:
          - infinity'''
      defaultContainer 'dotnetcore'
    }
  }
  stages {
    stage('SCM') {
      steps {
        checkout scm
      }
    }
    stage('SonarQube analysis') {
      steps {
        script {
          def scannerHome = tool 'sonar_msbuild_4.6';
            withSonarQubeEnv('sonarqube') {
              sh "dotnet tool install --global dotnet-sonarscanner"
              sh 'dotnet-sonarscanner begin /k:'eShopOnWeb''
              sh "dotnet build eShopOnWeb.sln"
              sh "dotnet-sonarscanner end"
          }
        }
      }
    }
    stage("Quality Gate") {
      steps {
        script {
          timeout(time: 1, unit: 'HOURS') { 
            def qg = waitForQualityGate() // Reuse taskId previously collected by withSonarQubeEnv
            if (qg.status != 'OK') {
              error "Pipeline aborted due to quality gate failure: ${qg.status}"
            } else {
              println("quality gate passed!")
            }
          }
        }
      }
    }
  }
}
