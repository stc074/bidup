<%@page import="java.util.Calendar"%>
<%@page import="classes.Enchere"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Mettre aux enchères [Photos]</title>
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
    <body><% int p=5; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu"><% if(request.getAttribute("info")!=null) {
        int info=Integer.parseInt(request.getAttribute("info").toString());
        switch(info) {
            case 1: %>
            <div class="info">Vous devez être connecter pour pouvoir déposer enchérir.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break; } } else {
            Enchere enchere=(Enchere)(request.getAttribute("enchere"));
            if(enchere.getTest()==-1) { %>
            <div class="info">Désolé cette annonce n'existe plus</div><% } else { %>
            <% if(session.getAttribute("uriAnnonce")!=null) { %>
            <div><a href="<%= session.getAttribute("uriAnnonce").toString() %>">Revenir a l'annonce</a></div><% } %>
            <br/>
            <div><a href="./">Revenir à la liste d'annonce</a></div>
            <h1><%= enchere.getTitre() %></h1>
            <% if(enchere.getTest()!=0) {
                switch(enchere.getTest()) {
                    case 1: %>
                    <div class="info2">Désolé quelqu'un d'autre a surrenchéri avant vous !</div><%
                    break;
                    case 2:
                    enchere.blank(); %>
                    <div class="info2">Votre enchère a été prise en compte, mais le prix de réserve (mini pour être vendu) n'a pas été atteint, vour pouvez réenchérir.</div><%
                    break;
                    case 3:
                    enchere.blank(); %>
                    <div class="info2">Bravo, vous ête le meilleur enchérisseur !</div><%
                    break;
                    } } %>
            <% Calendar cal=Calendar.getInstance();
            long timestampActuel=cal.getTimeInMillis();
            long ecart=enchere.getTimestampFin()-timestampActuel;
            int nbJours=(int)(ecart/(1000*60*60*24));
            long ecart2=ecart-(nbJours*1000*60*60*24);
            int nbHeures=(int)(ecart2/(1000*60*60));
            long ecart3=ecart2-(nbHeures*1000*60*60);
            int nbMinutes=(int)(ecart3/(1000*60));
            long ecart4=ecart3-(nbMinutes*1000*60);
            int nbSecondes=(int)(ecart4/(1000));
            %>
            <div>Temps restant : <% if(nbJours!=0) out.print(nbJours+"J-"); %><% if(!(nbJours==0&&nbHeures==0)) out.print(nbHeures+"H-"); %><% if(!(nbJours==0&&nbHeures==0&&nbMinutes==0)) out.print(nbMinutes+"Min-"); %><%= nbSecondes %>S.</div>
            <br/>
            <div class="info">Prix actuel : <%= enchere.getPrixActuel() %>&nbsp;&euro;</div>
            <br/><% if(enchere.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= enchere.getErrorMsg() %></div>
            </div><% } %>
            <form action="./Encherir-<%= enchere.getIdAnnonce()%>.html" method="POST">
                <input type="hidden" name="id" value="<%= enchere.getIdAnnonce()%>" />
                <input type="hidden" name="prixActuel" value="<%= enchere.getPrixActuel()%>" />
                <div>Encherir (tapez au minimum : <%= Objet.convertDouble(enchere.getPrixActuel()+enchere.getPasEnchere()) %>)</div>
                <input type="text" name="prixEnchere" value="" size="6" maxlength="10" />&nbsp;&euro;
                <br/>
                <input type="submit" value="Enchérir" name="kermit" />
            </form>
            <% } } %>
        </div>
    </body>
</html>
