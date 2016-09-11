<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
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
            <div class="info">Vous devez être connecté pour pouvoir consulter vos commande.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break; } } else if(request.getAttribute("idMembre")!=null) {
            long idMembre=Long.parseLong(request.getAttribute("idMembre").toString());
                %>
            <h1>Commandes - Objets vendus</h1>
            <h2>Objets achetés aux enchères</h2>
            <% Objet.getConnection();
            String query="SELECT t1.titre,t2.id,t2.timestamp FROM table_annonces AS t1,table_encheres_finies AS t2 WHERE t1.id=t2.id_annonce AND t2.etat='1' AND t2.id_membre_acheteur=? ORDER BY t2.timestamp ASC";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMembre);
            ResultSet result=prepare.executeQuery();
            int i=0;
            while(result.next()) {
                i++;
                String titre=result.getString("titre");
                long idEnchereFinie=result.getLong("id");
                long timestamp=result.getLong("timestamp");
                Calendar cal=Calendar.getInstance();
                cal.setTimeInMillis(timestamp);
                %>
                <div><a href="./detail-commande2-<%= idEnchereFinie %>.html" rel="nofollow"><%= titre %></a>&rarr;Le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %>.</div>
                <br/>
                <% } result.close();
                prepare.close();
                if(i==0) { %>
                <div class="info">Aucun objet vendu</div>
                <br/><% } %>
                <h2>Objets achetés en achat immédiat</h2>
                <% query="SELECT t1.titre,t2.id,t2.timestamp FROM table_annonces AS t1,table_achats_immediats AS t2 WHERE t1.id=t2.id_annonce AND t2.etat='1' AND t2.id_membre_acheteur=? ORDER BY t2.timestamp ASC";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, idMembre);
                result=prepare.executeQuery();
                i=0;
                while(result.next()) {
                    i++;
                    String titre=result.getString("titre");
                    long idAchatImmediat=result.getLong("id");
                    long timestamp=result.getLong("timestamp");
                    Calendar cal=Calendar.getInstance();
                    cal.setTimeInMillis(timestamp);
                    %>
                    <div><a href="./detail-commande4-<%= idAchatImmediat %>.html" rel="nofollow"><%= titre %></a>&rarr; Le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %>.</div>
                    <br/>
                    <% } result.close();
                    prepare.close();
                    Objet.closeConnection();
                    if(i==0) { %>
                    <div class="info">Aucun objet vendu</div>
                    <br/><% } %>
            <% } %>
        </div>
    </body>
</html>
