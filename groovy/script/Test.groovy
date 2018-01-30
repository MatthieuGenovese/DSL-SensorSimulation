mode "csv" path "toto"
sensor "temps" create 10 building 1
sensor "nombre" create 10 building 1
sensor "temps" create 5 building 2
sensor "nombre" create 5 building 2

runApp 60
