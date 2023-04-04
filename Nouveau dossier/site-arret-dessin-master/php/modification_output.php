<?php
session_start();
$db = new PDO("mysql:host=localhost; dbname=dessin_bdd;charset=UTF8","root","");

//verifie que l'utilsateur n'existe pas déjà
$req_user_exist = $db->prepare("select * from utilisateur where
surnom=?"
);
$req_user_exist->execute(
        [$_POST["texte_surnom"]]
);
$user_exist = $req_user_exist->fetch();



$req_user_surnom = $db->prepare('select surnom from utilisateur where id=?');
$req_user_surnom->execute([$_SESSION["connected"]]);
$user_surnom = $req_user_surnom->fetch();

$test = $_POST["texte_surnom"];


//si il n'existe pas la bdd est modifiée
if ($user_exist == NULL || implode($user_surnom) == $_POST["texte_surnom"] . $_POST["texte_surnom"]){

    $modify_user = $db->prepare('UPDATE utilisateur Set surnom=?, nom=?, prenom=?, passwd=password(?), naissance=?, niv_dessin=(select id from nom_niv where nom=?) where id=?');


    $modify_user->execute(
        [$_POST["texte_surnom"],
            $_POST["texte_nom"],
            $_POST["texte_prenom"],
            $_POST["mdp"],
            $_POST["date_naissance"],
            $_POST["niveau"],
            $_SESSION["connected"]]
    );
    header("location: index.php");

}else{
    header("location: modification.php?error=modification");
}