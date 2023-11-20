E' possibile effettuare la compilazione della sola parte client come di seguito :
- lanciare il comando "npm i" per installare tutte le dipendenze
- per buildare per test "npm run build:dev" o per buildare per produzione "npm run build" : il compilato viene generato sotto la cartella main/webapp
- il compilato andrà poi copiato nella target


Per fare il war lanciare il comando maven clean package, che provvederà alla compilazione lato java e poi alla compilazione della parte client js,
infine all'assemblaggio del war