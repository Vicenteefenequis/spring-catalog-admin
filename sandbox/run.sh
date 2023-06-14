
docker network create admin_video_services

mkdir -m 777 .docker
mkdir -m 777 .docker/keycloak


docker compose -f services/docker-compose.yml up -d

echo "Inicializando containers..."
sleep 20