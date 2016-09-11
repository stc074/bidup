/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Messagerie extends Objet {
    private long idMembre;
    private int nbMsgRecuNonLu;
    private int nbMsgEnvNonLu;
    public Messagerie() {
        super();
        this.nbMsgRecuNonLu=0;
        this.nbMsgEnvNonLu=0;
    }
    public void testNonLus(long idMembre) {
        this.setIdMembre(idMembre);
        try {
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_messages WHERE lu='0' AND id_destinataire=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdMembre());
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.setNbMsgRecuNonLu(result.getInt("nb"));
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void testNonLus2(long idMembre) {
        this.setIdMembre(idMembre);
        try {
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_messages WHERE lu='0' AND id_expediteur=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdMembre());
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.setNbMsgEnvNonLu(result.getInt("nb"));
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void effaceMessageRecu(long idMessage) {
        try {
            Objet.getConnection();
            String query="DELETE FROM table_messages WHERE id=? AND id_destinataire=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            query="UPDATE table_messages SET id_precedent='0' WHERE id_precedent=? AND id_expediteur=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            this.setTest(1);
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void effaceMessageEnvoye(long idMessage) {
        try {
            Objet.getConnection();
            String query="DELETE FROM table_messages WHERE id=? AND id_expediteur=?";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            query="UPDATE table_messages SET id_precedent='0' WHERE id_precedent=? AND id_destinataire=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idMessage);
            prepare.setLong(2, this.idMembre);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            this.setTest(1);
        } catch (NamingException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Messagerie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @return the idMembre
     */
    public long getIdMembre() {
        return idMembre;
    }

    /**
     * @return the nbMsgRecuNonLu
     */
    public int getNbMsgRecuNonLu() {
        return nbMsgRecuNonLu;
    }

    /**
     * @return the nbMsgEnvNonLu
     */
    public int getNbMsgEnvNonLu() {
        return nbMsgEnvNonLu;
    }

    /**
     * @param idMembre the idMembre to set
     */
    public void setIdMembre(long idMembre) {
        this.idMembre = idMembre;
    }

    /**
     * @param nbMsgRecuNonLu the nbMsgRecuNonLu to set
     */
    public void setNbMsgRecuNonLu(int nbMsgRecuNonLu) {
        this.nbMsgRecuNonLu = nbMsgRecuNonLu;
    }

    /**
     * @param nbMsgEnvNonLu the nbMsgEnvNonLu to set
     */
    public void setNbMsgEnvNonLu(int nbMsgEnvNonLu) {
        this.nbMsgEnvNonLu = nbMsgEnvNonLu;
    }
}
