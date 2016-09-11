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
public class SousCatForm1 extends Formulaire {
    private int idCategorie;
    private String categorie;
    private String sousCategorie;
    public SousCatForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.idCategorie=0;
        this.categorie="";
        this.sousCategorie="";
    }
    public void init() {
        if(this.idCategorie!=0) {
            try {
                Objet.getConnection();
                String query="SELECT categorie FROM table_categories WHERE id=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.idCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                this.categorie=result.getString("categorie");
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NamingException ex) {
                Logger.getLogger(SousCatForm1.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(SousCatForm1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void verif() {
        if(this.sousCategorie==null||this.sousCategorie.length()==0)
            this.setErrorMsg("Champ SOUS-CATÉGORIE vide.<br/>");
        else if(this.sousCategorie.length()>100)
            this.setErrorMsg("Champ SOUS-CATÉGORIE trop long.<br/>");
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_sous_categories WHERE id_categorie=? AND sous_categorie=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                Objet.closeConnection();
                if(nb!=0)
                    this.setErrorMsg("Désolé cette SOUS-CATÉGORIE existe déja.<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(SousCatForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(SousCatForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="INSERT INTO table_sous_categories (id_categorie,sous_categorie) VALUES (?,?)";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setInt(1, this.idCategorie);
                prepare.setString(2, this.sousCategorie);
                prepare.executeUpdate();
                prepare.close();
                Objet.closeConnection();
                this.setTest(1);
            } catch (NamingException ex) {
                Logger.getLogger(SousCatForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(SousCatForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setCategorie("");
        this.setSousCategorie("");
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

    /**
     * @return the sousCategorie
     */
    public String getSousCategorie() {
        return sousCategorie;
    }

    /**
     * @param sousCategorie the sousCategorie to set
     */
    public void setSousCategorie(String sousCategorie) {
        this.sousCategorie = sousCategorie;
    }
}
