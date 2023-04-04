<html lang="fr">
<?php
session_start();
?>
<head>
    <title>post</title>
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
                <li> <a href="post.php">Post</a></li>
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
        titre:
        <label for="titre">
            <input class="texte" type="text" name="titre" id="titre">
        </label>

        utilisateur
        <select name="utilisateur" id="utilisateur">
            <option value=""> </option>
            <?php
            $db = new PDO("mysql:host=localhost;dbname=dessin_bdd;charset=UTF8","root","");
            $req_utilisateur = $db->prepare("select id,prenom, nom, surnom from utilisateur");
            $req_utilisateur->execute();
            while ($utilisateur = $req_utilisateur->fetch()){
                echo "<option value=".$utilisateur["id"].">".$utilisateur["prenom"]. " ".$utilisateur["nom"]." ".$utilisateur["surnom"]."</option>";
            }
        ?>
        </select><br>

        <input class="submit" type="submit" name="rechercher" value="rechercher">
    </form>
</div>
<div>
<?php
if (isset($_GET["rechercher"])){
    $db = mysqli_connect("localhost", "root", "", "dessin_bdd");
    $prepare = "";
    $need_and=0;
    if ($_GET["titre"] || $_GET["utilisateur"]){
        $prepare=$prepare." where ";

        if ($_GET["titre"]){
            $prepare=$prepare." titre='".$_GET["titre"]."'";
            $need_and=1;
        }if ($_GET["utilisateur"]){
            if ($need_and){
                $prepare=$prepare." and ";
            }

            $prepare=$prepare." id_utilisateur=".$_GET["utilisateur"];
        }
    }
    $result = $db->query("SELECT p.titre, p.dessin, u.prenom, u.nom, u.surnom FROM post p inner join utilisateur u on p.id_utilisateur=u.id  ".$prepare);
    echo '<table>
  <tr>
    <td>artiste</td>
    <td>titre</td>
    <td>Images</td>
  </tr>';
    while($image = mysqli_fetch_array($result))
    {

        ?>
        <tr>
            <td><?php echo $image['prenom']." ".$image['nom']." ".$image['surnom']; ?></td>
            <td><?php echo $image['titre']; ?></td>
            <td><?php echo '<img src="data:image/jpeg;base64,'.base64_encode($image['dessin']).'" />'; ?></td>
           <?php echo '<img src="data:image/jpeg;base64,'.base64_encode($image['dessin']).'"/>'; ?>
        </tr>
        <?php
    }
    echo '</table>';
}
?>
</div>
<footer>
    <p>Arret dessin </p>
</footer>
</body>
</html>
