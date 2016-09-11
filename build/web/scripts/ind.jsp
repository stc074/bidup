<%@page import="java.util.Calendar"%>
<%@page import="classes.Img"%>
<%@page import="java.io.File"%>
<%@page import="classes.Datas"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.RechForm1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP, site d'enchères gratuites !</title>
<meta name="generator" content="Bluefish pour le squelette, Netbeans pour les organes vitaux"/>
<meta name="author" content=""/>
<meta name="date" content=""/>
<meta name="copyright" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="BidUP, votre site d'enchères gratuites !."/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8"/>
<meta http-equiv="content-style-type" content="text/css"/>
<link href="CSS/style.css" type="text/css" rel="stylesheet" />
<meta name="google-site-verification" content="gNxcqvagFjF5ovXZ0OpdALJwl6WvfDHmc_RgXH9G1bY" />
</head>
    <body><% int p=0; %>
        <%@include file="./haut.jsp" %>
         <div class="contenu">
            <% RechForm1 formulaire=(RechForm1)(request.getAttribute("formulaire")); %>
            <h1>Bidup, votre site d'encheres gratuites</h1>
            <p>Bienvenue sur Bidup, le site d'enchere qui ne prend aucune commission !</p>
            <p>Faites de bonnes affaires avec bidUp !</p>
            <h2>Biens mis aux enchères :</h2>
            <h3>Affinez votre recherche gràce au formulaire</h3>
            <div class="formGauche">
                <form action="./" method="POST">
                    <div>Je recherche :</div>
                    <input type="text" name="recherche" value="<%= formulaire.getRecherche() %>" size="50" maxlength="100" />
                    <div>Dans la catégorie :</div>
                    <div>
                        <select name="idCategorie" onchange="window.location.href='bidup7-'+this.value+'.html';">
                            <option value="0"<% if(formulaire.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Toutes</option><%
                    Objet.getConnection();
                    String query="SELECT id,categorie FROM table_categories ORDER BY categorie ASC";
                    Statement state=Objet.getConn().createStatement();
                    ResultSet result=state.executeQuery(query);
                    while(result.next()) {
                        int idCategorie=result.getInt("id");
                        String categorie=result.getString("categorie");
                        %>
                        <option value="<%= idCategorie %>"<% if(formulaire.getIdCategorie()==idCategorie) out.print(" selected=\"selected\""); %>><%= categorie %></option>
                        <% }
                        result.close();
                        state.close();
                        %>
                        </select>
                    </div>
                        <div>Sous-catégorie</div>
                        <select name="idSousCategorie" onchange="window.location.href='bidup8-'+this.value+'.html';">
                            <option value="0"<% if(formulaire.getIdSousCategorie()==0) out.print(" selected=\"selected\""); %>>Toutes</option><%
                            if(formulaire.getIdCategorie()!=0) {
                                query="SELECT id,sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie ASC";
                                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                                prepare.setInt(1, formulaire.getIdCategorie());
                                result=prepare.executeQuery();
                                while(result.next()) {
                                    int idSousCategorie=result.getInt("id");
                                    String sousCategorie=result.getString("sous_categorie");
                                    %>
                                    <option value="<%= idSousCategorie %>"<% if(formulaire.getIdSousCategorie()==idSousCategorie) out.print(" selected=\"selected\""); %>><%= sousCategorie %></option>
                                    <% } result.close();
                                    prepare.close();
                                    Objet.closeConnection();
                                    } %>
                        </select>
                    <div>Recherche des annonces, en premier :</div>
                    <div>
                        <select name="type1" onchange="window.location.href='./bidup1-'+this.value+'.html';">
                            <option value="0"<% if(formulaire.getType1()==0) out.print(" selected=\"selected\""); %>>Toutes</option>
                            <option value="1"<% if(formulaire.getType1()==1) out.print(" selected=\"selected\""); %>>Bientot finies</option>
                            <option value="2"<% if(formulaire.getType1()==2) out.print(" selected=\"selected\""); %>>Nouvelles annonces</option>
                     <option value="4"<% if(formulaire.getType1()==4) out.print(" selected=\"selected\""); %>>Prix : le moins cher</option>
                    <option value="5"<% if(formulaire.getType1()==5) out.print(" selected=\"selected\""); %>>Prix+Expedition : Le moins cher</option>
                    <option value="6"<% if(formulaire.getType1()==6) out.print(" selected=\"selected\""); %>>Achat immediat</option>
                    <option value="7"<% if(formulaire.getType1()==7) out.print(" selected=\"selected\""); %>>Achat immediat: du moins au plus cher</option>
                        </select>
                    </div>
                    <div>Type d'expedition :</div>
                    <select name="type2" onchange="window.location.href='./bidup2-'+this.value+'.html';">
                        <option value="0"<% if(formulaire.getType2()==0) out.print(" selected=\"selected\""); %>>Tous types</option>
                        <option value="1"<% if(formulaire.getType2()==1) out.print(" selected=\"selected\""); %>>Remise en main propre</option>
                        <option value="2"<% if(formulaire.getType2()==2) out.print(" selected=\"selected\""); %>>Envoyé par la poste</option>
                    </select>
                    <div>Zone géographique :</div>
                    <div>Région:</div>
                <div>
                    <select name="idRegion" onchange="window.location.href='bidup4-'+this.value+'.html';">
                        <option value="0"<% if(formulaire.getIdRegion().equals("0")) out.print(" selected=\"selected\""); %>>Choisissez</option>
                        <% Objet.getConnection();
                        query="SELECT id_region,region FROM table_regions ORDER BY region ASC";
                        state=Objet.getConn().createStatement();
                        result=state.executeQuery(query);
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
                    <select name="idDepartement" onchange="window.location.href='bidup5-'+this.value+'.html';">
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
                            <select name="idCommune" onchange="window.location.href='bidup6-'+this.value+'.html';">
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
                            <br/>
                            <div>
                                <input type="submit" value="Valider" name="kermit0" />
                                <input type="submit" value="Vider le formulaire" name="kermit" />
                            </div>
                </form>
            </div>
                            <div class="listAnnonces">
                            <div class="rechercheResult">
                                <table border="0" cellspacing="2" cellpadding="2" width="100%" class="liste">
                                <% Objet.getConnection();
                                query="SELECT t1.id,t1.titre,t1.prix_immediat,type_envoi,frais_expe,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp_fin,t2.prix_actuel,t2.nombre_encheres FROM table_annonces AS t1,table_encheres AS t2"+formulaire.getRechercheQuery();
                                query+=" LIMIT "+(formulaire.getPage()*Datas.NBANNONCES)+","+Datas.NBANNONCES;
                                //out.println(query);
                                state=Objet.getConn().createStatement();
                                result=state.executeQuery(query);
                                int i=0;
                                while(result.next()) {
                                    if(i==0) { %>
                                    <thead>
                                        <tr>
                                            <th width="<%= Datas.MINI1LARG+20 %>">Photo</th>
                                            <th></th>
                                            <th>Prix actuel/frais de port</th>
                                            <th>Fin de l'enchere</th>
                                            <th>Nombre d'enchères</th>
                                        </tr>
                                    </thead>
                                            <tbody><% } 
                                   i++;
                                    long idAnnonce=result.getLong("id");
                                    String titre=result.getString("titre");
                                    double prixImmediat=result.getDouble("prix_immediat");
                                    int typeEnvoi=result.getInt("type_envoi");
                                    double fraisExpe=result.getDouble("frais_expe");
                                    String[] extensions=new String[5];
                                    extensions[0]=result.getString("extension1");
                                    extensions[1]=result.getString("extension2");
                                    extensions[2]=result.getString("extension3");
                                    extensions[3]=result.getString("extension4");
                                    extensions[4]=result.getString("extension5");
                                    long timestampFin=result.getLong("timestamp_fin");
                                    double prixActuel=result.getDouble("prix_actuel");
                                    int nombreEncheres=result.getInt("nombre_encheres");
                                    String uri="enchere-"+idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
                                %>
                                <tr>
                                    <td>
                                        <a href="<%= uri %>">
                                        <% if(extensions[0].length()==0&&extensions[1].length()==0&&extensions[2].length()==0&&extensions[3].length()==0&&extensions[4].length()==0) { %>
                                        <img src="./GFXs/photos.png" width="100" height="100" alt="photos"/><% } else {
                                        String extension="";
                                        int index=0;
                                        while(extension.length()==0) {
                                            index=(int)(4.0*Math.random());
                                            extension=extensions[index];
                                            }
                                        index++;
                                        String filenameMini1=Datas.DIR+"/photos/mini1_"+idAnnonce+"_"+index+extension;
                                        File fileMini1=new File(filenameMini1);
                                        if(fileMini1.exists()) {
                                        Img img=new Img();
                                        img.getSize(fileMini1);
                                        int largeur=img.getWidth();
                                        int hauteur=img.getHeight();
                                        %>
                                        <img src="DisplayPhoto?mini=1&index=<%= index%>&id=<%= idAnnonce%>" width="<%= largeur%>" height="<%= hauteur%>" alt="image"/>
                                        <% } else { %>
                                        <img src="./GFXs/photos.png" width="100" height="100" alt="photos"/><% } } %>
                                        </a>
                                    </td>
                                        <td align="left"><div><a href="<%= uri %>"><%= titre %></a></div></td>
                                        <td align="center">
                                            <div><%= prixActuel %>&nbsp;&euro;/<% if(typeEnvoi==1) out.print("remise en main propre"); else out.print(fraisExpe+" &euro;"); %></div>
                                            <% if(prixImmediat!=0) { %>
                                            <div>Achat immédiat à <%= prixImmediat %>&nbsp;&euro;</div><% } %>
                                        </td>
                                        <td align="center">
                                            <% Calendar cal=Calendar.getInstance();
                                            long timestampActuel=cal.getTimeInMillis();
                                            long ecart=timestampFin-timestampActuel;
                                            int nbJours=(int)(ecart/(24*60*60*1000));
                                            long ecart2=ecart-(nbJours*1000*60*60*24);
                                            int nbHeures=(int)(ecart2/(1000*60*60));
                                            long ecart3=ecart2-(nbHeures*1000*60*60);
                                            int nbMinutes=(int)(ecart3/(1000*60));
                                            cal.setTimeInMillis(timestampFin);
                                            %>
                                            <div><% if(nbJours>0) out.print(nbJours+"J-"); %><% if(!(nbHeures==0&&nbMinutes==0)) out.print(nbHeures+"H-"); %><%= nbMinutes %>Min</div>
                                        </td>
                                        <td align="center">
                                            <div><%= nombreEncheres %></div>
                                        </td>
                                </tr>
                                        <tr><td colspan="5"><div class="line"></div></td></tr>
                                <% }
                                result.close();
                                state.close();
                                int nbAnnoncesPage=i;
                                if(i==0) { %>
                                <div class="info">Aucun résultat pour votre recherche.</div><% } else { %>
                                     </tbody><% } %>
                                </table>
                              </div><%
                            query="SELECT COUNT(t1.id) AS nbAnnonces FROM table_annonces AS t1,table_encheres AS t2"+formulaire.getRechercheQuery();
                            state=Objet.getConn().createStatement();
                            result=state.executeQuery(query);
                            result.next();
                            int nbAnnonces=result.getInt("nbAnnonces");
                            result.close();
                            state.close();
                            Objet.closeConnection();
                            if(nbAnnoncesPage>0&&nbAnnonces>0) {
                            int nbPages=(int)(Math.ceil(((double)nbAnnonces)/((double)Datas.NBANNONCES)));
                            out.println("Annonces : "+nbAnnonces+" - Pages : "+nbPages);
                            int j;
                            if((formulaire.getPage()-3)<0)
                                i=0;
                            else
                                i=formulaire.getPage()-3;
                            if(formulaire.getPage()+3>nbPages)
                                j=nbPages;
                            else
                                j=formulaire.getPage()+3;
                            %>
                            <div align="center" class="pagination">
                                <span><a href="./bidup10-0.html">1° page</a></span>
                                <%
                                if(formulaire.getPage()>0) { %>
                                <span><a href="./bidup10-<%= formulaire.getPage()-1 %>.html">Prec</a></span><% } %>
                                <%
                                int k=0;
                                for(k=i;k<j;k++) { %>
                                <span>[<a href="bidup10-<%= k %>.html"<% if(formulaire.getPage()==k) out.print(" class=\"actuel\""); %>><%= k+1 %></a>]</span>
                                <% }
                                if(formulaire.getPage()<(nbPages-1)) { %>
                                <span><a href="bidup10-<%= formulaire.getPage()+1 %>.html">Suiv</a></span><% } %>
                                <span><a href="bidup10-<%= k-1 %>.html">Dernière page</a></span>
                            </div><% } %>
                            <div class="clear_both"></div>
                            </div>
        </div>
                            <%@include file="./footer.jsp" %>
    </body>
</html>
