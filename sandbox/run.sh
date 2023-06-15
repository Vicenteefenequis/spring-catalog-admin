
docker network create admin_video_services
docker network create elastic

sudo chown root app/filebeat/filebeat.docker.yml
mkdir -m 777 .docker
mkdir -m 777 .docker/keycloak
mkdir -m 777 .docker/filebeat
mkdir -m 777 .docker/es01


#docker compose -f app/docker-compose.yml up -d
docker compose -f services/docker-compose.yml up -d
docker compose -f elk/docker-compose.yml up -d

echo "Inicializando containers..."
sleep 20