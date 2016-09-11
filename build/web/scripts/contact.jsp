<%@page import="classes.Ctc"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Contact</title>
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
            <div class="info">Vous devez être connecter pour pouvoir accéder à cette page.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break;
            } } else if(request.getAttribute("contact")!=null) {
            Ctc contact=(Ctc)(request.getAttribute("contact")); %>
            <h1>Contacter <%= contact.getPseudoDestinataire() %></h1>
            <% if(contact.getTest()==0) { %>
            <p>Vous pouvez contacter <%= contact.getPseudoDestinataire() %> en utilisant le formulaire ci-dessous.</p>
            <% if(contact.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= contact.getErrorMsg() %></div>
            </div><% } %>
            <form action="./Contact" method="POST">
                <input type="hidden" name="id" value="<%= contact.getIdMembreDestinataire() %>" />
                <div>Objet du message :</div>
                <input type="text" name="objet" value="<%= contact.getObjet() %>" size="30" maxlength="40" />
                <div>Contenu du message :</div>
                <textarea name="contenu" rows="5" cols="80"><%= contact.getContenu() %></textarea>
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
            <% } else { %>
            <div class="info">Votre message a bien été transmis à <%= contact.getPseudoDestinataire() %>.</div>
            <% contact.blank(); } } %>
        </div>
    </body>
</html>
