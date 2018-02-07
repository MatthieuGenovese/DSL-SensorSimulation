
law "name" type "markov" states (["soleil","nuage","pluie"]) transi ([[0.1,0.2,0.7],[0.3,0.5,0.2],[0.4,0.5,0.1]])



function {
    name "yolofunc"
    body {
        when "x < 10"
        then "oui"
        when "x > 10"
        then "tototototo"
    }
}

function {
    name "yolofunc2"
    equation "f(x) = x*x"
}


sensor "bubu" law "name" create 10 building 1 frequency 3 echantillonage 10
sensor "bibi" law "name" create 5 building 2 frequency 5 echantillonage 6

runApp 15
