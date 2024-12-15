# Feedback

## Révision 

- Échéancier mis à jour : Oui
- Diagrammes CU mis à jour 
  - Nouvel acteur: API de la ville : Non
  - Nouveau CU 
    - Consulter ou Chercher les entraves routières : Non
  - CU abandonné:  
    - Signaler un problème : Oui
    - Partager son avis sur les travaux : Oui
  - Corrections : Oui

- Diagrammes d'activités mis à jour : Ok

- Analyse mis à jour : Ok, c'est bon
  - Risques mis à jour 
  - Besoins non-fonctionnels mis à jour 
  - Solution de stockage et d'intégration mis à jour

## Architecture 

- Les utilisateurs sont bien identifiés : Oui
  - Ce serait préférable de donner un peu plus de détails sur les deux type d'utilisateur
- Linteraction entre utilisateur et système est bien identifié : Oui
  - Il est mieux de fournir un peu plus sur le détail comme par exemple Quelle est l’action effectuée?
- La frontière du système est claire et permet de distinguer les composantes du système des dépendances externes : Oui
- La composantes principales sont bien identifiés : Oui
- Les relations entre composantes sont bien identifiées. 
- Les services externes sont bien identifiés : 
  - Manque la distinction des ressources appelées dans l'API
- Les relations entre services externes et système sont bien identifiés : 
  - Non, je ne vois pas clairement l’interaction avec l’API, le type de requêtes, ni le type de données échangées entre votre système et l’API Info Entraves.

## Diagramme de classe 

### Identification des entités 

- Classes
 - Je ne suis pas certaine de bien saisir la différence d'utilisation entre ResidentActivityController et ResidentAccountController. À première vue, ils semblent similaires, mais je ne suis pas totalement sûre. J'aurais besoin d'une clarification supplémentaire de votre part à ce sujet. Ce sera préférable également inclure une discussion succincte pour expliquer la distinction.
- Attributs (pertinence et type): Oui
- Méthodes (pertinence et signature): Oui


### Identification des relations 

- C'est bien

### Cohésion et couplage 
  - Faites attention avec l'utilisation de vos méthodes statiques 

### Formalisme 

- Type des attributs absent: Non
- Signature des méthodes absente: Non
- Relation illégale: Non
- Nommage portant à confusion ou éloigné des concepts de l'énoncé 
 - Certaines définitions de l'attribut invalide : #SCANNER: Scanner = new Scanner(System.in)
 - WorkStatus = PLANNED?

## Diagramme de séquence 
- Veuillez consulter la correction du professeur afin d'adapter celle-ci à vos diagrammes.
- (-1) Dans votre rapport, vous avez fourni trois diagrammes "Consulter les entraves ".(-1). 
J'ai donc corrigé les autres diagrammes présents dans le dossier Diagrammes. Faites attention à ce type d’erreur à l’avenir.

### Formalisme et cohérence 

-  Bonne définition des objets et lignes de vie: Pas toujours
-  Bon usage des flèches de message: Pas toujours, les messages de retour devraient être représentés à l'aide de flèches pointillées.
 - Faites attention à l'ordre des messages !
-  Bon usage des fragments: Oui
-  Méthode appelée existe: Non pas toujours, par exemple: Où se trouve DisplaySuccessMessage() in DefaultMenu?
-  Méthode appelée présente la même signature (params + type de retour) : Non

### Consulter les entraves 

-  Flux général est cohérent et représentatif du CU: Oui
-  Récupération des entraves: Oui, mais il faudrait avoir un peu plus de détail comment vous avez effectué votre "searchWorks"...
-  Filtre des entraves (optionnel):Non

### Soumettre une requête de travail  : 

-  Flux général est cohérent et représentatif du CU: 
 - Pas sure de bien comprendre l'étape 2.2.2
 - Transition entre les étapes 2.2.2 et 2.2.3: Pourquoi displayWorks(works) et ensuite confirmation de l'insertion!
-  Entrée de données:Impossible de corriger: Non
-  Validation et traitement erreur: Partiellement
-  Suivi d'une requête de travail: Non

### Consulter la liste des requêtes de travail 

-  Flux général est cohérent et représentatif du CU: Partiellement
-  Voir la liste des requêtes de travail: Partiellement, diagramme incomplet
-  Accès à une requête de travail: Non
-  Soumettre sa candidature: Non

## Discussion design 

-  considérations prises dans le design (abstraction, couplage et cohésion, encapsulation) pour favoriser l'évolution
 - Réduire le couplage: Comment cela est-il pris en compte dans votre architecture ? Un exemple serait nécessaire.
 - Améliorer l'encapsulation : Quel est l'objectif précis de cette action ? Et vous avez fait quoi pour appliquer ce principe?
 - Et les choix concernant la cohésion et l'abstraction? Je les voir dans votre diagramme de classes, mais vous avez oublié de les discuter ici.
-  lien avec l'architecture ou style d'architecture (ex: MVC) et avantages de celles-ci: 
 - Oui, vous avez opté pour l'architecture MVC, mais comment l'avez-vous mise en œuvre concrètement ?
 - Il serait pertinent de discuter davantage du design de votre architecture. Sur quelles classes avez-vous appliqué MVC ? Et pourquoi ce choix est-il cohérent avec ces classes ? Pourquoi avez-vous choisi cette option plutôt qu'une autre ?
-  intégration de l'application (modularité, flexibilité, interopérabilité): Je vois pas la discussion concernant à ces points.
  
# Implémentation 

## Fonctionnalités 

- [ ] Se connecter comme résident et intervenant  : Oui
- [ ] Consulter les travaux en cours ou à venir   : Oui
- [ ] Consulter les entraves                      : Oui

- [ ] Soumettre une requête de travail            
  - [ ] Formulaire complet                        : Oui
  - [ ] Validation des données                    : Non
    Entrez les informations suivantes pour la requête :
    Titre : a
    Description : b
    Type de travaux : 
    Date de fin espérée (AAAA-MM-JJ) : aa-bb-cc
  - [ ] Message d'erreur                          : Non
    - Aucun message de confirmation n'apparaît, je ne sais donc pas si l'ajout de la requête dans le système a réussi.

- [ ] Consulter la liste des requêtes de travail  : Très bien

## Robustesse & Utilisabilité 

- [ ] Application ne *crash* pas lors de mauvaises entrées : 
  Choisissez l'une des options : 
    5
    Entrez les informations suivantes pour la requête :
    Titre : my work
    Description : my work
    Type de travaux : home
    Date de fin espérée (AAAA-MM-JJ) : 
    Exception in thread "main" java.lang.NullPointerException: Cannot invoke "com.maville.model.Project$TypeOfWork.toString()" because the return value of "com.maville.model.WorkRequestForm.getProjectType()" is null
            at com.maville.controller.repository.WorkRepository.saveWorkRequest(WorkRepository.java:212)
            at com.maville.controller.activity.ResidentActivityController.submitWorkRequest(ResidentActivityController.java:102)
            at com.maville.controller.menu.DefaultMenu.handleMainMenuOption(DefaultMenu.java:56)
            at com.maville.controller.menu.Menu.showUserMenu(Menu.java:37)
            at com.maville.controller.menu.AuthenticationMenu.continueProcess(AuthenticationMenu.java:114)
            at com.maville.controller.menu.AuthenticationMenu.handleLogIn(AuthenticationMenu.java:89)
            at com.maville.controller.menu.AuthenticationMenu.selection(AuthenticationMenu.java:73)
            at com.maville.controller.menu.AuthenticationMenu.logInManager(AuthenticationMenu.java:42)
            at com.maville.controller.menu.DefaultMenu.selection(DefaultMenu.java:17)
            at com.maville.controller.menu.Menu.welcome(Menu.java:16)
            at com.maville.MaVille.main(MaVille.java:12)

- [ ] Navigation et interaction intuitive : Oui

- [ ] Possibilité de revenir en arrière : Oui

- [ ] Informations présentées facile à lire : Partiellement
  - Les sorties de travaux sont difficiles à comprendre.
  - Même chose pour la consultation des requêtes : 
    Aménagement paysager pour un parc public, Aménagement d'un espace vert public comprenant la plantation d'arbres, la création de sentiers, l'installation d'équipements de loisirs et l'aménagement d'un espace de détente., LANDSCAPE, 2025-05-15
  - Ce serait pertinent d'ajouter les titres des informations correspondantes.


## Code 

- [ ] Code n'est pas complètement différent de la conception : Partiellement, mais ok
- [ ] Code est réparti à travers plusieurs classes et facile à maintenir : Oui
- [ ] Code est facile à lire et comprendre : Oui


## Tests unitiaire  
- 3 membres, 9 tests requis -> Vous avez fournit 10 tests:
    UserRepositoryTest(2 tests)
    WorkRepositoryTest (2 tests)
    AuthenticateTest (2 tests)
    PasswordUtilTest(1 tests)
    WorkRequestFormConstructorTest(3 tests)
    - Cependant, les tests dans cette classe se limitent à vérifier les getters/setters, donc je ne les compte pas. Par conséquent, il vous manque 2 tests.

Pour chaque test:
- Test unitaire 
  - [ ] Test passe : Oui
  - [ ] Test pertinent pour la fonction: Oui
  - [ ] Test distinct : Oui
  - [ ] Test bien nommé et structuré : Oui
  - [ ] Assertion pertinente: Oui

## Rapport et Git 

- Structure du rapport: Partiellement
 - Vous n'avez pas inclus les comptes prédéfinis dans le rapport !
- Échéancier clair (distribution des tâches) et mis à jour: Oui
- images s'affichant directement dans le rapport: Non
- Lien Git et VPP fonctionnel: Oui
  - Vous n'avez pas inclus de VPP cette fois-ci, mais cela me convient pour cette fois. Veuillez ne pas l'oublier pour le prochain devoir.
- Release créé pour le devoir 2: Oui

## Bonus 1: Exploitation de Git 

- Commits réguliers et descriptifs de chaque membre. : Partiellement
- Gestion de projet par les tickets. : Oui
- Usage d'une branche pour le développement du code source. : Oui

## Bonus 2: Architecture REST
Non