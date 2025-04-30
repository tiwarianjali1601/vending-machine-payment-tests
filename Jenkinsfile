pipeline {
    agent any
    
    tools {
        maven 'Maven 3.6.3'
        jdk 'JDK 11'
    }
    
    environment {
        ALLURE_RESULTS_DIR = 'target/allure-results'
        TEST_REPORTS_DIR = 'target/test-reports'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }
        
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        
        stage('Tests') {
            parallel {
                stage('Smoke Tests') {
                    steps {
                        sh 'mvn test -Dcucumber.filter.tags="@Smoke"'
                    }
                }
                
                stage('Regression Tests') {
                    steps {
                        sh 'mvn test -Dcucumber.filter.tags="@Regression"'
                    }
                }
            }
        }
        
        stage('Generate Allure Report') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }
    }
    
    post {
        always {
            // Archive test reports
            archiveArtifacts artifacts: [
                'target/test-reports/**/*',
                'target/cucumber.json'
            ].join(','), allowEmptyArchive: true
            
            // Clean workspace
            cleanWs()
        }
        
        success {
            echo '✅ All tests passed successfully!'
            
            emailext (
                subject: "Pipeline Success: ${currentBuild.fullDisplayName}",
                body: """
                    Pipeline execution completed successfully!
                    
                    Build Number: ${env.BUILD_NUMBER}
                    Build URL: ${env.BUILD_URL}
                    
                    Check Allure Report for detailed test results.
                """,
                to: 'your.email@example.com',
                attachLog: true
            )
        }
        
        failure {
            echo '❌ Tests failed!'
            
            emailext (
                subject: "Pipeline Failed: ${currentBuild.fullDisplayName}",
                body: """
                    Pipeline execution failed!
                    
                    Build Number: ${env.BUILD_NUMBER}
                    Build URL: ${env.BUILD_URL}
                    
                    Please check the build logs and Allure Report for details.
                """,
                to: 'your.email@example.com',
                attachLog: true
            )
        }
    }
}
