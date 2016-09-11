<%@page import="classes.MdpForm1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Inscription</title>
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
    <body><% int p=3; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Mot de passe oublié</h1><%
            MdpForm1 formulaire=(MdpForm1)(request.getAttribute("formulaire")); %>
            <p>Vous avez oublié votre mot de passe ? Ce n'est pas grave, utilisez le formulaire ci-dessous pour que je vous en envoie un autre.</p>
            <% if(formulaire.getTest()==0) { if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
            <form action="./MdpOublie" method="POST">
                <div>Votre adresse Email :</div>
                <input type="text" name="email" value="<%= formulaire.getEmail() %>" size="30" maxlength="100" />
                <br/>
                <input type="submit" value="Valider" name="submit" />
            </form><% } else { %>
            <div class="info">Un nouveau mot de passe a été envoyé à <%= formulaire.getEmail() %></div><% formulaire.blank(); } %>
        </div>
    </body>
</html>
