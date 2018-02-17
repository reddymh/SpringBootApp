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
               echo " Checkout"
            }
        }
        stage ('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage ('Sonar'){
            steps{
                sh 'mvn sonar:sonar -Dsonar.host.url=http://${SONAR_HOSTNAME}:${SONAR_PORT} -Dsonar.login=${SONAR_LOGIN_TOKEN}'
            }
            
        }
        stage ('Deploy To Dev'){
            steps{
			    echo "############## Stopping the Spring Boot App Service ####################"
				sh 'kill $(cat ./pid.txt) || true'
				echo "############## Stopped the Spring Boot App Service ####################"
				echo "############## Starting the Spring Boot App Service ####################"
                sh 'java -jar target/docker-spring-boot-0.0.1-RELEASE.jar > log.out & echo $! > ./pid.txt &'
				echo "############## Started the Spring Boot App Service ####################"
            }
            
        }
		stage ('Publish Artifacts') {
            steps {
                sh 'mvn deploy'
            }
        }
        
    }
}