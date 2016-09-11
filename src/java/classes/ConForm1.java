/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class ConForm1 extends Formulaire {
    private String pseudo;
    private String motDePasse;
    private long idMembre;
    public ConForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.pseudo="";
        this.motDePasse="";
    }
    public void verifCnx1(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher m = p.matcher(this.pseudo);
        if(this.pseudo==null||this.pseudo.length()==0)
            this.setErrorMsg("Champ PSEUDO vide.<br/>");
        else if(this.pseudo.length()>20)
            this.setErrorMsg("Champ PSEUDO trop long.<br/>");
        else if(m.matches()==false)
           this.setErrorMsg("champ PSEUDO non valide.<br/>");
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.pseudo);
                //this.setErrorMsg(prepare.toString());
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                Objet.closeConnection();
                if(nb==0)
                    this.setErrorMsg("PSEUDO inconnu.<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(ConForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(ConForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
        if(this.motDePasse==null||this.motDePasse.length()==0)
            this.setErrorMsg("Champ MOT DE PASSE vide.<br/>");
        else if(this.motDePasse.length()>15)
            this.setErrorMsg("Champ MOT DE PASSE trop long.<br/>");
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="SELECT id FROM table_membres WHERE pseudo=? AND mot_de_passe=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                String motDePasseCrypte=Objet.getEncoded(this.motDePasse);
                prepare.setString(1, this.pseudo);
                prepare.setString(2, motDePasseCrypte);
                ResultSet result=prepare.executeQuery();
                boolean flag=result.next();
                if(!flag) {
                    this.setErrorMsg("Mauvais MOT DE PASSE.<br/>");
                } else {
                    this.idMembre=result.getLong("id");
                    session.setAttribute("idMembre", this.idMembre);
                    Objet.setCookie(this.idMembre, response);
                    this.setTest(1);
                }
                result.close();
                prepare.close();
                Objet.closeConnection();
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ConForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(ConForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(ConForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setPseudo("");
        this.setMotDePasse("");
    }

    /**
     * @return the pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * @param pseudo the pseudo to set
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * @return the motDePasse
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * @param motDePasse the motDePasse to set
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
