
markovLaw "name" states (["soleil","nuage","pluie"]) transi ([[0.1,0.2,0.7],[0.3,0.5,0.2],[0.4,0.5,0.1]]) frequency 3

randomLaw "name2" interval ([1,2]) frequency 1


functionLaw("carre") {
    x ->
            if (x > 10 && x < 30) return "fdfdfdd"
            if (x < 10) return "toto"
            if (x < 300 && x > 30) return "tata"
}

sensor "toto" law "name" create 1 building 1 echantillonage 1
sensor "baba" law "carre" create 1 building 1 echantillonage 1
sensor "titi" law "name2" create 1 building 1 echantillonage 1

compositeLaw "name3" sensor "titi"


sensor "tototo" law "name3" create 1 building 1 echantillonage 1

runApp 5
