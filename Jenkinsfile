// Restful Booker API CI pipeline
pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven'
    }

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['smoke', 'regression', 'all'],
            description: 'Select test suite to run'
        )
    }

    stages {
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Run API tests') {
            steps {
                script {
                    if (params.TEST_SUITE == 'all') {
                        bat 'mvn test'
                    } else {
                        bat "mvn test -Dgroups=${params.TEST_SUITE}"
                    }
                }
            }
        }

        stage('Test completed') {
            steps {
                echo 'API tests successfully completed'
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