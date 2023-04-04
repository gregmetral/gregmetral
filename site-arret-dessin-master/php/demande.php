<html lang="fr">
<?php
session_start();
?>
<head>
    <title>Index</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<header>
    <h1> ARRET DESSIN </h1>
</header>
<nav>
    <div class="menu">
        <ul>
            <?php
            if (!isset($_SESSION["connected"]) || !$_SESSION["connected"]) {
                ?>
                <li> <a href="inscription.php">S'Inscrire</a> </li>
                <li> <a href="connexion.php">Connexion</a> </li>

                <?php
            }else{ ?>
                <li> <a href="connexion_output.php?connect=false">Deconnnexion</a> </li>
                <li> <a href="dessins.php">Dessins</a> </li>
                <li> <a href="modification.php">Modifier son Compte</a> </li>
                <?php
            } ?>
            <li> <a href="cours.php">Cours</a> </li>
            <li> <a href="adherent.php">Recherche d'adhérent</a></li>
            <li> <a href="demande.php">Demande au club</a> </li> <!-- demande materiel, cours ou autre -->
        </ul>
    </div>
</nav>
<div class="formulaire">
    <form method="post" action="envoie_mail.php">
        <h2>Demande au Club</h2>
        <label>
            Nom : <input class="texte" type="text" name="texte_nom" id="texte_nom" required="required">
        </label>
        <label>
            Prénom : <input class="texte" type="text" name="texte_prenom" id="texte_prenom" required="required">
        </label><br>
        <label>
            Votre email : <input class="texte" type="email" name="texte_email" id="texte_email" placeholder="Email" required="required">
        </label><br>
        <label for="demande"> Votre demande au club :</label>
        <select name="demande" id="demande">
            <option value="materiel">Demande de matériel</option>
            <option value="cours">Demande de cours</option>
            <option value="autre">Autre demande</option>
        </select><br>
        <label>
            Détaillez votre demande ici :<br>
            <textarea cols="70" rows="10" name="message"></textarea>
        </label><br>
        <label>
            <input class="submit" type="submit" value="Envoyer">
        </label>
    </form>
</div>
<footer>
    <p>Arret dessin </p>
</footer>
</body>
</html>