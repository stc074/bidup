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

/**
 *
 * @author pj
 */
public class MdpForm2 extends Formulaire {
    private String motDePasseActuel;
    private String motDePasse;
    private String motDePasse2;
    private long idMembre;
    public MdpForm2() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
    }
    public void verif(long idMembre) {
        try {
            this.idMembre = idMembre;
            Objet.getConnection();
            if (this.motDePasseActuel.length() == 0 || this.motDePasseActuel == null)
                this.setErrorMsg("Champ MOT DE PASSE ACTUEL vide.<br/>");
            else if(this.motDePasseActuel.length()>15)
                this.setErrorMsg("Champ MOT DE PASSE ACTUEL trop long.<br/>");
            else {
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE id=? AND mot_de_passe=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idMembre);
                prepare.setString(2, Objet.getEncoded(this.motDePasseActuel));
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                if(nb==0)
                    this.setErrorMsg("Ce n'est pas votre MOT DE PASSE ACTUEL.<br/>");
                result.close();
                prepare.close();
            }
            Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
            Matcher m = p.matcher(this.motDePasse);
            if(this.motDePasse.length()==0)
                this.setErrorMsg("Champ NOUVEAU MOT DE PASSE vide.<br/>");
            else if(this.motDePasse.length()<3)
                this.setErrorMsg("Champ NOUVEAU MOT DE PASSE trop court (3 Car. mini).<br/>");
            else if(this.motDePasse.length()>15)
                this.setErrorMsg("Champ NOUVEAU MOT DE PASSE trop long (15 Car. max).<br/>");
            else if(m.matches()==false)
                this.setErrorMsg("Champ NOUVEAU MOT DE PASSE non valide (Car alphanumériques seulement).<br/>");
            else if(this.motDePasse2.length() == 0)
                this.setErrorMsg("Champ CONFIRMATION vide.<br/>");
            else if(this.motDePasse2.length()>15)
                this.setErrorMsg("Champ CONFIRMATION trop long");
            else if(!this.motDePasse.equals(this.motDePasse2))
                this.setErrorMsg("Le champ CONFIRMATION est différent du NOUVEAU MOT DE PASSE.<br/>");
            if(this.getErrorMsg().length()==0) {
                String query="UPDATE table_membres SET mot_de_passe=? WHERE id=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, Objet.getEncoded(this.motDePasse));
                prepare.setLong(2, this.idMembre);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT pseudo,email FROM table_membres WHERE id=? LIMIT 0,1";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setLong(1, this.idMembre);
                ResultSet result=prepare.executeQuery();
                result.next();
                String pseudo=result.getString("pseudo");
                String email=result.getString("email");
                result.close();
                prepare.close();
                Mail mail=new Mail(email, pseudo, Datas.TITRESITE+" - nouveau mot de passe");
                mail.initMailMdp2(pseudo, email, this.motDePasse);
                mail.send();
                this.setTest(1);
            }
                Objet.closeConnection();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MdpForm2.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne.<br/>");
        } catch (NamingException ex) {
            this.setErrorMsg("Erreur interne.<br/>");
            Logger.getLogger(MdpForm2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MdpForm2.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne.<br/>");
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
    }

    /**
     * @param motDePasseActuel the motDePasseActuel to set
     */
    public void setMotDePasseActuel(String motDePasseActuel) {
        this.motDePasseActuel = motDePasseActuel;
    }

    /**
     * @param motDePasse the motDePasse to set
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * @param motDePasse2 the motDePasse2 to set
     */
    public void setMotDePasse2(String motDePasse2) {
        this.motDePasse2 = motDePasse2;
    }
}
