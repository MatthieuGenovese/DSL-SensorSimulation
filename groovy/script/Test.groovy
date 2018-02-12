
law "name" type "markov" states (["soleil","nuage","pluie"]) transi ([[0.1,0.2,0.7],[0.3,0.5,0.2],[0.4,0.5,0.1]]) frequency 3

function("carre") {
    x ->
            if (x > 10 && x < 30) return "fdfdfdd"
            if (x < 10) return "toto"
            if (x < 300 && x > 30) return "tata"
}


sensor "toto" law "name" create 1 building 1 echantillonage 10
sensor "baba" law "carre" create 3 building 2 echantillonage 1
runApp 15
