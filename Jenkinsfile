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
                sh 'mvn sonar:sonar -Dsonar.host.url=http://<SONAR_HOST>:<SONAR_PORT> -Dsonar.login=<SONAR_LOGIN_TOKEN>'
            }
            
        }
        stage ('Deploy To Dev'){
            steps{
                sh 'nohup java -jar target/docker-spring-boot.jar > log.out &'
            }
            
        }
		stage ('Publish Artifacts') {
            steps {
                sh 'mvn clean deploy'
            }
        }
        
    }
}