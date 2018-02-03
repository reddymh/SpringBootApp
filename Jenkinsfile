pipeline {
    agent any
    tools { 
        maven 'Maven_3.2.5' 
        jdk 'JDK8_131' 
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                ''' 
            }
        }
        stage ('Checkout') {
            steps {
               checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'GitHub', url: 'https://github.com/reddymh/SpringBootApp.git']]])
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn clean package -DaltDeploymentRepository=nexus-releases::default::http://nexus:8081/repository/totaldevops_snapshot'
            }
        }
        stage ('Sonar'){
            steps{
                sh 'mvn sonar:sonar -Dsonar.host.url=http://sonar:9000 -Dsonar.login=${SONAR_USER_TOKEN_ID} -DaltDeploymentRepository=nexus-releases::default::http://nexus:8081/repository/totaldevops_snapshot'
            }
            
        }
        stage ('Build Docker Image'){
            steps{
				echo "######################### Building Docker Image for JavaWebApp #############################"
                sh 'docker build -t docker_spring_demo:1.0 .'
				echo "######################### Built Docker Image for JavaWebApp Successfully #############################"
            }
            
        }
		stage ('Publish Docker Image') {
            steps {
                echo "Publishing Docker Image To Docker Private Registry ( Nexus )"
            }
        }
		stage ('Deploy Docker Container On Dev Env'){
            steps{
				echo " #################### Stopping JavaWebApp Container #######################"
				sh ' docker stop docker_spring_demo '
				echo " #################### Stopped JavaWebApp Container ########################"
				echo " #################### Removing JavaWebApp Container #######################"
				sh ' docker rm docker_spring_demo '
				echo " #################### Removed JavaWebApp Container #######################"
				echo " #################### Starting JavaWebApp Container #######################"
                sh ' docker run -p 8088:8088 -d --name docker_spring_demo --net=ecosystem -it docker_spring_demo:1.0'
				echo " #################### Started JavaWebApp Container #######################"
				echo " #################### JavaWebApp URL : http://<HOSTNAME>:8088/rest/hellodocker #######################"
            }
		}
        
    }
}