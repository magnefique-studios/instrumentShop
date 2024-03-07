docker-compose down 

mvn clean install


docker-compose build --build-arg DD_GIT_REPOSITORY_URL=github.com/shabuhabs/instrumentShop \
--build-arg DD_GIT_COMMIT_SHA=$(git rev-parse HEAD)

docker-compose up -d
