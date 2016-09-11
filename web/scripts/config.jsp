<%@page import="classes.Messagerie"%>
<%@page import="classes.Encheres"%>
<%@page import="classes.Objet"%>
<%@page import="classes.Membre"%>
<%
session=Objet.initSession(request, response);
//session.invalidate();
Membre membre=new Membre();
membre.testConnexion(request);
Encheres encheres=new Encheres();
encheres.verif();
Messagerie messagerie=new Messagerie();
if(membre.getIdMembre()!=0)
    messagerie.testNonLus(membre.getIdMembre());
//out.println(messagerie.getNbMsgRecuNonLu());
//out.println(membre.getPseudo());
//out.println(Objet.getEncoded("marsouin"));
%>