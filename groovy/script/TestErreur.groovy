markovLaw "markovLaw" states ([10,-158,180]) transi ([[0.3,0.2,0.7],["totovaalapeche",0.5,0.2],[0.4,0.5,0.1]]) frequency "f" by "er"
randomLaw "randomLaw" interval ([1,50]) frequency 1 by s
functionLaw "fonctionLaw", {
    x ->
        if (x > 1 && x < 3) return 1
}
sensor "markov" law "markovLaw2" create "ff" area 1 echantillonage "fd" by s
sensor "fonctionel" law "fonctionLaw" create 1 area 1 echantillonage 1 by s
sensor "random2" law "randomLaw" create 3 area 1 echantillonage 1 by s
extractionSensor "toto2" mode "csv54" path "554" sensor "toto" create 1 area 1 timeunit "5545"
compositeSensor "compositeSensor" sensor "random2" function "average" create 1 area 1 echantillonage 1 by s
compositeSensor.addSensor("mar")
runApp "17/02/2018 12:10:54 PM" to "17/02/2018 12:10:43 PM"
