<%@page import="classes.Datas"%>
<%@page import="classes.AchImmForm2"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Achat immediat</title>
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
                    case 2: %>
                    <div class="info">Erreur : Annonce inconnue.</div><%
                break; } } else if(request.getAttribute("formulaire")!=null) {
                    AchImmForm2 formulaire=(AchImmForm2)(request.getAttribute("formulaire"));
                    //out.println(formulaire.getErrorMsg());
                    if(formulaire.getTest()==0) {
                    %>
                    <h1><%= formulaire.getTitre() %></h1>
                    <div class="info">Mis en vente par <%= formulaire.getPseudoVendeur() %>.</div>
            <br/>
            <div>Prix à l'achat de ce bien :</div>
            <table border="0" cellspacing="2">
                <tbody>
                    <tr>
                        <td></td>
                        <td align="right"><%= formulaire.getPrixImmediat() %> &euro;</td>
                        <td align="left">Prix du bien (achat immédiat).</td>
                    </tr>
                    <tr>
                        <td align="left">+</td>
                        <td align="right"><%= formulaire.getFraisExpe() %> &euro;</td>
                        <td align="left">Frais de port</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td align="right"><%= formulaire.getMontantTotal() %> &euro;</td>
                        <td align="left">Prix total</td>
                    </tr>
                </tbody>
            </table><%
                        switch(formulaire.getModePaiement()) {
                        case 1: %>
                        <div class="info">Pour régler via PAYPAL, veuillez cliquer sur le bouton ci-dessous :</div>
                        <br/>
                <form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post">
<input type='hidden' value="<%= formulaire.getMontantTotal() %>" name="amount" />
<input name="currency_code" type="hidden" value="EUR" />
<input name="shipping" type="hidden" value="0.00" />
<input name="tax" type="hidden" value="0.00" />
<input name="return" type="hidden" value="<%= Datas.URLROOT %>/payer3-1.html" />
<input name="cancel_return" type="hidden" value="<%= Datas.URLROOT %>/payer3-2.html" />
<input name="notify_url" type="hidden" value="<%= Datas.URLROOT %>/pp/PP2" />
<input name="cmd" type="hidden" value="_xclick" />
<input name="business" type="hidden" value="<%= formulaire.getPaypal() %>" />
<input name="item_name" type="hidden" value="<%= formulaire.getTitre() %>" />
<input name="no_note" type="hidden" value="1" />
<input name="lc" type="hidden" value="FR" />
<input name="bn" type="hidden" value="PP-BuyNowBF" />
<input name="custom" type="hidden" value="<%= formulaire.getIdAchatImmediat() %>" />
                <input alt="Effectuez vos paiements via PayPal : une solution rapide, gratuite et sécurisée" name="submit" src="https://www.paypal.com/fr_FR/FR/i/btn/btn_buynow_LG.gif" type="image" /><img src="https://www.paypal.com/fr_FR/i/scr/pixel.gif" border="0" alt="" width="1" height="1" />
                </form>
<%
                        break;
                        case 2: %>
                        <div class="info">Pour confirmer votre futur achat par chèque, cliquez ci-dessous :</div>
                        <br/><% if(formulaire.getErrorMsg().length()>0) { %>
                        <div class="erreur">
                            <div>Erreur(s) :</div>
                            <br/>
                            <div><%= formulaire.getErrorMsg() %></div>
                        </div><% } %>
                        <form action="./AchatImmediat2" method="POST">
                            <br/>
                            <input type="submit" value="Je confirme mon paiement par chèque" name="kermit" />
                        </form><%
                        break;
                        case 3: %>
                        <div class="info">Pour confirmer votre achat en espèce, cliquez ci-dessous :</div>
                        <br/><% if(formulaire.getErrorMsg().length()>0) { %>
                        <div class="erreur">
                            <div>Erreur(s) :</div>
                            <br/>
                            <div><%= formulaire.getErrorMsg() %></div>
                        </div><% } %>
                        <form action="./AchatImmediat2" method="POST">
                            <br/>
                            <input type="submit" value="Je confirme mon paiement an espèce" name="kermit" />
                        </form><%
                        break;
                        case 4: %>
                        <div class="info">Pour confirmer votre achat en timbres, cliquez ci-dessous :</div>
                        <br/><% if(formulaire.getErrorMsg().length()>0) { %>
                        <div class="erreur">
                            <div>Erreur(s) :</div>
                            <br/>
                            <div><%= formulaire.getErrorMsg() %></div>
                        </div><% } %>
                        <form action="./AchatImmediat2" method="POST">
                            <br/>
                            <input type="submit" value="Je confirme mon paiement en timbres" name="kermit" />
                        </form><%
                        break;
                        }
                        } else { %>
                        <div class="info">Votre demande à été enregistrée nous allons prévenir le vendeur de votre achat.</div>
                        <% } } %>
        </div>
    </body>
</html>
