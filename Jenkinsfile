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
        stage('Run API tests') {
            steps {
                script {
                    if (params.TEST_SUITE == 'all') {
                        bat 'mvn clean test'
                    } else {
                        bat "mvn clean test -Dgroups=${params.TEST_SUITE}"
                    }
                }
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