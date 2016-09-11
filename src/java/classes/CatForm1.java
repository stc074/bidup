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
public class CatForm1 extends Formulaire {
    private String categorie;
    public CatForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.categorie="";
    }
    public void verif() {
        if(this.categorie==null||this.categorie.length()==0)
            this.setErrorMsg("Champ CATÉGORIE vide");
        else if(this.categorie.length()>100)
            this.setErrorMsg("Champ CATÉGORIE trop long.<br/>");
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_categories WHERE categorie=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.categorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                Objet.closeConnection();
                if(nb!=0)
                    this.setErrorMsg("Cette CATÉGORIE existe déjà.<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(CatForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(CatForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="INSERT INTO table_categories (categorie) VALUES (?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.categorie);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(CatForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(CatForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setCategorie("");
    }

    /**
     * @return the categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * @param categorie the categorie to set
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
