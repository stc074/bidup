<%@page import="java.util.Calendar"%>
<%@page import="classes.Conditions"%>
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
<script type="text/javascript" src="./../datas/ckeditor/ckeditor.js"></script>
</head>
    <body><% int p=0; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>CGUs</h1>
            <%
            Conditions conditions=(Conditions)(request.getAttribute("conditions"));
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(conditions.getTimestamp());
            if(conditions.getTest()==1) { %>
            <div class="info">Modifications enregistrées !</div><% }
            %>
            <div class="info">Derniere modif : <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %></div>
            <br/>
            <%
            if(conditions.getErrorMsg().length()>0) { %>
            <div class="erreur">
                <div>Erreur(s) :</div>
                <br/>
                <div><%= conditions.getErrorMsg() %></div>
            </div><% } %>
            <form action="./CGUs" method="POST">
                <div>Teste :</div>
                <textarea name="texte" rows="4" cols="20"><%= conditions.getTexte() %></textarea>
                <br/>
                <input type="submit" value="Modifier" name="kermit" />
            </form>
            <script type="text/javascript">
            CKEDITOR.replace( 'texte' );
            </script>
        </div>
    </body>
</html>
