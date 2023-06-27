docker build --tag chat-service:latest --platform=linux/amd64 .
docker tag chat-service:latest aledanna/chat-service
docker push aledanna/chat-service