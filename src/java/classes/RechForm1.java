/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class RechForm1 extends Formulaire {
    private String idRegion;
    private String idDepartement;
    private int idCommune;
    private int type1;
    private int type2;
    //private int type3;
    private String rechercheQuery;
    private String recherche;
    private int idCategorie;
    private int idSousCategorie;
    private int page;
    public RechForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.recherche="";
        this.idCategorie=0;
        this.idSousCategorie=0;
        this.type1=0;
        this.type2=0;
        this.idRegion="0";
        this.idDepartement="0";
        this.idCommune=0;
        this.page=0;
    }
    public void init(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.setRecherche("");
        if(session.getAttribute("recherche")!=null)
            this.setRecherche(session.getAttribute("recherche").toString());
        this.setIdCategorie(0);
        if(session.getAttribute("idCategorie")!=null)
            this.setIdCategorie(Integer.parseInt(session.getAttribute("idCategorie").toString()));
        this.setIdSousCategorie(0);
        if(session.getAttribute("idSousCategorie")!=null)
            this.setIdSousCategorie(Integer.parseInt(session.getAttribute("idSousCategorie").toString()));
        this.setType1(0);
        if(session.getAttribute("type1")!=null)
            this.setType1(Integer.parseInt(session.getAttribute("type1").toString()));
        this.setType2(0);
        if(session.getAttribute("type2")!=null)
            this.setType2(Integer.parseInt(session.getAttribute("type2").toString()));
        //this.setType3(0);
        //if(session.getAttribute("type3")!=null)
            //this.setType3(Integer.parseInt(session.getAttribute("type3").toString()));
        this.setIdRegion("0");
        if(session.getAttribute("idRegion")!=null)
            this.setIdRegion(session.getAttribute("idRegion").toString());
        this.setIdDepartement("0");
        if(session.getAttribute("idDepartement")!=null)
            this.setIdDepartement(session.getAttribute("idDepartement").toString());
        this.setIdCommune(0);
        if(session.getAttribute("idCommune")!=null)
            this.setIdCommune(Integer.parseInt(session.getAttribute("idCommune").toString()));
        this.page=0;
        if(session.getAttribute("page")!=null)
            this.setPage(Integer.parseInt(session.getAttribute("page").toString()));
    }
    public void reset(HttpServletRequest request) {
        this.setRecherche("");
        this.setIdCategorie(0);
        this.setIdSousCategorie(0);
        this.setType1(0);
        this.setType2(0);
        //this.type3=0;
        this.setIdRegion("0");
        this.setIdDepartement("0");
        this.setIdCommune(0);
        this.setPage(0);
        HttpSession session=request.getSession(true);
        session.setAttribute("recherche", null);
        session.setAttribute("idCategorie", 0);
        session.setAttribute("idSousCategorie", 0);
        session.setAttribute("type1", 0);
        session.setAttribute("type2", 0);
        //session.setAttribute("type3", 0);
        session.setAttribute("idRegion", "0");
        session.setAttribute("idDepartement", "0");
        session.setAttribute("idCommune", 0);
        session.setAttribute("page", 0);
    }
    public void initRecherche() {
        this.setRechercheQuery(" WHERE t1.id=t2.id_annonce AND t1.etat='0'");
        if(this.getRecherche().length()>0) {
            String arrayRecherche[]=getRecherche().split(" ");
            for(String mot:arrayRecherche){
                boolean flag=true;
                for(String str:Datas.arrayArticles) {
                    if(mot.equals(str))
                        flag=false;
                }
                if(flag)
                    this.setRechercheQuery(this.getRechercheQuery() + " AND t1.titre LIKE '%" + mot + "%' AND t1.description LIKE '%" + mot + "%'");
                }
            }
        if(this.getIdSousCategorie()!=0)
            this.setRechercheQuery(this.getRechercheQuery() + " AND t1.id_sous_categorie='" + this.getIdSousCategorie() + "'");
        else if(this.getIdCategorie()!=0)
            this.setRechercheQuery(this.getRechercheQuery() + " AND t1.id_categorie='" + this.getIdCategorie() + "'");
        if(this.getIdCommune()!=0)
            this.setRechercheQuery(this.getRechercheQuery() + " AND id_commune='" + this.getIdCommune() + "'");
        else if(!this.idDepartement.equals("0"))
            this.setRechercheQuery(this.getRechercheQuery() + " AND id_departement='" + this.getIdDepartement() + "'");
        else if(!this.idRegion.equals("0"))
            this.setRechercheQuery(this.getRechercheQuery() + " AND t1.id_region='" + this.getIdRegion() + "'");
        switch(this.getType2()) {
            case 1:
                this.setRechercheQuery(this.getRechercheQuery() + " AND t1.type_envoi='1'");
                break;
                case 2:
                    this.setRechercheQuery(this.getRechercheQuery() + " AND t1.type_envoi='2'");
                    break;
        }
        switch(this.getType1()) {
            case 1:
                this.setRechercheQuery(this.getRechercheQuery() + " ORDER BY t1.timestamp_fin ASC");
                break;
                case 2:
                    this.setRechercheQuery(this.getRechercheQuery() + " ORDER BY t1.timestamp_enregistrement DESC");
                    break;
                        case 4:
                            this.setRechercheQuery(this.getRechercheQuery() + " ORDER BY t2.prix_actuel ASC");
                            break;
                            case 5:
                                this.setRechercheQuery(this.getRechercheQuery() + " ORDER BY (t2.prix_actuel+t1.frais_expe) ASC");
                                break;
                                case 6:
                                    this.setRechercheQuery(this.getRechercheQuery() + " AND t1.prix_immediat!='0'");
                                    break;
                                    case 7:
                                        this.setRechercheQuery(this.getRechercheQuery() + " AND t1.prix_immediat!='0' ORDER BY t1.prix_immediat ASC");
                                        break;
        }
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
     * @return the type1
     */
    public int getType1() {
        return type1;
    }

    /**
     * @param type1 the type1 to set
     */
    public void setType1(int type1) {
        this.type1 = type1;
    }

    /**
     * @return the type2
     */
    public int getType2() {
        return type2;
    }

    /**
     * @param type2 the type2 to set
     */
    public void setType2(int type2) {
        this.type2 = type2;
    }

    /**
     * @return the rechercheQuery
     */
    public String getRechercheQuery() {
        return rechercheQuery;
    }

    /**
     * @param rechercheQuery the rechercheQuery to set
     */
    public void setRechercheQuery(String rechercheQuery) {
        this.rechercheQuery = rechercheQuery;
    }

    /**
     * @return the recherche
     */
    public String getRecherche() {
        return recherche;
    }

    /**
     * @param recherche the recherche to set
     */
    public void setRecherche(String recherche) {
        this.recherche = recherche;
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
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }
}
