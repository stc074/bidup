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
public class PP extends HttpServlet {
   
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
            out.print("bad");
            //Mail mail=new Mail(Datas.EMAILADMIN,"paypal","paypal");
            //mail.initMailTest("Baaaaaaaaad");
            //mail.send();
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PP</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PP at " + request.getContextPath () + "</h1>");
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
        //mail=new Mail(Datas.EMAILADMIN,"admin", "testpaypal");
        //mail.initMailTest(req);
        //mail.send();
        InetAddress serveur = InetAddress.getByName("www.paypal.com");
        Socket sock = new Socket(serveur, 80);
        String itemName, itemNumber, paymentStatus = null, paymentAmount = null, paymentCurrency = null, txnId = null, receiverEmail = null, payerEmail, idUser;
        long idEnchereFinie=0;
        if(request.getParameter("item_name")!=null)
            itemName = request.getParameter("item_name");
        if(request.getParameter("item_number")!=null)
            itemNumber=request.getParameter("item_number");
        if(request.getParameter("payment_status")!=null)
            paymentStatus=request.getParameter("payment_status");
        if(request.getParameter("mc_gross")!=null)
            paymentAmount=request.getParameter("mc_gross");
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
            idEnchereFinie=Long.parseLong(idUser);
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
                            //txn_id non encore traité
                            //mail.initMailTest("txn_id OK");
                            //mail.send();
                            Objet.getConnection();
                            String query="SELECT t1.montant,t2.titre,t2.frais_expe,t2.type_envoi,t2.paypal,t3.pseudo AS pseudoAcheteur,t3.email AS emailAcheteur,t3.nom,t3.prenom,t3.adresse,t3.code_postal,t3.ville,t4.pseudo AS pseudoVendeur,t4.email AS emailVendeur FROM table_encheres_finies AS t1,table_annonces AS t2,table_membres AS t3,table_membres AS t4 WHERE t1.id=? AND t2.id=t1.id_annonce AND t3.id=t1.id_membre_acheteur AND t4.id=t1.id_membre_vendeur LIMIT 0,1";
                            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                            prepare.setLong(1, idEnchereFinie);
                            //mail.initMailTest(prepare.toString());
                            //mail.send();
                            ResultSet result=prepare.executeQuery();
                            boolean flag=result.next();
                            if(flag) {
                                double montant=result.getDouble("montant");
                                String titre=result.getString("titre");
                                double fraisExpe=result.getDouble("frais_expe");
                                int typeEnvoi=result.getInt("type_envoi");
                                String paypal=result.getString("paypal");
                                double montantTotal=montant+fraisExpe;
                                double amount=Double.parseDouble(paymentAmount);
                                String pseudoAcheteur=result.getString("pseudoAcheteur");
                                String emailAcheteur=result.getString("emailAcheteur");
                                String nom=result.getString("nom");
                                String prenom=result.getString("prenom");
                                String adresse=result.getString("adresse");
                                String codePostal=result.getString("code_postal");
                                String ville=result.getString("ville");
                                String pseudoVendeur=result.getString("pseudoVendeur");
                                String emailVendeur=result.getString("emailVendeur");
                                if((amount==montantTotal)&&(paymentCurrency.equals("EUR"))&&(receiverEmail.equals(paypal))) {
                                    query="UPDATE table_encheres_finies SET mode_paiement='1',etat='1' WHERE id=?";
                                    prepare=Objet.getConn().prepareStatement(query);
                                    prepare.setLong(1, idEnchereFinie);
                                    prepare.executeUpdate();
                                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Paiement confirmé");
                                    if(typeEnvoi==1)
                                       mail.initMailPaypal11(pseudoVendeur, pseudoAcheteur, montantTotal, titre, idEnchereFinie);
                                    else if(typeEnvoi==2)
                                        mail.initMailPaypal1(pseudoVendeur, pseudoAcheteur, montantTotal, titre, nom, prenom, adresse, codePostal, ville, idEnchereFinie);
                                    mail.send();
                                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiement PAYPAL confirmé");
                                    mail.initMailPaypal2(pseudoVendeur, pseudoAcheteur, montantTotal, titre, idEnchereFinie);
                                    mail.send();
                                }
                            }
                            result.close();
                            prepare.close();
                            Objet.closeConnection();
                        } catch (NamingException ex) {
                           Logger.getLogger(PP.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(PP.class.getName()).log(Level.SEVERE, null, ex);
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
            String query="SELECT COUNT(id) AS nbTxnId FROM table_txn_id WHERE txn_id=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setString(1, txnID);
            ResultSet result=prepare.executeQuery();
            result.next();
            int nbTxnId=result.getInt("nbTxnId");
            if(nbTxnId==0) {
                flag=true;
                Calendar cal=Calendar.getInstance();
                long timestamp=cal.getTimeInMillis();
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
            Logger.getLogger(PP.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        } catch (SQLException ex) {
            Logger.getLogger(PP.class.getName()).log(Level.SEVERE, null, ex);
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
