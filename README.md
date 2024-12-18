# MaVille

## Contexte
MaVille est une application en ligne de commande conçue pour améliorer la communication et la coordination des travaux publics et privés à Montréal. Elle permet aux résidents et aux intervenants de consulter et gérer les projets de travaux afin de réduire les perturbations dans la ville.

## Fonctionnalités ajoutées

### Pour les résidents
- **Inscription** : Créer un compte avec nom, courriel, mot de passe et adresse résidentielle.
- **Consulter les travaux** : Visualiser les travaux en cours ou prévus, filtrés par quartier, rue ou type de travaux.
- **Rechercher des travaux** : Rechercher des projets par titre, type ou localisation.
- **Soumettre une requête de travail** : Proposer des travaux à réaliser dans son quartier et suivre leur progression.

### Pour les intervenants
- **Inscription** : Créer un compte avec identifiant municipal.
- **Consulter les requêtes** : Accéder aux demandes de travaux soumises par les résidents.

## Données utilisateurs

### Résidents
1. Adresse courriel : camille.dupont@gmail.com | Mot de passe : Dupont@1985
2. Adresse courriel : alex.tremblay@yahoo.ca | Mot de passe : Tremblay_92
3. Adresse courriel : sophie.lavoie@gmail.com | Mot de passe : !Sophie_Lavoie90
4. Adresse courriel : marcandre.b@outlook.com | Mot de passe : Bouchard.Marc4$
5. Adresse courriel : emilie.gagnon@proton.me | Mot de passe : Gagnon@Mtl
-> camille.dupont@gmail.com et alex.tremblay@yahoo.ca habitent tous les deux au Plateau Mont-Royal

### Intervenants
1. Adresse courriel : aisha.ahmed@construction.com | Mot de passe : ConstructeurAhmed@Mtl
2. Adresse courriel : carlos@reparateur.com | Mot de passe : Rivera_C
3. Adresse courriel : mathieu.robert@quickfix.com | Mot de passe : robertQUICK!
4. Adresse courriel : mei.lin@linindustries.com | Mot de passe : MEIinc2024
5. Adresse courriel : juju.martin@mtl.ca | Mot de passe : julieM_90

## Exécuter le fichier JAR
Rendez-vous dans le dossier `application` à partir de votre terminal. Exécuter le fichier jar avec la commande :
```shell
java -jar prototype.jar
```
## Tester l'application
Les 9 tests unitaires se retrouvent dans le chemin workspace/src/test/java/com/maville. Ils sont accessibles via le répertoire GitHub dans le dossier Devoir 2. Pour plus de détails sur comment les exécuter, voir la fin du rapport.

### Soumettre une requête
Quand un résident désire soumettre une requête de travail, il faut faire attention d'entrer un type de travaux valide, autrement ça plante (la gestion d'erreur n'a pas été fait partout). Également, il faut que le type de travaux soit entré en anglais (encore une fois, ceci est en attendant l'interfaçe front-end qui permettra la gestion des choix avec des menus déroulants). Un exemple serait `urban_maintenance` pour le type aménagament urbain ou encore `underground` pour tout ce qui est soutterain. Pour que cela soit plus facile pour vous, je conseil de visiter le fichier `Project.java` qui contient le enum `TypeOfWork`. 

