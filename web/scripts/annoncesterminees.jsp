<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
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
            break; } } else if(request.getAttribute("idMembre")!=null) {
                long idMembre=Long.parseLong(request.getAttribute("idMembre").toString());
                %>
                <h1>Annonce terminées</h1>
                <%
                Objet.getConnection();
                String query="SELECT id,titre,timestamp_fin,etat FROM table_annonces WHERE id_membre=? AND (etat='2' OR etat='3' OR etat='4') ORDER BY timestamp_fin DESC";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, idMembre);
                ResultSet result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                    i++;
                    long idAnnonce=result.getLong("id");
                    String titre=result.getString("titre");
                    long timestamp=result.getLong("timestamp_fin");
                    int etat=result.getInt("etat");
                    Calendar cal=Calendar.getInstance();
                    cal.setTimeInMillis(timestamp);
                    %>
               <div>
                    <a href="edit-annonce2-<%= idAnnonce %>.html" rel="nofollow"><%= titre %></a>
                    <span>&rarr;<% switch(etat) {
                        case 2:
                            out.print("Vendu");
                            break;
                            case 3:
                                out.print("Invendu");
                                break;
                                case 4:
                                    out.print("Vendu(achat immédiat)");
                                    break; } %></span>
                    <span>&rArr;Fin de la vente le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %> à <%= cal.get(Calendar.HOUR_OF_DAY) %> : <%= cal.get(Calendar.MINUTE) %>.</span>
                    <span>&rArr;<a href="efface-annonce2-<%= idAnnonce %>.html" rel="nofollow">Éffacer cette annonce</a></span>
                </div>
                <br/>
                <% } if(i==0) { %>
                <div class="info">Aucune annonce en cours !</div><% } } %>
         </div>
    </body>
</html>
