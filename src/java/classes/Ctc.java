/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class Ctc extends Objet {
    private long idMembreExpediteur;
    private long idMembreDestinataire;
    private String pseudoExpediteur;
    private String emailExpediteur;
    private String pseudoDestinataire;
    private String emailDestinataire;
    private String objet;
    private String contenu;
    private String captcha;
    public Ctc() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.objet="";
        this.contenu="";
        this.captcha="";
    }
    public boolean verif(long idMembreExpediteur, long idMembreDestinataire) {
        boolean flag=false;
        this.setIdMembreExpediteur(idMembreExpediteur);
        this.setIdMembreDestinataire(idMembreDestinataire);
        if(idMembreExpediteur!=idMembreDestinataire) {
            try {
                Objet.getConnection();
                String query="SELECT t1.pseudo AS pseudoExpediteur,t1.email AS emailExpediteur,t2.pseudo AS pseudoDestinataire,t2.email AS emailDestinataire FROM table_membres AS t1,table_membres AS t2 WHERE t1.id=? AND t2.id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.getIdMembreExpediteur());
                prepare.setLong(2, this.getIdMembreDestinataire());
                ResultSet result=prepare.executeQuery();
                flag=result.next();
                if(flag) {
                    this.setPseudoExpediteur(result.getString("pseudoExpediteur"));
                    this.setEmailExpediteur(result.getString("emailExpediteur"));
                    this.setPseudoDestinataire(result.getString("pseudoDestinataire"));
                    this.setEmailDestinataire(result.getString("emailDestinataire"));
                }
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(Ctc.class.getName()).log(Level.SEVERE, null, ex);
                flag=false;
            } catch (SQLException ex) {
                Logger.getLogger(Ctc.class.getName()).log(Level.SEVERE, null, ex);
                flag=false;
            }
        }
        return flag;
    }
    public void verifFormulaire(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.objet=this.getObjet().replaceAll("<","&lt;");
        this.objet=this.getObjet().replaceAll(">", "&gt;");
        this.contenu=this.getContenu().replaceAll("<","&lt;");
        this.contenu=this.getContenu().replaceAll(">", "&gt;");
        this.captcha=this.getCaptcha().toLowerCase();
        if(this.getObjet().length()==0)
            this.setErrorMsg("Champ OBJET DU MESSAGE vide.<br/>");
        else if(this.getObjet().length()>40)
            this.setErrorMsg("Champ OBJET DU MESSAGE trop long (40 Car. max).<br/>");
        if(this.getContenu().length()==0)
            this.setErrorMsg("Champ CONTENU DU MESSAGE vide.<br/>");
        else if(this.getContenu().length()>3000)
            this.setErrorMsg("Champ CONTENU DU MESSAGE trop long.<br/>");
        if(this.getCaptcha().length()==0)
            this.setErrorMsg("Champ CODE ANTI-ROBOT vide.<br/>");
        else if(this.getCaptcha().length()>5)
            this.setErrorMsg("Champ CODE ANTI-ROBOT trop long.<br/>");
        else try {
            if (!Objet.getEncoded(this.captcha).equals(session.getAttribute("captcha").toString())) {
                this.setErrorMsg("Mauvais CODE ANTI-ROBOT.<br/>");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Ctc.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne.<br/>");
        }
        long idMessage = 0;
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="INSERT INTO table_messages (id_precedent,id_expediteur,id_destinataire,objet,contenu,timestamp,lu) VALUES ('0',?,?,?,?,?,'0')";
                Calendar cal=Calendar.getInstance();
                long timestamp=cal.getTimeInMillis();
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idMembreExpediteur);
                prepare.setLong(2, this.idMembreDestinataire);
                prepare.setString(3, this.objet);
                prepare.setString(4, this.contenu);
                prepare.setLong(5, timestamp);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_expediteur=? AND id_destinataire=? LIMIT 0,1";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idMembreExpediteur);
                prepare.setLong(2, this.idMembreDestinataire);
                ResultSet result=prepare.executeQuery();
                result.next();
                idMessage=result.getLong("idMessage");
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(Ctc.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(Ctc.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
            Mail mail=new Mail(emailExpediteur, pseudoExpediteur, Datas.TITRESITE+" - Message envoyé");
            mail.initMailContact1(this.pseudoExpediteur, this.pseudoDestinataire, this.objet);
            mail.send();
            mail=new Mail(emailDestinataire, pseudoDestinataire, Datas.TITRESITE+" - Nouveau message");
            mail.initMailContact2(this.pseudoExpediteur, this.pseudoDestinataire, this.objet, idMessage);
            mail.send();
            this.setTest(1);
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setObjet("");
        this.setContenu("");
        this.setCaptcha("");
    }

    /**
     * @return the idMembreExpediteur
     */
    public long getIdMembreExpediteur() {
        return idMembreExpediteur;
    }

    /**
     * @return the idMembreDestinataire
     */
    public long getIdMembreDestinataire() {
        return idMembreDestinataire;
    }

    /**
     * @return the pseudoExpediteur
     */
    public String getPseudoExpediteur() {
        return pseudoExpediteur;
    }

    /**
     * @return the emailExpediteur
     */
    public String getEmailExpediteur() {
        return emailExpediteur;
    }

    /**
     * @return the pseudoDestinataire
     */
    public String getPseudoDestinataire() {
        return pseudoDestinataire;
    }

    /**
     * @return the emailDestinataire
     */
    public String getEmailDestinataire() {
        return emailDestinataire;
    }

    /**
     * @param idMembreExpediteur the idMembreExpediteur to set
     */
    public void setIdMembreExpediteur(long idMembreExpediteur) {
        this.idMembreExpediteur = idMembreExpediteur;
    }

    /**
     * @param idMembreDestinataire the idMembreDestinataire to set
     */
    public void setIdMembreDestinataire(long idMembreDestinataire) {
        this.idMembreDestinataire = idMembreDestinataire;
    }

    /**
     * @param pseudoExpediteur the pseudoExpediteur to set
     */
    public void setPseudoExpediteur(String pseudoExpediteur) {
        this.pseudoExpediteur = pseudoExpediteur;
    }

    /**
     * @param emailExpediteur the emailExpediteur to set
     */
    public void setEmailExpediteur(String emailExpediteur) {
        this.emailExpediteur = emailExpediteur;
    }

    /**
     * @param pseudoDestinataire the pseudoDestinataire to set
     */
    public void setPseudoDestinataire(String pseudoDestinataire) {
        this.pseudoDestinataire = pseudoDestinataire;
    }

    /**
     * @param emailDestinataire the emailDestinataire to set
     */
    public void setEmailDestinataire(String emailDestinataire) {
        this.emailDestinataire = emailDestinataire;
    }

    /**
     * @param objet the objet to set
     */
    public void setObjet(String objet) {
        this.objet = objet;
    }

    /**
     * @param contenu the contenu to set
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * @return the objet
     */
    public String getObjet() {
        return objet;
    }

    /**
     * @return the contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * @return the captcha
     */
    public String getCaptcha() {
        return captcha;
    }
}
