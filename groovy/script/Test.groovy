markovLaw "markovLaw" states ([10,-158,180]) transi ([[0.1,0.2,0.7],[0.3,0.5,0.2],[0.4,0.5,0.1]]) frequency 3 by s
randomLaw "randomLaw" interval ([1,10]) frequency 1 by min
/*functionLaw "fonctionLaw", {
    x ->
            if (x > 10 && x < 30) return 1
            if (x < 10) return 17
            if (x < 300 && x > 30) return 72
            if(x > 300) return 99
}*/
sensor "markov" law "markovLaw" create 1 area 1 echantillonage 1 by s
//sensor "fonctionel" law "fonctionLaw" create 10 area 1 echantillonage 10
//sensor "random" law "randomLaw" create 10 area 1 echantillonage 1

//compositeLaw "compositeSensor" sensor "fonctionel" function "average"

//sensor "composite" law "compositeSensor" create 1 area 1 echantillonage 1
testlol "toto" frequency 1 by s

runApp "17/02/2018 12:10:00 PM" to "17/02/2018 12:11:00 PM"
