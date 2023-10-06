**Base de données**
Table Pokemon :

ID (clé primaire)
Nom du Pokémon
Type
Niveau
Attaque
Défense
Points de vie (HP)

----
Table PokemonCapture :

ID (clé primaire)
ID du Pokémon (clé étrangère faisant référence à la table Pokemon)
Date de capture

----
Table Joueur :

ID (clé primaire)
Nom du joueur
Autres caractéristiques du joueur (niveau, argent, etc.)