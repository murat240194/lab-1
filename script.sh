mvn -Dmaven.test.skip=true package
cd docker-hadoop-master
docker-compose up -d
id=$(docker ps | grep namenode | AWK 'printf $1')
docker cp ../target/MapReduseProject-1.0-SNAPSHOT-jar-with-dependencies.jar ec57ba396caa:MapReduseProject-1.0-SNAPSHOT-jar-with-dependencies.jar
docker cp ../000000 ec57ba396caa:000000
docker cp ../hadoop.sh ec57ba396caa:hadoop.sh
docker exec -it namenode bash