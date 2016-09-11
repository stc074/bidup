/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Datas;
import classes.Mail;
import classes.Objet;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class PP2 extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PP2</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PP2 at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        long idAchatImmediat = 0;
        Mail mail;
        Enumeration parameters=request.getParameterNames();
        String req=URLEncoder.encode("cmd","UTF-8")+"="+URLEncoder.encode("_notify-validate","UTF-8");
        while(parameters.hasMoreElements()) {
            String key=parameters.nextElement().toString();
            String value=request.getParameter(key);
            req+="&"+URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(value,"UTF-8");
        }
        String header = "POST /cgi-bin/webscr HTTP/1.0\r\n";
        header += "Content-Type: application/x-www-form-urlencoded\r\n";
        header += "Content-Length: " + req.length()+ "\r\n\r\n";
        mail=new Mail(Datas.EMAILADMIN,"admin", "testpaypal");
        //mail.initMailTest(req);
        //mail.send();
        InetAddress serveur = InetAddress.getByName("www.paypal.com");
        Socket sock = new Socket(serveur, 80);
        String itemName, itemNumber, paymentStatus = null, paymentAmount = null, paymentCurrency = null, txnId = null, receiverEmail = null, payerEmail, idUser;
        long idEnchereFinie=0;
        double montantTotal = 0;
        if(request.getParameter("item_name")!=null)
            itemName = request.getParameter("item_name");
        if(request.getParameter("item_number")!=null)
            itemNumber=request.getParameter("item_number");
        if(request.getParameter("payment_status")!=null)
            paymentStatus=request.getParameter("payment_status");
        if(request.getParameter("mc_gross")!=null) {
            paymentAmount=request.getParameter("mc_gross");
            montantTotal=Double.parseDouble(paymentAmount);
            }
        if(request.getParameter("mc_currency")!=null)
            paymentCurrency=request.getParameter("mc_currency");
        if(request.getParameter("txn_id")!=null)
            txnId=request.getParameter("txn_id");
        if(request.getParameter("receiver_email")!=null)
            receiverEmail=request.getParameter("receiver_email");
        if(request.getParameter("payer_email")!=null)
            payerEmail=request.getParameter("payer_email");
        if(request.getParameter("custom")!=null) {
            idUser=request.getParameter("custom");
            idAchatImmediat = Long.parseLong(idUser);
            }
        if(sock==null) {
            //Probleme de connexion
        //mail.initMailTest("socket nulle");
        //mail.send();
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())), true);
            out.println(header+req);
            //mail.initMailTest("ok !");
            //mail.send();
            String reponse="", ligne="";
            while((ligne=in.readLine())!=null)
                reponse+=ligne;
            if(reponse.indexOf("VERIFIED")!=-1) {
                //transaction valide
                //mail.initMailTest("Transaction valide\ntxn_id="+txnId);
                //mail.send();
                if(paymentStatus.equals("Completed")) {
                   //mail.initMailTest("status=Completed");
                   if(this.verifTxnId(txnId)==true) {
                        try {
                            //mail.initMailTest("Transaction OK");
                            //mail.send();
                            Objet.getConnection();
                            Calendar cal=Calendar.getInstance();
                            long timestampLimite=cal.getTimeInMillis()-(1000*60*60);
                            String query="UPDATE table_annonces AS t1,table_achats_immediats AS t2 SET t1.etat='0' WHERE t2.timestamp<? AND t2.etat='0' AND t1.etat='1' AND t1.id=t2.id_annonce";
                            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                            prepare.setLong(1, timestampLimite);
                            prepare.executeUpdate();
                            prepare.close();
                            query="SELECT COUNT(id) AS nb FROM table_achats_immediats WHERE id=? AND montant_total=? LIMIT 0,1";
                            prepare=Objet.getConn().prepareStatement(query);
                            prepare.setLong(1, idAchatImmediat);
                            prepare.setDouble(2, montantTotal);
                            //mail.initMailTest(prepare.toString());
                            //mail.send();
                            ResultSet result=prepare.executeQuery();
                            //mail.initMailTest("Montant total :"+montantTotal);
                            //mail.send();
                            result.next();
                            int nb=result.getInt("nb");
                            if(nb==0) {
                                result.close();
                                prepare.close();
                            } else {
                                result.close();
                                prepare.close();
                            query = "SELECT t1.id,t1.titre,t1.remise_vente_vendu,t1.type_envoi,t2.pseudo AS pseudoVendeur,t2.email AS emailVendeur,t2.prenom AS prenomVendeur,t2.nom AS nomVendeur,t2.adresse AS adresseVendeur,t2.code_postal AS codePostalVendeur,t2.ville AS villeVendeur,t3.pseudo AS pseudoAcheteur,t3.email AS emailAcheteur,t3.nom AS nomAcheteur,t3.prenom AS prenomAcheteur,t3.adresse AS adresseAcheteur,t3.code_postal AS codePostalAcheteur,t3.ville AS villeAcheteur FROM table_annonces AS t1,table_membres AS t2,table_membres AS t3,table_achats_immediats AS t4 WHERE t4.id=? AND t1.id=t4.id_annonce  AND t1.etat='1' AND t2.id=t4.id_membre_vendeur AND t3.id=t4.id_membre_acheteur LIMIT 0,1";
                            prepare=Objet.getConn().prepareStatement(query);
                            prepare.setLong(1, idAchatImmediat);
                            //mail.initMailTest(prepare.toString());
                            //mail.send();
                            result=prepare.executeQuery();
                            boolean flag=result.next();
                            long idAnnonce=result.getLong("id");
                            String titre=result.getString("titre");
                            int remiseVenteVendu=result.getInt("remise_vente_vendu");
                            int typeEnvoi=result.getInt("type_envoi");
                            String pseudoVendeur=result.getString("pseudoVendeur");
                            String emailVendeur=result.getString("emailVendeur");
                            String prenomVendeur=result.getString("prenomVendeur");
                            String nomVendeur=result.getString("nomVendeur");
                            String adresseVendeur=result.getString("adresseVendeur");
                            String codePostalVendeur=result.getString("codePostalVendeur");
                            String villeVendeur=result.getString("villeVendeur");
                            String pseudoAcheteur=result.getString("pseudoAcheteur");
                            String emailAcheteur=result.getString("emailAcheteur");
                            String nomAcheteur=result.getString("nomAcheteur");
                            String prenomAcheteur=result.getString("prenomAcheteur");
                            String adresseAcheteur=result.getString("adresseAcheteur");
                            String codePostalAcheteur=result.getString("codePostalAcheteur");
                            String villeAcheteur=result.getString("villeAcheteur");
                            result.close();
                            prepare.close();
                            //Objet.closeConnection();
                            //mail.initMailTest("mysql passée");
                            //mail.send();
                            switch(typeEnvoi) {
                                case 1:
                                    mail=new Mail(emailVendeur, pseudoAcheteur, Datas.TITRESITE+" - Achat immediat de votre objet");
                                    mail.initMailAchImmPaypal3(titre, pseudoVendeur, pseudoAcheteur, idAchatImmediat);
                                    mail.send();
                                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiement PAYPAL confirmé");
                                    mail.initMailAchImmPaypal4(titre, pseudoVendeur, prenomVendeur, nomVendeur, adresseVendeur, codePostalVendeur, villeVendeur, pseudoAcheteur, idAchatImmediat);
                                    mail.send();
                                    break;
                                case 2:
                                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Achat immédiat");
                                    mail.initMailAchImmPaypal1(titre, pseudoVendeur, pseudoAcheteur, nomAcheteur, prenomAcheteur, adresseAcheteur, codePostalAcheteur, villeAcheteur, idAchatImmediat);
                                    mail.send();
                                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiment confirmé");
                                    mail.initMailAchImmPaypal2(titre, pseudoVendeur, pseudoAcheteur, idAchatImmediat);
                                    mail.send();
                                    break;
                            }
                            query="UPDATE table_achats_immediats SET etat='1' WHERE id=?";
                            prepare=Objet.getConn().prepareStatement(query);
                            prepare.setLong(1, idAchatImmediat);
                            prepare.executeUpdate();
                            prepare.close();
                            if(remiseVenteVendu==0) {
                                query="UPDATE table_annonces SET etat='4' WHERE id=?";
                                prepare=Objet.getConn().prepareStatement(query);
                                prepare.setLong(1, idAnnonce);
                                prepare.executeUpdate();
                                prepare.close();
                            } else {
                                query="UPDATE table_annonces SET etat='0' WHERE id=?";
                                prepare=Objet.getConn().prepareStatement(query);
                                prepare.setLong(1, idAnnonce);
                                prepare.executeUpdate();
                                prepare.close();
                            }
                           }
                            Objet.closeConnection();
                        } catch (NamingException ex) {
                            Logger.getLogger(PP2.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(PP2.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   } else {
                       //mail.initMailTest("txn_id  PAS OK");
                       //mail.send();
                   }
                }
            } else if(reponse.indexOf("INVALID")!=-1) {
                //transaction invalide
                //mail.initMailTest("Transaction invalide");
                //mail.send();
            } else {
                //mail.initMailTest("reponse = "+reponse+".");
                //mail.send();
            }
            out.close();
            in.close();
            sock.close();
        }
    }
    private boolean verifTxnId(String txnID) {
        boolean flag=false;
        try {
            Objet.getConnection();
            Calendar cal=Calendar.getInstance();
            long timestamp=cal.getTimeInMillis()-(1000*60*60*24*30);
            String query="DELETE FROM table_txn_id WHERE timestamp<?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, timestamp);
            prepare.executeUpdate();
            prepare.close();
            query="SELECT COUNT(id) AS nbTxnId FROM table_txn_id WHERE txn_id=? LIMIT 0,1";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setString(1, txnID);
            ResultSet result=prepare.executeQuery();
            result.next();
            int nbTxnId=result.getInt("nbTxnId");
            if(nbTxnId==0) {
                flag=true;
                cal=Calendar.getInstance();
                timestamp=cal.getTimeInMillis();
                query="INSERT INTO table_txn_id (txn_id,timestamp) VALUES (?,?)";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, txnID);
                prepare.setLong(2, timestamp);
                prepare.executeUpdate();
                }
            else
                flag=false;
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(PP2.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        } catch (SQLException ex) {
            Logger.getLogger(PP2.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        }
        return flag;
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
