<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Messages reçus</title>
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
            <% if(request.getAttribute("info")!=null) {
                int info=Integer.parseInt(request.getAttribute("info").toString());
                switch(info) {
                    case 1: %>
            <div class="info">Vous devez être connecté pour pouvoir consulter vos messages.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break; } } else if(request.getAttribute("messagerie")!=null) {
                Messagerie messagerie2=(Messagerie)(request.getAttribute("messagerie"));
                %>
        <h1>Messages reçus</h1><%
        if(messagerie2.getTest()==1) { %>
        <div class="info">Message éffacé !</div>
        <br/><% }
        long idMembre=messagerie2.getIdMembre();
        Objet.getConnection();
        String query="SELECT t1.id,t1.objet,t1.timestamp,t1.lu,t2.pseudo AS pseudoExpediteur FROM table_messages AS t1,table_membres AS t2 WHERE t1.id_destinataire=? AND t2.id=t1.id_expediteur ORDER BY t1.timestamp DESC";
        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
        prepare.setLong(1, idMembre);
        ResultSet result=prepare.executeQuery();
        int i=0;
        while(result.next()) {
            i++;
            long idMessage=result.getLong("id");
            String objet=result.getString("objet");
            long timestamp=result.getLong("timestamp");
            int lu=result.getInt("lu");
            String pseudoExpediteur=result.getString("pseudoExpediteur");
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            %>
            <div><a href="./message1-<%= idMessage %>.html" rel="nofollow"><%= objet %></a> <% if(lu==0) out.print("[<span class=\"blink\">Non lu</span>]"); %> envoyé le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %> par <%= pseudoExpediteur %>.</div>
            <div><a href="effacemsg1-<%= idMessage %>.html" rel="nofollow" title="Éffacer"><img src="./GFXs/cross.png" width="16" height="16" alt="cross"/>
                    <span>&rarr;Éffacer</span></a></div>
            <br/>
            <% } if(i==0) { %>
            <div class="info">Aucun message reçus</div>
            <% }
        result.close();
        prepare.close();
        Objet.closeConnection();
                } %>
        </div>
    </body>
</html>
