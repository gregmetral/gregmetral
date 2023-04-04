<html lang="fr">
    <head>
        <title>Connexion</title>
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
                <li> <a href="inscription.php">S'Inscrire</a> </li>
                <li> <a href="connexion.php">Connexion</a> </li>
                <li> <a href="cours.php">Cours</a> </li>
                <li> <a href="adherent.php">Recherche d'adhérent</a></li>
                <li> <a href="demande.php">Demande au club</a> </li> <!-- demande materiel, cours ou autre -->

            </ul>
        </div>
    </nav>
    <?php
    if (isset($_GET["error"]) && $_GET["error"]=="connexion"){
        echo "<p>email ou mot de passe faux</p>";
    }
    ?>
    <div class="formulaire">
        <form method="post" action="connexion_output.php?connect=true">
            <h2>Se Connecter</h2>
            <label>
                <input class="texte" type="email" name="texte_email" id="texte_email" placeholder="Email" required="required" size="30">
            </label>
            <label>
                <input class="texte" type="password" name="mdp" id="mdp" placeholder="Mot de Passe" required="required" size="30">
            </label><br>
            <p>Vous n'avez pas de compte ? Inscrivez-vous <a href="inscription.php">ici</a></p>
            <label>
                <input class="submit" type="submit" value="Se connecter">
            </label>
        </form>
    </div>

    <footer>
    <p>Arret dessin </p>
    </footer>
    </body>
</html>