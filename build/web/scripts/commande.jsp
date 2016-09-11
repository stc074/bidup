<%@page import="java.util.Calendar"%>
<%@page import="classes.Comm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Commandes</title>
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
        <div class="contenu"><% if(request.getAttribute("info")!=null) {
            int info=Integer.parseInt(request.getAttribute("info").toString());
            switch(info) {
                case 1: %>
            <div class="info">Vous devez être connecté pour pouvoir consulter vos commande.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break;
                case 2: %>
                <div class="info">Commande inconnue, désolé !</div><%
            break; } } else if(request.getAttribute("commande")!=null) {
                Comm commande=(Comm)(request.getAttribute("commande")); %>
                <h1><%= commande.getTitre() %></h1>
                <%
                Calendar cal=Calendar.getInstance();
                cal.setTimeInMillis(commande.getTimestamp());
                switch(commande.getType()) {
                    case 1:
                        case 3: %>
                        <p>Objet vendu à <%= commande.getPseudoAcheteur() %> le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %> à <%= cal.get(Calendar.HOUR_OF_DAY) %>h-<%= cal.get(Calendar.MINUTE) %>Min.</p>
                        <% break;
                        case 2:
                            case 4: %>
                            <p>Objet acheté à <%= commande.getPseudoVendeur() %> le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %> à <%= cal.get(Calendar.HOUR_OF_DAY) %>h-<%= cal.get(Calendar.MINUTE) %>Min.</p>
                            <% break;
                            } %>
                            <h2>Detail de la commande</h2>
         <table border="0" cellspacing="5">
             <tbody>
                <tr>
                    <td></td>
                    <td align="right"><%= commande.getMontant() %>&nbsp;&euro;</td>
                    <td>Montant</td>
                </tr>
                <tr>
                    <td align="left">+</td>
                    <td align="right"><%= commande.getFraisExpe() %>&nbsp;&euro;</td>
                    <td>Frais de port</td>
                </tr>
                <tr>
                    <td></td>
                    <td align="rignt"><%= commande.getMontantTotal() %>&nbsp;&euro;</td>
                    <td>Total</td>
                </tr>
            </tbody>
        </table><% switch(commande.getModePaiement()) {
            case 1: %>
            <div class="info">Paiement PAYPAL</div>
            <%
            break;
            case 2: %>
            <div class="info">Paiement par chèque</div><%
            break;
            case 3: %>
            <div class="info">Paiement en espèce</div>
            <% break;
            case 4: %>
            <div class="info">Paiement en timbres</div><%
            break; } %>
            <br/>
            <%
            switch(commande.getType()) {
                case 1:
                    case 2:
                        %>
                        <div>Objet aux enchères</div>
                        <br/><%
                        break;
                        case 3:
                            case 4: %>
                            <div>Achat imméfiat</div>
                            <br/><%
                            break;
                            }
             switch(commande.getType()) {
                            case 1:
                                case 3: %>
                                    <div>Contactez l'acheteur <%= commande.getPseudoAcheteur() %> en <a href="contact-<%= commande.getIdAcheteur() %>.html" rel="nofollow">Cliquant ICI</a></div>
                                <% break;
                                case 2:
                                    case 4: %>
                                <div>Contacter le vendeur <%= commande.getPseudoVendeur() %> en <a href="contact-<%= commande.getIdVendeur() %>.html" rel="nofollow">Cliquant ICI</a></div>
                                    <% break; } } %>
        </div>
    </body>
</html>
