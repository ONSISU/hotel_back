docker stop #!/bin/bash

set -e

CONTAINER_NAME="hotel-back-container"
IMAGE_NAME="hotel-back-image"
PORT1="33000"
PORT2="33000"
NETWORK_NAME="chatting-network"


echo "🎯 기존 컨테이너 중지"
docker stop $CONTAINER_NAME || true

echo "🎯 기존 컨테이너 삭제"
docker rm $CONTAINER_NAME || true

echo "🎯 기존 이미지 제거"
docker rmi $IMAGE_NAME || true

echo "🎯 이미지 생성"
docker build -t $IMAGE_NAME . || true

echo "🎯 빌드 시작 "
docker run -d -p $PORT1:$PORT2 --network $NETWORK_NAME --name $CONTAINER_NAME $IMAGE_NAME