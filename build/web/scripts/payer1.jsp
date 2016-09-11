<%@page import="classes.EnchForm1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Votre site d'enchères</title>
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
    <body><% int p=10; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <% if(membre.getIdMembre()==0||(request.getAttribute("info")!=null&&request.getAttribute("info").toString().equals("1"))) { %>
            <div class="info">Vous devez être connecter pour pouvoir accéder à cette page.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><% } else {
        EnchForm1 formulaire=(EnchForm1)(request.getAttribute("formulaire")); %>
        <h1><%= formulaire.getTitre() %></h1>
        <div>Objet mis en ligne par <%= formulaire.getPseudoVendeur() %>.</div>
        <br/><% if(formulaire.getTest()==0) { %>
        <table border="0" cellspacing="5">
             <tbody>
                <tr>
                    <td></td>
                    <td align="right"><%= formulaire.getMontant() %>&nbsp;&euro;</td>
                    <td>Montant</td>
                </tr>
                <tr>
                    <td align="left">+</td>
                    <td align="right"><%= formulaire.getFraisExpe() %>&nbsp;&euro;</td>
                    <td>Frais de port</td>
                </tr>
                <tr>
                    <td></td>
                    <td align="rignt"><%= formulaire.getMontantTotal() %>&nbsp;&euro;</td>
                    <td>Total</td>
                </tr>
            </tbody>
        </table>
        <div class="info">Vous avez remporté cet objet, pour le payer veuillez choisir le mode de paiement ci-dessous :</div>
        <% if(formulaire.getErrorMsg().length()>0) { %>
        <div class="erreur">
            <div>Erreur(s) :</div>
            <br/>
            <div><%= formulaire.getErrorMsg() %></div>
        </div><% } %>
        <form action="./Payer1" method="POST">
            <input type="hidden" name="id" value="<%= formulaire.getIdEnchereFinie() %>" />
        <% if(formulaire.getPaypal().length()>0) { %>
        <div>
            <input type="radio" name="modePaiement" value="1" id="mp1" />
            <label for="mp1">Paypal</label>
        </div><% } if(formulaire.getTypePaiementCheque()==1) { %>
        <div>
            <input type="radio" name="modePaiement" value="2" id="mp2" />
            <label for="mp2">Chèque</label>
        </div><% } if(formulaire.getTypePaiementEspece()==1) { %>
        <div>
            <input type="radio" name="modePaiement" value="3" id="mp3" />
            <label for="mp3">Espèce</label>
        </div><% } if(formulaire.getTypePaiementTimbre()==1) { %>
        <div>
            <input type="radio" name="modePaiement" value="4" id="mp4" />
            <label for="mp4">Timbres</label>
        </div><% } %>
        <br/>
        <input type="submit" value="Valider" name="kermit" />
         </form>
       <% } else if(formulaire.getTest()==2) {
       formulaire.blank();
        %>
        <script type="text/javascript">
            window.location.href="./Payer2";
        </script><% } } %>
        </div>
    </body>
</html>
