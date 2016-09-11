<%@page import="classes.MdpForm2"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Annonces en cours</title>
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
            <div class="info">Vous devez être connecté pour pouvoir accéder à cette page.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break; } } else if(request.getAttribute("formulaire")!=null) {
                MdpForm2 formulaire=(MdpForm2)(request.getAttribute("formulaire"));
                %>
                <h1>Changer mon mot de passe</h1><% if(formulaire.getTest()==0) { %>
                <p>Pour changer votre mot de passe, veuillez utiliser le formulaire ci-dessous</p>
                <% if(formulaire.getErrorMsg().length()>0) { %>
                <div class="erreur">
                    <div>Erreur(s) :</div>
                    <br/>
                    <div><%= formulaire.getErrorMsg() %></div>
                </div><% } %>
                <form action="./ChangerMDP" method="POST">
                    <div>Mot de passe actuel :</div>
                    <input type="password" name="motDePasseActuel" value="" size="15" maxlength="15" />
                    <div>Nouveau mot de passe :</div>
                    <input type="password" name="motDePasse" value="" size="15" maxlength="15" />
                    <div>Confirmation du nouveau mot de passe :</div>
                    <input type="password" name="motDePasse2" value="" size="15" maxlength="15" />
                    <br/>
                    <input type="submit" value="Valider" name="kermit" />
                </form>
                <% } else {
                formulaire.blank();
                %>
                <div class="info">Mot de passe modifié, vous allez recevoir un mail de confirmation</div>
                <% } } %>
        </div>
    </body>
</html>
