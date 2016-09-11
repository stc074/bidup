/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

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
public class Enchere extends Objet {
    private long idAnnonce;
    private String titre;
    private double pasEnchere;
    private double prixReserve;
    private long timestampFin;
    private double prixActuel;
    private double prixActuel0;
    private double prixEnchere;
    private long idEnchere;
    private long idMembre;
    private long idMembreAnnonce;
    private String pseudo;
    private String email;
    public Enchere(long idAnnonce) {
        super();
        this.idAnnonce=idAnnonce;
        this.setTest(0);
        this.setErrorMsg2("");
        try {
            Objet.getConnection();
            String query="SELECT t1.id_membre,t1.titre,t1.pas_enchere,t1.prix_reserve,t1.timestamp_fin,t2.id AS idEnchere,t2.prix_actuel FROM table_annonces AS t1,table_encheres AS t2 WHERE t1.id=? AND t2.id_annonce=t1.id LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            ResultSet result=prepare.executeQuery();
            boolean flag=result.next();
            if(flag) {
                this.idMembreAnnonce=result.getLong("id_membre");
                this.titre=result.getString("titre");
                this.pasEnchere=result.getDouble("pas_enchere");
                this.prixReserve=result.getDouble("prix_reserve");
                this.timestampFin=result.getLong("timestamp_fin");
                this.idEnchere=result.getLong("idEnchere");
                this.prixActuel=result.getDouble("prix_actuel");
            } else
                this.setTest(-1);
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne n°1");
        } catch (SQLException ex) {
            Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne n°2");
        }
    }
    public void verif(long idMembre) {

        this.idMembre=idMembre;
            if(this.idMembre==this.idMembreAnnonce)
                this.setErrorMsg("Désolé, vous ne pouvez pas enchérir sur vos propres annonces.<br/>");
            if(this.prixActuel!=this.prixActuel0)
                this.setTest(1);
            if(this.prixEnchere<Objet.convertDouble(this.prixActuel+this.pasEnchere))
                this.setErrorMsg("votre enchere est insuffisante !");
        if(this.getErrorMsg().length()==0&&this.getTest()==0) {
            this.prixActuel=this.prixEnchere;
            try {
                Objet.getConnection();
                String query="UPDATE table_encheres SET prix_actuel=?,nombre_encheres=nombre_encheres+1 WHERE id_annonce=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setDouble(1, this.prixEnchere);
                prepare.setLong(2, this.idAnnonce);
                prepare.executeUpdate();
                prepare.close();
                Calendar cal=Calendar.getInstance();
                long timestamp=cal.getTimeInMillis();
                query="INSERT INTO historiques_encheres (id_annonce,id_enchere,id_membre,montant_enchere,timestamp) VALUES (?,?,?,?,?)";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idAnnonce);
                prepare.setLong(2, this.idEnchere);
                prepare.setLong(3, this.idMembre);
                prepare.setDouble(4, this.prixEnchere);
                prepare.setLong(5, timestamp);
                prepare.executeUpdate();
                query="SELECT pseudo,email FROM table_membres WHERE id=? LIMIT 0,1";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idMembre);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.pseudo=result.getString("pseudo");
                this.email=result.getString("email");
                Mail mail=new Mail(this.email, this.pseudo, Datas.TITRESITE+" - Vous avez enchéri !");
                mail.initMailEnch1(this.pseudo, this.titre, this.prixEnchere);
                mail.send();
                Objet.closeConnection();
                if(this.prixEnchere<this.prixReserve)
                    this.setTest(2);
                else
                    this.setTest(3);
            } catch (NamingException ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne n°1");
            } catch (SQLException ex) {
                Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne n°2");
            }
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
    }

    /**
     * @return the idAnnonce
     */
    public long getIdAnnonce() {
        return idAnnonce;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the pasEnchere
     */
    public double getPasEnchere() {
        return pasEnchere;
    }

    /**
     * @return the prixReserve
     */
    public double getPrixReserve() {
        return prixReserve;
    }

    /**
     * @return the timestampFin
     */
    public long getTimestampFin() {
        return timestampFin;
    }

    /**
     * @return the prixActuel
     */
    public double getPrixActuel() {
        return prixActuel;
    }

    /**
     * @param idAnnonce the idAnnonce to set
     */
    public void setIdAnnonce(long idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @param pasEnchere the pasEnchere to set
     */
    public void setPasEnchere(double pasEnchere) {
        this.pasEnchere = pasEnchere;
    }

    /**
     * @param prixReserve the prixReserve to set
     */
    public void setPrixReserve(double prixReserve) {
        this.prixReserve = prixReserve;
    }

    /**
     * @param timestampFin the timestampFin to set
     */
    public void setTimestampFin(long timestampFin) {
        this.timestampFin = timestampFin;
    }

    /**
     * @param prixActuel the prixActuel to set
     */
    public void setPrixActuel(double prixActuel) {
        this.prixActuel = prixActuel;
    }

    /**
     * @return the prixActuel0
     */
    public double getPrixActuel0() {
        return prixActuel0;
    }

    /**
     * @param prixActuel0 the prixActuel0 to set
     */
    public void setPrixActuel0(double prixActuel0) {
        this.prixActuel0 = prixActuel0;
    }

    /**
     * @return the prixEnchere
     */
    public double getPrixEnchere() {
        return prixEnchere;
    }

    /**
     * @param prixEnchere the prixEnchere to set
     */
    public void setPrixEnchere(double prixEnchere) {
        this.prixEnchere = prixEnchere;
    }
}
