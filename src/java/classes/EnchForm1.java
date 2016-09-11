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
public class EnchForm1 extends Formulaire {
    private long idEnchereFinie;
    private long idMembre;
    private double montant;
    private double fraisExpe;
    private double timestamp;
    private String titre;
    private String paypal;
    private int typePaiementCheque;
    private int typePaiementEspece;
    private int typePaiementTimbre;
    private String pseudoVendeur;
    private double montantTotal;
    private int modePaiement;
    public EnchForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.modePaiement=0;
    }
    public void init(long idEnchereFinie, long idMembre) {
        this.setIdEnchereFinie(idEnchereFinie);
        this.setIdMembre(idMembre);
        try {
            Objet.getConnection();
            String query="SELECT t1.montant,t1.timestamp,t2.titre,t2.paypal,t2.frais_expe,t2.type_paiement_cheque,t2.type_paiement_espece,t2.type_paiement_timbre,t3.pseudo FROM table_encheres_finies AS t1,table_annonces AS t2,table_membres AS t3,table_annonces AS t4 WHERE t1.id=? AND t1.id_membre_acheteur=? AND t2.id=t1.id_annonce AND t3.id=t1.id_membre_vendeur LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdEnchereFinie());
            prepare.setLong(2, this.getIdMembre());
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            boolean flag=result.next();
            if(flag) {
                this.setTest(0);
                this.setMontant(result.getDouble("montant"));
                this.setTimestamp(result.getDouble("timestamp"));
                this.setTitre(result.getString("titre"));
                this.setPaypal(result.getString("paypal"));
                this.setFraisExpe(result.getDouble("frais_expe"));
                this.setTypePaiementCheque(result.getInt("type_paiement_cheque"));
                this.setTypePaiementEspece(result.getInt("type_paiement_espece"));
                this.setTypePaiementTimbre(result.getInt("type_paiement_timbre"));
                this.setPseudoVendeur(result.getString("pseudo"));
                this.setMontantTotal(this.getMontant() + this.getFraisExpe());
            } else {
                this.setTest(1);
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(1);
        } catch (SQLException ex) {
            Logger.getLogger(Enchere.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(1);
        }
    }
    public void verif() {
        if(this.modePaiement==0)
            this.setErrorMsg("Veuillez choisir le mode de paiement SVP.<br/>");
        if(this.getErrorMsg().length()==0) {

            this.setTest(2);
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setModePaiement(0);
    }

    /**
     * @return the idEnchereFinie
     */
    public long getIdEnchereFinie() {
        return idEnchereFinie;
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
     * @return the fraisExpe
     */
    public double getFraisExpe() {
        return fraisExpe;
    }

    /**
     * @return the timestamp
     */
    public double getTimestamp() {
        return timestamp;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the paypal
     */
    public String getPaypal() {
        return paypal;
    }

    /**
     * @return the typePaiementCheque
     */
    public int getTypePaiementCheque() {
        return typePaiementCheque;
    }

    /**
     * @return the typePaiementEspece
     */
    public int getTypePaiementEspece() {
        return typePaiementEspece;
    }

    /**
     * @return the typePaiementTimbre
     */
    public int getTypePaiementTimbre() {
        return typePaiementTimbre;
    }

    /**
     * @return the pseudoVendeur
     */
    public String getPseudoVendeur() {
        return pseudoVendeur;
    }

    /**
     * @return the montantTotal
     */
    public double getMontantTotal() {
        return montantTotal;
    }

    /**
     * @param idEnchereFinie the idEnchereFinie to set
     */
    public void setIdEnchereFinie(long idEnchereFinie) {
        this.idEnchereFinie = idEnchereFinie;
    }

    /**
     * @param idMembre the idMembre to set
     */
    public void setIdMembre(long idMembre) {
        this.idMembre = idMembre;
    }

    /**
     * @param montant the montant to set
     */
    public void setMontant(double montant) {
        this.montant = montant;
    }

    /**
     * @param fraisExpe the fraisExpe to set
     */
    public void setFraisExpe(double fraisExpe) {
        this.fraisExpe = fraisExpe;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @param paypal the paypal to set
     */
    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    /**
     * @param typePaiementCheque the typePaiementCheque to set
     */
    public void setTypePaiementCheque(int typePaiementCheque) {
        this.typePaiementCheque = typePaiementCheque;
    }

    /**
     * @param typePaiementEspece the typePaiementEspece to set
     */
    public void setTypePaiementEspece(int typePaiementEspece) {
        this.typePaiementEspece = typePaiementEspece;
    }

    /**
     * @param typePaiementTimbre the typePaiementTimbre to set
     */
    public void setTypePaiementTimbre(int typePaiementTimbre) {
        this.typePaiementTimbre = typePaiementTimbre;
    }

    /**
     * @param pseudoVendeur the pseudoVendeur to set
     */
    public void setPseudoVendeur(String pseudoVendeur) {
        this.pseudoVendeur = pseudoVendeur;
    }

    /**
     * @param montantTotal the montantTotal to set
     */
    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    /**
     * @return the modePaiement
     */
    public int getModePaiement() {
        return modePaiement;
    }

    /**
     * @param modePaiement the modePaiement to set
     */
    public void setModePaiement(int modePaiement) {
        this.modePaiement = modePaiement;
    }
}
