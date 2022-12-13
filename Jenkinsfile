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
                nodejs(nodeJSInstallationName: 'node') {
                  sh "apt update && apt install -y openjdk-17-jre && dotnet tool install --global dotnet-sonarscanner && export PATH=\"$PATH:/root/.dotnet/tools\" && dotnet-sonarscanner begin /k:\"eShopOnWeb\" && dotnet build eShopOnWeb.sln && dotnet-sonarscanner end"
                  //sh 'dotnet-sonarscanner begin /k:\"eShopOnWeb\"'
                  //sh "dotnet build eShopOnWeb.sln"
                  //sh "dotnet-sonarscanner end"
            }
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
