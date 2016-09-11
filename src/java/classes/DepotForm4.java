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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class DepotForm4 extends Formulaire {
    private int idCommune;
    private String idRegion;
    private String idDepartement;
    private int typeEnvoi;
    private double fraisExpe;
    private String noteExpe;
    private String paypal;
    private int typePaiementCheque;
    private int typePaiementEspece;
    private int typePaiementTimbre;
    private int idCategorie;
    private int idSousCategorie;
    private String titre;
    private String description;
    private double prixDepart;
    private double pasEnchere;
    private double prixReserve;
    private double prixImmediat;
    private int remiseVenteInvendu;
    private int remiseVenteVendu;
    private String dateFin;
    private String heureFin;
    private String minuteFin;
    private long idMembre;
    private String pseudo;
    private String email;
    private long idAnnonce;
    public DepotForm4() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
        this.typeEnvoi=0;
        this.fraisExpe=0.0;
        this.noteExpe="";
        this.paypal="";
        this.typePaiementCheque=0;
        this.typePaiementEspece=0;
        this.typePaiementTimbre=0;
    }
    public void init(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(session.getAttribute("idRegion")!=null)
            this.setIdRegion(session.getAttribute("idRegion").toString());
        if(session.getAttribute("idDepartement")!=null)
            this.setIdDepartement(session.getAttribute("idDepartement").toString());
        if(session.getAttribute("idCommune")!=null)
            this.setIdCommune(Integer.parseInt(session.getAttribute("idCommune").toString()));
        if(session.getAttribute("typeEnvoi")!=null)
            this.setTypeEnvoi(Integer.parseInt(session.getAttribute("typeEnvoi").toString()));
        if(session.getAttribute("fraisExpe")!=null)
            this.setFraisExpe(Double.parseDouble(session.getAttribute("fraisExpe").toString()));
        if(session.getAttribute("noteExpe")!=null)
            this.setNoteExpe(session.getAttribute("noteExpe").toString());
        if(session.getAttribute("paypal")!=null)
            this.setPaypal(session.getAttribute("paypal").toString());
        if(session.getAttribute("typePaiementCheque")!=null)
            this.setTypePaiementCheque(Integer.parseInt(session.getAttribute("typePaiementCheque").toString()));
        if(session.getAttribute("typePaiementEspece")!=null)
            this.setTypePaiementEspece(Integer.parseInt(session.getAttribute("typePaiementEspece").toString()));
        if(session.getAttribute("typePaiementTimbre")!=null)
            this.setTypePaiementTimbre(Integer.parseInt(session.getAttribute("typePaiementTimbre").toString()));
    }
    public void verif(HttpServletRequest request) {
        if(this.getIdRegion()==null||this.getIdRegion().equals("0"))
            this.setErrorMsg("Veuillez choisir la RÉGION SVP.<br/>");
        if(this.getIdDepartement()==null||this.getIdDepartement().equals("0"))
            this.setErrorMsg("Veuillez choisir UN DÉPARTEMENT SVP.<br/>");
        if(this.getIdCommune()==0)
            this.setErrorMsg("Veuillez choisir UNE COMMUNE SVP.<br/>");
        if(this.getTypeEnvoi()==0)
            this.setErrorMsg("Veuillez choisir le MOYEN D'EXPÉDITION SVP.<br/>");
        if(this.getPaypal().length()!=0) {
            Pattern p = Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$");
            Matcher m = p.matcher(this.getPaypal());
            if(this.getPaypal().length()>100)
                this.setErrorMsg("Champ PAYPAL trop long.<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ PAYPAL non valide.<br/>");
        }
        if(getPaypal().length()==0&&this.getTypePaiementCheque()==0&&this.getTypePaiementEspece()==00&&this.getTypePaiementTimbre()==0)
            this.setErrorMsg("Veuillez choisir un MODE DE PAIEMENT SVP.<br/>");
        if(this.getErrorMsg().length()==0) {
            HttpSession session=request.getSession(true);
            try {
                this.setIdCategorie(Integer.parseInt(session.getAttribute("idCategorie").toString()));
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ CATÉGORIE vide (Revenez à l'étape 1).<br/>");
                session.setAttribute("idSousCategorie", null);
            }
            try {
                this.setIdSousCategorie(Integer.parseInt(session.getAttribute("idSousCategorie").toString()));
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ SOUS-CATÉGORIE vide (Revenez à l'étape 1).<br/>");
            }
            try {
                this.setTitre(session.getAttribute("titre").toString());
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ TITRE vide (Revenez à l'étape 2).<br/>");
            }
            try {
                this.setDescription(session.getAttribute("description").toString());
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ DESCRIPTION vide (Revenez à l'étape 2).<br/>");
            }
            try {
                this.setPrixDepart(Double.parseDouble(session.getAttribute("prixDepart").toString()));
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ PRIX DE DÉPART non renseigné (Revenez à l'étape 3).<br/>");
            }
            try {
                this.setPasEnchere(Double.parseDouble(session.getAttribute("pasEnchere").toString()));
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ PAS DE L'ENCHÈRE non renseigné (Revenez à l'étape 3).<br/>");
            }
            try {
                this.setPrixReserve(Double.parseDouble(session.getAttribute("prixReserve").toString()));
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ PRIX DE RÉSERVE non renseigné (Revenez à l'étape 3).<br/>");
            }
            try {
                this.setPrixImmediat(Double.parseDouble(session.getAttribute("prixImmediat").toString()));
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ PRIX IMMÉDIAT non renseigné (Revenez à l'étape 3).<br/>");
            }
            try {
                this.setDateFin(session.getAttribute("dateFin").toString());
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ DATE DE FIN DE L'ENCHÈRE  non renseigné (Revenez à l'étape 3).<br/>");
            }
            try {
                this.setHeureFin(session.getAttribute("heureFin").toString());
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ HEURE DE FIN DE L'ENCHÈRE  non renseigné (Revenez à l'étape 3).<br/>");
            }
            try {
                this.setMinuteFin(session.getAttribute("minuteFin").toString());
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ MINUTES DE FIN DE L'ENCHÈRE  non renseigné (Revenez à l'étape 3).<br/>");
            }
            try {
                this.setRemiseVenteInvendu(Integer.parseInt(session.getAttribute("remiseVenteInvendu").toString()));
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ REMISE EN VENTE DE L'INVENDU non renseigné (Revenez à l'étape 3).<br/>");
            }
            try {
                this.setRemiseVenteVendu(Integer.parseInt(session.getAttribute("remiseVenteVendu").toString()));
            } catch(NullPointerException ex) {
                this.setErrorMsg("Champ REMISE EN VENTE DE L'OBJET VENDU non renseigné (Revenez à l'étape 3).<br/>");
            }
            if(this.getErrorMsg().length()==0) {
                Calendar cal=Calendar.getInstance();
                long timestampEnregistrement=cal.getTimeInMillis();
                long lastTimestamp=cal.getTimeInMillis();
                String[] arrayDate=this.getDateFin().split("/");
                cal.set(Integer.parseInt(arrayDate[2]), Integer.parseInt(arrayDate[1])-1, Integer.parseInt(arrayDate[0]), Integer.parseInt(this.getHeureFin()), Integer.parseInt(this.getMinuteFin()), 0);
                long timestampFin=cal.getTimeInMillis();
                long ecartTimestamp=timestampFin-lastTimestamp;
                try {
                    Objet.getConnection();
                    String query="SELECT pseudo,email FROM table_membres WHERE id=? LIMIT 0,1";
                    PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.idMembre);
                    ResultSet result=prepare.executeQuery();
                    result.next();
                    this.pseudo=result.getString("pseudo");
                    this.email=result.getString("email");
                    result.close();
                    prepare.close();
                    query="INSERT INTO table_annonces (id_membre,id_categorie,id_sous_categorie,titre,description,prix_depart,pas_enchere,prix_reserve,prix_immediat,date_fin,heure_fin,minute_fin,remise_vente_invendu,remise_vente_vendu,id_region,id_departement,id_commune,type_envoi,frais_expe,note_expe,paypal,type_paiement_cheque,type_paiement_espece,type_paiement_timbre,timestamp_enregistrement,timestamp_fin,ecart_timestamp,etat) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'0')";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.idMembre);
                    prepare.setInt(2, this.idCategorie);
                    prepare.setInt(3, this.idSousCategorie);
                    prepare.setString(4, this.titre);
                    prepare.setString(5, this.description);
                    prepare.setDouble(6, this.prixDepart);
                    prepare.setDouble(7, this.pasEnchere);
                    prepare.setDouble(8, this.prixReserve);
                    prepare.setDouble(9, this.prixImmediat);
                    prepare.setString(10, this.dateFin);
                    prepare.setString(11, this.heureFin);
                    prepare.setString(12, this.minuteFin);
                    prepare.setInt(13, this.remiseVenteInvendu);
                    prepare.setInt(14, this.remiseVenteVendu);
                    prepare.setString(15, this.idRegion);
                    prepare.setString(16, this.idDepartement);
                    prepare.setInt(17, this.idCommune);
                    prepare.setInt(18, this.typeEnvoi);
                    prepare.setDouble(19, this.fraisExpe);
                    prepare.setString(20, this.noteExpe);
                    prepare.setString(21, this.paypal);
                    prepare.setInt(22, this.typePaiementCheque);
                    prepare.setInt(23, this.typePaiementEspece);
                    prepare.setInt(24, this.typePaiementTimbre);
                    prepare.setLong(25, timestampEnregistrement);
                    prepare.setLong(26, timestampFin);
                    prepare.setLong(27, ecartTimestamp);
                    //this.setErrorMsg(prepare.toString());
                    prepare.executeUpdate();
                    prepare.close();
                    query="SELECT LAST_INSERT_ID() AS idAnnonce FROM table_annonces WHERE id_membre=?";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.idMembre);
                    result=prepare.executeQuery();
                    result.next();
                    this.idAnnonce=result.getLong("idAnnonce");
                    session.setAttribute("idAnnonce", this.idAnnonce);
                    result.close();
                    prepare.close();
                    query="INSERT INTO table_encheres (id_annonce,prix_actuel) VALUES (?,?)";
                    prepare=Objet.getConn().prepareStatement(query);
                    prepare.setLong(1, this.idAnnonce);
                    prepare.setDouble(2, this.prixDepart);
                    prepare.executeUpdate();
                    prepare.close();
                    Objet.closeConnection();
                    Mail mail=new Mail(this.email, this.pseudo, Datas.TITRESITE+" - Bien mis aux enchères");
                    mail.initMailAnn1(this.pseudo, this.titre);
                    mail.send();
                    this.setTest(1);
                } catch (NamingException ex) {
                    Logger.getLogger(DepotForm4.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("Erreur interne, veuillez réessayer SVP.<br/>");
                } catch (SQLException ex) {
                    Logger.getLogger(DepotForm4.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("Erreur interne, veuillez réessayer SVP.<br/>");
                }
            }
        }
    }
    public void blank(HttpServletRequest request) {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setIdRegion("0");
        this.setIdDepartement("0");
        this.setIdCommune(0);
        this.setTypeEnvoi(0);
        this.setFraisExpe(0.0);
        this.setNoteExpe("");
        this.setPaypal("");
        this.setTypePaiementCheque(0);
        this.setTypePaiementEspece(0);
        this.setTypePaiementTimbre(0);
        HttpSession session=request.getSession(true);
        session.setAttribute("idCategorie", null);
        session.setAttribute("idSousCategorie", null);
        session.setAttribute("titre", null);
        session.setAttribute("description", null);
        session.setAttribute("prixDepart", null);
        session.setAttribute("pasEnchere", null);
        session.setAttribute("prixReserve", null);
        session.setAttribute("prixImmediat", null);
        session.setAttribute("dateFin", null);
        session.setAttribute("heureFin", null);
        session.setAttribute("minuteFin", null);
        session.setAttribute("remiseVenteInvendu", null);
        session.setAttribute("remiseVenteVendu", null);
        session.setAttribute("idRegion", null);
        session.setAttribute("idDepartement", null);
        session.setAttribute("idCommune", null);
        session.setAttribute("typeEnvoi", null);
        session.setAttribute("fraisExpe", null);
        session.setAttribute("noteExpe", null);
        session.setAttribute("paypal", null);
        session.setAttribute("typePaiementCheque", null);
        session.setAttribute("typePaiementEspece", null);
        session.setAttribute("typePaiementTimbre", null);
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

    /**
     * @return the idCategorie
     */
    public int getIdCategorie() {
        return idCategorie;
    }

    /**
     * @param idCategorie the idCategorie to set
     */
    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    /**
     * @return the idSousCategorie
     */
    public int getIdSousCategorie() {
        return idSousCategorie;
    }

    /**
     * @param idSousCategorie the idSousCategorie to set
     */
    public void setIdSousCategorie(int idSousCategorie) {
        this.idSousCategorie = idSousCategorie;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
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
     * @return the idMembre
     */
    public long getIdMembre() {
        return idMembre;
    }

    /**
     * @param idMembre the idMembre to set
     */
    public void setIdMembre(long idMembre) {
        this.idMembre = idMembre;
    }
}
