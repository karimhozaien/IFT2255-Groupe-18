# Feedback

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
-  Dans votre rapport, vous avez fourni trois diagrammes "Consulter les entraves ".. 
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
  