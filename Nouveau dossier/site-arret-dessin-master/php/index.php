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
        <div>
            <h2>Bienvenue sur le site du club Arret Dessin!</h2>
        </div>
        <footer>
            <p>Arret dessin </p>
        </footer>
    </body>
</html>