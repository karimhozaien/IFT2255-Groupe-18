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
1. Adresse courriel : john.doe@gmail.com | Mot de passe : johndoe123
2. Adresse courriel : jane.smith@outlook.com | Mot de passe : janesmith95
3. Adresse courriel : alex.dupont@gmail.com | Mot de passe : alexdu88

### Intervenants
1. Adresse courriel : paul.martin@company.com | Mot de passe : paulmartin80
2. Adresse courriel : sophie.leclerc@service.com | Mot de passe : sophiel92
3. Adresse courriel : michel.durand@tech.com | Mot de passe : michel75

## Exécuter le fichier JAR
Rendez-vous dans le dossier `application` à partir de votre terminal. Exécuter le fichier jar avec la commande :
```shell
java -jar prototype.jar
```
## Tester l'application
Les 9 tests unitaires se retrouvent dans le chemin workspace/src/test/java/com/maville. Ils sont accessibles via le répertoire GitHub dans le dossier Devoir 2. Pour plus de détails sur comment les exécuter, voir la fin du rapport.

### Soumettre une requête
Quand un résident désire soumettre une requête de travail, il faut faire attention d'entrer un type de travaux valide, autrement ça plante (la gestion d'erreur n'a pas été fait partout). Également, il faut que le type de travaux soit entré en anglais (encore une fois, ceci est en attendant l'interfaçe front-end qui permettra la gestion des choix avec des menus déroulants). Un exemple serait `urban_maintenance` pour le type aménagament urbain ou encore `underground` pour tout ce qui est soutterain. Pour que cela soit plus facile pour vous, je conseil de visiter le fichier `Project.java` qui contient le enum `TypeOfWork`. 

