<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.SousCatForm1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="classes.Objet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>ADMINISTRATION</title>
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
<link href="./../CSS/style.css" type="text/css" rel="stylesheet" />
</head>
    <body><% int p=0; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu"><% SousCatForm1 formulaire=(SousCatForm1)(request.getAttribute("formulaire")); %>
            <h1>Sous-Catégories</h1><% if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
            <form action="./SousCategories" method="POST">
                <div>Choisissez la catégorie:</div>
                <select name="idCategorie" onchange="window.location.href='./SousCategories?id='+this.value;">
                    <option value="0"<% if(formulaire.getIdCategorie()==0) out.print(" selected=\"selected\""); %>>Choisissez</option>
                    <% Objet.getConnection();
                    String query="SELECT id,categorie FROM table_categories ORDER BY categorie";
                    Statement state=Objet.getConn().createStatement();
                    ResultSet result=state.executeQuery(query);
                    while(result.next()) {
                        int idCategorie=result.getInt("id");
                        String categorie=result.getString("categorie");
                        %>
                        <option value="<%= idCategorie %>"<% if(formulaire.getIdCategorie()==idCategorie) out.print(" selected=\"selected\""); %>><%= categorie %></option><% } %>
                </select>
                <% if(formulaire.getIdCategorie()!=0) { %>
                <h2>Ajouter une sous catégorie à la catégorie <%= formulaire.getCategorie() %></h2>
                <% if(formulaire.getTest()==1) { %>
                <div class="info"><%= formulaire.getSousCategorie() %> ajoutée à <%= formulaire.getCategorie() %>.</div><% formulaire.blank(); } %>
                <div>Nom de la sous catégorie :</div>
                <input type="text" name="sousCategorie" value="" size="30" maxlength="100" />
                <br/>
                <input type="submit" value="Valider" name="submit" />
                <h2>Sous catégories de <%= formulaire.getCategorie() %> :</h2>
                <%
                query="SELECT sous_categorie FROM table_sous_categories WHERE id_categorie=? ORDER BY sous_categorie";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, formulaire.getIdCategorie());
                result=prepare.executeQuery();
                int i=0;
                while(result.next()) {
                i++;
                String sousCategorie=result.getString("sous_categorie");
                %>
                <div><%= sousCategorie %></div>
                <br/><% } if(i==0) { %>
                <div class="info">Aucune sous catégorie enregistrée</div><% } } %>
            </form>
        </div>
    </body>
</html>
