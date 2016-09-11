/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
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
public class Ann extends Objet {
    private long idAnnonce;
    private String titre;
    private String description;
    private double prixDepart;
    private double pasEnchere;
    private double prixReserve;
    private double prixImmediat;
    private int typeEnvoi;
    private double fraisExpe;
    private String noteExpe;
    private String paypal;
    private int typePaiementCheque;
    private int typePaiementEspece;
    private int typePaiementTimbre;
    private String[] extensions;
    private long timestampEnregistrement;
    private long timestampFin;
    private String categorie;
    private String sousCategorie;
    private String region;
    private String departement;
    private String commune;
    private String codePostal;
    private String pseudo;
    private double prixActuel;
    private String uri;
    private long idMembre;
    private long idMembreVendeur;
    public Ann() {
        super();
    }
    public Ann(long idAnnonce) {
        super();
        this.setTest(0);
        this.idAnnonce=idAnnonce;
        try {
            Objet.getConnection();
            String query="SELECT t1.titre,t1.description,t1.prix_depart,t1.pas_enchere,t1.prix_reserve,t1.prix_immediat,t1.type_envoi,t1.frais_expe,t1.note_expe,t1.paypal,t1.type_paiement_cheque,t1.type_paiement_espece,t1.type_paiement_timbre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t1.timestamp_enregistrement,t1.timestamp_fin,t2.categorie,t3.sous_categorie,t4.region,t5.departement,t6.commune,t6.code_postal,t7.id AS idMembreVendeur,t7.pseudo,t8.prix_actuel FROM table_annonces AS t1,table_categories AS t2,table_sous_categories AS t3,table_regions AS t4,table_departements AS t5,table_communes AS t6,table_membres AS t7,table_encheres AS t8 WHERE t1.id=? AND t1.etat='0' AND t2.id=t1.id_categorie AND t3.id=t1.id_sous_categorie AND t4.id_region=t1.id_region AND t5.id_departement=t1.id_departement AND t6.id=t1.id_commune AND t7.id=t1.id_membre AND t8.id_annonce=t1.id LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idAnnonce);
            //this.titre=prepare.toString();
            ResultSet result=prepare.executeQuery();
            boolean flag=result.next();
            if(flag) {
                this.setTest(0);
                this.titre=result.getString("titre");
                this.description=result.getString("description");
                this.prixDepart=result.getDouble("prix_depart");
                this.pasEnchere=result.getDouble("pas_enchere");
                this.prixReserve=result.getDouble("prix_reserve");
                this.prixImmediat=result.getDouble("prix_immediat");
                this.typeEnvoi=result.getInt("type_envoi");
                this.fraisExpe=result.getDouble("frais_expe");
                this.noteExpe=result.getString("note_expe");
                this.paypal=result.getString("paypal");
                this.typePaiementCheque=result.getInt("type_paiement_cheque");
                this.typePaiementEspece=result.getInt("type_paiement_espece");
                this.typePaiementTimbre=result.getInt("type_paiement_timbre");
                this.extensions=new String[5];
                this.extensions[0]=result.getString("extension1");
                this.extensions[1]=result.getString("extension2");
                this.extensions[2]=result.getString("extension3");
                this.extensions[3]=result.getString("extension4");
                this.extensions[4]=result.getString("extension5");
                this.timestampEnregistrement=result.getLong("timestamp_enregistrement");
                this.timestampFin=result.getLong("timestamp_fin");
                this.categorie=result.getString("categorie");
                this.sousCategorie=result.getString("sous_categorie");
                this.region=result.getString("region");
                this.departement=result.getString("departement");
                this.commune=result.getString("commune");
                this.codePostal=result.getString("code_postal");
                this.idMembreVendeur=result.getLong("idMembreVendeur");
                this.pseudo=result.getString("pseudo");
                this.prixActuel=result.getDouble("prix_actuel");
                this.uri="./enchere-"+this.idAnnonce+"-"+Objet.encodeTitre(titre)+".html";
            } else
                this.setTest(-1);
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Ann.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(-1);
        } catch (SQLException ex) {
            Logger.getLogger(Ann.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(-1);
        }
    }
    public void effaceAnnonce(long idMembre, long idAnnonce, int etat) {
        this.setTest(0);
        this.idMembre=idMembre;
        this.idAnnonce=idAnnonce;
        try {
            Objet.getConnection();
            String query="SELECT COUNT(id) AS nb FROM table_annonces WHERE id=? AND id_membre=? AND etat=? LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdAnnonce());
            prepare.setLong(2, this.getIdMembre());
            prepare.setLong(3, etat);
            ResultSet result=prepare.executeQuery();
            result.next();
            int nb=result.getInt("nb");
            if(nb==0) {
                result.close();
                prepare.close();
                this.setTest(1);
            } else {
                result.close();
                prepare.close();
                query="SELECT t1.titre,t1.extension1,t1.extension2,t1.extension3,t1.extension4,t1.extension5,t2.pseudo AS pseudoVendeur,t2.email AS emailVendeur FROM table_annonces AS t1,table_membres AS t2 WHERE t1.id=? AND t1.id_membre=? AND t2.id=t1.id_membre AND t1.etat=? LIMIT 0,1";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, idAnnonce);
                prepare.setLong(2, idMembre);
                prepare.setInt(3, etat);
                result=prepare.executeQuery();
                result.next();
                this.extensions=new String[5];
                this.titre=result.getString("titre");
                this.extensions[0]=result.getString("extension1");
                this.extensions[1]=result.getString("extension2");
                this.extensions[2]=result.getString("extension3");
                this.extensions[3]=result.getString("extension4");
                this.extensions[4]=result.getString("extension5");
                String pseudoVendeur=result.getString("pseudoVendeur");
                String emailVendeur=result.getString("emailVendeur");
                result.close();
                prepare.close();
                for(int i=0;i<5;i++) {
                    int j=i+1;
                    String extension=this.getExtensions()[i];
                    if(extension.length()>0) {
                            String filename=Datas.DIR+"photos/"+this.getIdAnnonce()+"_"+j+extension;
                            String filenameMini1=Datas.DIR+"photos/mini1_"+this.getIdAnnonce()+"_"+j+extension;
                            String filenameMini2=Datas.DIR+"photos/mini2_"+this.getIdAnnonce()+"_"+j+extension;
                            File file=new File(filename);
                            File fileMini1=new File(filenameMini1);
                            File fileMini2=new File(filenameMini2);
                            if(file.exists())
                                file.delete();
                            if(fileMini1.exists())
                                fileMini1.delete();
                            if(fileMini2.exists())
                                fileMini2.delete();
                    }
                }
                if(etat==0) {
                    query="SELECT DISTINCT t1.pseudo AS pseudoAcheteur,t1.email AS emailAcheteur FROM table_membres AS t1,historiques_encheres AS t2 WHERE t2.id_annonce=? AND t1.id=t2.id_membre";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.getIdAnnonce());
                    result=prepare.executeQuery();
                    while(result.next()) {
                        String pseudoAcheteur=result.getString("pseudoAcheteur");
                        String emailAcheteur=result.getString("emailAcheteur");
                        Mail mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - annonce supprimée");
                        mail.initMailDel1(pseudoVendeur, pseudoAcheteur, this.getTitre());
                        mail.send();
                    }
                    result.close();
                    prepare.close();
                    query="DELETE FROM historiques_encheres WHERE id_annonce=? AND id_enchere_finie='0'";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.getIdAnnonce());
                    prepare.executeUpdate();
                    prepare.close();
                }
                query="DELETE FROM table_encheres WHERE id_annonce=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.getIdAnnonce());
                prepare.executeUpdate();
                prepare.close();
                query="DELETE FROM table_annonces WHERE id=?";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.getIdAnnonce());
                prepare.executeUpdate();
                prepare.close();
                Mail mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Annonce éffacée");
                mail.initMailDel2(pseudoVendeur, this.getTitre());
                mail.send();
                this.setTest(0);
            }
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Ann.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(1);
        } catch (SQLException ex) {
            Logger.getLogger(Ann.class.getName()).log(Level.SEVERE, null, ex);
            this.setTest(1);
        }
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
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the prixDepart
     */
    public double getPrixDepart() {
        return prixDepart;
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
     * @return the prixImmediat
     */
    public double getPrixImmediat() {
        return prixImmediat;
    }

    /**
     * @return the typeEnvoi
     */
    public int getTypeEnvoi() {
        return typeEnvoi;
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
     * @return the paypal
     */
    public String getPaypal() {
        return paypal;
    }
    /**
     * @return the extensions
     */
    public String[] getExtensions() {
        return extensions;
    }

    /**
     * @return the timestampEnregistrement
     */
    public long getTimestampEnregistrement() {
        return timestampEnregistrement;
    }

    /**
     * @return the timestampFin
     */
    public long getTimestampFin() {
        return timestampFin;
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @return the departement
     */
    public String getDepartement() {
        return departement;
    }

    /**
     * @return the commune
     */
    public String getCommune() {
        return commune;
    }

    /**
     * @return the codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
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
     * @return the prixActuel
     */
    public double getPrixActuel() {
        return prixActuel;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the idMembre
     */
    public long getIdMembre() {
        return idMembre;
    }

    /**
     * @return the idMembreVendeur
     */
    public long getIdMembreVendeur() {
        return idMembreVendeur;
    }
}
