<?php

$texte_nom=$_POST["texte_nom"];
$texte_prenom=$_POST["texte_prenom"];
$texte_email=$_POST["texte_email"];
$message=$_POST["message"];
$demande=$_POST["demande"];


$msg = "Nom:\t$texte_nom\n";
$msg .= "Prénom:\t$texte_prenom\n";
$msg .= "E-Mail:\t$texte_email\n";
$msg .= "Message:\t$message\n\n";

$recipient = "arretdessin@yahoo.com";
$subject = "Demande:\t$demande\n";


if(mail($recipient, $subject, $msg)){
    echo "Email envoyé !";
}else{
    echo "Erreur, le mail ne s'est pas envoyé";

}
echo '<a href="index.php"> Retour au menu principal </a>';
?>