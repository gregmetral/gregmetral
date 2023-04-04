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
    <form>
        <h2>Recherche d'Adhérent</h2>
        <label>
            Nom : <input class="texte" type="text" name="texte_nom" id="texte_nom">
        </label>
        <label>
            Prénom : <input class="texte" type="text" name="texte_prenom" id="texte_prenom">
        </label><br>
        <label>
            Email : <input class="texte" type="email" name="texte_email" id="texte_email">
        </label>
        <label for="niveau">Niveau de Dessin : </label>
        <select name="niveau" id="niveau">
            <option value=""> </option>

            <?php
            $db = new PDO("mysql:host=localhost;dbname=dessin_bdd;charset=UTF8","root","");
            $req_name_lvl = $db->prepare("select nom,id from nom_niv");
            $req_name_lvl->execute();

            while ($name = $req_name_lvl->fetch()) {
                echo "<option value=" . $name["id"] . ">" . $name["nom"] . "</option>";
            }
            ?>

        </select><br>
        <label>
            <input class="submit" type="submit" id="search" name="search" value="Rechercher">
        </label>
    </form>
</div>
<div>
    <?php
    if (isset($_GET["search"])){
        $prepare = "";
        $need_and = 0;

        if ($_GET["texte_nom"] || $_GET["texte_prenom"] || $_GET["texte_email"] || $_GET["niveau"]) {//si une condition est demandé
            $prepare = $prepare . "where";//ajouter where
            if ($_GET["texte_nom"]) {
                $prepare = $prepare . " nom='" . $_GET["texte_nom"] . "'";
                $need_and = 1;
            }
            if ($_GET["texte_prenom"]) {
                if ($need_and) {//si condition avant celle ci
                    $prepare = $prepare . " and ";
                }
                $need_and = 1;

                $prepare = $prepare . " prenom='" . $_GET["texte_prenom"] . "'";

            }
            if ($_GET["texte_email"]) {
                if ($need_and) {//si condition avant celle ci
                    $prepare = $prepare . " and ";
                }
                $need_and=1;

                $prepare = $prepare . " email='" . $_GET["texte_email"] . "'";
            } else {
                $time = "";
            }
            if ($_GET["niveau"]){
                if ($need_and) {//si condition avant celle ci
                    $prepare = $prepare . " and ";
                }

                $prepare=$prepare." niv_dessin=". $_GET["niveau"];
            }
        }
        $req_utilisateur = $db->prepare("select prenom, nom, surnom, niv_dessin from utilisateur ".$prepare);
        $req_utilisateur->execute();
        $utilisateur = $req_utilisateur->fetchAll();

        if ($utilisateur) {
            echo "<form id='info_cour' class='cours'>";
            echo "<table>
            <tr>
                <th class='haut'>prenom</th>
                <th class='haut'>nom</th>
                <th class='haut'>surnom</th>
                <th class='haut'>niveau de dessin</th>
            </tr>";

            foreach ($utilisateur as $u){
                echo
                    "<tr>
                <th> " . $u["prenom"] . "</th>
                <th> " . $u["nom"] . "</th>
                <th>" . $u["surnom"] . "</th>
                <th>" . $u["niv_dessin"] . "</th>";
            }
            echo "</table>";
            echo "</form>";

        }else{
            echo "<h2>Aucun utilisateur ne correspond a votre requête</h2>";
        }
    }
    ?>
</div>
<footer>
    <p>Arret dessin </p>
</footer>
</body>
</html>