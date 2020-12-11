docker-compose -f docker/docker-compose.yml build --force-rm

docker-compose -f docker/docker-compose.yml up -d

docker-compose -f docker/docker-compose.yml exec db sh "/data/import_data.sh"

docker-compose -f docker/docker-compose.yml run cli bash