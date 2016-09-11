<%@page import="classes.Datas"%>
<%@page import="classes.EnchForm2"%>
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
            <% if(membre.getIdMembre()==0||(request.getAttribute("info")!=null)&&request.getAttribute("info").toString().equals("1")) { %>
             <div class="info">Vous devez être connecter pour pouvoir accéder à cette page.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><% } else {
            EnchForm2 formulaire=(EnchForm2)(request.getAttribute("formulaire"));
            out.println(formulaire.getErrorMsg());
            if(formulaire.getTest()==0) { %>
            <h1><%= formulaire.getTitre() %></h1>
            <div class="info">Bien mis aux enchères par <%= formulaire.getPseudo() %>.</div>
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
            <% switch(formulaire.getModePaiement()) {
                case 1: %>
                <div class="info">Afin de régler via Paypal, cliquez sur le bouton ci-dessous:</div>
                <br/>
                <form action="https://www.paypal.com/cgi-bin/webscr" method="post">
<input type='hidden' value="<%= formulaire.getMontantTotal() %>" name="amount" />
<input name="currency_code" type="hidden" value="EUR" />
<input name="shipping" type="hidden" value="0.00" />
<input name="tax" type="hidden" value="0.00" />
<input name="return" type="hidden" value="<%= Datas.URLROOT %>/payer3-1.html" />
<input name="cancel_return" type="hidden" value="<%= Datas.URLROOT %>/payer3-2.html" />
<input name="notify_url" type="hidden" value="<%= Datas.URLROOT %>/pp/PP" />
<input name="cmd" type="hidden" value="_xclick" />
<input name="business" type="hidden" value="<%= formulaire.getPaypal() %>" />
<input name="item_name" type="hidden" value="<%= formulaire.getTitre() %>" />
<input name="no_note" type="hidden" value="1" />
<input name="lc" type="hidden" value="FR" />
<input name="bn" type="hidden" value="PP-BuyNowBF" />
<input name="custom" type="hidden" value="<%= formulaire.getIdEnchereFinie() %>" />
                <input alt="Effectuez vos paiements via PayPal : une solution rapide, gratuite et sécurisée" name="submit" src="https://www.paypal.com/fr_FR/FR/i/btn/btn_buynow_LG.gif" type="image" /><img src="https://www.paypal.com/fr_FR/i/scr/pixel.gif" border="0" alt="" width="1" height="1" />
                </form>
                <% break;
                case 2: %>
                <div class="info">Paiement par chèques</div>
                <br/><% if(formulaire.getTypeEnvoi()==1) { %>
                <div>Ce bien est remis en main propre.</div>
                <br/>
                <div class="info">Paiement par chèque d'un montant de <%= formulaire.getMontantTotal() %> &euro;</div>
                <% } else if(formulaire.getTypeEnvoi()==2) { %>
                <p>Envoyez un chèque d'un montant de <%= formulaire.getMontantTotal() %> &euro; à l'adresse ci-dessous :</p>
                <div><%= formulaire.getPrenom() %> <%= formulaire.getNom() %></div>
                <div><%= formulaire.getAdresse() %></div>
                <div><%= formulaire.getCodePostal() %>&nbsp;<%= formulaire.getVille() %></div><% } %>
                <br/>
                <div>Cliquez sur le bouton ci dessous pour confirmer votre paiement :</div>
                <form action="./Payer2" method="POST">
                    <input type="hidden" name="payer" value="1" />
                    <input type="submit" value="Je confirme mon paiement" name="kermit" />
                </form><%
                break;
                case 3: %>
                <div class="info">Paiement en espèce</div>
                <br/><% if(formulaire.getTypeEnvoi()==1) { %>
                <div>Bien remis en main propre</div>
                <br/>
                <div>Paiement en espèce d'un montant de <%= formulaire.getMontantTotal() %> &euro; .</div>
                <% } else if(formulaire.getTypeEnvoi()==2) { %>
                <div>Paiment en espèce d'un montant de <%= formulaire.getMontantTotal() %> &euro;.</div>
                <br/>
                <div>Adresse de facturation :</div>
                <br/>
                <div><%= formulaire.getPrenom() %> <%= formulaire.getNom() %></div>
                <div><%= formulaire.getAdresse() %></div>
                <div><%= formulaire.getCodePostal() %>&nbsp;<%= formulaire.getVille() %></div>
                <br/>
                <div class="info">Je déconseille l'envoi d'argent liquide par la poste, contactez le vendeur pour vous arranger.</div>
                <% } %>
                <br/>
                <div>Confirmer votre paiement en cliquant sur le bouton ci-dessous :</div>
                <form action="./Payer2" method="POST">
                    <input type="hidden" name="payer" value="1" />
                    <input type="submit" value="Je confirme mon paiement" name="kermit" />
                </form><%
                break;
                case 4: %>
                <div class="info">Paiment en timbres.</div>
                <br/>
                <% if(formulaire.getTypeEnvoi()==1) { %>
                <div>Objet remis en main propre.</div>
                <br/>
                <div>Paiement en timbres d'une valeur de <%= formulaire.getMontantTotal() %> &euro;</div>
                <% } else if(formulaire.getTypeEnvoi()==2) { %>
                <div>Envoyez <%= formulaire.getMontantTotal() %> &euro; en timbres à l'adresse suivante :</div>
                <br/>
                <div><%= formulaire.getPrenom() %> <%= formulaire.getNom() %></div>
                <div><%= formulaire.getAdresse() %></div>
                <div><%= formulaire.getCodePostal() %>&nbsp;<%= formulaire.getVille() %></div>
                <% } %>
                <br/>
                <div class="info">Veuillez confirmer votre paiement en cliquant ci-dessous :</div>
                <br/>
                <form action="./Payer2" method="POST">
                    <input type="hidden" name="payer" value="1" />
                    <input type="submit" value="Je confirme mon paiement" name="kermit" />
                </form><%
                break;
                } %>
            <% } else if(formulaire.getTest()==2) { %>
            <div class="info">Paiement pris en compte</div><% } } %>
        </div>
    </body>
</html>
