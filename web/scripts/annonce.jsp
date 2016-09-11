<%@page import="classes.Img"%>
<%@page import="java.io.File"%>
<%@page import="classes.Datas"%>
<%@page import="java.util.Calendar"%>
<%@page import="classes.Ann"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<% Ann annonce=(Ann)(request.getAttribute("annonce")); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><% if(annonce.getTest()==-1) out.print("BidUP -- Votre site d'enchères"); else out.print("Annonce d'enchère - "+annonce.getTitre()); %></title>
<meta name="generator" content="Bluefish pour le squelette, Netbeans pour les organes vitaux"/>
<meta name="author" content=""/>
<meta name="date" content=""/>
<meta name="copyright" content=""/>
<meta name="keywords" content=""/>
<meta name="description" content="BidUP, votre site d'enchères gratuites !."/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8"/>
<meta http-equiv="content-style-type" content="text/css"/>
<link href="./CSS/style.css" type="text/css" rel="stylesheet" />
<script src="./js-global/FancyZoom.js" type="text/javascript"></script>
<script src="./js-global/FancyZoomHTML.js" type="text/javascript"></script>
</head>
    <body onload="setupZoom()"><% int p=0; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <% if(annonce.getTest()==-1) { %>
            <div class="info">Annonce inconnue, désolé !</div><% } else { %>
            <div><a href="./">Revenir à la liste</a></div>
            <h1><%= annonce.getTitre() %></h1>
            <% Calendar cal=Calendar.getInstance();
            long timestampActuel=cal.getTimeInMillis();
            cal.setTimeInMillis(annonce.getTimestampEnregistrement());
            %>
            <div class="info">Annonce déposée par <%= annonce.getPseudo() %>, le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %>.</div>
            <%           
            long ecart=annonce.getTimestampFin()-timestampActuel;
            int nbJours=(int)(ecart/(1000*60*60*24));
            long ecart2=ecart-((long)nbJours*1000*60*60*24);
            //out.println(ecart2);
            int nbHeures=(int)(ecart2/(1000*60*60));
            //out.print(nbHeures);
            long ecart3=ecart2-((long)nbHeures*1000*60*60);
            int nbMinutes=(int)(ecart3/(1000*60));
            long ecart4=ecart3-((long)nbMinutes*1000*60);
            int nbSecondes=(int)(ecart4/(1000));
            %>
            <br/>
            <div>Temps restant : <% if(nbJours!=0) out.print(nbJours+"J-"); %><% if(!(nbJours==0&&nbHeures==0)) out.print(nbHeures+"H-"); %><% if(!(nbJours==0&&nbHeures==0&&nbMinutes==0)) out.print(nbMinutes+"Min-"); %><%= nbSecondes %>S.</div>
            <h3>Photos</h3>
            <% if(annonce.getExtensions()[0].length()==0&&annonce.getExtensions()[1].length()==0&&annonce.getExtensions()[2].length()==0&&annonce.getExtensions()[3].length()==0&&annonce.getExtensions()[4].length()==0) { %>
            <div class="info">Pas de photos pour cette annonce</div><% } else { %>
            <div class="info">Cliquez dessus pour voir en taille réelle.</div>
                        <table border="0" cellspacing="5">
                 <tbody>
                    <tr><%
                Img img=new Img();
                boolean flag=false;
                for(int i=0;i<5;i++) {
                    String extension=annonce.getExtensions()[i];
                    if(extension.length()!=0) {
                        int j=i+1;
                        String filenameMini2=Datas.DIR+"/photos/mini2_"+annonce.getIdAnnonce()+"_"+j+extension;
                        File fileMini2=new File(filenameMini2);
                        if(fileMini2.exists()) {
                            flag=true;
                        img.getSize(fileMini2);
                        int largeur=img.getWidth();
                        int hauteur=img.getHeight();
                        %>
                        <td>
                        <a href="./DisplayPhoto?index=<%= j %>&id=<%= annonce.getIdAnnonce() %>" zoom="1">
                            <img src="./DisplayPhoto?mini=2&index=<%= j%>&id=<%= annonce.getIdAnnonce()%>" width="<%= largeur%>" height="<%= hauteur%>" alt="Photo"/>
                        </a>
                        </td><% } } } if(flag==false) { %>
                        <div class="info">Pas de photos pour cette annonce</div><% } %>
                    </tr>
                 </tbody>
                        </table><% } %>
                        <br/>
                        <div class="info">Description de ce bien :</div>
                        <p><%= annonce.getDescription() %></p>
                        <div class="info">Localisation : <%= annonce.getCodePostal() %>-<%= annonce.getCommune() %>-[<%= annonce.getDepartement() %>]</div>
                        <br/>
                        <div>Contacter l'annonceur de ce bien <a href="contact-<%= annonce.getIdMembreVendeur() %>.html" rel="nofollow"><%= annonce.getPseudo() %></a></div>
                        <br/>
                        <div>Prix actuel : <%= annonce.getPrixActuel() %>&nbsp;&euro;</div>
                        <br/><% if(annonce.getTypeEnvoi()==2) { %>
                        <div>Envoi postal: <%= annonce.getFraisExpe() %>&nbsp;&euro;.</div>
                        <% if(annonce.getNoteExpe().length()>0) { %>
                        <p>Note : <%= annonce.getNoteExpe() %>.</p><% } %>
                        <div>Paiement(s) accepté(s) : <% if(annonce.getPaypal().length()>0) out.print(" -paypal- ");
                            if(annonce.getTypePaiementCheque()==1) out.print(" -chèque- ");
                        if(annonce.getTypePaiementEspece()==1) out.print(" -espèces- ");
                        if(annonce.getTypePaiementTimbre()==1) out.print(" -timbres- "); %>
                        </div><% } else if(annonce.getTypeEnvoi()==1) { %>
                        <div>Remise en main propre</div><% } %>
                        <br/>
                        <a href="./Encherir-<%= annonce.getIdAnnonce() %>.html" rel="nofollow">
                            <img src="./GFXs/encherir.png" width="97" height="38" alt="encherir"/>
                        </a>
                        <div>(Prix mini de l'enchère : <%= Objet.convertDouble(annonce.getPrixActuel()+annonce.getPasEnchere()) %>&nbsp;&euro;).</div>
                        <% if(annonce.getPrixImmediat()!=0) { %>
                        <br/>
                        <div class="info">Vous pouvez acquérir ce bien directement au prix de <%= annonce.getPrixImmediat() %>&nbsp;&euro; :</div>
                        <a href="./achat-immediat-<%= annonce.getIdAnnonce() %>.html" rel="nofollow">
                            <img src="./GFXs/acheter.png" width="93" height="38" alt="acheter"/>
                        </a><% } %>
            <% } %>
        </div>
    </body>
</html>
