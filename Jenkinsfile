pipeline {
    agent any


 	tools {
		jdk 'JDK17'
        maven 'Maven_Jenkins' // Use the name you provided in the Global Tool Configuration
        dockerTool 'Docker_Jenkins'      
          }
    
   


    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'Branch to build')
    }

    triggers {
        pollSCM('H/5 * * * *')
    }

    environment {
        AWS_ACCOUNT_ID = '773120352783'
        AWS_REGION = 'ap-south-1'
        ECR_REPO_NAME = 'cicd/doctor-service'
        IMAGE_TAG = "${env.BRANCH_NAME}-${env.BUILD_ID}"
        EC2_USER = 'ec2-user' // Change if using Ubuntu (e.g., 'ubuntu')
        EC2_HOST = 'ec2-3-109-4-202.ap-south-1.compute.amazonaws.com'
        SSH_KEY = '/var/tmp/bindisha0101-db-jar-key1.pem'
        PORT_NO = '9096'
        DB_HOST = '3.109.4.202'
        DB_PORT = '15432'
        DB_NAME = 'postgres_docker_db'
        DB_USER = 'bindisha0101'
        DB_PASSWORD = 'bindisha@123'
        DB_SCHEMA = 'hsm-doctor'
        DOCKER_SERVICE_NAME = 'doctor-service'
        
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    if (env.BRANCH_NAME == 'main') {
                        checkout scm
                    } else {
                        checkout([$class: 'GitSCM', branches: [[name: "*/${params.BRANCH_NAME}"]], userRemoteConfigs: [[url: 'https://github.com/sachin19927/doctor-service.git']]])
                    }
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Code Coverage') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                scannerHome = tool 'SonarQubeScanner'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """${scannerHome}/bin/sonar-scanner \
                    -Dsonar.projectKey=doctor-service-key \
                    -Dsonar.projectName=doctor-service \
                    -Dsonar.projectVersion=1.0 \
                    -Dsonar.sources=src/main/java \
                    -Dsonar.tests=src/test/java \
                    -Dsonar.java.binaries=target/classes \
                    -Dsonar.junit.reportPaths=target/surefire-reports \
                    -Dsonar.jacoco.reportPaths=target/jacoco.exec
                    """
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
            def dockerImage = sh(script: "sudo docker build -t ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG} .", returnStdout: true).trim()
        }
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                script {
					
					def token = sh(script: "aws ecr get-login-password --region ${AWS_REGION}", returnStdout: true).trim()
                    sh """
                    sudo docker login --username AWS -p ${token} ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                    sudo docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}
                    """
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
					// Get a fresh ECR token before deployment
            		def ecrToken = sh(script: "aws ecr get-login-password --region ${AWS_REGION}", returnStdout: true).trim()
                    sh """
                    ssh -o StrictHostKeyChecking=no -i ${SSH_KEY} ${EC2_USER}@${EC2_HOST} 
                        echo "Logging into AWS ECR...Get fresh ECR token on EC2 instance"
                        aws ecr get-login-password --region ${AWS_REGION} | sudo docker login --username AWS -p ${ecrToken} ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com

                        echo "Pulling latest Docker image..."
                        sudo docker pull ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}

                        echo "Stopping existing container (if any)..."
                        sudo docker stop ${DOCKER_SERVICE_NAME} || true
                        sudo docker rm ${DOCKER_SERVICE_NAME} || true

                        echo "Running new container..."
                        sudo docker run -d --name ${DOCKER_SERVICE_NAME} -p ${PORT_NO}:8080 --restart always \
                        -e DB_HOST=${DB_HOST} -e DB_PORT=${DB_PORT} -e DB_NAME=${DB_NAME} -e DB_USER=${DB_USER} -e DB_PASSWORD=${DB_PASSWORD} -e DB_SCHEMA=${DB_SCHEMA} \
                        ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO_NAME}:${IMAGE_TAG}
                        
                        echo "Deployment Successful!"
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline executed successfully!"
            jacoco execPattern: '**/target/jacoco.exec', classPattern: '**/classes', sourcePattern: '**/src/main/java', exclusionPattern: '**/src/test*'
        }
        failure {
            echo "Pipeline execution failed!"
        }
    }
}
