<<<<<<< HEAD
law "name" type "markov" states (["soleil","nuage","pluie"]) transi ([[0.1,0.2,0.7],[0.3,0.5,0.2],[0.4,0.5,0.1]])
=======
law "name" type "markov" states (["soleil","nuage","pluie"]) transi ([[0,0,0],[0,0,0]])
>>>>>>> 6311dd3b5f4685bf7c6e2a8b626b5b5c3616e0f7
law "name2" type "random"

sensor "bubu" law "name" create 10 building 1
sensor "bibi" law "name" create 5 building 2

runApp 10
