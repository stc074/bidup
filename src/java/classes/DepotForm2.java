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
public class DepotForm2 extends Formulaire {
    private String titre;
    private String description;
    public DepotForm2() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.titre="";
        this.description="";
    }
    public void init(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if(session.getAttribute("titre")!=null)
            this.setTitre(session.getAttribute("titre").toString());
        if(session.getAttribute("description")!=null)
            this.setDescription(session.getAttribute("description").toString());
    }
    public void verif(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        this.titre=this.titre.replaceAll("<", "&lt;");
        this.description=this.description.replaceAll("<", "&lt;");
        this.titre=this.titre.replaceAll(">", "&gt;");
        this.description=this.description.replaceAll(">", "&gt;");
        if(this.titre==null||this.titre.length()==0)
            this.setErrorMsg("Champ TITRE vide.<br/>");
        else if(this.titre.length()>40)
            this.setErrorMsg("Champ TITRE trop long.<br/>");
        if(this.description==null||this.description.length()==0)
            this.setErrorMsg("Champ DESCRIPTION vide.<br/>");
        else if(this.description.length()>2000)
            this.setErrorMsg("Champ DESCRIPTION trop long.<br/>");
        if(this.getErrorMsg().length()==0) {
            this.setTest(1);
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setTitre("");
        this.setDescription("");
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
}
