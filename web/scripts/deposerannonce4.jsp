<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.DepotForm4"%>
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
<script type="text/javascript" src="./scripts/scripts.js"></script>
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
            DepotForm4 formulaire=(DepotForm4)(request.getAttribute("formulaire"));
            if(formulaire.getTest()==0) { %>
            <p>4- Livraison et paiement de votre bien.</p><% if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
            <form action="./DeposerAnnonce4" method="POST">
                <h2>Localisation de l'objet</h2>
                <div>Région :</div>
                <div>
                    <select name="idRegion" onchange="changeRegion1(this.value);">
                        <option value="0"<% if(formulaire.getIdRegion().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                        <% Objet.getConnection();
                        String query="SELECT id_region,region FROM table_regions ORDER BY region ASC";
                        Statement state=Objet.getConn().createStatement();
                        ResultSet result=state.executeQuery(query);
                        while(result.next()) {
                            String idRegion=result.getString("id_region");
                            String region=result.getString("region");
                            %>
                            <option value="<%= idRegion %>"<% if(formulaire.getIdRegion().equals(idRegion)) out.print(" selected=\"selected\""); %>><%= region %></option><% } result.close();
                            state.close();
                            Objet.closeConnection();
                            %>
                    </select>
                </div>
                    <div id="innerDepartements">
                        <div>Departement :</div>
                    <select name="idDepartement" onchange="changeDepartement1(this.value);">
                            <option value="0"<% if(formulaire.getIdDepartement().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                    <% if(!formulaire.getIdRegion().equals("0")) {
                        Objet.getConnection();
                        query="SELECT id_departement,departement FROM table_departements WHERE id_region=? ORDER BY departement ASC";
                        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                        prepare.setString(1, formulaire.getIdRegion());
                        result=prepare.executeQuery();
                        while(result.next()) {
                            String idDepartement=result.getString("id_departement");
                            String departement=result.getString("departement");
                            %>
                            <option value="<%= idDepartement %>"<% if(formulaire.getIdDepartement().equals(idDepartement)) out.print(" selected=\"selected\""); %>><%= idDepartement %> - <%= departement %></option><% }
                        result.close();
                        prepare.close();
                        Objet.closeConnection();
                        } %>
                        </select>
                        <div id="innerCommunes">
                            <div>Commune :</div>
                            <select name="idCommune">
                                <option value="0"<% if(formulaire.getIdCommune()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                <%
                        if(!formulaire.getIdDepartement().equals("0")) {
                                Objet.getConnection();
                                query="SELECT id,commune,code_postal FROM table_communes WHERE id_departement=? ORDER BY commune ASC";
                                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                prepare.setString(1, formulaire.getIdDepartement());
                                result=prepare.executeQuery();
                                while(result.next()) {
                                    int idCommune=result.getInt("id");
                                    String commune=result.getString("commune");
                                    String codePostal=result.getString("code_postal");
                                    %>
                                    <option value="<%= idCommune %>"<% if(formulaire.getIdCommune()==idCommune) out.print(" selected=\"selected\""); %>><%= codePostal %>&rarr;<%= commune %></option><% }
                            result.close();
                            prepare.close();
                            Objet.closeConnection();
                            } %>
                            </select>
                            </div>
                            </div>
                            <h2>Expedition</h2>
                            <div>
                                <input type="radio" name="typeEnvoi" value="1" id="type1"<% if(formulaire.getTypeEnvoi()==1) out.print(" checked=\"checked\""); %> onclick="document.getElementById('expe').style['display']='none';" />
                                <label for="type1">Remise en main propre</label>
                            </div>
                            <div>
                                <input type="radio" name="typeEnvoi" value="2" id="type2"<% if(formulaire.getTypeEnvoi()==2) out.print(" checked=\"checked\""); %> onclick="document.getElementById('expe').style['display']='block';" />
                                <label for="type2">Expedition par la poste</label>
                            </div>
                            <div id="expe"<% if(formulaire.getTypeEnvoi()!=2) out.print(" style=\"display: none;\""); %>>
                                <div>Frais d'expedition</div>
                                <input type="text" name="fraisExpe" value="<%= formulaire.getFraisExpe() %>" size="6" maxlength="10" />
                                <span>&euro;</span>
                                <div>Note concernant l'expédition</div>
                                <textarea name="noteExpe" rows="4" cols="50"><%= formulaire.getNoteExpe() %></textarea>
                            </div>
                            <h2>Paiement</h2>
                            <div>Si vous acceptez le paiement via PAYPAL, saisissez ci-dessous votre adresse PAYPAL :</div>
                            <input type="text" name="paypal" value="<%= formulaire.getPaypal() %>" size="30" maxlength="100" />
                            <div>
                                <input type="checkbox" name="typePaiementCheque" value="1" id="tpc"<% if(formulaire.getTypePaiementCheque()==1) out.print(" checked=\"checked\""); %> />
                                <label for="tpc">Paiement par chèque accepté</label>
                            </div>
                            <div>
                                <input type="checkbox" name="typePaiementEspece" value="1" id="tpe"<% if(formulaire.getTypePaiementEspece()==1) out.print(" checked=\"checked\""); %> />
                                <label for="tpe">Paiement en espèce accepté</label>
                            </div>
                            <div>
                                <input type="checkbox" name="typePaiementTimbre" value="1" id="tpt"<% if(formulaire.getTypePaiementTimbre()==1) out.print(" checked=\"checked\""); %> />
                                <label for="tpt">Paiement par timbres accepté</label>
                            </div>
                            <br/>
                            <input type="submit" value="Valider" name="kermit" />
            </form>
                            <br/>
                            <div><a href="./DeposerAnnonce3" rel="nofollow">Étape précédente</a></div>
            <% } else { formulaire.blank(request); %>
            <script type="text/javascript">
                window.location.href="./DeposerAnnoncePhotos";
            </script><% } } %>
              </div>
    </body>
</html>
