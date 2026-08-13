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

        stage('Allure Report') {
            steps {
                allure results: [[path: 'target/allure-results']]
            }
        }
    }
}