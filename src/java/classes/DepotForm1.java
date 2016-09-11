/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class DepotForm1 extends Formulaire {
    private int idCategorie;
    private int idSousCategorie;
    public DepotForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.idCategorie=0;
        this.idSousCategorie=0;
    }
    public void init(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        this.setIdCategorie(0);
        this.setIdSousCategorie(0);
        if(session.getAttribute("idCategorie")!=null)
            this.setIdCategorie(Integer.parseInt(session.getAttribute("idCategorie").toString()));
        if(session.getAttribute("idSousCategorie")!=null)
            this.setIdSousCategorie(Integer.parseInt(session.getAttribute("idSousCategorie").toString()));
    }
    public void verif(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        if(this.getIdCategorie()==0)
            this.setErrorMsg("Veuillez choisir une CATÉGORIE SVP.<br/>");
        if(this.getIdSousCategorie()==0)
            this.setErrorMsg("Veuiller choisir une SOUS-CATÉGORIE SVP.<br/>");
        if(this.getErrorMsg().length()==0) {
            this.setTest(1);
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setIdCategorie(0);
        this.setIdSousCategorie(0);
    }
    public void blank2(HttpServletRequest request) {
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
}
