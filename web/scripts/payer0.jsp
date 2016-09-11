<%@page import="java.util.Calendar"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Objets remportés à payer</title>
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
    <body><% int p=0; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Objets remportés à payer</h1>
            <% if(request.getAttribute("info")!=null) {
            int info=Integer.parseInt(request.getAttribute("info").toString());
        switch(info) {
        case 1: %>
            <div class="info">Vous devez être connecter pour pouvoir déposer une annonce.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
         break; } } else if(request.getAttribute("idMembre")!=null) {
             long idMembre=Long.parseLong(request.getAttribute("idMembre").toString());
        Objet.getConnection();
        String query="SELECT t1.titre,t2.id,t2.montant,t2.timestamp FROM table_annonces AS t1,table_encheres_finies AS t2 WHERE t1.id=t2.id_annonce AND t2.id_membre_acheteur=? AND t2.etat='0'";
        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
        prepare.setLong(1, idMembre);
        ResultSet result=prepare.executeQuery();
        int i=0;
        while(result.next()) {
            i++;
            String titre=result.getString("titre");
            long idEnchereFinie=result.getLong("id");
            double montant=result.getDouble("montant");
            long timestamp=result.getLong("timestamp");
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            %>
            <div>
                <a href="payer-<%= idEnchereFinie %>.html" rel="nofollow"><%= titre %></a>
                <span>[<%= montant %>&nbsp;&euro;. - le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %>.]</span>
            </div>
            <br/>
            <% } if(i==0) { %>
            <div class="info">Aucun objet remporté à payer.</div>
            <% } } %>
        </div>
    </body>
</html>
