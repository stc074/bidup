<%@page import="java.io.File"%>
<%@page import="classes.Img"%>
<%@page import="classes.Datas"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.PhotosForm1"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Mettre aux enchères [Photos]</title>
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
    <body><% int p=5; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Déposer une annonce(Photos)</h1>
            <% if(request.getAttribute("info")!=null) {
            int info=Integer.parseInt(request.getAttribute("info").toString());
        switch(info) {
        case 1: %>
            <div class="info">Vous devez être connecter pour pouvoir déposer une annonce.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
         break;
            case 2: %>
            <div class="info">Annonce inconnue !</div><%
        break; } } else if(request.getAttribute("formulaire")!=null) {
            PhotosForm1 formulaire=(PhotosForm1)(request.getAttribute("formulaire"));
            if(formulaire.getTest()==0) { %>
            <p>Votre annonce est enregistrée, mais vous pouvez y ajouter jusq'à 5 photos.(Note: une annonce avec photos est plus consultée que sans).</p>
            <p>Si vos photos sont de gros fichier, je vous conseille de les uploader l'une après l'autre afin d'eviter les problèmes de mémoire.</p>
            <h2>Vos photos</h2>
            <table border="0" cellspacing="5">
                <tbody>
                    <tr>
            <% Objet.getConnection();
            String query="SELECT extension1,extension2,extension3,extension4,extension5 FROM table_annonces WHERE id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            long idAnnonce=Long.parseLong(session.getAttribute("idAnnonce").toString());
            prepare.setLong(1, idAnnonce);
            ResultSet result=prepare.executeQuery();
            result.next();
            int i=0;
            for(int j=1;j<=5;j++) {
                String extension=result.getString("extension"+j);
                if(extension.length()>0) {
                    i++;
                    String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+"_"+j+extension;
                    File fileMini2=new File(filenameMini2);
                    Img img=new Img();
                    img.getSize(fileMini2);
                    int largeur=img.getWidth();
                    int hauteur=img.getHeight();
                    %>
                    <td>
                    <img src="./DisplayPhoto?mini=2&index=<%= j%>&id=<%= idAnnonce%>" width="<%= largeur%>" height="<%= hauteur%>" alt="miniature"/>
                    </td>
                    <% } } %>
                    </tr>
                </tbody>
            </table><%
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                    if(i==0) { %>
                    <div class="info">Aucune photo encore uploadée !</div><% }
                if(formulaire.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= formulaire.getErrorMsg() %></div>
            </div><% } %>
            <form action="./DeposerAnnoncePhotos" method="POST" enctype="multipart/form-data">
                <div>Photo n°1 :</div>
                <input type="file" name="1" value="" />
                <div>Photo n°2 :</div>
                <input type="file" name="2" value="" />
                <div>Photo n°3 :</div>
                <input type="file" name="3" value="" />
                <div>Photo n°4 :</div>
                <input type="file" name="4" value="" />
                <div>Photo n°5 :</div>
                <input type="file" name="5" value="" />
                <br/>
                <input type="submit" value="Valider" name="kermit" />
            </form>
            <% } else { formulaire.blank(); %>
            <div class="info">Photo enregistrée</div><% } } %>
        </div>
    </body>
</html>
