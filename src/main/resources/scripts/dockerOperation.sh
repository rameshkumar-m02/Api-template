 #!/bin/bash
# Define variables
DOCKER_IMAGE_NAME=$1
DOCKER_IMAGE_TAG="latest"
DOCKER_REGISTRY_USERNAME=""
DOCKER_REGISTRY_PASSWORD=""
DOCKER_REGISTRY_URL="hub.docker.com"
DOCKER_PROJECT_PATH=$2

echo "varibaless=====${DOCKER_IMAGE_NAME},${DOCKER_PROJECT_PATH}"
cd ${DOCKER_PROJECT_PATH}

mvn install -DskipTests
 
docker build -t ${DOCKER_IMAGE_NAME} .
docker tag ${DOCKER_IMAGE_NAME} ${DOCKER_REGISTRY_USERNAME}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}
#docker login -u ${DOCKER_REGISTRY_USERNAME} -p${DOCKER_REGISTRY_PASSWORD}
echo "$DOCKER_REGISTRY_PASSWORD" | docker login -u "$DOCKER_REGISTRY_USERNAME" --password-stdin "$DOCKER_REGISTRY_URL"
docker push ${DOCKER_REGISTRY_USERNAME}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}