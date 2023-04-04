<html lang="fr">
    <head>
        <title>Inscription</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <header>
            <h1> ARRET DESSIN </h1>
        </header>
        <?php
        if (isset($_GET["error"])){
            echo "<p>email ou surnom déjà utilisé</p>";
        }
        ?>
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
        <div class="formulaire">
            <form action="inscription_output.php" method="post">
                <h2>S'Inscrire</h2>
                <label>
                    Nom : <input class="texte" type="text" name="texte_nom" id="texte_nom" required="required" size="30">
                </label> <br/>
                <label>
                    Prenom : <input class="texte" type="text" name="texte_prenom" id="texte_prenom" required="required" size="30">
                </label><br>
                <label>
                    Surnom : <input class="texte" type="text" name="texte_surnom" id="texte_surnom" placeholder="surnom UTBM" size="30">
                </label><br>
                <label>
                    Email : <input class="texte" type="email" name="texte_email" id="texte_email" placeholder="exemple@mail.fr" required="required" size="30">
                </label><br>
                <label for="niveau">Niveau de Dessin : </label>
                <select name="niveau" id="niveau">

                    <?php
                    $db = new PDO("mysql:host=localhost;dbname=dessin_bdd;charset=UTF8","root","");
                    $req_name_lvl = $db->prepare("select nom from nom_niv");
                    $req_name_lvl->execute();

                    while ($name = $req_name_lvl->fetch()) {
                        echo "<option value=" . $name["nom"] . ">" . $name["nom"] . "</option>";
                    }
                    ?>
                    
                </select><br>
                <label>
                    Date de Naissance : <input class="date" type="date" name="date_naissance" id="date_naissance" required="required">
                </label><br>
                <label>
                    Mot de Passe : <input class="texte" type="password" name="mdp" id="mdp" required="required" size="30">
                </label><br>
                <label>
                    <input class = "submit" type="submit" value="S'inscrire">
                </label>
            </form>
        </div>
        <footer>
            <p>Arret dessin </p>
        </footer>
    </body>
</html>