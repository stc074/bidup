<%@page import="java.util.Calendar"%>
<%@page import="classes.Conditions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
<%@include file="./config.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>BidUP -- Coditions générales d'utilisation</title>
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
    <body><% int p=2; %>
        <%@include file="./haut.jsp" %>
        <div class="contenu">
            <h1>Conditions générales d'utilisation</h1>
            <%
            Conditions conditions=(Conditions)(request.getAttribute("conditions"));
            Calendar cal=Calendar.getInstance();
            cal.setTimeInMillis(conditions.getTimestamp());
            %>
            <div class="info">Dernières modifications le <%= cal.get(Calendar.DATE) %>-<%= cal.get(Calendar.MONTH)+1 %>-<%= cal.get(Calendar.YEAR) %>.</div>
            <br/>
            <%= conditions.getTexte() %>
        </div>
    </body>
</html>
