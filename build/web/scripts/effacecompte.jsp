<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Commandes - Objets Vendus</title>
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
</head>
    <body><% int p=2; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <% if(request.getAttribute("info")!=null) {
            int info=Integer.parseInt(request.getAttribute("info").toString());
            switch(info) {
                case 1: %>
            <div class="info">Vous devez être connecté pour pouvoir éffacer votre compte.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break; } } else if(request.getAttribute("membre")!=null) {
            Membre membre2=(Membre)(request.getAttribute("membre")); %>
            <h1>Éffacer mon compte</h1><%
            if(membre2.getTest()==0) { %>
            <p>Si vous éffacez votre compte toutes vos données ainsi que vos annonces disparaitront de ce site</p>
            <p>Pour se faire cliquez sur le bouton ci-dessous :</p>
            <form action="./EffaceCompte" method="POST">
                <input type="submit" value="Je veux éffacer mon compte" name="kermit" />
            </form>
            <% } else if(membre2.getTest()==1) { %>
            <div class="info">Votre compte a été supprimé !</div>
            <% } } %>
        </div>
       </body>
</html>
