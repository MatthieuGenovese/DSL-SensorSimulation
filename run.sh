#!/bin/sh

docker-compose up -d

cd groovy

java target\groovy-1.0-SNAPSHOT-jar-with-dependencies.jar $1