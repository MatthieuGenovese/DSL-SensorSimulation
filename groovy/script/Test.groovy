markovLaw "markovLaw" states ([10,-158,180]) transi ([[0.1,0.2,0.7],[0.3,0.5,0.2],[0.4,0.5,0.1]]) frequency 1 by min
randomLaw "randomLaw" interval ([1,50]) frequency 1 by s
functionLaw "fonctionLaw", {
    x ->
            if (x > 1 && x < 3) return 1
            if (x < 10) return "toto"
            if (x < 30 && x > 30) return 72
            if (x > 40) return 99
}
sensor "markov" law "markovLaw" create 10 area 1 echantillonage 1 by s
sensor "fonctionel" law "fonctionLaw" create 1 area 1 echantillonage 1 by s

sensor "random2" law "randomLaw" create 3 area 1 echantillonage 1 by s
extractionSensor "toto2" mode "csv" path "E:\\DSLSensorSimultion\\kernel\\src\\main\\java\\dataextraction\\data.csv" sensor "toto" create 1 area 1 timeunit s

toto2.addNoise([0,10])
toto2.addMinOffset(0)
toto2.addMaxOffset(2)

compositeSensor "compositeSensor" sensor "random2" function "average" create 1 area 1 echantillonage 1 by s
compositeSensor.addSensor("markov")
//compositeSensor "compositeSensor2" sensor "markov" function "sum" create 1 area 1 echantillonage 1 by s
runApp "17/02/2018 12:10:00 PM" to "17/02/2018 12:10:20 PM"
