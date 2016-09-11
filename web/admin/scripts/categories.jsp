<%@page import="classes.CatForm1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>ADMINISTRATION</title>
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
<link href="./../CSS/style.css" type="text/css" rel="stylesheet" />
</head>
    <body><% int p=0; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Catégories</h1><% CatForm1 formulaire=(CatForm1)(request.getAttribute("formulaire"));
            if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } if(formulaire.getTest()==1) { %>
            <div class="info">La catégorie "<%= formulaire.getCategorie() %>" a bien été enregistrée.</div><% } %>
            <form action="./Categories" method="POST">
                <div>Nom de la nouvelle catégories :</div>
                <input type="text" name="categorie" value="" size="30" maxlength="100" />
                <br/>
                <input type="submit" value="Valider" name="submit" />
            </form>
        </div>
    </body>
</html>
