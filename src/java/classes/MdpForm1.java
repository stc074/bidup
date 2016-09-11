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
public class MdpForm1 extends Formulaire {
    private String email;
    private String pseudo;
    public MdpForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.email="";
    }
    public void verifMdpOublie() {
        Pattern p = Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$");
        Matcher m = p.matcher(this.email);
        if(this.email==null||this.email.length()==0)
            this.setErrorMsg("Champ EMAIL vide.<br/>");
        else if(this.email.length()>100)
            this.setErrorMsg("Champ EMAIL trop long.<br/>");
        else if(m.matches()==false)
            this.setErrorMsg("Champ EMAIL non valide.<br/>");
        if(this.getErrorMsg().length()==0) {
            try {
                Objet.getConnection();
                String query="SELECT COUNT(id) AS nb FROM table_membres WHERE email=? LIMIT 0,1";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                result.next();
                int nb=result.getInt("nb");
                result.close();
                prepare.close();
                Objet.closeConnection();
                if(nb==0)
                    this.setErrorMsg("Désolé cet EMAIL n'est pas enregistré.<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(MdpForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(MdpForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
        if(this.getErrorMsg().length()==0) {
            try {
                String motDePasse="";
                for(int i=1;i<=6;i++)
                    motDePasse+=Datas.arrayChars[(int)((double)(Datas.arrayChars.length-1)*Math.random())];
                String motDePasseCrypte=Objet.getEncoded(motDePasse);
                Objet.getConnection();
                String query="UPDATE table_membres SET mot_de_passe=? WHERE email=?";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, Objet.getEncoded(motDePasse));
                prepare.setString(2, this.email);
                prepare.executeUpdate();
                prepare.close();
                query="SELECT pseudo FROM table_membres WHERE email=? LIMIT 0,1";
                prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, this.email);
                ResultSet result=prepare.executeQuery();
                boolean flag=result.next();
                if(flag) {
                    this.pseudo=result.getString("pseudo");
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                    Mail mail=new Mail(this.email, this.pseudo, Datas.TITRESITE+" - Nouveau mot de passe");
                    mail.initMailMdp1(this.pseudo, this.email, motDePasse);
                    mail.send();
                    this.setTest(1);
                } else {
                    this.setErrorMsg("Erreur interne, veuillez réessayer.<br/>");
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(MdpForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (NamingException ex) {
                Logger.getLogger(MdpForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            } catch (SQLException ex) {
                Logger.getLogger(MdpForm1.class.getName()).log(Level.SEVERE, null, ex);
                this.setErrorMsg("Erreur interne.<br/>");
            }
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setEmail("");
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
