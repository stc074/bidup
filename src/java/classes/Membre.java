/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Membre extends Objet {
    private long idMembre;
    private String pseudo;
    private String email;
    public Membre() {
        super();
    }
    public void testConnexion(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        this.idMembre=0;
        this.pseudo="";
        this.email="";
        if(session.getAttribute("idMembre")!=null) {
            this.idMembre=Long.parseLong(session.getAttribute("idMembre").toString());
            try {
                Objet.getConnection();
                Connection conn = Objet.getConn();
                String query="SELECT pseudo,email FROM table_membres WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=conn.prepareStatement(query);
                prepare.setLong(1, this.getIdMembre());
                ResultSet result=prepare.executeQuery();
                boolean flag=result.next();
                if(flag) {
                    this.pseudo=result.getString("pseudo");
                    this.email=result.getString("email");
                } else {
                    this.idMembre=0;
                    this.pseudo="";
                    this.email="";
                }
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.idMembre=0;
            } catch (SQLException ex) {
                Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                this.idMembre=0;
            }

        } else {
            Cookie[] cookies = request.getCookies();
            String cookieValeur="";
            int j=0;
            if(cookies!=null) {
                for(int i=0;i<cookies.length;i++) {
                    if(cookies[i].getName().equals("bidupcook")) {
                        cookieValeur=cookies[i].getValue();
                        j=i;
                    }

                }
            }
            if(cookieValeur.length()>0) {
                try {
                    Objet.getConnection();
                    Connection conn=Objet.getConn();
                    String query="SELECT id,pseudo,email FROM table_membres WHERE cookie_code=? LIMIT 0,1";
                    PreparedStatement prepare=conn.prepareStatement(query);
                    prepare.setString(1, cookieValeur);
                    ResultSet result=prepare.executeQuery();
                    boolean flag=result.next();
                    if(flag) {
                        this.idMembre=result.getLong("id");
                        this.pseudo=result.getString("pseudo");
                        this.email=result.getString("email");
                        session.setAttribute("idMembre", this.getIdMembre());
                        cookies[j].setMaxAge(24*60*60*1000*300);
                    } else {
                        this.idMembre=0;
                        this.pseudo="";
                        this.email="";
                    }
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                } catch (NamingException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    public void deconnexion(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        session.setAttribute("idMembre", null);
            Cookie[] cookies = request.getCookies();
            int j=0;
            if(cookies!=null) {
                for(int i=0;i<cookies.length;i++) {
                    if(cookies[i].getName().equals("bidupcook")) {
                        j=i;
                    }

                }
        }
            cookies[j].setValue("blank");
            response.addCookie(cookies[j]);
    }
    public void effaceCompte() {
        try {
            Objet.getConnection();
            String query="SELECT id,titre,extension1,extension2,extension3,extension4,extension5 FROM table_annonces WHERE id_membre=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idMembre);
            ResultSet result=prepare.executeQuery();
            while(result.next()) {
                String extensions[]=new String[5];
                long idAnnonce=result.getLong("id");
                String titre=result.getString("titre");
                extensions[0]=result.getString("extension1");
                extensions[1]=result.getString("extension2");
                extensions[2]=result.getString("extension3");
                extensions[3]=result.getString("extension4");
                extensions[4]=result.getString("extension5");
                for(int i=0;i<5;i++) {
                    String extension=extensions[i];
                    int j=i+1;
                    String filename=Datas.DIR+"photos/"+idAnnonce+"_"+j+extension;
                    String filenameMini1=Datas.DIR+"photos/mini1_"+idAnnonce+"_"+j+extension;
                    String filenameMini2=Datas.DIR+"photos/mini2_"+idAnnonce+"_"+j+extension;
                    File file=new File(filename);
                    File fileMini1=new File(filenameMini1);
                    File fileMini2=new File(filenameMini2);
                    if(file.exists())
                        file.delete();
                    if(fileMini1.exists())
                        fileMini1.delete();
                    if(fileMini2.exists())
                        fileMini2.delete();
                }
                    String query2="SELECT DISTINCT t1.pseudo,t1.email FROM table_membres AS t1,historiques_encheres AS t2 WHERE t2.id_annonce=? AND t2.id_enchere_finie='0' AND t1.id=t2.id_membre";
                    PreparedStatement prepare2=Objet.getConn().prepareStatement(query2);
                    prepare2.setLong(1, idAnnonce);
                    //this.setErrorMsg(prepare2.toString());
                    ResultSet result2=prepare2.executeQuery();
                    while(result2.next()) {
                        String pseudoAcheteur=result2.getString("pseudo");
                        String emailAcheteur=result2.getString("email");
                        Mail mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Annonce supprimée");
                        mail.initMailDel1(this.pseudo, pseudoAcheteur, titre);
                        mail.send();
                    }
                    result2.close();
                    prepare2.close();
           query2="DELETE FROM historiques_encheres WHERE id_annonce=?";
            prepare2=Objet.getConn().prepareStatement(query2);
            prepare2.setLong(1, idAnnonce);
            prepare2.executeUpdate();
            prepare2.close();
            query2="DELETE FROM table_encheres WHERE id_annonce=?";
            prepare2=Objet.getConn().prepareStatement(query2);
            prepare2.setLong(1, idAnnonce);
            prepare2.executeUpdate();
            prepare2.close();
            query2="DELETE FROM table_encheres_finies WHERE id_annonce=?";
            prepare2=Objet.getConn().prepareStatement(query2);
            prepare2.setLong(1, idAnnonce);
            prepare2.executeUpdate();
            prepare2.close();
            }
            query="DELETE FROM historiques_encheres WHERE id_membre=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_achats_immediats WHERE id_membre_vendeur=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_encheres_finies WHERE id_membre_acheteur=? OR id_membre_vendeur=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idMembre);
            prepare.setLong(2, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_annonces WHERE id_membre=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_membres WHERE id=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            this.idMembre=0;
            this.setTest(1);
         } catch (NamingException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Membre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @return the idMembre
     */
    public long getIdMembre() {
        return idMembre;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
}
