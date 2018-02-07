
law "name" type "markov" states (["soleil","nuage","pluie"]) transi ([[0.1,0.2,0.7],[0.3,0.5,0.2],[0.4,0.5,0.1]])



function {
    name "yolofunc"
    interval ([0.0,">=",10.0])
    interval ([10.0,"=",32.0])
    interval ([15.0,"=",52.2])
}

function {
    name "yolofunc2"
    equation "f(x) = x*x"
}

sensor "bubu" law "csv" building 1

sensor "bubu" law "name" create 10 building 1 frequency 3 echantillonage 10
sensor "bibi" law "name" create 5 building 2 frequency 5 echantillonage 6

runApp 15
