pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven'
    }

    stages {
        stage('Run API tests') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Publish test results') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}