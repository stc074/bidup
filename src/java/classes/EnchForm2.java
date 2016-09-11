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
public class EnchForm2 extends Objet {
    private int modePaiement;
    private long idEnchereFinie;
    private long idMembre;
    private double montant;
    private String titre;
    private double fraisExpe;
    private String pseudo;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private double montantTotal;
    private String paypal;
    private int typeEnvoi;
    public EnchForm2() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
    }
    public void init(long idMembre,long idEnchereFinie, int modePaiement) {
        this.idMembre=idMembre;
        this.idEnchereFinie=idEnchereFinie;
        this.modePaiement=modePaiement;
        try {
            Objet.getConnection();
            String query="SELECT t1.montant,t2.titre,t2.frais_expe,t2.paypal,t2.type_envoi,t3.pseudo,t3.nom,t3.prenom,t3.adresse,t3.code_postal,t3.ville FROM table_encheres_finies AS t1,table_annonces AS t2,table_membres AS t3 WHERE t1.id=? AND t1.id_membre_acheteur=? AND t2.id=t1.id_annonce AND t3.id=t1.id_membre_vendeur LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdEnchereFinie());
            prepare.setLong(2, this.getIdMembre());
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            boolean flag=result.next();
            if(flag) {
                this.setTest(0);
                this.montant=result.getDouble("montant");
                this.titre=result.getString("titre");
                this.fraisExpe=result.getDouble("frais_expe");
                this.typeEnvoi=result.getInt("type_envoi");
                this.paypal=result.getString("paypal");
                this.pseudo=result.getString("pseudo");
                this.nom=result.getString("nom");
                this.prenom=result.getString("prenom");
                this.adresse=result.getString("adresse");
                this.codePostal=result.getString("code_postal");
                this.ville=result.getString("ville");
                this.montantTotal=this.getMontant()+this.getFraisExpe();
            } else {
                this.setTest(1);
            }
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(EnchForm2.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(1);
        } catch (SQLException ex) {
            Logger.getLogger(EnchForm2.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(1);
        }
    }
    public void payer(long idMembre,long idEnchereFinie, int modePaiement) {
        this.setTest(1);
        try {
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_encheres_finies WHERE id=? AND id_membre_acheteur=? AND mode_paiement='0' AND etat='0' LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, idEnchereFinie);
            prepare.setLong(2, idMembre);
            ResultSet result=prepare.executeQuery();
            result.next();
            int nb=result.getInt("nb");
            if(nb==0) {
                //int i=1/0;
                result.close();
                prepare.close();
                this.setTest(1);
            } else {
                result.close();
                prepare.close();
                query="SELECT t1.pseudo AS pseudoVendeur,t1.email AS emailVendeur,t1.nom AS nomVendeur,t1.prenom AS prenomVendeur,t1.adresse AS adresseVendeur,t1.code_postal AS codePostalVendeur,t1.ville AS villeVendeur,t2.pseudo AS pseudoAcheteur,t2.email AS emailAcheteur,t2.nom AS nomAcheteur,t2.prenom AS prenomAcheteur,t2.adresse AS adresseAcheteur,t2.code_postal AS codePostalAcheteur,t2.ville AS villeAcheteur,t3.montant,t4.titre,t4.frais_expe,t4.type_envoi FROM table_membres AS t1,table_membres AS t2,table_encheres_finies AS t3,table_annonces AS t4 WHERE t3.id=? AND t1.id=t3.id_membre_vendeur AND t2.id=t3.id_membre_acheteur AND t4.id=t3.id_annonce LIMIT 0,1";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, idEnchereFinie);
                //this.setErrorMsg(prepare.toString());
                result=prepare.executeQuery();
                result.next();
                String pseudoVendeur=result.getString("pseudoVendeur");
                String pseudoAcheteur=result.getString("pseudoAcheteur");
                String emailVendeur=result.getString("emailVendeur");
                String emailAcheteur=result.getString("emailAcheteur");
                String nomVendeur=result.getString("nomVendeur");
                String prenomVendeur=result.getString("prenomVendeur");
                String adresseVendeur=result.getString("adresseVendeur");
                String codePostalVendeur=result.getString("codePostalVendeur");
                String villeVendeur=result.getString("villeVendeur");
                String nomAcheteur=result.getString("nomAcheteur");
                String prenomAcheteur=result.getString("prenomAcheteur");
                String adresseAcheteur=result.getString("adresseAcheteur");
                String codePostalAcheteur=result.getString("codePostalAcheteur");
                String villeAcheteur=result.getString("villeAcheteur");
                double montantEnchere=result.getDouble("montant");
                this.titre=result.getString("titre");
                double fraisExpeEnchere=result.getDouble("frais_expe");
                int type=result.getInt("type_envoi");
                double total=montantEnchere+fraisExpeEnchere;
                result.close();
                prepare.close();
                query="UPDATE table_encheres_finies SET mode_paiement=?,etat='1' WHERE id=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, modePaiement);
                prepare.setLong(2, idEnchereFinie);
                prepare.executeUpdate();
                prepare.close();
                Mail mail;
                switch(modePaiement) {
                    case 2:
                switch(type) {
                    case 1:
                     mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Paiement par chèque");
                     mail.initMailCheque11(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie);
                     mail.send();
                     mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiement par chèque");
                     mail.initMailCheque12(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie);
                     mail.send();
                     break;
                     case 2:
                     mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Paiement par chèque");
                     mail.initMailCheque21(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie, nomAcheteur, prenomAcheteur, adresseAcheteur, codePostalAcheteur, villeAcheteur);
                     mail.send();
                     mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiement par chèque");
                     mail.initMailCheque22(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie, nomVendeur, prenomVendeur, adresseVendeur, codePostalVendeur, villeVendeur);
                     mail.send();
                     break;
                }
                    break;
                    case 3:
                        switch(type) {
                            case 1:
                                mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Paiement en espèce");
                                mail.initMailEspece11(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie);
                                mail.send();
                                mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiement en espèce");
                                mail.initMailEspece12(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie);
                                mail.send();
                                break;
                                case 2:
                                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Paiement en espèce");
                                    mail.initMailEspece21(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie, nomAcheteur, prenomAcheteur, adresseAcheteur, codePostalAcheteur, villeAcheteur);
                                    mail.send();
                                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiement en espèce");
                                    mail.initMailEspece22(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie, nomVendeur, prenomVendeur, adresseVendeur, codePostalVendeur, villeVendeur);
                                    mail.send();
                                    break;
                        }
                        break;
                        case 4:
                        switch(type) {
                            case 1:
                                mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Paiement en timbres");
                                mail.initMailTimbre11(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie);
                                mail.send();
                                mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiement en timbres");
                                mail.initMailTimbre12(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie);
                                mail.send();
                                break;
                                case 2:
                                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Paiement en timbres");
                                    mail.initMailTimbre21(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie, nomAcheteur, prenomAcheteur, adresseAcheteur, codePostalAcheteur, villeAcheteur);
                                    mail.send();
                                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Paiement en timbres");
                                    mail.initMailTimbre22(pseudoVendeur, pseudoAcheteur, total, titre, idEnchereFinie, nomVendeur, prenomVendeur, adresseVendeur, codePostalVendeur, villeVendeur);
                                    mail.send();
                                    break;
                        }
                        break;

                }
                this.setTest(2);
            }
        } catch (NamingException ex) {
            Logger.getLogger(EnchForm2.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(1);
        } catch (SQLException ex) {
            Logger.getLogger(EnchForm2.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(1);
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
    }

    /**
     * @return the modePaiement
     */
    public int getModePaiement() {
        return modePaiement;
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
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return the fraisExpe
     */
    public double getFraisExpe() {
        return fraisExpe;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @return the codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @return the ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * @return the montantTotal
     */
    public double getMontantTotal() {
        return montantTotal;
    }

    /**
     * @return the paypal
     */
    public String getPaypal() {
        return paypal;
    }

    /**
     * @return the typeEnvoi
     */
    public int getTypeEnvoi() {
        return typeEnvoi;
    }
}
