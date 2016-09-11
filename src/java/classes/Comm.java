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
public class Comm extends Objet {
    private int type;
    private long idEnchereFinie;
    private long idAchatImmediat;
    private final long idMembre;
    private double montant;
    private double montantTotal;
    private double fraisExpe;
    private int modePaiement;
    private long timestamp;
    private String pseudoAcheteur;
    private String pseudoVendeur;
    private String titre;
    private long idAcheteur;
    private long idVendeur;
    public Comm(int type, long id, long idMembre) {
        super();
        this.type = type;
        this.idMembre=idMembre;
        try {
            Objet.getConnection();
            switch (this.type) {
                case 1:
                    this.idEnchereFinie=id;
                    break;
                case 2:
                    this.idEnchereFinie = id;
                    break;
                case 3:
                    this.idAchatImmediat=id;
                    break;
                case 4:
                    this.idAchatImmediat = id;
                    break;
            }
        } catch (NamingException ex) {
            Logger.getLogger(Comm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Comm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean verif() {
            boolean flag = false;
        try {
            String query = "";
            switch (this.getType()) {
                case 1:
                case 2:
                    query = "SELECT t1.montant,t1.montant_total,t1.frais_expe,t1.mode_paiement,t1.timestamp,t2.id AS idAcheteur,t2.pseudo AS pseudoAcheteur,t3.titre,t4.id AS idVendeur,t4.pseudo AS pseudoVendeur FROM table_encheres_finies AS t1,table_membres AS t2,table_annonces AS t3,table_membres AS t4 WHERE t1.id=? AND t1.etat='1' AND t2.id=t1.id_membre_acheteur AND t3.id=t1.id_annonce AND t4.id=t1.id_membre_vendeur LIMIT 0,1";
                    break;
                case 3:
                case 4:
                    query = "SELECT t1.montant,t1.montant_total,t1.frais_expe,t1.mode_paiement,t1.timestamp,t2.id AS idAcheteur,t2.pseudo AS pseudoAcheteur,t3.id AS idVendeur,t3.pseudo AS pseudoVendeur,t4.titre FROM table_achats_immediats AS t1,table_membres AS t2,table_membres AS t3,table_annonces AS t4 WHERE t1.id=? AND t1.etat='1' AND t2.id=t1.id_membre_acheteur AND t3.id=t1.id_membre_vendeur AND t4.id=t1.id_annonce LIMIT 0,1";
                    break;
            }
            Objet.getConnection();
            PreparedStatement prepare = Objet.getConn().prepareStatement(query);
            switch(this.getType()) {
                case 1:
                case 2:
                    prepare.setLong(1, this.getIdEnchereFinie());
                    break;
                case 3:
                case 4:
                    prepare.setLong(1, this.getIdAchatImmediat());
                    break;
            }
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            flag=result.next();
            if(flag) {
                this.montant=result.getDouble("montant");
                this.montantTotal=result.getDouble("montant_total");
                this.fraisExpe=result.getDouble("frais_expe");
                this.modePaiement=result.getInt("mode_paiement");
                this.timestamp=result.getLong("timestamp");
                this.idAcheteur=result.getLong("idAcheteur");
                this.idVendeur=result.getLong("idVendeur");
                this.pseudoAcheteur=result.getString("pseudoAcheteur");
                this.pseudoVendeur=result.getString("pseudoVendeur");
                this.titre=result.getString("titre");
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Comm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Comm.class.getName()).log(Level.SEVERE, null, ex);
        }
            //flag=true;
            return flag;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the idEnchereFinie
     */
    public long getIdEnchereFinie() {
        return idEnchereFinie;
    }

    /**
     * @return the idAchatImmediat
     */
    public long getIdAchatImmediat() {
        return idAchatImmediat;
    }

    /**
     * @return the idMembre
     */
    public long getIdMembre() {
        return idMembre;
    }

    /**
     * @return the montant
     */
    public double getMontant() {
        return montant;
    }

    /**
     * @return the montantTotal
     */
    public double getMontantTotal() {
        return montantTotal;
    }

    /**
     * @return the fraisExpe
     */
    public double getFraisExpe() {
        return fraisExpe;
    }

    /**
     * @return the modePaiement
     */
    public int getModePaiement() {
        return modePaiement;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @return the pseudoAcheteur
     */
    public String getPseudoAcheteur() {
        return pseudoAcheteur;
    }

    /**
     * @return the pseudoVendeur
     */
    public String getPseudoVendeur() {
        return pseudoVendeur;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the idAcheteur
     */
    public long getIdAcheteur() {
        return idAcheteur;
    }

    /**
     * @return the idVendeur
     */
    public long getIdVendeur() {
        return idVendeur;
    }
}
