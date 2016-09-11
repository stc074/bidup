<%@page import="classes.DepotForm3"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Mettre aux enchères</title>
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
<link href="./datas/jquery/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="./datas/jquery/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="./datas/jquery/development-bundle/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="./datas/jquery/development-bundle/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="./datas/jquery/development-bundle/ui/i18n/jquery.ui.datepicker-fr.js"></script>
<script type="text/javascript">
var dp_config =  { dateFormat: 'dd/mm/yy',
showAnim: 'fadeIn'};
function initialize() {
 $('#dateFin').datepicker(dp_config);
 }
 $(document).ready( initialize );
</script>
</head>
    <body><% int p=5; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Déposer une annonce</h1><% if(request.getAttribute("info")!=null) {
            int info=Integer.parseInt(request.getAttribute("info").toString());
        switch(info) {
        case 1: %>
            <div class="info">Vous devez être connecter pour pouvoir déposer une annonce.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
         break; } } else if(request.getAttribute("formulaire")!=null) {
        DepotForm3 formulaire=(DepotForm3)(request.getAttribute("formulaire"));
        if(formulaire.getTest()==0) { %>
            <p>3-Paramétrez ci-dessous les options de votre annonce.</p><% if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
            <form action="./DeposerAnnonce3" method="POST">
                <div>Prix de départ de votre enchère :</div>
                <input type="text" name="prixDepart" value="<%= formulaire.getPrixDepart() %>" size="6" maxlength="10" />
                <span>&euro;</span>
                <div>Pas de l'enchère (montant ajouté à chaque enchère) :</div>
                <input type="text" name="pasEnchere" value="<%= formulaire.getPasEnchere() %>" size="6" maxlength="10" />
                <span>&euro;</span>
                <div>Prix de réserve (prix minimum pour la vente, laisser à zéro si vous n'en voulez pas) :</div>
                <input type="text" name="prixReserve" value="<%= formulaire.getPrixReserve() %>" size="6" maxlength="10" />
                <div>Achat immédiat (laissez à zéro si vous n'en voulez pas ou au besoin mettez le prix auquel vous consentez à vendre immédiatement votre bien) :</div>
                <input type="text" name="prixImmediat" value="<%= formulaire.getPrixImmediat() %>" size="6" maxlength="10" />
                <span>&euro;</span>
                <div>Date de la fin de l'enchere (jj/mm/aaaa|hh|mm) :</div>
                <input type="text" name="dateFin" id="dateFin" value="<%= formulaire.getDateFin() %>" size="10" maxlength="10" />
                <span>à</span>
                <input type="text" name="heureFin" value="<%= formulaire.getHeureFin() %>" size="2" maxlength="2" />
                <span>Heure</span>
                <input type="text" name="minuteFin" value="<%= formulaire.getMinuteFin() %>" size="2" maxlength="2" />
                <span>Minutes</span>
                <br/>
                <div>Remise en vente :</div>
                <div>
                <input type="checkbox" name="remiseVenteInvendu" value="1" id="rem0"<% if(formulaire.getRemiseVenteInvendu()==1) out.print(" checked=\"checked\""); %> />
                <label for="rem0">Remettre en vente si l'objet ne s'est pas vendu.</label>
                </div>
                <div>
                <input type="checkbox" name="remiseVenteVendu" value="1" id="rem1"<% if(formulaire.getRemiseVenteVendu()==1) out.print(" checked=\"checked\""); %> />
                <label for="rem1">Remettre en vente même si l'objet est vendu</label>
                </div>
                <br/>
                <br/>
                <input type="submit" value="Valider" name="kermit" />
             </form>
            <br/>
            <div><a href="./DeposerAnnonce2" rel="nofollow">Étape précédente</a></div><% } else { formulaire.blank(); %>
            <script type="text/javascript">
                window.location.href="./DeposerAnnonce4";
            </script><% } } %>
        </div>
    </body>
</html>
