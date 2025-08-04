// Jenkinsfile for DOT503 Video Manager Application
// This pipeline defines the Continuous Integration and Continuous Delivery stages
// for a Maven-based Java application.

pipeline {
    // Agent specifies where the pipeline will run. 'any' means Jenkins will pick any available agent.
    agent any

    environment {
        DUMMY_VAR = 'true'
    }

    // Stages define the logical steps of your pipeline.
    stages {
        // Stage 1: Checkout Source Code from git
        stage('Checkout SCM') {
            steps {
                // Diagnostic echo to confirm Jenkinsfile execution
                echo "Starting SCM Checkout stage from Jenkinsfile."
                // Cleans up the workspace before checkout
                cleanWs()
                // Checks out the source code from the configured SCM (Git)
                checkout scm // Use the SCM configuration defined in the Jenkins job
            }
        }

        // Stage 2: Build Application using Maven
        stage('Build') {
            steps {
                // Use withMaven to ensure the correct Maven installation is used
                withMaven(maven: 'Maven 3.9.10') {
                    sh 'mvn clean compile'
                }
            }
        }

        // Stage 3: Run Unit Tests
        stage('Test') {
            steps {
                withMaven(maven: 'Maven 3.9.10') {
                    sh 'mvn test'
                }
            }
        }

        // Stage 4: Package Application (Create Executable JAR)
        stage('Package') {
            steps {
                withMaven(maven: 'Maven 3.9.10') {
                    sh 'mvn package'
                }
            }
        }

        // Stage 5: Generate HTML Test Report (Site)
        stage('Generate Test Report') {
            steps {
                withMaven(maven: 'Maven 3.9.10') {
                    sh 'mvn site'
                }
            }
        }

        // Stage 6: Archive Artifacts (Optional but Recommended for CI/CD)
        stage('Archive Artifacts') {
            steps {
                // Archives the generated JAR file and the HTML test report
                archiveArtifacts artifacts: 'target/*.jar, target/site/surefire-report.html', fingerprint: true
            }
        }

        // Stage 7: Simulate Continuous Delivery/Deployment
        // For a console application, this might involve copying the JAR or running it.
        stage('Deploy (Simulated)') {
            steps {
                echo "Simulating deployment of video-manager-app-1.0-SNAPSHOT.jar..."
                // Example: Copying the JAR to a 'deploy' folder within the Jenkins workspace
                sh 'mkdir -p deploy && cp target/video-manager-app-1.0-SNAPSHOT.jar deploy/'
                echo "Application JAR is ready in 'deploy/' folder within Jenkins workspace."
                echo "Staging deployment simulation complete."
            }
        }
    }

    // Post-build actions, executed after all stages are completed (or failed).
    post {
        // Always run, regardless of build status
        always {
            echo 'Pipeline finished.'
        }
        // Run only if the build succeeds
        success {
            echo 'Build successful!'
        }
        // Run only if the build fails
        failure {
            echo 'Build failed! Check logs for details.'
        }
    }
}
