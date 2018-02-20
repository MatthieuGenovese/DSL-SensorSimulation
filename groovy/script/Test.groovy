markovLaw "markovLaw" states ([10,-158,180]) transi ([[0.1,0.2,0.7],[0.3,0.5,0.2],[0.4,0.5,0.1]]) frequency 1 by min
randomLaw "randomLaw" interval ([1,10]) frequency 1 by min
functionLaw "fonctionLaw", {
    x ->
            if (x > 10 && x < 30) return 1
            if (x < 10) return 17
            if (x < 300 && x > 30) return 72
            if (x > 300) return 99
}
//sensor "markov" law "markovLaw" create 10 area 1 echantillonage 1 by s
//sensor "fonctionel" law "fonctionLaw" create 10 area 1 echantillonage 10
//sensor "random2" law "randomLaw" create 3 area 1 echantillonage 1 by s


extractionSensor "toto2" mode "csv" path "D:\\Projet\\DSL-SensorSimulation\\kernel\\src\\main\\java\\dataextraction\\data.csv" sensor "toto" create 1 area 1
toto2.addNoise([0,10])
toto2.addMinOffset(0)
toto2.addMaxOffset(2)

//compositeSensor "compositeSensor" sensor "random2" function "average" create 1 area 1 echantillonage 1 by s
//compositeSensor "compositeSensor2" sensor "markov" function "sum" create 1 area 1 echantillonage 1 by s
runApp "17/02/2018 12:10:00 PM" to "17/02/2018 12:12:00 PM"
