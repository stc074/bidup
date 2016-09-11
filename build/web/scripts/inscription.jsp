<%@page import="classes.InsForm1"%>
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
    <body><% int p=1; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Inscription gratuite</h1>
                        <% InsForm1 formulaire=(InsForm1)(request.getAttribute("formulaire"));
                        if(formulaire.getTest()==0) { %>
        <p>Comme tout le contenu de ce site, l'inscription est gratuite, utilisez le formulaire ci-dessous.</p>
        <p>Votre pseudo et mot de passe devront ne comporter que des caractères alphanumériques.</p>
        <div id="form"><% if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
        <form action="./Inscription#form" method="POST">
            <div>Choisissez un pseudo :</div>
            <input type="text" name="pseudo" value="<%= formulaire.getPseudo() %>" size="15" maxlength="20" />
            <div>Choisissez un mot de passe :</div>
            <input type="password" name="motDePasse" value="" size="15" maxlength="15" />
            <div>Confirmation du mot de passe :</div>
            <input type="password" name="motDePasse2" value="" size="15" maxlength="15" />
            <div>Votre adresse Email :</div>
            <input type="text" name="email" value="<%= formulaire.getEmail() %>" size="30" maxlength="100" />
            <div>Votre nom :</div>
            <input type="text" name="nom" value="<%= formulaire.getNom() %>" size="30" maxlength="100" />
            <div>Votre prénom :</div>
            <input type="text" name="prenom" value="<%= formulaire.getPrenom() %>" size="30" maxlength="100" />
            <div>Votre adresse :</div>
            <textarea name="adresse" rows="5" cols="80"><%= formulaire.getAdresse() %></textarea>
            <div>Votre code postal :</div>
            <input type="text" name="codePostal" value="<%= formulaire.getCodePostal() %>" size="6" maxlength="6" />
            <div>Votre Ville/village :</div>
            <input type="text" name="ville" value="<%= formulaire.getVille() %>" size="30" maxlength="100" />
            <br/>
            <br/>
            <div>
                <span>
                    <img src="./Captcha" width="80" height="16" alt="captcha"/>
                </span>
                <span>&rarr; Recopier le code SVP &rarr;</span>
                <span>
                    <input type="text" name="captcha" value="" size="5" maxlength="5" />
                </span>
            </div>
            <div>
                <input type="radio" name="valid" value="1" checked="checked" />
                <span>En validant ce formulaire, je déclare avoir lu les conditions d'utilisations de ce site et les accepter.</span>
            </div>
            <input type="submit" value="Valider" name="submit" />
        </form>
        </div><% } else { formulaire.blank(); %>
        <div class="info">Vous êtes désormais inscrit !</div><% } %>
        </div>
    </body>
</html>
