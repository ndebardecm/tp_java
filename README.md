## TP Chemin le plus court - Masterclass Java

### Avant de commencer
Il est nécessaire d'utiliser le gestionnaire de dépendances Maven et d'ajouter la dépendance relative à jUnit.

### Méthode utilisée
Le contenu du tp a été développé en suivant la méthode par les tests.
La méthode d'obtention du chemin le plus court est la suivante:
- Etude de tous les chemins possibles reliant deux verticies, en évitant les cycles
- Calcul de la longueur de tous ces chemins
- Choix du plus court

A noter qu'en cas d'impossibilité de calcul (vertex hors du graphe...), la valeur 0 est retournée par la méthode getDistance.