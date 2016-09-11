<%@page import="classes.ConForm1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Connexion</title>
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
            <h1>Connexion</h1><%
            ConForm1 formulaire=(ConForm1)(request.getAttribute("formulaire"));
            if(formulaire.getTest()==0) { %>
            <p>Pour vous connecter, utilisez le formulaire ci-dessous.</p><% if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
            <form action="./Connexion" method="POST">
                <div>Votre pseudo :</div>
                <input type="text" name="pseudo" value="<%= formulaire.getPseudo() %>" size="15" maxlength="20" />
                <div>Votre mot de passe :</div>
                <input type="password" name="motDePasse" value="" size="15" maxlength="15" />
                <br/>
                <input type="submit" value="Connexion" name="submit" />
                <br/>
                <br/>
                <div><a href="./MdpOublie" rel="nofollow">J'ai oublié mon mot de passe</a></div>
            </form><% } else { formulaire.blank(); %>
            <div class="info">Vous êtes désormais connecté !</div><% } %>
        </div>
    </body>
</html>
