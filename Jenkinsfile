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
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            allure results: [[path: 'target/allure-results']]
        }
    }
}