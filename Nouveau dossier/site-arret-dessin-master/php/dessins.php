<html lang="fr">
<?php
session_start();
?>
<head>
    <title>dessin</title>
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
                <li> <a href="post.php">Post</a> </li>
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

</div>
<div id="content" class="formulaire">

    <form method="POST"
          action=""
          enctype="multipart/form-data">
        <h2>Postez vos dessins ici !</h2>
        Titre <input class="texte" type="text" name="titre" required="required"><br>
        <input type="file"
               name="uploadfile"
               value="" />

        <div>
            <button class="submit" type="submit"
                    name="upload">
                UPLOAD
            </button>
        </div>
    </form>
</div>
<?php
$msg = "";

// If upload button is clicked ...
if (isset($_POST['upload'])) {
    if ($_FILES["uploadfile"]["name"] <> NULL) {
        $db = mysqli_connect("localhost", "root", "", "dessin_bdd");
        $query="select * from post where id_utilisateur=".$_SESSION["connected"]." and titre='".$_POST["titre"]."'";
        $titre_existe = mysqli_query($db  ,$query);

        if ($titre_existe->num_rows == 0){
            $image = addslashes(file_get_contents($_FILES['uploadfile']['tmp_name']));

            // Get all the submitted data from the form
            $query = "INSERT INTO post(id_utilisateur,dessin, titre) VALUES (".$_SESSION["connected"].",'$image','".$_POST["titre"]."')";

            // Execute query
            $qry = mysqli_query($db, $query);
        }else{
            echo "une de vos oeuvres à le meme nom, veuillez en chosir un autre titre";
        }
    } else {
        echo "aucun fichier n'as été selectionnez";
    }
}
?>
<footer>
    <p>Arret dessin </p>
</footer>
</body>
</html>