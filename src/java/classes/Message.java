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
public class Message extends Objet{
    private long idMembre;
    private long idMessage;
    private String objet;
    private String contenu;
    private long timestamp;
    private String pseudoExpediteur;
    private long idPrecedent;
    private String objetPrecedent;
    private String contenuPrecedent;
    private long timestampPrecedent;
    private String pseudoDestinataire;
    private long idMembreDestinataire;
    private String captcha;
    private long idMembreExpediteur;
    private String emailExpediteur;
    private String emailDestinataire;
    public Message() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.objet="";
        this.contenu="";
    }
    public boolean initRecu(long idMembre, long idMessage) {
        boolean flag=false;
        this.setIdMembre(idMembre);
        this.setIdMessage(idMessage);
        try {
            Objet.getConnection();
            String query="SELECT t1.id_precedent,t1.objet,t1.contenu,t1.timestamp,t2.id,t2.email AS emailExpediteur,t2.pseudo AS pseudoExpediteur,t3.pseudo AS pseudoDestinataire,t3.email AS emailDestinataire FROM table_messages AS t1,table_membres AS t2,table_membres AS t3 WHERE t1.id=? AND t1.id_destinataire=? AND t2.id=t1.id_expediteur AND t3.id=t1.id_destinataire LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdMessage());
            prepare.setLong(2, this.getIdMembre());
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            flag=result.next();
            if(flag) {
                this.setIdPrecedent(result.getLong("id_precedent"));
                this.setObjet(result.getString("objet"));
                this.setContenu(result.getString("contenu"));
                this.setTimestamp(result.getLong("timestamp"));
                this.idMembreExpediteur=result.getLong("id");
                this.emailExpediteur=result.getString("emailExpediteur");
                this.setPseudoExpediteur(result.getString("pseudoExpediteur"));
                this.pseudoDestinataire=result.getString("pseudoDestinataire");
                this.emailDestinataire=result.getString("emailDestinataire");
                query="UPDATE table_messages SET lu='1' WHERE id=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.getIdMessage());
                prepare.executeUpdate();
                if(this.getIdPrecedent()!=0) {
                    query="SELECT objet,contenu,timestamp FROM table_messages WHERE id=? LIMIT 0,1";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.getIdPrecedent());
                    result=prepare.executeQuery();
                    flag=result.next();
                    if(flag) {
                        this.setObjetPrecedent(result.getString("objet"));
                        this.setContenuPrecedent(result.getString("contenu"));
                        this.setTimestampPrecedent(result.getLong("timestamp"));
                    }
                }
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    public boolean initEnvoye(long idMembre, long idMessage) {
        boolean flag=false;
        this.setIdMembre(idMembre);
        this.setIdMessage(idMessage);
        try {
            Objet.getConnection();
            String query="SELECT t1.id_precedent,t1.objet,t1.contenu,t1.timestamp,t2.id,t2.pseudo AS pseudoDestinataire FROM table_messages AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_expediteur=? AND t2.id=t1.id_destinataire LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdMessage());
            prepare.setLong(2, this.getIdMembre());
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            flag=result.next();
            if(flag) {
                this.setIdPrecedent(result.getLong("id_precedent"));
                this.setObjet(result.getString("objet"));
                this.setContenu(result.getString("contenu"));
                this.setTimestamp(result.getLong("timestamp"));
                this.setIdMembreDestinataire(result.getLong("id"));
                this.setPseudoDestinataire(result.getString("pseudoDestinataire"));
                if(this.getIdPrecedent()!=0) {
                    query="SELECT objet,contenu,timestamp FROM table_messages WHERE id=? LIMIT 0,1";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.getIdPrecedent());
                    result=prepare.executeQuery();
                    flag=result.next();
                    if(flag) {
                        this.setObjetPrecedent(result.getString("objet"));
                        this.setContenuPrecedent(result.getString("contenu"));
                        this.setTimestampPrecedent(result.getLong("timestamp"));
                    }
                }
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    public void verif(HttpServletRequest request) {
        this.objet=this.getObjet().replaceAll("<","&lt;");
        this.objet=this.getObjet().replaceAll(">", "&gt;");
        this.contenu=this.getContenu().replaceAll("<","&lt;");
        this.contenu=this.getContenu().replaceAll(">", "&gt;");
        HttpSession session=request.getSession(true);
        if(this.objet.length()==0)
            this.setErrorMsg("Champ OBJET DU MESSAGE vide.<br/>");
        else if(this.objet.length()>40)
            this.setErrorMsg("Champ OBJET DU MESSAGE trop long.<br/>");
        if(this.contenu.length()==0)
            this.setErrorMsg("Champ CONTENU DU MESSAGE vide.<br/>");
        else if(this.contenu.length()>3000)
            this.setErrorMsg("Champ CONTENU DU MESSAGE trop long.<br/>");
        if(this.captcha.length()==0)
            this.setErrorMsg("Champ CODE ANTI-ROBOT vide.<br/>");
        else try {
            if (session.getAttribute("captcha")==null||!Objet.getEncoded(this.captcha).equals(session.getAttribute("captcha").toString())) {
                this.setErrorMsg("Mauvais CODE ANTI-ROBOT.</br>");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne.<br/>");
        }
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="INSERT INTO table_messages (id_precedent,id_expediteur,id_destinataire,objet,contenu,timestamp,lu) VALUES (?,?,?,?,?,?,'0')";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idMessage);
                prepare.setLong(2, this.idMembre);
                prepare.setLong(3, this.idMembreExpediteur);
                prepare.setString(4, this.objet);
                prepare.setString(5, this.contenu);
                Calendar cal=Calendar.getInstance();
                this.timestamp=cal.getTimeInMillis();
                prepare.setLong(6, this.timestamp);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT LAST_INSERT_ID() AS idMessage FROM table_messages WHERE id_expediteur=? AND id_destinataire=? LIMIT 0,1";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idMembre);
                prepare.setLong(2, this.idMembreExpediteur);
                //this.setErrorMsg(prepare.toString());
                ResultSet result=prepare.executeQuery();
                result.next();
                long idMsg=result.getLong("idMessage");
                result.close();
                prepare.close();
                Objet.closeConnection();
            Mail mail=new Mail(emailDestinataire, pseudoDestinataire, Datas.TITRESITE+" - Message envoyé");
            mail.initMailContact1(this.pseudoDestinataire, this.pseudoExpediteur, this.objet);
            mail.send();
            mail=new Mail(emailExpediteur, pseudoExpediteur, Datas.TITRESITE+" - Nouveau message");
            mail.initMailContact2(this.pseudoDestinataire, this.pseudoExpediteur, this.objet, idMsg);
            mail.send();
            this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
            
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.objet="";
        this.contenu="";
    }

    /**
     * @return the idMembre
     */
    public long getIdMembre() {
        return idMembre;
    }

    /**
     * @return the idMessage
     */
    public long getIdMessage() {
        return idMessage;
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
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the pseudoExpediteur
     */
    public String getPseudoExpediteur() {
        return pseudoExpediteur;
    }

    /**
     * @return the idPrecedent
     */
    public long getIdPrecedent() {
        return idPrecedent;
    }

    /**
     * @return the objetPrecedent
     */
    public String getObjetPrecedent() {
        return objetPrecedent;
    }

    /**
     * @return the contenuPrecedent
     */
    public String getContenuPrecedent() {
        return contenuPrecedent;
    }

    /**
     * @return the timestampPrecedent
     */
    public long getTimestampPrecedent() {
        return timestampPrecedent;
    }

    /**
     * @return the pseudoDestinataire
     */
    public String getPseudoDestinataire() {
        return pseudoDestinataire;
    }

    /**
     * @return the idMembreDestinataire
     */
    public long getIdMembreDestinataire() {
        return idMembreDestinataire;
    }

    /**
     * @param idMembre the idMembre to set
     */
    public void setIdMembre(long idMembre) {
        this.idMembre = idMembre;
    }

    /**
     * @param idMessage the idMessage to set
     */
    public void setIdMessage(long idMessage) {
        this.idMessage = idMessage;
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
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @param pseudoExpediteur the pseudoExpediteur to set
     */
    public void setPseudoExpediteur(String pseudoExpediteur) {
        this.pseudoExpediteur = pseudoExpediteur;
    }

    /**
     * @param idPrecedent the idPrecedent to set
     */
    public void setIdPrecedent(long idPrecedent) {
        this.idPrecedent = idPrecedent;
    }

    /**
     * @param objetPrecedent the objetPrecedent to set
     */
    public void setObjetPrecedent(String objetPrecedent) {
        this.objetPrecedent = objetPrecedent;
    }

    /**
     * @param contenuPrecedent the contenuPrecedent to set
     */
    public void setContenuPrecedent(String contenuPrecedent) {
        this.contenuPrecedent = contenuPrecedent;
    }

    /**
     * @param timestampPrecedent the timestampPrecedent to set
     */
    public void setTimestampPrecedent(long timestampPrecedent) {
        this.timestampPrecedent = timestampPrecedent;
    }

    /**
     * @param pseudoDestinataire the pseudoDestinataire to set
     */
    public void setPseudoDestinataire(String pseudoDestinataire) {
        this.pseudoDestinataire = pseudoDestinataire;
    }

    /**
     * @param idMembreDestinataire the idMembreDestinataire to set
     */
    public void setIdMembreDestinataire(long idMembreDestinataire) {
        this.idMembreDestinataire = idMembreDestinataire;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
