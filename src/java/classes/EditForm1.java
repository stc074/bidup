/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class EditForm1 extends Formulaire {
    private long idAnnonce;
    private long idMembre;
    private int idCategorie;
    private int idSousCategorie;
    private String titre;
    private String description;
    private double prixDepart;
    private double pasEnchere;
    private double prixReserve;
    private double prixImmediat;
    private String dateFin;
    private String heureFin;
    private String minuteFin;
    private int remiseVenteInvendu;
    private int remiseVenteVendu;
    private String titreDefaut;
    private long timestampFin;
    private long timestampFin2;
    private String idRegion;
    private String idDepartement;
    private int idCommune;
    private int typeEnvoi;
    private double fraisExpe;
    private String noteExpe;
    private String paypal;
    private int typePaiementCheque;
    private int typePaiementEspece;
    private int typePaiementTimbre;
    private long timestampEnregistrement;
    private long ecartTimestamp;
    private String pseudo;
    private String email;
    public EditForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
    }
    public boolean init(long idAnnonce, long idMembre) {
        this.setIdAnnonce(idAnnonce);
        this.setIdMembre(idMembre);
        boolean flag=false;
        try {
            Objet.getConnection();
            String query="SELECT id_categorie,id_sous_categorie,titre,description,prix_depart,pas_enchere,prix_reserve,prix_immediat,date_fin,heure_fin,minute_fin,remise_vente_invendu,remise_vente_vendu,id_region,id_departement,id_commune,type_envoi,frais_expe,note_expe,paypal,type_paiement_cheque,type_paiement_espece,type_paiement_timbre,timestamp_enregistrement,timestamp_fin,ecart_timestamp FROM table_annonces WHERE id=? AND id_membre=? AND etat='0' LIMIT 0,1";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.getIdAnnonce());
            prepare.setLong(2, this.getIdMembre());
            //this.setErrorMsg(prepare.toString());
            ResultSet result=prepare.executeQuery();
            boolean flag2=result.next();
            if(!flag2) {
                flag=false;
                result.close();
                prepare.close();
            } else {
                this.setIdCategorie(result.getInt("id_categorie"));
                this.setIdSousCategorie(result.getInt("id_sous_categorie"));
                this.setTitre(result.getString("titre"));
                this.setTitreDefaut(this.getTitre());
                this.setDescription(result.getString("description"));
                this.setPrixDepart(result.getDouble("prix_depart"));
                this.setPasEnchere(result.getDouble("pas_enchere"));
                this.setPrixReserve(result.getDouble("prix_reserve"));
                this.setPrixImmediat(result.getDouble("prix_immediat"));
                this.setDateFin(result.getString("date_fin"));
                this.setHeureFin(result.getString("heure_fin"));
                this.setMinuteFin(result.getString("minute_fin"));
                this.setRemiseVenteInvendu(result.getInt("remise_vente_invendu"));
                this.setRemiseVenteVendu(result.getInt("remise_vente_vendu"));
                this.setTimestampFin2(result.getLong("timestamp_fin"));
                this.setIdRegion(result.getString("id_region"));
                this.setIdDepartement(result.getString("id_departement"));
                this.setIdCommune(result.getInt("id_commune"));
                this.setTypeEnvoi(result.getInt("type_envoi"));
                this.setFraisExpe(result.getDouble("frais_expe"));
                this.setNoteExpe(result.getString("note_expe"));
                this.setPaypal(result.getString("paypal"));
                this.setTypePaiementCheque(result.getInt("type_paiement_cheque"));
                this.setTypePaiementEspece(result.getInt("type_paiement_espece"));
                this.setTypePaiementTimbre(result.getInt("type_paiement_timbre"));
                flag=true;
                result.close();
                prepare.close();
            }
            query="SELECT pseudo,email FROM table_membres WHERE id=? LIMIT 0,1";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, this.idMembre);
            result=prepare.executeQuery();
            result.next();
            this.pseudo=result.getString("pseudo");
            this.email=result.getString("email");
            result.close();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(EditForm1.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        } catch (SQLException ex) {
            Logger.getLogger(EditForm1.class.getName()).log(Level.SEVERE, null, ex);
            flag=false;
        }
        return flag;
    }
    public void verif(HttpServletRequest request) {
        this.setTitre(this.getTitre().replaceAll("<", "&lt;"));
        this.setTitre(this.getTitre().replaceAll(">", "&gt;"));
        this.setDescription(this.getDescription().replaceAll("<", "&lt;"));
        this.setDescription(this.getDescription().replaceAll(">", "&gt;"));
        if(this.getIdCategorie()==0)
            this.setErrorMsg("Veuillez choisir une CATÉGORIE SVP.<br/>");
        if(this.getIdSousCategorie()==0)
            this.setErrorMsg("Veuillez choisir une SOUS-CATÉGORIE SVP.<br/>");
        if(this.getTitre().length()==0)
            this.setErrorMsg("Champ TITRE vide.<br/>");
        else if(this.getTitre().length()>40)
            this.setErrorMsg("Champ TITRE trop long.<br/>");
        if(this.getDescription().length()==0)
            this.setErrorMsg("Champ DESCRIPTION vide.<br/>");
        else if(this.getDescription().length()>2000)
            this.setErrorMsg("Champ DESCRIPTION trop long.<br/>");
        if(this.getPasEnchere()==0)
            this.setErrorMsg("Votre PAS DE L'ENCHÈRE ne peut être nul.<br/>");
        Pattern p = Pattern.compile("^[0-3]{0,1}[0-9]{0,1}/[0-1]{0,1}[1-9]{0,1}/[0-9]{4}$");
        Matcher m = p.matcher(this.getDateFin());
        if(this.getDateFin()==null||this.getDateFin().length()==0)
            this.setErrorMsg("Champ DATE vide.<br/>");
        else if(m.matches()==false)
            this.setErrorMsg("Champ DATE non valide.<br/>");
        String[] arrayDate=this.getDateFin().split("/");
        if(this.getErrorMsg().length()==0) {
            if(arrayDate[0].length()!=2)
                arrayDate[0]="0"+arrayDate[0];
            if(arrayDate[1].length()!=2)
                arrayDate[1]="0"+arrayDate[1];
            if(arrayDate[2].length()!=4)
                arrayDate[2]="20"+arrayDate[2];
            String formatDate=arrayDate[2]+arrayDate[1]+arrayDate[0];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            Date date = null;
            try {
                date = dateFormat.parse(formatDate);
            } catch (ParseException ex) {
            this.setErrorMsg("DATE DE FIN non valide");
            }
        }
        if(this.getHeureFin()==null||this.getHeureFin().length()==0)
            this.setErrorMsg("Champ HEURE vide.<br/>");
        else {
            try {
                int h;
                h=Integer.parseInt(this.getHeureFin());
                if(h>23)
                    this.setErrorMsg("Champ HEURE non valide.<br/>");
            } catch(NumberFormatException ex) {
                this.setErrorMsg("Champ HEURE non valide.<br/>");
            }
        }
        if(this.getMinuteFin()==null||this.getMinuteFin().length()==0)
            this.setErrorMsg("Champ MINUTES vide.<br/>");
        else {
            try {
                int min;
                min=Integer.parseInt(this.getMinuteFin());
                if(min>59)
                    this.setErrorMsg("Champ MINUTES non valide.<br/>");
            } catch(NumberFormatException ex) {
                this.setErrorMsg("Champ MINUTES non valide.<br/>");
            }
        }
        if(this.getErrorMsg().length()==0) {
            Calendar cal=Calendar.getInstance();
            cal.set(Integer.parseInt(arrayDate[2]), Integer.parseInt(arrayDate[1])-1, Integer.parseInt(arrayDate[0]), Integer.parseInt(this.getHeureFin()), Integer.parseInt(this.getMinuteFin()), 0);
            this.setTimestampFin(cal.getTimeInMillis());
            if((this.getTimestampFin()/1000)<(this.getTimestampFin2()/1000))
                this.setErrorMsg("La date de fin de votre enchère doit être supérieure ou égale à l'ancienne");
    }
        if(this.getIdRegion()==null||this.getIdRegion().equals("0"))
            this.setErrorMsg("Veuillez choisir une RÉGION SVP.<br/>");
        if(this.getIdDepartement()==null||this.getIdDepartement().equals("0"))
            this.setErrorMsg("Veuilles choisir un DÉPARTEMENT SVP.<br/>");
        if(this.getIdCommune()==0)
            this.setErrorMsg("Veuillez choisir une COMMUNE SVP.<br/>");
        if(this.getPaypal().length()!=0) {
            p = Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$");
            m = p.matcher(this.getPaypal());
            if(this.getPaypal().length()>100)
                this.setErrorMsg("Champ PAYPAL trop long.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ PAYPAL non valide.<br/>");
        }
        if(getPaypal().length()==0&&this.getTypePaiementCheque()==0&&this.getTypePaiementEspece()==00&&this.getTypePaiementTimbre()==0)
            this.setErrorMsg("Veuillez choisir un MODE DE PAIEMENT SVP.<br/>");
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="UPDATE table_annonces SET id_categorie=?,id_sous_categorie=?,titre=?,description=?,pas_enchere=?,prix_reserve=?,prix_immediat=?,date_fin=?,heure_fin=?,minute_fin=?,remise_vente_invendu=?,remise_vente_vendu=?,id_region=?,id_departement=?,id_commune=?,type_envoi=?,frais_expe=?,note_expe=?,paypal=?,type_paiement_cheque=?,type_paiement_espece=?,type_paiement_timbre=?,timestamp_enregistrement=?,timestamp_fin=?,ecart_timestamp=? WHERE id=? AND id_membre=?";
                Calendar cal=Calendar.getInstance();
                this.timestampEnregistrement=cal.getTimeInMillis();
                this.ecartTimestamp=this.timestampFin-this.timestampEnregistrement;
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.idCategorie);
                prepare.setInt(2, this.idSousCategorie);
                prepare.setString(3, this.titre);
                prepare.setString(4, this.description);
                prepare.setDouble(5, this.pasEnchere);
                prepare.setDouble(6, this.prixReserve);
                prepare.setDouble(7, this.prixImmediat);
                prepare.setString(8, this.dateFin);
                prepare.setString(9, this.heureFin);
                prepare.setString(10, this.minuteFin);
                prepare.setInt(11, this.remiseVenteInvendu);
                prepare.setInt(12, this.remiseVenteVendu);
                prepare.setString(13, this.idRegion);
                prepare.setString(14, this.idDepartement);
                prepare.setInt(15, this.idCommune);
                prepare.setInt(16, this.typeEnvoi);
                prepare.setDouble(17, this.fraisExpe);
                prepare.setString(18, this.noteExpe);
                prepare.setString(19, this.paypal);
                prepare.setInt(20, this.typePaiementCheque);
                prepare.setInt(21, this.typePaiementEspece);
                prepare.setInt(22, this.typePaiementTimbre);
                prepare.setLong(23, this.timestampEnregistrement);
                prepare.setLong(24, this.timestampFin);
                prepare.setLong(25, this.ecartTimestamp);
                prepare.setLong(26, this.idAnnonce);
                prepare.setLong(27, this.idMembre);
                prepare.executeUpdate();
                Objet.closeConnection();
                Mail mail=new Mail(this.email, this.pseudo, Datas.TITRESITE+" - Annonce modifiée");
                mail.initMailModif1(this.pseudo, this.titre, this.idAnnonce);
                mail.send();
                HttpSession session=request.getSession(true);
                session.setAttribute("idAnnonce", this.idAnnonce);
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(EditForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(EditForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
    }
    public void blank() {
        this.setErrorMsg2("");
        this.setTest(0);
    }
    /**
     * @return the idAnnonce
     */
    public long getIdAnnonce() {
        return idAnnonce;
    }

    /**
     * @return the idMembre
     */
    public long getIdMembre() {
        return idMembre;
    }

    /**
     * @return the idCategorie
     */
    public int getIdCategorie() {
        return idCategorie;
    }

    /**
     * @return the idSousCategorie
     */
    public int getIdSousCategorie() {
        return idSousCategorie;
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
     * @param idAnnonce the idAnnonce to set
     */
    public void setIdAnnonce(long idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    /**
     * @param idMembre the idMembre to set
     */
    public void setIdMembre(long idMembre) {
        this.idMembre = idMembre;
    }

    /**
     * @param idCategorie the idCategorie to set
     */
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    /**
     * @param idSousCategorie the idSousCategorie to set
     */
    public void setIdSousCategorie(int idSousCategorie) {
        this.idSousCategorie = idSousCategorie;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the prixDepart
     */
    public double getPrixDepart() {
        return prixDepart;
    }

    /**
     * @param prixDepart the prixDepart to set
     */
    public void setPrixDepart(double prixDepart) {
        this.prixDepart = prixDepart;
    }

    /**
     * @return the pasEnchere
     */
    public double getPasEnchere() {
        return pasEnchere;
    }

    /**
     * @param pasEnchere the pasEnchere to set
     */
    public void setPasEnchere(double pasEnchere) {
        this.pasEnchere = pasEnchere;
    }

    /**
     * @return the prixReserve
     */
    public double getPrixReserve() {
        return prixReserve;
    }

    /**
     * @param prixReserve the prixReserve to set
     */
    public void setPrixReserve(double prixReserve) {
        this.prixReserve = prixReserve;
    }

    /**
     * @return the prixImmediat
     */
    public double getPrixImmediat() {
        return prixImmediat;
    }

    /**
     * @param prixImmediat the prixImmediat to set
     */
    public void setPrixImmediat(double prixImmediat) {
        this.prixImmediat = prixImmediat;
    }

    /**
     * @return the dateFin
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * @param dateFin the dateFin to set
     */
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * @return the heureFin
     */
    public String getHeureFin() {
        return heureFin;
    }

    /**
     * @param heureFin the heureFin to set
     */
    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * @return the minuteFin
     */
    public String getMinuteFin() {
        return minuteFin;
    }

    /**
     * @param minuteFin the minuteFin to set
     */
    public void setMinuteFin(String minuteFin) {
        this.minuteFin = minuteFin;
    }

    /**
     * @return the remiseVenteInvendu
     */
    public int getRemiseVenteInvendu() {
        return remiseVenteInvendu;
    }

    /**
     * @param remiseVenteInvendu the remiseVenteInvendu to set
     */
    public void setRemiseVenteInvendu(int remiseVenteInvendu) {
        this.remiseVenteInvendu = remiseVenteInvendu;
    }

    /**
     * @return the remiseVenteVendu
     */
    public int getRemiseVenteVendu() {
        return remiseVenteVendu;
    }

    /**
     * @param remiseVenteVendu the remiseVenteVendu to set
     */
    public void setRemiseVenteVendu(int remiseVenteVendu) {
        this.remiseVenteVendu = remiseVenteVendu;
    }

    /**
     * @return the titreDefaut
     */
    public String getTitreDefaut() {
        return titreDefaut;
    }

    /**
     * @param titreDefaut the titreDefaut to set
     */
    public void setTitreDefaut(String titreDefaut) {
        this.titreDefaut = titreDefaut;
    }

    /**
     * @return the timestampFin
     */
    public long getTimestampFin() {
        return timestampFin;
    }

    /**
     * @param timestampFin the timestampFin to set
     */
    public void setTimestampFin(long timestampFin) {
        this.timestampFin = timestampFin;
    }

    /**
     * @return the timestampFin2
     */
    public long getTimestampFin2() {
        return timestampFin2;
    }

    /**
     * @param timestampFin2 the timestampFin2 to set
     */
    public void setTimestampFin2(long timestampFin2) {
        this.timestampFin2 = timestampFin2;
    }

    /**
     * @return the idRegion
     */
    public String getIdRegion() {
        return idRegion;
    }

    /**
     * @param idRegion the idRegion to set
     */
    public void setIdRegion(String idRegion) {
        this.idRegion = idRegion;
    }

    /**
     * @return the idDepartement
     */
    public String getIdDepartement() {
        return idDepartement;
    }

    /**
     * @param idDepartement the idDepartement to set
     */
    public void setIdDepartement(String idDepartement) {
        this.idDepartement = idDepartement;
    }

    /**
     * @return the idCommune
     */
    public int getIdCommune() {
        return idCommune;
    }

    /**
     * @param idCommune the idCommune to set
     */
    public void setIdCommune(int idCommune) {
        this.idCommune = idCommune;
    }

    /**
     * @return the typeEnvoi
     */
    public int getTypeEnvoi() {
        return typeEnvoi;
    }

    /**
     * @param typeEnvoi the typeEnvoi to set
     */
    public void setTypeEnvoi(int typeEnvoi) {
        this.typeEnvoi = typeEnvoi;
    }

    /**
     * @return the fraisExpe
     */
    public double getFraisExpe() {
        return fraisExpe;
    }

    /**
     * @param fraisExpe the fraisExpe to set
     */
    public void setFraisExpe(double fraisExpe) {
        this.fraisExpe = fraisExpe;
    }

    /**
     * @return the noteExpe
     */
    public String getNoteExpe() {
        return noteExpe;
    }

    /**
     * @param noteExpe the noteExpe to set
     */
    public void setNoteExpe(String noteExpe) {
        this.noteExpe = noteExpe;
    }

    /**
     * @return the paypal
     */
    public String getPaypal() {
        return paypal;
    }

    /**
     * @param paypal the paypal to set
     */
    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    /**
     * @return the typePaiementCheque
     */
    public int getTypePaiementCheque() {
        return typePaiementCheque;
    }

    /**
     * @param typePaiementCheque the typePaiementCheque to set
     */
    public void setTypePaiementCheque(int typePaiementCheque) {
        this.typePaiementCheque = typePaiementCheque;
    }

    /**
     * @return the typePaiementEspece
     */
    public int getTypePaiementEspece() {
        return typePaiementEspece;
    }

    /**
     * @param typePaiementEspece the typePaiementEspece to set
     */
    public void setTypePaiementEspece(int typePaiementEspece) {
        this.typePaiementEspece = typePaiementEspece;
    }

    /**
     * @return the typePaiementTimbre
     */
    public int getTypePaiementTimbre() {
        return typePaiementTimbre;
    }

    /**
     * @param typePaiementTimbre the typePaiementTimbre to set
     */
    public void setTypePaiementTimbre(int typePaiementTimbre) {
        this.typePaiementTimbre = typePaiementTimbre;
    }
}
