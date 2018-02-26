#!/bin/sh

mvn clean install

cd groovy

mvn clean compile assembly:single