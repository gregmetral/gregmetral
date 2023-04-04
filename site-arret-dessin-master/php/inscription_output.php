<?php
$db = new PDO("mysql:host=localhost; dbname=dessin_bdd;charset=UTF8","root","");

//verifie que l'utilsateur n'existe pas déjà
$req_user_exist = $db->prepare("select * from utilisateur where
email=? or
surnom=?"
);
$req_user_exist->execute(
    [$_POST["texte_email"],
    $_POST["texte_surnom"]]
);
$user_exist = $req_user_exist->fetch();

//si il n'existe pas il est ajouté à la bdd
if ($user_exist == NULL) {

    $add_user = $db->prepare("insert into utilisateur(
                            email,
                            surnom,
                            nom,
                            prenom,
                            passwd,
                            naissance,
                            niv_dessin
    ) values (?,?,?,?,password(?),?, (select id from nom_niv where nom=?))");

    $add_user->execute(
        [$_POST["texte_email"],
            $_POST["texte_surnom"],
            $_POST["texte_nom"],
            $_POST["texte_prenom"],
            $_POST["mdp"],
            $_POST["date_naissance"],
            $_POST["niveau"]]
    );
    header("location: index.php");
}else{
    header("location: inscription.php?error=inscription");
}