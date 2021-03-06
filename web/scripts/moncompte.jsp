<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Mon compte</title>
<meta name="generator" content="Bluefish pour le squelette, Netbeans pour les organes vitaux"/>
<meta name="author" content=""/>
<meta name="date" content=""/>
<meta name="copyright" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="BidUP, votre site d'enchères gratuites !."/>
<meta name="ROBOTS" content="NOINDEX, NOFOLLOW"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8"/>
<meta http-equiv="content-style-type" content="text/css"/>
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./scripts/scripts.js"></script>
</head>
    <body><% int p=5; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <%
            if(request.getAttribute("info")!=null) {
                int info=Integer.parseInt(request.getAttribute("info").toString());
                switch(info) {
                    case 1: %>
            <div class="info">Vous devez être connecter pour pouvoir accéder à votre compte.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
         break; } } else { %>
         <h1>Mon Compte</h1>
        <div><a href="./Deconnexion" rel="nofollow">Déconnexion</a></div>
        <br/>
        <div><a href="./ChangerMDP" rel="nofollow">Changer mon mot de passe</a></div>
        <br/>
        <div><a href="./EffaceCompte" rel="nofollow">Éffacer mon compte</a></div>
        <br/>
        <div><a href="./Payer0" rel="nofollow">Objets a payer</a></div>
        <br/>
        <div><a href="./AnnoncesEnCours" rel="nofollow">Annonces en cours</a></div>
        <br/>
        <div><a href="./AnnoncesTerminees" rel="nofollow">Annonces terminées</a></div>
        <br/>
        <div><a href="./Commandes" rel="nofollow">Commandes</a></div>
        <br/>
        <div><a href="./Messagerie" rel="nofollow">Messagerie</a><% if(messagerie.getNbMsgRecuNonLu()!=0) { out.print(" [<span class=\"blink\">"+messagerie.getNbMsgRecuNonLu()+" Msg(s)</span>]"); } %></div>
        <% } %>
        </div>
    </body>
</html>
