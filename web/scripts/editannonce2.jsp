<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.EditForm2"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Annonce</title>
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
<script type="text/javascript" src="./scripts/scripts.js"></script>
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
            <% if(request.getAttribute("info")!=null) {
                int info=Integer.parseInt(request.getAttribute("info").toString());
                switch(info) {
                    case 1: %>
            <div class="info">Vous devez être connecter pour pouvoir éditer une annonce.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break;
            case 2: %>
            <div class="info">Annonce inconnue !</div><%
            break;
            } } else if(request.getAttribute("formulaire")!=null) {
                EditForm2 formulaire=(EditForm2)(request.getAttribute("formulaire"));
                //out.println(formulaire.getErrorMsg());
                if(formulaire.getTest()==0) {
                %>
                <h1>Éditer mon annonce "<%= formulaire.getTitreDefaut() %>"</h1>
                <p>Pour remettre aux enchères votre bien, utiliser le formulaire ci-dessous avec une nouvelle date de fin.</p>
                <% if(formulaire.getErrorMsg().length()>0) { %>
                <div id="form">
                    <div class="erreur">
                        <div>Erreur(s) :</div>
                        <br/>
                        <div><%= formulaire.getErrorMsg() %></div>
                    </div><% } %>
                <form action="./EditAnnonce2#form" method="POST">
                                     <input type="hidden" name="id" value="<%= formulaire.getIdAnnonce() %>" />
                                <h3>Catégorie</h3>
            <div class="categoriesGauche">
                    <select name="idCategorie" size="20" onchange="changeCategorie1(this.value);" class="selectCategories"><%
             Objet.getConnection();
            String query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
            Statement state=Objet.getConn().createStatement();
            ResultSet result=state.executeQuery(query);
            while(result.next()) {
                int idCategorie=result.getInt("id");
                String categorie=result.getString("categorie");
                %>
                        <option value="<%= idCategorie %>"<% if(idCategorie==formulaire.getIdCategorie()) out.print(" selected=\"selected\""); %>><%= categorie %></option><% }
                        result.close();
                state.close();
        %>
                    </select>
            </div>
        <div class="categoriesDroite" id="categoriesDroite"><%
        if(formulaire.getIdCategorie()!=0) { %>
        <select name="idSousCategorie" size="20" class="selectCategories"><%
            Objet.getConnection();
            query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setInt(1, formulaire.getIdCategorie());
            result=prepare.executeQuery();
            while(result.next()) {
                int idSousCategorie=result.getInt("id");
                String sousCategorie=result.getString("sous_categorie");
                %>
                <option value="<%= idSousCategorie %>"<% if(idSousCategorie==formulaire.getIdSousCategorie()) out.print(" selected=\"selected\""); %>><%= sousCategorie %></option>
                <% } %>
                        </select><%
                        result.close();
                        prepare.close();
                     } %>
                    </div>
                    <div class="clear_both"></div>
                    <h3>Titre et description</h3>
                <div>Titre de votre annonce :</div>
                <input type="text" name="titre" value="<%= formulaire.getTitre() %>" size="30" maxlength="40" />
                <div>Description de votre annonce :</div>
                <textarea name="description" rows="8" cols="80"><%= formulaire.getDescription() %></textarea>
                <h3>Prix et date</h3>
                <div>Prix de départ de votre enchère :</div>
                <input type="text" name="prixDepart" value="<%= formulaire.getPrixDepart() %>" size="6" maxlength="10" readonly="readonly" />
                <span>&euro;</span>
                <div>Pas de l'enchère (montant ajouté à chaque enchère) :</div>
                <input type="text" name="pasEnchere" value="<%= formulaire.getPasEnchere() %>" size="6" maxlength="10" />
                <span>&euro;</span>
                <div>Prix de réserve (prix minimum pour la vente, laisser à zéro si vous n'en voulez pas) :</div>
                <input type="text" name="prixReserve" value="<%= formulaire.getPrixReserve() %>" size="6" maxlength="10" />
                <div>Achat immédiat (laissez à zéro si vous n'en voulez pas ou au besoin mettez le prix auquel vous consentez à vendre immédiatement votre bien) :</div>
                <input type="text" name="prixImmediat" value="<%= formulaire.getPrixImmediat() %>" size="6" maxlength="10" />
                <span>&euro;</span>
                <div>Date de la fin de l'enchere (jj/mm/aaaa|hh|mm) :[note: la nouvelle date devra être superieure à l'ancienne]</div>
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
                <h3>Livraison</h3>
                <div>Région :</div>
                <div>
                    <select name="idRegion" onchange="changeRegion1(this.value);">
                        <option value="0"<% if(formulaire.getIdRegion().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                        <%
                        query="SELECT id_region,region FROM table_regions ORDER BY region ASC";
                        state=Objet.getConn().createStatement();
                        result=state.executeQuery(query);
                        while(result.next()) {
                            String idRegion=result.getString("id_region");
                            String region=result.getString("region");
                            %>
                            <option value="<%= idRegion %>"<% if(formulaire.getIdRegion().equals(idRegion)) out.print(" selected=\"selected\""); %>><%= region %></option><% } result.close();
                            state.close();
                             %>
                    </select>
                </div>
                    <div id="innerDepartements">
                        <div>Departement :</div>
                    <select name="idDepartement" onchange="changeDepartement1(this.value);">
                            <option value="0"<% if(formulaire.getIdDepartement().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                    <% if(!formulaire.getIdRegion().equals("0")) {
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
                         } %>
                        </select>
                        <div id="innerCommunes">
                            <div>Commune :</div>
                            <select name="idCommune">
                                <option value="0"<% if(formulaire.getIdCommune()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                                <%
                        if(!formulaire.getIdDepartement().equals("0")) {
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
                            } %>
                            </select>
                            </div>
                            </div>
                            <h3>Expedition</h3>
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
                            <h3>Paiement</h3>
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
                            </div><%
                            Objet.closeConnection();
                            %>
                <br/>
                <br/>
                <input type="submit" value="Modifier" name="kermit" />
           </form>
                </div>
                <% } else { formulaire.blank();
                %>
                <script type="text/javascript">
                    window.location.href="./EditPhotos"
                </script><% }} %>
        </div>
    </body>
</html>
