Bliss
Projet JEE

I. Résumé
Vous allez réaliser un système de supervision en temps réel (ou presque) de parc informatique. Le principe est le suivant :

Chaque machine supervisée du parc a un « agent » déployé dessus, qui tourne en arrière-plan. Il s'agit d'un script JAVA que vous allez réaliser, capable d'examiner les taux d'occupation de la RAM et de différents points de montage disque.
Un serveur central (que vous allez réaliser en JEE) permet à l'administrateur de définir facilement et rapidement son parc (salles, machines dans ces salles avec leurs IP…), et va interroger à intervalles réguliers ces machines pour vérifier qu'elles sont actives, et leur agent opérationnel.
Les agents examinent à intervalle régulier leur machine, et font spontanément remonter au serveur tout incident (taux d'utilisation trop élevé).
Le serveur fournit, dans son interface web, une page de supervision en temps réel, qui représente « graphiquement » le parc, et utilise AJAX pour ajuster fréquemment l'aspect des machines représentées, afin de traduire les éventuels incidents (ou terminaisons d'incident).
I.0 Échéances du projet
Vendredi 3 Novembre 2017
I.1. Historique de ce document
19/10/2017 : Version 1
II. Fonctionnalités obligatoires
Cette partie présente en détail les fonctionnalités que votre projet doit obligatoirement fournir. Ces fonctionnalités sont suffisantes pour obtenir la note maximale (20/20). Des développements supplémentaires sont mis en avant dans la prochaine partie, « Fonctionnalités bonus ».

II.1. Définition simple de parc
L'administrateur doit pouvoir injecter son parc dans le système : salles et machines dans ces salles. Une machine, pour les besoins de notre outil, peut se limiter à son nom et son IP, mais si vous voulez vous lâcher, libre à vous.

La définition du parc doit pouvoir se faire sans JavaScript, évidemment. Tout le système doit d'ailleurs être accessible, et JavaScript/AJAX ne sont là que pour rendre le tout bien plus confortable. On doit donc avoir des liens et des formulaires classiques pour ajouter une salle, ajouter des machines dans une salle, et maintenir tout ça par la suite (retrait ou déplacement de machine, retrait de salle, etc.).

Une salle a simplement un nom : pour notre outil, on se fout un peu de sa capacité, son emplacement, etc.

II.2. Vérification de connectivité
À intervalle régulier (par exemple toutes les minutes), le serveur va vérifier que les machines inscrites dans le système sont joignables, et que l'agent y est opérationnel. Pour cela, il se connecte sur l'agent et envoie le message idoine (voir la documentation de l'agent). Si la vérification échoue, il s'agit d'un incident à inscrire en tant que tel et à historiser, comme indiqué dans la section II.4. 
Cette fonctionnalitée est optionelle. Si vous ne pouvez faire l'agent, vous devrez créer des incidents en base de données directement.

II.3. Affichage de supervision
Il s'agit d'un écran affichant (graphiques et/ou tableaux) le parc, qui se met à jour régulièrement pour refléter les incidents éventuels en cours.

Les salles sont représentées sous forme de boîtes rectangulaires.
Dans ces boîtes, les machines concernées sont affichées (boîte obtenue par CSS, grand icône d'ordinateur, comme vous voulez…)
Tout incident en cours pour une machine est affiché en association avec celle-ci (icône plus petit superposé à, ou au-dessous de, la représentation de la machine)
L'écran doit pouvoir se mettre à jour régulièrement, de façon accessible. Si JavaScript n'est pas actif, on aura donc recours à une équivalent HTTP de l'en-tête Refresh, grâce à l'élément <meta />. Dans le cas contraire, on utilisera AJAX pour éviter de rafraîchir toute la page : la réponse nous indiquera alors uniquement les incidents terminés ou apparus depuis la dernière consultation, et on ajustera dynamiquement le contenu de la page.

Toujours dans un souci d'accessibilité, toute image doit bien sûr avoir une contrepartie textuelle (qui n'est pas forcément visible avec les images activées, mais doit l'être en images désactivées ou dans une consultation textuelle de la page).

Il est inutile de rafraîchir trop souvent, sauf peut-être pendant les tests : un intervalle d'une minute est a priori amplement suffisant (en revanche, au-delà de 5 minutes on n'est vraiment plus « temps réel »).

II.4. Remontée d'incidents et historisation
Les incidents parviennent au serveur de deux façons :

Il les détecte lui-même lors de vérifications périodiques de connectivité des agents, comme vu en section II.2.
Les agents font spontanément remonter des incidents au travers d'une requête HTTP de création REST appropriée.
Le serveur doit historiser les incidents, ce qui suppose qu'un incident a au moins les propriétés suivantes :

Machine concernée
Type d'incident (ex. RAM trop basse, espace libre trop bas sur un point de montage, connectivité défaillante)
Propriétés complémentaires (ex. RAM restante, espace disque restant, etc.)
Moment de détection (date/heure)
Moment de résolution (constatation d'incident terminé)
Pour la connectivité, c'est le serveur lui-même qui crée l'incident et, lorsqu'il constate qu'elle fonctionne alors qu'un incident est actif (détection sans résolution), le marque comme résolu. Pour les autres types d'incidents, qui sont remontés spontanément par les agents, ces derniers gardent trace des incidents qu'ils ont signalés et notifient automatiquement leur résolution lorsqu'ils la constatent.

Cette historisation ouvre la voie au calcul de durées de résolution, de durées totales d'incident, etc. On peut imaginer pas mal de statistiques.

II.5. Partie authentifiée
L'application dispose d'un ou plusieurs comptes utilisateur, tous administrateurs, et il est nécessaire de s'authentifier pour pouvoir accéder à toute opération modificatrice, donc principalement la définition du parc. En revanche, la supervision, l'interrogation manuelle, les éventuelles statistiques, etc. sont accessibles en anonyme.

II.6. Interrogation manuelle d'une machine
Depuis l'écran de supervision, il doit être possible d'interroger manuellement l'état d'une machine en s'adressant à son agent. On peut simplement vérifier sa connectivité, ou demander l'état de la RAM ou d'un point de montage. Cela peut avoir lieu dans un écran à part ou en AJAX (ceci dit, ça doit pouvoir être utilisable sans AJAX, comme d'habitude).

Le contrôleur sollicité va se connecter à l'agent comme il le ferait pour vérifier la connectivité, mais invoquer d'autres commandes (voir la documentation), créer ou résoudre l'incident éventuel comme il le ferait lors des vérifications périodiques normales, et signifier le résultat à la partie client.

III. Fonctionnalités bonus
Cette partie présente les fonctionnalités supplémentaires que votre projet peut fournir. Toutefois, les fonctionnalités obligatoires ont priorité. Vous devez vous concentre sur les fonctionnalités obligatoires avant de commencer le développement des bonus. Toutefois, connaître à l'avance les bonus potentiels peut vous aider à orienter votre conception afin de faciliter leur intégration ultérieure. Donc, lisez !

Elles sont classées par ordre décroissant d'intérêt au regard de la plate-forme, mais vous pouvez réaliser celles que vous voulez…

III.1. Définition visuelle de parc
Au lieu de définir le parc à coup de formulaires successifs, on peut travailler en AJAX depuis l'écran de supervision, à l'aide d'un menu disponible dans un coin (pourquoi pas déroulant avec l'effet approprié). On peut notamment :

Créer directement une machine dans une salle sélectionnée.
Déplacer une machine d'une salle à l'autre par glisser-déplacer.
Supprimer une machine directement (moyennant confirmation).
Créer une nouvelle salle.
III.2. Extraction automatique de parc
En précisant une salle, un masque d'IP et une série de valeurs, le système peut examiner tour à tour (ou en multi-thread !) les machines de la plage ainsi définie à la recherche d'agents connectés, et inscrire automatiquement les machines concernées dans la salle. Le nom est obtenu par requête DNS (IPAddr devrait faire ça très bien) et simplifié éventuellement à son préfixe (ex. Athos.linux.org devient Athos ou Athos.linux).

III.3. Statistiques
Du point de vue de l'administrateur du parc, certaines statistiques pourraient être intéressantes, qu'elles soient au niveau du parc, d'une salle ou d'une machine précise. Elles peuvent être calculées sur une période quelconque (les mois et années calendaires seraient facilement utilisables de base), ou sur tout l'historique. On pense notamment à :

Nombre d'incidents, découpés par catégorie
Taux opérationnel (pourcentage de temps sans incident sur le temps total)
MTBF (Mean Time Between Failures)
MTTR (Maximum Time To Repair)
Si on établit de telles stats par tranche (pas juste un chiffre pour l'année, mais les valeurs pour chaque mois de l'année, par exemple), on peut imaginer une belle vue graphique, par exemple à l'aide de l'irremplaçable gem Gruff (qui requiert toutefois un paquet d'autres, ahem…).

IV. Groupes, exigences techniques et notation
Cette section présente le mode de composition des groupes, le type de travail en équipe attendu, et l'impact d'un fonctionnement par groupe sur la notation. Il fournit aussi les exigences et contraintes techniques auxquelles tous les projets doivent répondre. Enfin, il présente le mécanisme de notation.

IV.1. Groupes & travail d'équipe
Ce projet est à développer en groupes de 4.

Les soutenances/rendus auront lieu le Vendredi 3 Novembre 2017

Nom du groupe	Membres	Soutenance
MASTERE 1/2 INGESUP
Tous les groupes seront définis en cours, sous la supervision de l'enseignant. Les groupes s'enregistrent avec un nom de groupe ainsi que les noms de leurs membres.

Toute inscription est définitive. Les étudiants ne sont pas autorisés, par la suite, à changer de groupe.

Au sein d'un groupe, les étudiants se répartiront les tâches pour le projet, de façon équitable. Il est explicitement exigé que chaque membre consacre au moins 50% de son travail à du développement technique. Du code de test est acceptable, tant qu'il ne constitue pas l'intégralité de la réalisation technique du membre du groupe.

Les étudiants ne sont pas tenus de travailler exclusivement à l'école. Ils sont autorisés, et même encouragés, à travailler de chez eux, ou depuis tout autre endroit à leur convenance. Toutefois, il est interdit de travailler sur le lieu de stage, afin d'éviter toute interférence projet/stage.

Les étudiants sont encouragés (mais pas obligés) à mettre en place un système de contrôle des sources de type Subversion ou équivalent, afin d'affecter et partager efficacement les fichiers de codes et autres composants numériques du projet (fichiers sources, descripteurs de déploiement, documents de recherche, cas d'utilisation, suites de tests, etc.).

IV.2. Exigences techniques : assurez-vous de les respecter !
Cette section présente les exigences techniques pour votre rendu complet. Tout manquement entraînera des pénalités.

JEE
Vous choisirez une architecture de type HTTP autant que possible, mais des actions complémentaires sur les contrôleurs sont autorisés.
Écrivez un maximum (intelligent) de tests, notamment unitaires (sur les modèles) et fonctionnels (sur les contrôleurs).
L'application doit fonctionner sans problèmes sur la configuration sur la configuration de votre choix, la configuration par défaut étant JEE/Servlet/JSP/JSTL/Hibernate.Vous pouvez si vous le désirez travailler sur une version supérieure à celle ci mais pas inférieur.
Exploitez au mieux les validateurs et les possibilités des définitions de relations
Les vues doivent être réalisées en HTML. Dans les deux cas, tirez au mieux parti des helpers.
Accès SGBD/R
La base de données (qui n'est pas obligatoirement MySQL, mais votre application devra marcher sur MySQL si on le définit ainsi).
Limitez aux cas de performance aigüs l'utilisation manuelle de SQL (sauf pour préciser les options d'ordre ou de groupement dans les méthodes d'accès prédéfinies, ces options prenant des fragments de select).
Format de rendu
Les projets doivent faire l'objet d'un rendu électronique au plus tard…

Le 03 Novembre 2017
Les rendus doivent figurer sur un seul compte par groupe.
Le mail de rendu est vincent.leclerc@ingesup.com.
Les fichiers rendus doivent contenir :

Un .zip contenant l'arborescence du projet, immédiatement exploitable après création des bases de données et exécution des migrations.
Un AUTHORS.TXT listant les membres du groupe (prénom, nom), à raison d'un par ligne. Il liste ensuite les responsabilités effectives de chacun dans le groupe.
Le sujet du mail doit contenir votre section ainsi que le nom du projet.

Les fichiers rendus peuvent aussi comprendre :

Des documents de recherche créés pour le projet et fournissant plus de détails pour l'enseignant.
Pour tout autre type de fichier, veuillez demander à l'enseignant si son inclusion est appropriée.

IV.3. Modalités de notation
La notation du projet sera gérée comme suit :

Le groupe soutient son projet dans un créneau dédié
Le groupe est présent pour une soutenance de 15 minutes durant laquelle ses membres présentent chacun leurs travaux, font la démo de fonctionnement du produit et fournissent un retour d'expérience sur le projet (déroulement, points faciles, difficultés résolues ou non, tâches restantes).

L'atmosphère sera légèrement formelle. Toutefois, la soutenance a lieu sur une des machines des élèves du groupe.

Chaque étudiant devra présenter spécifiquement sa contribution au projet.

Le planning de soutenances sera disponible dans ce sujet.

Note : tout retard à la soutenance entraînera des pénalités sur la notation.

© 2017 VINCENT LECLERC - INGESUP. Toute mise à jour du sujet sera annoncée.
