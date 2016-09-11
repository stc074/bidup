/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Conditions extends Objet {
    private long idConditions;
    private String texte;
    private long timestamp;
    public Conditions() {
        try {
            Objet.getConnection();
            String query="SELECT * FROM conditions LIMIT 0,1";
            Statement state=Objet.getConn().createStatement();
            ResultSet result=state.executeQuery(query);
            result.next();
            this.idConditions=result.getLong("id");
            this.texte=result.getString("texte");
            this.timestamp=result.getLong("timestamp");
            result.close();
            state.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Conditions.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conditions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void verif() {
        if(this.texte.length()==0)
            this.setErrorMsg("Champ TEXTE vide.<br/>");
        if(this.getErrorMsg().length()==0) {
            try {
                Calendar cal=Calendar.getInstance();
                this.timestamp=cal.getTimeInMillis();
                Objet.getConnection();
                String query="UPDATE conditions SET texte=?,timestamp=? WHERE id=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.texte);
                prepare.setLong(2, this.timestamp);
                prepare.setLong(3, this.idConditions);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(Conditions.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(Conditions.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
    }
    /**
     * @return the idConditions
     */
    public long getIdConditions() {
        return idConditions;
    }

    /**
     * @return the texte
     */
    public String getTexte() {
        return texte;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param idConditions the idConditions to set
     */
    public void setIdConditions(long idConditions) {
        this.idConditions = idConditions;
    }

    /**
     * @param texte the texte to set
     */
    public void setTexte(String texte) {
        this.texte = texte;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
