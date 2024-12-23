# MaVille

## Contexte
MaVille est une application en ligne de commande conçue pour améliorer la communication et la coordination des travaux publics et privés à Montréal. Elle permet aux résidents et aux intervenants de consulter et gérer les projets de travaux afin de réduire les perturbations dans la ville.

## Fonctionnalités ajoutées

### Pour les résidents
- **Inscription** : Créer un compte avec nom, courriel, mot de passe et adresse résidentielle.
- **Consulter les travaux** : Visualiser les travaux en cours ou prévus, filtrés par quartier, rue ou type de travaux.
- **Consulter les entraves** : Visualiser les entraves en cours, filtrées par rue ou par type de travaux.
- **Rechercher des travaux** : Rechercher des projets par titre, type ou localisation.
- **Soumettre une requête de travail** : Proposer des travaux à réaliser dans son quartier et suivre leur progression.
- **Ajouter des préférences horaires** : Ajouter ses préférences horaires pour les heures de travaux dans son quartier.
- **Consulter ses notifications** : Consulter toutes les notifications reçues à propos de l'ajout de projets et de la modification de projets.
### Pour les intervenants
- **Inscription** : Créer un compte avec identifiant municipal.
- **Consulter les requêtes de travail** : Accéder aux demandes de travaux soumises par les résidents.
- **Soumettre un projet** : Soumettre une idée de projet à la ville de Montréal.
- **Mettre à jour un projet** : Mettre à jour un projet à venir.
- **Soumettre sa candidature** : Soumettre sa candidature pour une requête de travail. Possibilité de suivre le processus.

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

## Description des données incluses dans l'application
Notre application inclut 5 résidents, dont 2 habitant dans le même quartier, et 5 intervenants de type varié.
De plus, dans la base de données, vous pouvez trouver 5 requêtes de travail provenant des résidents, et 2 d'entre elles ont une candidature. 5 projets sont aussi disponible, dont 4 prévus dans les 3 prochains mois. 

## Organisation des fichiers du répertoire

Chaque dossier Devoir représente les différentes remises qui on été nécessaires pour ce projet.
Dans chacun des dossiers, vous allez trouver un dossier workplace avec le code source du projet
et la base de données ainsi qu'un dossier project files avec un rapport et les documents nécessaires 
à ce rapport ainsi que les informations consernant javadoc et jacoco. Pour avoir accès à la dernière 
version de notre application, s'il vous plait voir le dernier release.

Arborescence des dossiers pour notre plus récente remise: 

📦Devoir 3
 ┣ 📂feedback-dm2
 ┣ 📂project-files
 ┃ ┣ 📂assets
 ┃ ┃ ┣ 📂architecture
 ┃ ┃ ┣ 📂diagrammes
 ┃ ┣ 📂diagrammes
 ┃ ┃ ┣ 📂d_activites
 ┃ ┃ ┣ 📂de_cas_d_utilisation
 ┃ ┃ ┣ 📂de_classes
 ┃ ┃ ┣ 📂de_sequences
 ┃ ┣ 📂docs
 ┃ ┃ ┣ 📂com
 ┃ ┃ ┃ ┗ 📂maville
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📂account
 ┃ ┃ ┃ ┃ ┃ ┣ 📂activity
 ┃ ┃ ┃ ┃ ┃ ┣ 📂menu
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┗ 📂services
 ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┣ 📂view
 ┃ ┃ ┣ 📂index-files
 ┃ ┃ ┣ 📂legal
 ┃ ┃ ┣ 📂resource-files
 ┃ ┃ ┃ ┣ 📂fonts
 ┃ ┃ ┣ 📂script-files
 ┃ ┣ 📂tests
 ┃ ┃ ┣ 📂com.maville
 ┃ ┃ ┣ 📂com.maville.controller.account
 ┃ ┃ ┣ 📂com.maville.controller.activity
 ┃ ┃ ┣ 📂com.maville.controller.menu
 ┃ ┃ ┣ 📂com.maville.controller.repository
 ┃ ┃ ┣ 📂com.maville.controller.services
 ┃ ┃ ┣ 📂com.maville.model
 ┃ ┃ ┣ 📂com.maville.view
 ┃ ┃ ┣ 📂jacoco-resources
 ┣ 📂workspace
 ┃ ┣ 📂.idea
 ┃ ┃ ┣ 📂inspectionProfiles
 ┃ ┣ 📂data
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┗ 📂java
 ┃ ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┃ ┗ 📂maville
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂account
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂activity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂menu
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂services
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂view
 ┃ ┃ ┗ 📂test
 ┃ ┃ ┃ ┗ 📂java
 ┃ ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┃ ┗ 📂maville
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂activity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂services
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂model
 ┃ ┣ 📂target
 ┃ ┃ ┣ 📂classes
 ┃ ┃ ┃ ┣ 📂com
 ┃ ┃ ┃ ┃ ┣ 📂maville
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂account
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂activity
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂menu
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂services
 ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┣ 📂view
 ┃ ┃ ┣ 📂generated-sources
 ┃ ┃ ┃ ┣ 📂annotations


## Exécuter l'application (avec JAR)
Rendez-vous dans le dossier `./Devoir\ 3/project-files/application/` à partir de votre terminal. Exécuter le fichier jar avec la commande :
```shell
java -jar MaVille.jar
```

## Exécuter l'application (avec script Bash & Docker) à partir du répertoire GitHub
Rendez-vous dans le dossier `./Devoir\ 3/workspace/` à partir de votre terminal. Exécuter le script avec la commande :
```shell
./run.sh --build
```

## Installer l'application complète

Pour installer l'application complète, il vous faudra un JDK (Nous avons utilisé 23.0.1) et Maven (3.9.9). Il faudra ensuite cloner le repositoire et télécharger les dépendances Maven disponibles dans pom.xml. Vous pourriez ensuite compiler et exécuter le projet et les tests.

## Tester l'application
Les 18 tests unitaires se retrouvent dans le chemin workspace/src/test/java/com/maville. Ils sont accessibles via le répertoire GitHub dans le dossier Devoir 3. Pour plus de détails sur comment les exécuter, voir la fin du rapport.

### Soumettre une requête
Quand un résident désire soumettre une requête de travail, il faut faire attention d'entrer un type de travaux valide, autrement ça plante (la gestion d'erreur n'a pas été fait partout). Également, il faut que le type de travaux soit entré en anglais (encore une fois, ceci est en attendant l'interfaçe front-end qui permettra la gestion des choix avec des menus déroulants). Un exemple serait `urban_maintenance` pour le type aménagament urbain ou encore `underground` pour tout ce qui est soutterain. Pour que cela soit plus facile pour vous, je conseil de visiter le fichier `Project.java` qui contient le enum `TypeOfWork`. 

