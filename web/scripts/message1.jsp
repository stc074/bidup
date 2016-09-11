<%@page import="java.util.Calendar"%>
<%@page import="classes.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Message reçu</title>
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
            break; } } else if(request.getAttribute("message")!=null) {
                Message message=(Message)(request.getAttribute("message"));
                if(message.getTest()==0) {
                %>
                <h1>Message reçu</h1>
                <%
                if(message.getIdPrecedent()!=0) {
                Calendar cal=Calendar.getInstance();
                cal.setTimeInMillis(message.getTimestampPrecedent()); %>
                <div class="info">Vous aviez écris à <%= message.getPseudoExpediteur() %>, le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %> :</div>
                <h2><%= message.getObjetPrecedent() %></h2>
                <p><%= message.getContenuPrecedent() %></p><% }
                Calendar cal=Calendar.getInstance();
                cal.setTimeInMillis(message.getTimestamp());
                //out.println(message.getErrorMsg());
                %>
                <div class="info"><%= message.getPseudoExpediteur() %> vous a écris le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %> :</div>
                <h2><%= message.getObjet() %></h2>
                <p><%= message.getContenu() %></p>
                <h1>Répondre à <%= message.getPseudoExpediteur() %></h1>
                <p>Pour répondre à <%= message.getPseudoExpediteur() %>, veuillez utiliser le formulaire ci-dessous.</p>
                <div id="form"><% if(message.getErrorMsg().length()>0) { %>
                    <div class="erreur">
                        <div>Erreurs :</div>
                        <br/>
                        <div><%= message.getErrorMsg() %></div>
                    </div><% } %>
                <form action="./Message1#form" method="POST">
                    <input type="hidden" name="id" value="<%= message.getIdMessage() %>" />
                    <div>Objet du message :</div>
                    <input type="text" name="objet" value="<%= message.getObjet() %>" size="30" maxlength="40" />
                    <div>Contenu du message :</div>
                    <textarea name="contenu" rows="8" cols="80"><%= message.getContenu2() %></textarea>
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
          <br/>
                    <input type="submit" value="Envoyer" name="kermit" />
                </form>
                </div>
                <% } else { %>
                <div class="info">Nous avons bien transmis le message à <%= message.getPseudoExpediteur() %>.</div>
                <% message.blank(); } }%>
        </div>
    </body>
</html>
