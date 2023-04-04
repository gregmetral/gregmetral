<?php
session_start();

if ($_GET["connect"] == "true"){
    $db = new PDO("mysql:host=localhost; dbname=dessin_bdd;charset=UTF8","root","");
    $req_user = $db->prepare("select id, admin from utilisateur where email=? and passwd=password(?)");
    $req_user->execute([$_POST["texte_email"], $_POST["mdp"]]);
    $user = $req_user->fetch();


    if ($user==NULL){
        header("Location: connexion.php?error=connexion");
    }else {
        $_SESSION["connected"] = $user["id"];
        if ($user["admin"]){
            $_SESSION["admin"]=true;
        }
        header("Location: index.php");
    }
}else{
    unset($_SESSION["connected"]);
    unset($_SESSION["admin"]);
    header("location: index.php");
}