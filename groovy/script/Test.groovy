
law "name" type "markov" states "soleil,nuage,pluie"
law "name2" type "random"

sensor "bubu" law "name" create 10 building 1
sensor "bobo" law "name2" create 10 building 1
sensor "bibi" law "name" create 5 building 2
sensor "baba" law "name2" create 5 building 2

runApp 10
