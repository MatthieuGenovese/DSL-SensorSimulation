#!/bin/sh

docker-compose up -d


java -jar ./groovy/target/groovy-1.0-SNAPSHOT-jar-with-dependencies.jar $1
