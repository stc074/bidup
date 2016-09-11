<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.DepotForm1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
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
        //out.println("cat :"+session.getAttribute("idCategorie")+" - SouSCat : "+session.getAttribute("idSousCategorie"));
            DepotForm1 formulaire=(DepotForm1)(request.getAttribute("formulaire"));
            if(formulaire.getTest()==0) { %>
            <p>1-Choisissez ci-dessous la categorie du bien que vous mettez aux encheres.</p><%
            if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
                <form action="./DeposerAnnonce1" method="POST">
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
        Objet.closeConnection(); %>
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
                        Objet.closeConnection();
                     } %>
                    </div>
                    <div class="clear_both"></div>
                    <br/>
                    <input type="submit" value="Valider" name="submit" />
                </form><% } else { formulaire.blank(); %>
                <script type="text/javascript">
                    window.location.href="./DeposerAnnonce2";
                </script><% } } %>
        </div>
    </body>
</html>
