# DSL-SensorSimulation

## 1. Prérequis
Installer grafana et influxDB ou bien utiliser le docker-compose  
Avoir Maven d'installé
Avoir Java 1.8 d'installé  

## 2. Exécution

Pour build le projet : simplement lancer le script build.sh  
Pour exécuter le programme avec un fichier "Scenario.groovy" en entrée en utilisant docker : 
lancer le script run.sh avec en argumant le chemin du fichier (exemple : run.sh ./groovy/script/Scenario.groovy)  
Pour exécuter le programme avec un fichier "Test.groovy" en entrée sans docker (il faut au préalable avoir installé puis lancé influxDB sur sa machine), lancer le script run-without-docker.sh avec en argumant le chemin du fichier  
(exemple : run-without-docker.sh ./groovy/script/Scenario.groovy)
