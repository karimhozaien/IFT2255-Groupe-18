# Barème de correction

## Glossaire 


### Termes importants 

- Application MaVille: Non
- Intervenant: Non
- Résident: Non
- Statut du projet, Projet: Non
- Type de travaux, Travaux: Oui, partiellement, vous devez aussi clarifier les types des travaux.
- Type de problème, Signaler un problème: Non
 - Il vous manque plusieurs termes importants. Je constate que vous avez déclaré les informations personnelles pour chaque type d'utilisateur dans le système, ce qui est une bonne démarche, mais je ne peux malheureusement pas les considérer comme les termes corrects.

## Diagramme de cas d'utilisation 

### Identification des acteurs 

- Ville ou autre système externe : Vous n'avez pas pris en compte le représentant de la ville. Comme mentionné dans votre rapport, l'administrateur est un acteur qui modère la plateforme. Cependant, ce n'est pas très clair. D'après le diagramme, je suppose que vous faites référence à un acteur qui représente la ville. Il serait préférable d'apporter cette modification pour les prochains DMs. Vous pouvez consulter les corrections du prof. pour plus de détails.

### Cas d'utilisation 

  - Cas n'est pas bien nommés (commence par un verbe): Consultation des travaux en cours et à venir.
   - Il faudrait avoir une relation entre s'enregistrer en tant qu'intervenant avec la ville

   - Il vous manque par contre certains CU importants, référez-vous à la correction du prof. pour modifier/adapter certaines relations manquantes à votre diagramme.

   - CU manquants: 
    - Répondre à une candidature
    - Filter les travaux
    - S'abonner à un projet
    - Modifier ses préférences
    - Soumettre sa candidature
    - Envoyer une notification

## Scenarios 

  - Vous ignorez les cas extend et include dans vos scénarios

  - Il est à noter que, une fois que vous ajoutez une précondition l'utilisateur doit se connecter à l'application, vous n'aurez plus besoin de répéter cette étape dans le scénario principal.

- Certains CU ne respectent pas du formalisme (bonne numerotation, numerotation des scenario alternatifs) 
- Certains CU ne respectent pas d'alternance entre acteur et système (max 2 étapes successives de la meme source)
  - Certains CU étaient trop court(moins 5 étapes)

  - Certains scénarios alternatifs ne sont pas complets. Par exemple, pour la Recherche des travaux, que se passe-t-il après l'étape 1a ? Y a-t-il un time out ? Le système affiche-t-il un message ? Le scénario se termine-t-il ? Ou autre chose ?
  
- Scénario:
 - Recevoir des notifications: Ce CU est trop court
 - Faire le suivi d'une requête de travaux: Vous avez omis ce CU
 - Soumettre une requête de travaux: Vous n'avez pas fait un scénario pour le cas extend de ce CU.
 - Signaler un problème: Vous n'avez pas fait un scénario pour le cas extend de ce CU.
 - Faire le suivi des problèmes signalés: Ce CU est trop court.
 - S'enregistrer en tant qu'intervenant:  C'est bien, mais vous avez manqué de scénario pour le CU include
 - Consulter les préférences des résidents pour la planification des travaux: Ce CU est trop court.
 - Consluter la liste de requête de travaux:  Ce CU est trop court.
 - Soumettre de nouveaux travaux: Ce CU est trop court.
 - MAJ les travaux: Ce CU est trop court.
 - Appliquer à une requête de travaux: Ce CU est trop court, vous avez manqué de scénario pour le CU extend.

 - Je remarque que vous avez utilisé les SOLUTION dans vos scénarios alternatifs, vous devriez ajouter comme une Note/Hypothèse plutôt qu'une solution

## Diagramme d'activités 
  Signaler un problème:
    - Boucle infinie potentielle. Si le formulaire n'est pas bien rempli, le flux retourne à l'étape "Remplir le formulaire" sans limite. Alors l'utilisateur pourrait rester bloqué dans ce cycle s'il n'arrive pas à remplir correctement le formulaire.

  Activité principale:
   - Boucle infinie potentielle pour la véfication information 

  Notifications de travaux: 
   - Boucle infinie potentielle.
   - Comment on peut s'abonner à une rue/un quartier? Entrer le nom de rue, ou choisir dans la liste prédéfinie? Il faut avoir un peu plus de détails

  Recherche et consultation de travaux:
   - Boucle infinie potentielle
   - Après avoir sélectionné le travail à consulter dans les résultats, que se passe-t-il ensuite ? Il serait préférable d'afficher les détails avant de relancer une nouvelle recherche.

  Soumettre une requête:
   - Boucle infinie potentielle
   - "Faire le suivi de la requête" mène directement à "Un intervenant pose sa candidature, envoyer une notification", ce qui n’a pas de sens. Cette étape devrait plutôt se trouver dans un autre flux. Veuillez vous référer au diapo 17 des notes de cours 6-Analyse pour voir comment cela pourrait être structuré.

  CU manquants:
   - Se connecter, modifier, s'inscrire un nouveau compte
   - Évaluer les travaux réalisés

  - Note: Il faudrait revoir vos diagrammes pour les boucle infinies potentielle
  
  - Pour tous les diagrammes: Comment on pourrait retourner au menu principal sans quitter l'application?
   - Ce serait mieux d'aller un peu plus loin, pensez à l'utilisation des noeuds d'objets.
 
## Analyse 

### Risques 

  - Convivialité: Ce n'est pas un risque, c'est plutôt un besoin non-fonctionnel.
  - Bugs et erreurs: C'est fiabilité, ce n'est pas non plus un risque, c'est aussi un besoin non-fonctionnel.

### Besoins non fonctionnels 

  - Le besoin Fiabilité que vous avez mentionné ici, ce n'est pas le cas de fiabilité, voici un exemple de la fiabilité
   - Fiabilité: L'application doit être fiable et fonctionner sans bugs majeurs. Un système de logs détaillés et de surveillance doit permettre de détecter les erreurs et de les corriger rapidement.
   Dans votre cas, le bon terme devrait être DISPONIBILITÉ
   - Disponibilité: L’application doit être disponible 24/7 avec un taux de disponibilité élevé (>99%). Une surveillance des serveurs et des systèmes doit être en place pour minimiser les interruptions de service.

### Besoins matériels, solution de stockage et solution d'intégration 

- Matériels nécessaires 
  - Considération pour le déploiement du programme: Vous avez manqué ce critère. Comment l'intégrer à votre projet? Où et comment elle sera déployée? Comment les utilisateurs installeront votre programme? Comment vous gérerez les futures MAJ ou corrections de bugs?
  - Matériel et interface utilisateur: Quelle est l'interface utilisateur proposée par votre application? Je voyais que vous l'avez mentionné brièvement en haut mais ce n'était pas claire
  - Analyse de cout: Vous avez manqué ce critère
- Solutions de stockage 
  - Architecture de stockage (partagée, distribuée, etc.): Vous avez parlé de serveurs, comment vos fichiers ou la DB seront organisés ou distribués dans une architecture plus large? Centralisé ou distribué.
  - Sécurité des données ou autre considérations: Vous devriez prendre en compte ce critère de sécurité.

## Prototype   
 - Avec fichier .jar
  - Bon, vous avez fourni le fichier .jar, mais vous n'avez pas inclus de comptes fictifs pour que je puisse tester votre application!
 - Bugs lors de la création du compte Résident. 
  

## Git 
- Votre readme était vide

## Rapport 
 - Les diagrammes de votre rapport sont vraiment petits, pensez aux lecteurs svp
