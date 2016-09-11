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

/**
 *
 * @author pj
 */
public class AchImmForm2 extends Formulaire {
    private long idMembre;
    private long idAnnonce;
    private int modePaiement;
    private String titre;
    private double prixImmediat;
    private double fraisExpe;
    private double montantTotal;
    private String pseudoVendeur;
    private long idMembreVendeur;
    private String paypal;
    private long idAchatImmediat;
    public AchImmForm2() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
    }
    public boolean init(long idMembre, long idAnnonce, int modePaiement) {
        boolean flag=false;
        this.idMembre=idMembre;
        this.idAnnonce=idAnnonce;
        this.modePaiement=modePaiement;
        String query="";
        try {
            Objet.getConnection();
        switch(modePaiement) {
            case 1:
                query="SELECT t1.id_membre,t1.titre,t1.prix_immediat,t1.frais_expe,t1.paypal,t2.pseudo FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t1.paypal!='' AND t1.prix_immediat!=0 AND t2.id=t1.id_membre LIMIT 0,1";
                break;
                case 2:
                    query="SELECT t1.id_membre,t1.titre,t1.prix_immediat,t1.frais_expe,t1.paypal,t2.pseudo FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t1.type_paiement_cheque='1' AND t1.prix_immediat!=0 AND t2.id=t1.id_membre LIMIT 0,1";
                    break;
                case 3:
                    query="SELECT t1.id_membre,t1.titre,t1.prix_immediat,t1.frais_expe,t1.paypal,t2.pseudo FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t1.type_paiement_espece='1' AND t1.prix_immediat!=0 AND t2.id=t1.id_membre LIMIT 0,1";
                    break;
                case 4:
                    query="SELECT t1.id_membre,t1.titre,t1.prix_immediat,t1.frais_expe,t1.paypal,t2.pseudo FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t1.type_paiement_timbre='1' AND t1.prix_immediat!=0 AND t2.id=t1.id_membre LIMIT 0,1";
                    break;
        }
        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
        prepare.setLong(1, this.getIdAnnonce());
        prepare.setLong(2, this.getIdMembre());
        //this.setErrorMsg(prepare.toString());
        ResultSet result=prepare.executeQuery();
        flag=result.next();
        if(flag) {
            this.idMembreVendeur=result.getLong("id_membre");
            this.titre=result.getString("titre");
            this.prixImmediat=result.getDouble("prix_immediat");
            this.fraisExpe=result.getDouble("frais_expe");
            this.montantTotal=Objet.convertDouble(this.getPrixImmediat()+this.getFraisExpe());
            this.paypal=result.getString("paypal");
            this.pseudoVendeur=result.getString("pseudo");
                    query="UPDATE table_annonces SET etat='1' WHERE id=?";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.idAnnonce);
                    prepare.executeUpdate();
                    prepare.close();
                    query="INSERT INTO table_achats_immediats (id_annonce,id_membre_vendeur,id_membre_acheteur,montant,frais_expe,montant_total,mode_paiement,timestamp) VALUES (?,?,?,?,?,?,?,?)";
                    Calendar cal=Calendar.getInstance();
                    long timestamp=cal.getTimeInMillis();
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.getIdAnnonce());
                    prepare.setLong(2, this.getIdMembreVendeur());
                    prepare.setLong(3, this.getIdMembre());
                    prepare.setDouble(4, this.getPrixImmediat());
                    prepare.setDouble(5, this.getFraisExpe());
                    prepare.setDouble(6, this.getMontantTotal());
                    prepare.setInt(7, this.getModePaiement());
                    prepare.setLong(8, timestamp);
                    //this.setErrorMsg(prepare.toString());
                    prepare.executeUpdate();
                    prepare.close();
                    query="SELECT LAST_INSERT_ID() AS idAchatImmediat FROM table_achats_immediats WHERE id_annonce=? AND id_membre_vendeur=? AND id_membre_acheteur=? LIMIT 0,1";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.getIdAnnonce());
                    prepare.setLong(2, this.getIdMembreVendeur());
                    prepare.setLong(3, this.getIdMembre());
                    result=prepare.executeQuery();
                    flag=result.next();
                    if(flag) {
                        this.idAchatImmediat=result.getLong("idAchatImmediat");
                    }
        }
        result.close();
        prepare.close();
        Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AchImmForm2.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        } catch (SQLException ex) {
            Logger.getLogger(AchImmForm2.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        }
        //flag=true;
        return flag;
    }
    public boolean init2(long idMembre, long idAnnonce, int modePaiement) {
        boolean flag=false;
        this.idMembre=idMembre;
        this.idAnnonce=idAnnonce;
        this.modePaiement=modePaiement;
        String query="";
        try {
            Objet.getConnection();
        switch(modePaiement) {
            case 1:
                query="SELECT t1.id_membre,t1.titre,t1.prix_immediat,t1.frais_expe,t1.paypal,t2.pseudo FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t1.paypal!='' AND t1.prix_immediat!=0 AND t2.id=t1.id_membre LIMIT 0,1";
                break;
                case 2:
                    query="SELECT t1.id_membre,t1.titre,t1.prix_immediat,t1.frais_expe,t1.paypal,t2.pseudo FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t1.type_paiement_cheque='1' AND t1.prix_immediat!=0 AND t2.id=t1.id_membre LIMIT 0,1";
                    break;
                case 3:
                    query="SELECT t1.id_membre,t1.titre,t1.prix_immediat,t1.frais_expe,t1.paypal,t2.pseudo FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t1.type_paiement_espece='1' AND t1.prix_immediat!=0 AND t2.id=t1.id_membre LIMIT 0,1";
                    break;
                case 4:
                    query="SELECT t1.id_membre,t1.titre,t1.prix_immediat,t1.frais_expe,t1.paypal,t2.pseudo FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre!=? AND t1.type_paiement_timbre='1' AND t1.prix_immediat!=0 AND t2.id=t1.id_membre LIMIT 0,1";
                    break;
        }
        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
        prepare.setLong(1, this.getIdAnnonce());
        prepare.setLong(2, this.getIdMembre());
        //this.setErrorMsg(prepare.toString());
        ResultSet result=prepare.executeQuery();
        flag=result.next();
        if(flag) {
            this.idMembreVendeur=result.getLong("id_membre");
            this.titre=result.getString("titre");
            this.prixImmediat=result.getDouble("prix_immediat");
            this.fraisExpe=result.getDouble("frais_expe");
            this.montantTotal=Objet.convertDouble(this.getPrixImmediat()+this.getFraisExpe());
            this.paypal=result.getString("paypal");
            this.pseudoVendeur=result.getString("pseudo");
            }
       result.close();
        prepare.close();
        Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(AchImmForm2.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        } catch (SQLException ex) {
            Logger.getLogger(AchImmForm2.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        }
        //flag=true;
        return flag;
    }
    public void verif(long idAchatImmediat) {
        this.idAchatImmediat=idAchatImmediat;
        try {
            Objet.getConnection();
            String query="SELECT t1.titre,t1.type_envoi,t1.remise_vente_vendu,t2.pseudo AS pseudoVendeur,t2.email AS emailVendeur,t2.prenom AS prenomVendeur,t2.nom AS nomVendeur,t2.adresse AS adresseVendeur,t2.code_postal AS codePostalVendeur,t2.ville AS villeVendeur,t3.pseudo AS pseudoAcheteur,t3.email AS emailAcheteur,t3.prenom AS prenomAcheteur,t3.nom AS nomAcheteur,t3.adresse AS adresseAcheteur,t3.code_postal AS codePostalAcheteur,t3.ville AS villeAcheteur FROM table_annonces AS t1,table_membres AS t2,table_membres AS t3,table_achats_immediats AS t4 WHERE t4.id=? AND t1.id=t4.id_annonce AND t2.id=t4.id_membre_vendeur AND t3.id=t4.id_membre_acheteur LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idAchatImmediat);
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            result.next();
            this.titre=result.getString("titre");
            int typeEnvoi=result.getInt("type_envoi");
            int remiseVenteVendu=result.getInt("remise_vente_vendu");
            this.pseudoVendeur=result.getString("pseudoVendeur");
            String emailVendeur=result.getString("emailVendeur");
            String prenomVendeur=result.getString("prenomVendeur");
            String nomVendeur=result.getString("nomVendeur");
            String adresseVendeur=result.getString("adresseVendeur");
            String codePostalVendeur=result.getString("codePostalVendeur");
            String villeVendeur=result.getString("villeVendeur");
            String pseudoAcheteur=result.getString("pseudoAcheteur");
            String emailAcheteur=result.getString("emailAcheteur");
            String prenomAcheteur=result.getString("prenomAcheteur");
            String nomAcheteur=result.getString("nomAcheteur");
            String adresseAcheteru=result.getString("adresseAcheteur");
            String codePostalAcheteur=result.getString("codePostalAcheteur");
            String villeAcheteur=result.getString("villeAcheteur");
            result.close();
            prepare.close();
            if(remiseVenteVendu==1) {
                query="UPDATE table_annonces SET etat='0' WHERE id=?";
            } else {
                query="UPDATE table_annonces SET etat='4' WHERE id=?";
            }
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            prepare.executeUpdate();
            prepare.close();
            query="UPDATE table_achats_immediats SET etat='1' WHERE id=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idAchatImmediat);
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
            Mail mail;
            if(typeEnvoi==1) {
                switch(this.modePaiement) {
                case 2:
                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmCheque21(titre, pseudoVendeur, pseudoAcheteur, prenomAcheteur, nomAcheteur, adresseAcheteru, codePostalAcheteur, villeAcheteur, idAchatImmediat);
                    mail.send();
                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmCheque22(titre, pseudoVendeur, pseudoAcheteur, prenomVendeur, nomVendeur, adresseVendeur, codePostalVendeur, villeVendeur, idAchatImmediat);
                    mail.send();
                    break;
                case 3:
                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmEspece21(titre, pseudoVendeur, pseudoAcheteur, prenomAcheteur, nomAcheteur, adresseAcheteru, codePostalAcheteur, villeAcheteur, idAchatImmediat);
                    mail.send();
                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmEspece22(titre, pseudoVendeur, pseudoAcheteur, prenomVendeur, nomVendeur, adresseVendeur, codePostalVendeur, villeVendeur, idAchatImmediat);
                    mail.send();
                    break;
                case 4:
                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmTimbre21(titre, pseudoVendeur, pseudoAcheteur, prenomAcheteur, nomAcheteur, adresseAcheteru, codePostalAcheteur, villeAcheteur, idAchatImmediat);
                    mail.send();
                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmTimbre22(titre, pseudoVendeur, pseudoAcheteur, prenomVendeur, nomVendeur, adresseVendeur, codePostalVendeur, villeVendeur, idAchatImmediat);
                    mail.send();
                    break;
                }
            } else if(typeEnvoi==2) {
            switch(this.modePaiement) {
                case 2:
                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmCheque11(titre, pseudoVendeur, pseudoAcheteur, prenomAcheteur, nomAcheteur, adresseAcheteru, codePostalAcheteur, villeAcheteur, idAchatImmediat);
                    mail.send();
                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmCheque12(titre, pseudoVendeur, pseudoAcheteur, prenomVendeur, nomVendeur, adresseVendeur, codePostalVendeur, villeVendeur, idAchatImmediat);
                    mail.send();
                    break;
                case 3:
                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmEspece11(titre, pseudoVendeur, pseudoAcheteur, prenomAcheteur, nomAcheteur, adresseAcheteru, codePostalAcheteur, villeAcheteur, idAchatImmediat);
                    mail.send();
                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmEspece12(titre, pseudoVendeur, pseudoAcheteur, prenomVendeur, nomVendeur, adresseVendeur, codePostalVendeur, villeVendeur, idAchatImmediat);
                    mail.send();
                    break;
                case 4:
                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmTimbre11(titre, pseudoVendeur, pseudoAcheteur, prenomAcheteur, nomAcheteur, adresseAcheteru, codePostalAcheteur, villeAcheteur, idAchatImmediat);
                    mail.send();
                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Achat immédiat");
                    mail.initMailAchImmTimbre12(titre, pseudoVendeur, pseudoAcheteur, prenomVendeur, nomVendeur, adresseVendeur, codePostalVendeur, villeVendeur, idAchatImmediat);
                    mail.send();
                    break;
            }
            }
            this.setTest(1);
        } catch (NamingException ex) {
            Logger.getLogger(AchImmForm2.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne");
        } catch (SQLException ex) {
            Logger.getLogger(AchImmForm2.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur Interne");
        }
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
     * @return the modePaiement
     */
    public int getModePaiement() {
        return modePaiement;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
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
     * @return the montantTotal
     */
    public double getMontantTotal() {
        return montantTotal;
    }

    /**
     * @return the pseudoVendeur
     */
    public String getPseudoVendeur() {
        return pseudoVendeur;
    }

    /**
     * @return the idMembreVendeur
     */
    public long getIdMembreVendeur() {
        return idMembreVendeur;
    }

    /**
     * @return the paypal
     */
    public String getPaypal() {
        return paypal;
    }

    /**
     * @return the idAchatImmediat
     */
    public long getIdAchatImmediat() {
        return idAchatImmediat;
    }
}
