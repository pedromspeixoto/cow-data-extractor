# Change base directory
cd ../../

# Install utils
cd cde.utils/
mvn clean install
cd ..

# Build All Modules
cd cde.cow-data-authorization/
mvn clean package
cd ..

cd cde.cow-data-publisher/
mvn clean package
cd ..

cd cde.cow-data-consumer/
mvn clean package
cd ..

cd cde.cow-data-processor/
mvn clean package
cd ..

cd cde.cow-data-visualization/
mvn clean package
cd ..

# Change base directory
cd deployment/dev/

# Create Docker Network
docker network create cde-compose-network

# Run Docker Compose
docker-compose up -d --build