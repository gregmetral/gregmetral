Projet IF3A Tom Palleau Alexandra Laguguie Greg Metral

Dans le .zip, vous trouverez :
-un dossier image, comprenant les images utilisées sur le site (utilisées pour le css)
-un dossier php, comprenant toutes les pages php et css du site
-un dossier sql, comprenant la base de données
-un document word, le rapport présentant le projet (dont le diagramme)
-ce document README, expliquant comment utiliser le site et la base de données
-un document mise_en_place_xampp, voir dans ce fichier son utilité

------------------------------------------------------------------------------------------

BASE DE DONNEES :

-sur xampp, allumer apache et mysql
-sur phpmyadmin, crée une nouvelle base de données et l'appeler précisemment: dessin_bdd
-importer la base de données se trouvant dans le dossier sql
la base de données est a présent opérationnel.
des données sont déja présentes.

------------------------------------------------------------------------------------------

DONNEES PRESENTE SUR LA BASE DE DONNES :

3 comptes utilisateurs :
	-email: admin@admin.fr   surnom: admin   nom: admin   prenom: admin   niveau de dessin: 1   admin: 1 (le compte est administrateur)   mot de passe: admin
	-email: gregmetral@gmail.com   surnom: Evry   nom: metral   prenom: greg   niveau de dessin: 4   admin: 0   mot de passe: oui
	-email: pierrejean@gmail.com   surnom: eau   nom: pierre   prenom: jean   niveau de dessin: 2   admin: 0   mot de passe: oui

vous pourrez bien evidemment aussi créer vos propres compte sur la page inscription du site
pour etre administrateur, il faut changer manuellement la valeur de admin sur phpmyadmin pour le compte (passer la valeur de 0 à 1)*


3 cours différents
quelques reservations de la part de certains utilisateurs

------------------------------------------------------------------------------------------

UTILISER LE SITE :

-Pour une utilisation optimale, ouvrir le site depuis la page php index.php

-Pour s'inscrire, cliquez sur inscription dans le menu de la page web.
	l'email et le surnom sont unique parmis tous les utilisateurs, si un eamil est utilisé il ne peut plus etre utilisé, de meme pour le surnom

-Pour se connecter, cliquez sur connexion dans le menu de la page web.
	entrer l'email et le mot de passe correspondant pour ouvrir la session (les utilisateurs pré-enregistrés peuvent etre utilisés)
	une fois connecter, vous pourrez vous deconnecter en cliquant sur deconnexion

-Pour rechercher un membre du club, cliquez sur recherche d'adhérant dans le menu de la page web.
	vous pouvez trier votre recherche en remplissant les champs dédiés.
	si aucun champs n'est rempli, tout les utilisateurs vont etre affichés.

-Pour effectuer une demande au club, cliquez sur demande au club dans le menu de la page web.
	remplir les champs et cliquer sur envoyer pour envoyer le mail 
	ATTENTION : voir le document txt mise_en_place_xampp pour que la fonctionnalité marche (il sera ecrit que le mail a été envoyé, mais l'email n'est pas reçu par la boite mail car nous sommes en localhost)

-Comme précisé dans le rapport, la fonctionnalité de post de dessin ne marche pas correctement. 
	Si vous voulez tout de meme essayer, cliquez sur dessin dans le menu de la page web (visible uniquement une fois connecté)
		Vous pourrez alors poster une image avec un titre.
		Pour voir les posts des utilisateurs, cliquer sur post dans le menu de la page web (visible uniquement sur la page dessin)
			sur cette page, on peut trier les posts grace aux champs présents
			si rien n'est précisé, tous les posts seront affichés.

-Pour rechercher un cours, cliquez sur cours dans le menu de la page web.
	vous pouvez trier les cours graces aux champs présents
	si rien n'est précisé, tous cours disponibles seront affichés
	Il faut cependant etre connecté pour participer a un cours.
		lorsque connecté, un bouton participer apparait a coté de chaque cours.
		si on participe a un cours, un bouton ajout invite apparait (permettant d'ajouter un utilisateur non memebre du club) 
	En version admin, un bouton suppr aparait a coté de chaque cours, il permet de supprimer le cours.
	On ne peut pas participer a 2 cours se passant la meme date, heure et salle.

-Pour créer un cours, il faut etre connecté en administrateur et etre sur la page cours.
	il suffit de renseigner une date et heure précise dans le formulaire.
		un second formulaire s'ouvrira, ou l'on peut préciser la salle, le nombre max d'éleves et de choisir entre un cours pour la blouse ou pour le dessin

-Pour modifier les informations de son compte, cliquez sur Modifier son Compte sur le menu de la page web (visible uniquement une fois connecté)
	réécrire alors ses données dans le formaulaire 
		l'email ne peut pas etre changé, mais le surnom peut l'etre.


------------------------------------------------------------------------------------------


	











