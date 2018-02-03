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
				echo "######################### Building Docker Image for Sprint Boot #############################"
                sh 'docker build -t docker_spring_demo:1.0 .'
				echo "######################### Built Docker Image for Sprint Boot Successfully #############################"
            }
            
        }
		stage ('Publish Docker Image To Nexus') {
            steps {
                echo "################ Publishing Docker Image To Docker Private Registry ( Nexus ) #####################"
				sh 'docker tag docker_spring_demo:1.0 ${DOCKER_PRIVATE_REGISTRY_HOST}:${DOCKER_PRIVATE_REGISTRY_PORT}/docker_spring_demo:1.0'
				sh 'docker login -u admin -p ${DOCKER_PRIVATE_REGISTRY_PASSWORD} ${DOCKER_PRIVATE_REGISTRY_HOST}:${DOCKER_PRIVATE_REGISTRY_PORT}'
				sh 'docker pull ${DOCKER_PRIVATE_REGISTRY_HOST}:${DOCKER_PRIVATE_REGISTRY_PORT}/docker_spring_demo:1.0'
				echo "################ Successfully Published Docker Image To Docker Private Registry ( Nexus ) #####################"
            }
        }
		stage ('Deploy Docker Container On Dev Env'){
            steps{
				echo " #################### Stopping Sprint Boot Docker Container #######################"
				sh ' docker stop docker_spring_demo || true '
				echo " #################### Stopped Sprint Boot Docker Container ########################"
				echo " #################### Removing Sprint Boot Docker Container #######################"
				sh ' docker rm docker_spring_demo || true '
				echo " #################### Removed Sprint Boot Docker Container #######################"
				echo " #################### Starting Sprint Boot Docker Container #######################"
                sh ' docker run -p 8088:8088 -d --name docker_spring_demo --net=ecosystem -it docker_spring_demo:1.0'
				echo " #################### Started Sprint Boot Docker Container #######################"
				echo " #################### Sprint Boot URL : http://<HOSTNAME>:8088/rest/hellodocker #######################"
            }
		}
        
    }
}