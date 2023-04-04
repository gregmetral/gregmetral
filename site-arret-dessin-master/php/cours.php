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
        <h2>Cours</h2>
        <label for="cour">Cours : </label>
        <select name="cour" id="cour">
            <option value="">tout</option>

            <?php
            $db = new PDO("mysql:host=localhost;dbname=dessin_bdd;charset=UTF8","root","");
            $req_cour_nom = $db->prepare("select nom, id from nom_cours");
            $req_cour_nom->execute();

            while ($name = $req_cour_nom->fetch())
                echo "<option value=" . $name["id"] . ">" . $name["nom"] . "</option>";
            ?>

        </select>
        <label>
            Date : <input class="date" type="date" name="date_cour" id="date_cour" value="*" min="<?= date('Y-m-d'); ?>">
        </label>

        <label for="heure_cour">Heure </label>
        <select name="heure_cour" id="heure_cour">

            <option value=""> </option>
            <?php
            for ($heure=8; $heure<=18; ++$heure){
                if ($heure!=12){
                    echo "<option value=" . $heure . ">" . $heure . "h</option>";
                }
            }
            ?><br>

        </select><br>

        <?php
            if (isset($_SESSION["connected"])){
        echo "
        <label for='participation'>
            mes participations<input type='checkbox' name='participation' id='participation' value=".$_SESSION["connected"].">
        </label>";
            } ?>

        <label for="particpant">
            <select name="participant" id="particpant">
                <option value=""> </option>
                <?php
                $req_utilisateur = $db->prepare("select id,prenom, nom, surnom from utilisateur where id!=?");

                //affiche tout les participants sauf l'utilisateur connecter
                if (isset($_SESSION["connected"])){
                    $req_utilisateur->execute([$_SESSION["connected"]]);
                }else{
                    $req_utilisateur->execute([-1]);
                }
                while ($utilisateur = $req_utilisateur->fetch()){
                    echo "<option value=".$utilisateur["id"].">".$utilisateur["prenom"]. " ".$utilisateur["nom"]." ".$utilisateur["surnom"]."</option>";
                }
                ?>
            </select><br>
        </label>

        <label>
            <input class="submit" type="submit" id="search" name="search" value="Rechercher" />
        </label>
    </form>
</div>
<div class="recherche_cours">
<?php

//cherche selon type cour date et jour
if (isset($_GET["search"])) {

    $jour_heure_utiliser=array();
    $cour_par_participant=array();
    
    //cherche tout les cours auquel participe l'utilisateur et invite: pour bouton rejoindre, quitter et aucun
    if (isset($_SESSION["connected"])){
        $req_cour_user = $db->prepare("select id_cour,jour, heure from cours inner join
        reservation on cours.id = reservation.id_cour where reservation.id_utilisateur=?");
        $req_invite=$db->prepare("select invite from reservation where id_cour=? and id_utilisateur=".$_SESSION["connected"]);


        $req_cour_user->execute([$_SESSION["connected"]]);
        $jour_heure_utiliser = $req_cour_user->fetchAll();

    }

    //si on cherche par utilisateur
    $recherche_utilisateure=false;
    if(isset($_GET["participation"]) || $_GET["participant"]) {
        $recherche_utilisateure=true;

        //syntax unique pour quand on cherche pour les 2
        if (isset($_GET["participation"]) && $_GET["participant"]) {
            $req_cour_par_participant = $db->prepare("SELECT r1.id_cour FROM reservation r1 INNER JOIN
                reservation r2 ON r2.id_utilisateur=? and r1.id_utilisateur=? and r1.id_cour=r2.id_cour");
            $req_cour_par_participant->execute([$_GET["participation"], $_GET["participant"]]);

        }else{ //si pas les 2 on cherche lequel à été appelé
            $id = "";
            if (isset($_GET["participation"])) {
                $id = $_GET["participation"];
            } else {
                $id = $_GET["participant"];
            }

            $req_cour_par_participant = $db->prepare("select id_cour from reservation where id_utilisateur=?");
            $req_cour_par_participant->execute([$id]);
        }
        $id_cour = $req_cour_par_participant->fetchAll(PDO::FETCH_COLUMN,0);
    }



    $prepare = "";
    $need_and = 0;

    if ($_GET["cour"] || $_GET["date_cour"] || $_GET["heure_cour"] || $recherche_utilisateure) {//si une condition est demandé
        $prepare = $prepare . "where";//ajouter where
        if ($_GET["cour"]) {
            $prepare = $prepare . " nom=" . $_GET["cour"];
            $need_and = 1;
        }
        if ($_GET["date_cour"]) {
            if ($need_and) {//si condition avant celle ci
                $prepare = $prepare . " and ";
            }
            $need_and = 1;

            $prepare = $prepare . ' jour="' . $_GET["date_cour"] . '"';

        }
        if ($_GET["heure_cour"]) {
            if ($need_and) {//si condition avant celle ci
                $prepare = $prepare . " and ";
            }
            $need_and=1;

            $prepare = $prepare . ' heure="' . $_GET["heure_cour"] . ':00:00"';
        } else {
            $time = "";
        }
        if ($recherche_utilisateure && $id_cour<>NULL){
            if ($need_and) {//si condition avant celle ci
                $prepare = $prepare . " and ";
            }

            $prepare=$prepare." id in (".implode(",",$id_cour).")";
        }elseif ($recherche_utilisateure && $id_cour==NULL){
            $prepare="where id=-1";
        }
    }
    //recup toute les infos sur ce cours
    $req_cour_existe = $db->prepare("select id,nom ,salle,nb_place,jour,heure from cours " . $prepare);

    $req_cour_existe->execute();
    $cour_existe = [];
    $cours = $req_cour_existe->fetchAll();


    //recup toute les infos sur ce cours
    $req_cour_info = $db->prepare("select id,nom ,salle,nb_place,jour,heure from cours " . $prepare);
    //recup le nom du cours
    $req_cour_nom = $db->prepare("select nom from nom_cours where id=?");
    //recup le nombre de participant
    $req_participant = $db->prepare("select 
       (count(id_utilisateur) + sum(invite)) as participe  from reservation where id_cour=?");

    if ($cours <> NULL) {
        echo "<form id='info_cour' class='cours'>";
        echo "<table>
            <tr>
                <th class='haut'>Cours</th>
                <th class='haut'>Jour</th>
                <th class='haut'>Heure</th>
                <th class='haut'>Salle</th>
                <th class='haut'>Nombre d'Inscrits</th>
                <th class='haut'>Nombre de Places</th>          
            </tr>";

        $req_cour_info->execute();
        $cour_existe = [];

        foreach ($cours as $cour) {

            $req_cour_nom->execute([$cour["nom"]]); //recup nom du cour
            $req_participant->execute([$cour["id"]]);

            $cour_nom = $req_cour_nom->fetch();
            $particpant = $req_participant->fetch();

            // verifie si on participe déjà à un cour sur le meme crenaux
            $participe_deja_creneau = array_filter($jour_heure_utiliser, function($val) use($cour){
                return ($val["jour"]==$cour["jour"] and $val["heure"]==$cour["heure"]); });

            $complet = $particpant["participe"]>= $cour["nb_place"];

            array_push($cour_existe, $cour["nom"]);

            //affiche nom/jour/heure/salle/nb participant/nb place
            echo
                "<tr>
            <th> " . $cour_nom["nom"] . "</th>
            <th> " . $cour["jour"] . "</th>
            <th>" . $cour["heure"] . "H</th>
            <th>" . $cour["salle"] . "</th>
            <th>" . $particpant["participe"] . "</th>
            <th>" . $cour["nb_place"] . "</th>";

            if (isset($_SESSION["connected"]) && !$participe_deja_creneau && !$complet) {
                echo "<th class='hors_tableau'><button class='bouton_rejoindre' type='submit' name='rejoindre' value=" . $cour["id"] . ">participer</button></th>";
            } elseif (isset($_SESSION["connected"]) && in_array($cour["id"], array_column($jour_heure_utiliser, "id_cour"))) {
                echo "<th class='hors_tableau'><button class='bouton_quitter' type='submit' name='quitter' value=" . $cour["id"] . ">quitter</button></th>";

                //on ne peut ajouter un invite si on participe au cours
                $req_invite->execute([$cour["id"]]);
                $est_invite = $req_invite->fetch();
                if ($est_invite===NULL || $est_invite[0]==false){
                    print_r($est_invite);
                    echo "<th class='hors_tableau'><button type='submit' name='ajouter_invite' value=" . $cour["id"] . ">ajout invite</button></th>";
                }else{
                    print_r($est_invite);
                    echo "<th class='hors_tableau'><button type='submit' name='quitter_invite' value=" . $cour["id"] . ">retirer invite</button></th>";
                }
            }
            if (isset($_SESSION["admin"])) {
                echo "<th class='hors_tableau'><button class='bouton_suppr' type='submit' name='suppr' value=" . $cour["id"] . ">SUPPR</button></th>";
            }
            echo "</tr>";
        }
        echo "</table>";
        echo "</form>";


    }else{
        echo "<h2>Aucun cours ne correspond a votre requête</h2>";
    }


    //si admin, (date et heure) specifier et recherche utilisateur NON specifier: afiche cours que l'on peut ajouter
    if ($date = $_GET["date_cour"] && $heure = $_GET["heure_cour"] && isset($_SESSION["admin"]) && !$recherche_utilisateure) {
        if ($cour_existe == []) {
            $req_cour_existe_pas = $db->prepare("select id from nom_cours");
        } else {
            $req_cour_existe_pas = $db->prepare("select id from nom_cours 
        where id not in (" . implode(",", $cour_existe) . ")");
        }

        $req_cour_existe_pas->execute();

        echo "<form id='ajout_cour'>";
        echo $_GET["date_cour"] . " | " . $_GET["heure_cour"] . "H " . "<br>";
        echo " salle: <input class='texte' type='text' name='salle' required='required' form='ajout_cour'><br>";
        echo "nombre d'élève: <input class='texte' type='number' name='nb_place' required='required' form='ajout_cour' min='1'><br><br>";
        echo "<input type='hidden' name='date' value=" . $_GET['date_cour'] . ">";
        echo "<input type='hidden' name='heure' value=" . $_GET['heure_cour'] . ">";

        while ($cour = $req_cour_existe_pas->fetch()) {
            $req_cour_nom->execute([$cour["id"]]);
            $cour_nom = $req_cour_nom->fetch();
            echo $cour_nom["nom"] . "   " . $_GET["date_cour"] . "   "
                . $_GET["heure_cour"] . "H";
            echo "<button class='bouton_rejoindre' type='submit' name='ajout_cour' form='ajout_cour' value='" . $cour["id"] . "'>creer</button><br>";
        }
        echo "</form>";
    }
}

//ajout participant
if (isset($_GET["rejoindre"])){
    $req_ajout_participer = $db->prepare("insert into reservation(id_cour, id_utilisateur) values (?,?)");
    $req_ajout_participer->execute([$_GET["rejoindre"], $_SESSION["connected"]]);
}
//retirer participant
elseif (isset($_GET["quitter"])){
    $req_retirer_participant=$db->prepare("delete from reservation where id_cour=? and id_utilisateur=?");
    $req_retirer_participant->execute([$_GET["quitter"], $_SESSION["connected"]]);
}
//ajout invite
elseif (isset($_GET["ajouter_invite"])){
    $req_ajout_invite = $db->prepare("update reservation set invite=1 where id_cour=? and id_utilisateur=".$_SESSION["connected"]);
    $req_ajout_invite->execute([$_GET["ajouter_invite"]]);
}
//retirer invite
elseif (isset($_GET["quitter_invite"])){
    $req_ajout_invite = $db->prepare("update reservation set invite=0 where id_cour=? and id_utilisateur=".$_SESSION["connected"]);
    $req_ajout_invite->execute([$_GET["quitter_invite"]]);
}
//suppression cour
elseif (isset($_GET["suppr"])){
    $req_suppr_reservation=$db->prepare("delete from reservation where id_cour=?");
    $req_suppr_cour = $db->prepare("delete from cours where id=?");
    $req_suppr_reservation->execute([$_GET["suppr"]]);
    $req_suppr_cour->execute([$_GET["suppr"]]);
}
//ajout cour
elseif (isset($_GET["ajout_cour"])){
            $req_ajout_cour=$db->prepare("insert into cours(nom, salle, nb_place, jour, heure)
 VALUES (? ,? ,? ,? ,?)");
            $req_ajout_cour->execute([$_GET["ajout_cour"],$_GET["salle"], $_GET["nb_place"], $_GET["date"], $_GET["heure"].":00:00" ]);

}

?>
</div>
<footer>
    <p>Arret dessin </p>
</footer>
</body>
</html>