<%@page import="classes.Img"%>
<%@page import="java.io.File"%>
<%@page import="classes.Datas"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="classes.PhotosForm2"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Annonce [Photos]</title>
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
            <h1>Photos</h1><% if(request.getAttribute("info")!=null) {
                int info=Integer.parseInt(request.getAttribute("info").toString());
                switch(info) {
                    case 1: %>
            <div class="info">Vous devez être connecter pour pouvoir accéder à cette page.</div>
            <br/>
            <div><a href="./Connexion" rel="nofollow">Se connecter</a></div>
            <div><a href="./Inscription" rel="nofollow">S'inscrire</a></div><%
            break; } } else if(request.getAttribute("formulaire")!=null) {
                PhotosForm2 formulaire=(PhotosForm2)(request.getAttribute("formulaire"));
                %>
                <p>Votre annonce vient d'être modifiée, vous pouvez aussi changer les photos si vous le voulez.</p>
                <%
                Objet.getConnection();
                String query="SELECT titre,extension1,extension2,extension3,extension4,extension5 FROM table_annonces WHERE id=? AND id_membre=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, formulaire.getIdAnnonce());
                prepare.setLong(2, formulaire.getIdMembre());
                ResultSet result=prepare.executeQuery();
                boolean flag=result.next();
                if(!flag) {
                    result.close();
                    prepare.close();
                    %>
                    <div class="info">Annonce inconnue</div>
                    <%
                    } else {
                    String titre=result.getString("titre");
                    String[] extensions=new String[5];
                    extensions[0]=result.getString("extension1");
                    extensions[1]=result.getString("extension2");
                    extensions[2]=result.getString("extension3");
                    extensions[3]=result.getString("extension4");
                    extensions[4]=result.getString("extension5");
                    %>
                    <h1><%= titre %></h1>
                    <table border="0" cellspacing="5">
                         <tbody>
                            <tr>
                     <%
                    int j=0;
                    for(int i=0;i<5;i++) {
                        if(extensions[i].length()!=0) {
                            int index=i+1;
                            String extension=extensions[i];
                            String filename=Datas.DIR+"photos/"+formulaire.getIdAnnonce()+"_"+index+extension;
                            String filenameMini1=Datas.DIR+"photos/mini1_"+formulaire.getIdAnnonce()+"_"+index+extension;
                            String filenameMini2=Datas.DIR+"photos/mini2_"+formulaire.getIdAnnonce()+"_"+index+extension;
                            //out.println(filename);
                            File file=new File(filename);
                            File fileMini1=new File(filenameMini1);
                            File fileMini2=new File(filenameMini2);
                            if((file.exists())&&(fileMini1.exists())&&(fileMini2.exists())) {
                                Img img=new Img();
                                img.getSize(fileMini2);
                                int largeur=img.getWidth();
                                int hauteur=img.getHeight();
                                j++;
                                %>
                                <td>
                                    <img src="DisplayPhoto?id=<%= formulaire.getIdAnnonce() %>&index=<%= index %>&mini=2" width="<%= largeur %>" height="<%= hauteur %>" alt="photo"/>
                                </td>
                                <%
                                }
                            }
                        } if(j==0) { %>
                        <div class="info">Aucune photo enregistrée !</div>
                        <% }
                    }
                Objet.closeConnection();
                    %>
                             </tr>
                        </tbody>
                    </table>
                             <p>Pour uploader de nouvelles photo, utilisez le formulaire ci-dessous</p>
                             <% if(formulaire.getErrorMsg().length()>0) { %>
                             <div class="erreur">
                                 <div>Erreur(s) :</div>
                                 <br/>
                                 <div><%= formulaire.getErrorMsg() %></div>
                             </div><% } %>
                             <form action="./EditPhotos" method="POST" enctype="multipart/form-data">
                                 <div>Photo N°1 :</div>
                                 <input type="file" name="1" value="" />
                                 <div>Photo N°2 :</div>
                                 <input type="file" name="2" value="" />
                                 <div>Photo N°3 :</div>
                                 <input type="file" name="3" value="" />
                                 <div>Photo N°4 :</div>
                                 <input type="file" name="4" value="" />
                                 <div>Photo N°5 :</div>
                                 <input type="file" name="5" value="" />
                                 <br/>
                                 <input type="submit" value="Valider" name="kermit" />
                             </form>
               <%  } %>
        </div>
    </body>
</html>
