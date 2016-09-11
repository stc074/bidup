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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class AchImmForm1 extends Formulaire {
    private long idMembre;
    private long idAnnonce;
    private String titre;
    private int typeEnvoi;
    private String paypal;
    private int typePaiementCheque;
    private int typePaiementEspece;
    private int typePaiementTimbre;
    private double prixImmediat;
    private double fraisExpe;
    private String noteExpe;
    private String pseudoVendeur;
    private double prixTotal;
    private String nomVendeur;
    private String prenomVendeur;
    private String adresseVendeur;
    private String codePostalVendeur;
    private String villeVendeur;
    private int modePaiement;
    public AchImmForm1() {
        super();
    }
    public boolean init(long idMembre, long idAnnonce) {
        boolean flag=false;
        this.setIdMembre(idMembre);
        this.setIdAnnonce(idAnnonce);
        this.setModePaiement(0);
        try {
            Objet.getConnection();
            String query="SELECT t1.titre,t1.prix_immediat,t1.type_envoi,t1.frais_expe,t1.note_expe,t1.paypal,t1.type_paiement_cheque,t1.type_paiement_espece,t1.type_paiement_timbre,t2.pseudo,t2.nom,t2.prenom,t2.adresse,t2.code_postal,t2.ville FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t2.id=t1.id_membre LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdAnnonce());
            prepare.setLong(2, this.getIdMembre());
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            flag=result.next();
            if(flag) {
                this.setTitre(result.getString("titre"));
                this.setPrixImmediat(result.getDouble("prix_immediat"));
                this.setTypeEnvoi(result.getInt("type_envoi"));
                this.setFraisExpe(result.getDouble("frais_expe"));
                this.setNoteExpe(result.getString("note_expe"));
                this.setPaypal(result.getString("paypal"));
                this.setTypePaiementCheque(result.getInt("type_paiement_cheque"));
                this.setTypePaiementEspece(result.getInt("type_paiement_espece"));
                this.setTypePaiementTimbre(result.getInt("type_paiement_timbre"));
                this.setPseudoVendeur(result.getString("pseudo"));
                this.setPrixTotal(Objet.convertDouble(this.getPrixImmediat() + this.getFraisExpe()));
                this.setNomVendeur(result.getString("nom"));
                this.setPrenomVendeur(result.getString("prenom"));
                this.setAdresseVendeur(result.getString("adresse"));
                this.setCodePostalVendeur(result.getString("code_postal"));
                this.setVilleVendeur(result.getString("ville"));
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AchImmForm1.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        } catch (SQLException ex) {
            Logger.getLogger(AchImmForm1.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        }
        //flag=true;
        return flag;
    }
    public void verif(HttpServletRequest request) {
        if(this.modePaiement==0)
            this.setErrorMsg("Veuillez choisir le MODE DE PAIEMENT SVP.<br/>");
        if(this.getErrorMsg().length()==0) {
            HttpSession session=request.getSession(true);
            session.setAttribute("idAnnonce", this.idAnnonce);
            session.setAttribute("modePaiement", this.modePaiement);
            this.setTest(1);
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setModePaiement(0);
    }
    /**
     * @return the idMembre
     */
    public long getIdMembre() {
        return idMembre;
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
     * @return the typeEnvoi
     */
    public int getTypeEnvoi() {
        return typeEnvoi;
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
     * @return the prixImmediat
     */
    public double getPrixImmediat() {
        return prixImmediat;
    }

    /**
     * @return the fraisExpe
     */
    public double getFraisExpe() {
        return fraisExpe;
    }

    /**
     * @return the noteExpe
     */
    public String getNoteExpe() {
        return noteExpe;
    }

    /**
     * @return the pseudoVendeur
     */
    public String getPseudoVendeur() {
        return pseudoVendeur;
    }

    /**
     * @return the prixTotal
     */
    public double getPrixTotal() {
        return prixTotal;
    }

    /**
     * @param idMembre the idMembre to set
     */
    public void setIdMembre(long idMembre) {
        this.idMembre = idMembre;
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
     * @param typeEnvoi the typeEnvoi to set
     */
    public void setTypeEnvoi(int typeEnvoi) {
        this.typeEnvoi = typeEnvoi;
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
     * @param prixImmediat the prixImmediat to set
     */
    public void setPrixImmediat(double prixImmediat) {
        this.prixImmediat = prixImmediat;
    }

    /**
     * @param fraisExpe the fraisExpe to set
     */
    public void setFraisExpe(double fraisExpe) {
        this.fraisExpe = fraisExpe;
    }

    /**
     * @param noteExpe the noteExpe to set
     */
    public void setNoteExpe(String noteExpe) {
        this.noteExpe = noteExpe;
    }

    /**
     * @param pseudoVendeur the pseudoVendeur to set
     */
    public void setPseudoVendeur(String pseudoVendeur) {
        this.pseudoVendeur = pseudoVendeur;
    }

    /**
     * @param prixTotal the prixTotal to set
     */
    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    /**
     * @param nomVendeur the nomVendeur to set
     */
    public void setNomVendeur(String nomVendeur) {
        this.nomVendeur = nomVendeur;
    }

    /**
     * @param prenomVendeur the prenomVendeur to set
     */
    public void setPrenomVendeur(String prenomVendeur) {
        this.prenomVendeur = prenomVendeur;
    }

    /**
     * @param adresseVendeur the adresseVendeur to set
     */
    public void setAdresseVendeur(String adresseVendeur) {
        this.adresseVendeur = adresseVendeur;
    }

    /**
     * @param codePostalVendeur the codePostalVendeur to set
     */
    public void setCodePostalVendeur(String codePostalVendeur) {
        this.codePostalVendeur = codePostalVendeur;
    }

    /**
     * @param villeVendeur the villeVendeur to set
     */
    public void setVilleVendeur(String villeVendeur) {
        this.villeVendeur = villeVendeur;
    }

    /**
     * @param modePaiement the modePaiement to set
     */
    public void setModePaiement(int modePaiement) {
        this.modePaiement = modePaiement;
    }
}
