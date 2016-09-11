<%@page import="classes.DepotForm2"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Mettre aux enchères</title>
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
            <h1>Déposer une annonce</h1><%  if(request.getAttribute("info")!=null) {
            int info=Integer.parseInt(request.getAttribute("info").toString());
        switch(info) {
        case 1: %>
            <div class="info">Vous devez être connecter pour pouvoir déposer une annonce.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
         break; } } else if(request.getAttribute("formulaire")!=null) {
            DepotForm2 formulaire=(DepotForm2)(request.getAttribute("formulaire"));
            if(formulaire.getTest()==0) { %>
            <p>2-Titre et description de votre annonce.</p><% if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
            <form action="./DeposerAnnonce2"  method="POST">
                <div>Titre de votre annonce :</div>
                <input type="text" name="titre" value="<%= formulaire.getTitre() %>" size="30" maxlength="40" />
                <div>Description de votre annonce :</div>
                <textarea name="description" rows="8" cols="80"><%= formulaire.getDescription() %></textarea>
                <br/>
                <input type="submit" value="Valider" name="kermit" />
            </form>
            <br/>
            <div><a href="./DeposerAnnonce1" rel="nofollow">Étape précédente</a></div><% } else { formulaire.blank(); %>
            <script type="text/javascript">
                window.location.href="./DeposerAnnonce3";
            </script><% } } %>
        </div>
    </body>
</html>
