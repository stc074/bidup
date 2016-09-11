/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class InsForm1 extends Formulaire {
    private String pseudo;
    private String motDePasse;
    private String motDePasse2;
    private String email;
    private String nom;
    private String prenom;
    private String adresse;
    private String codePostal;
    private String ville;
    private String captcha;
    private long idMembre;
    public InsForm1() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.pseudo="";
        this.motDePasse="";
        this.motDePasse2="";
        this.email="";
        this.nom="";
        this.prenom="";
        this.adresse="";
        this.codePostal="";
        this.ville="";
        this.captcha="";
    }
    public void verif(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(true);
            Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
            Matcher m = p.matcher(this.pseudo);
            if (this.pseudo == null || this.pseudo.length() == 0) {
                this.setErrorMsg("Champ PSEUDO vide.<br/>");
            } else if (this.pseudo.length() > 20) {
                this.setErrorMsg("Champ PSEUDO trop long(20 Car. max).<br/>");
            } else if (m.matches() == false) {
                this.setErrorMsg("Champ PSEUDO non valide (caractères Alphanumériques seulement).<br/>");
            }
            if(this.getErrorMsg().length()==0) {
                try {
                    Objet.getConnection();
                    Connection conn=Objet.getConn();
                    String query="SELECT COUNT(id) AS nb FROM table_membres WHERE pseudo=? OR pseudo=? OR pseudo=? LIMIT 0,1";
                    PreparedStatement prepare=conn.prepareStatement(query);
                    prepare.setString(1, this.pseudo);
                    prepare.setString(2, this.pseudo.toLowerCase());
                    prepare.setString(3, this.pseudo.toUpperCase());
                    ResultSet result=prepare.executeQuery();
                    result.next();
                    int nb=result.getInt("nb");
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                    if(nb!=0) {
                        this.setErrorMsg("Désolé, ce PSEUDO est déjà pris.<br/>");
                    }
                } catch (NamingException ex) {
                    Logger.getLogger(InsForm1.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(InsForm1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            m = p.matcher(this.motDePasse);
            if (this.motDePasse == null || this.motDePasse.length() == 0) {
                this.setErrorMsg("Champ MOT DE PASSE vide.<br/>");
            } else if (this.motDePasse.length() > 15) {
                this.setErrorMsg("Champ MOT DE PASSE trop long (15 Car. max).<br/>");
            } else if (m.matches() == false) {
                this.setErrorMsg("Champ MOT DE PASSE non valide (Caractères Alphanumériques seulement).<br/>");
            } else if (this.motDePasse2 == null || this.motDePasse2.length() == 0) {
                this.setErrorMsg("Champ CONFIRMATION vide.<br/>");
            } else if (this.motDePasse2.length() > 15) {
                this.setErrorMsg("Champ CONFIRMATION trop long.<br/>");
            } else if (!this.motDePasse.equals(this.motDePasse2)) {
                this.setErrorMsg("Champ CONFIRMATION différent du MOT DE PASSE.<br/>");
            }
            p = Pattern.compile("^[a-z0-9._-]+@[a-z0-9._-]{2,}\\.[a-z]{2,4}$");
            m = p.matcher(this.email);
            if (this.email == null || this.email.length() == 0) {
                this.setErrorMsg("Champ EMAIL vide.<br/>");
            } else if (this.email.length() > 100) {
                this.setErrorMsg("Champ EMAIL trop long(100 Car. max).<br/>");
            } else if (m.matches() == false) {
                this.setErrorMsg("Champ EMAIL non valide.<br/>");
            }
            if(this.getErrorMsg().length()==0) {
                try {
                    Objet.getConnection();
                    Connection conn=Objet.getConn();
                    String query="SELECT COUNT(id) AS nb FROM table_membres WHERE email=? LIMIT 0,1";
                    PreparedStatement prepare=conn.prepareStatement(query);
                    prepare.setString(1, this.email);
                    ResultSet result=prepare.executeQuery();
                    result.next();
                    int nb=result.getInt("nb");
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                    if(nb!=0)
                        this.setErrorMsg("Désolé il existe déjà un compte avec cette EMAIL.<br/>");
                } catch (NamingException ex) {
                    Logger.getLogger(InsForm1.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("Erreur interne.<br/>");
                } catch (SQLException ex) {
                    Logger.getLogger(InsForm1.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("Erreur interne.<br/>");
                }
            }
            if (this.nom == null || this.nom.length() == 0) {
                this.setErrorMsg("Champ NOM vide.<br/>");
            } else if (this.nom.length() > 100) {
                this.setErrorMsg("Champ NOM trop long(100 Car. max).<br/>");
            }
            if (this.prenom == null || this.prenom.length() == 0) {
                this.setErrorMsg("Champ PRÉNOM vide.<br/>");
            } else if (this.prenom.length() > 100) {
                this.setErrorMsg("Champ PRÉNOM trop long (100 Car. max).<br/>");
            }
            if (this.adresse == null || this.adresse.length() == 0) {
                this.setErrorMsg("Champ ADRESSE vide.<br/>");
            } else if (this.adresse.length() > 300) {
                this.setErrorMsg("Champ ADRESSE trop long(300 Car. max).<br/>");
            }
            if (this.codePostal == null || this.codePostal.length() == 0) {
                this.setErrorMsg("Champ CODE POSTAL vide.<br/>");
            } else if (this.codePostal.length() > 6) {
                this.setErrorMsg("Champ CODE POSTAL trop long.<br/>");
            }
            if (this.ville == null || this.ville.length() == 0) {
                this.setErrorMsg("Champ VILLE vide.<br/>");
            } else if (this.ville.length() > 100) {
                this.setErrorMsg("Champ VILLE trop long (100 Car. max).<br/>");
            }
            if (this.captcha == null || this.captcha.length() == 0) {
                this.setErrorMsg("Champ CODE ANTI-ROBOT vide.<br/>");
            } else if (this.captcha.length() != 5) {
                this.setErrorMsg("Champ CODE ANTI-ROBOT non valide (5 Car.).<br/>");
            } else if (!Objet.getEncoded(this.captcha).equals(session.getAttribute("captcha").toString())) {
                this.setErrorMsg("Mauvais CODE ANTI-ROBOT.<br/>");
            }
            if(this.getErrorMsg().length()==0) {
                try {
                    Objet.getConnection();
                    Connection conn=Objet.getConn();
                    String query="INSERT INTO table_membres (pseudo,mot_de_passe,email,nom,prenom,adresse,code_postal,ville,timestamp_inscription) VALUES (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement prepare=conn.prepareStatement(query);
                    String motDePasseCrypte=Objet.getEncoded(this.motDePasse);
                    Calendar cal=Calendar.getInstance();
                    long timestamp=cal.getTimeInMillis();
                    prepare.setString(1, this.pseudo);
                    prepare.setString(2, motDePasseCrypte);
                    prepare.setString(3, this.email);
                    prepare.setString(4, this.nom);
                    prepare.setString(5, this.prenom);
                    prepare.setString(6, this.adresse);
                    prepare.setString(7, this.codePostal);
                    prepare.setString(8, this.ville);
                    prepare.setLong(9, timestamp);
                    prepare.executeUpdate();
                    prepare.close();
                    query="SELECT LAST_INSERT_ID() AS idMembre FROM table_membres WHERE email=? LIMIT 0,1";
                    prepare=conn.prepareStatement(query);
                    prepare.setString(1, this.email);
                    ResultSet result=prepare.executeQuery();
                    boolean flag=result.next();
                    if(flag) {
                        this.idMembre=result.getLong("idMembre");
                        session.setAttribute("idMembre", this.idMembre);
                        Objet.setCookie(this.idMembre, response);
                    } else {
                        this.setErrorMsg("Erreur interne, veuillez réésayer SVP.<br/>");
                    }
                    result.close();
                    prepare.close();
                    Objet.closeConnection();
                    Mail mail=new Mail(this.email, this.pseudo, Datas.TITRESITE+" - Inscription");
                    mail.initMailIns1(this.pseudo, this.email, this.motDePasse);
                    mail.send();
                    this.setTest(1);
                } catch (NamingException ex) {
                    Logger.getLogger(InsForm1.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("Erreur interne1.<br/>");
                } catch (SQLException ex) {
                    Logger.getLogger(InsForm1.class.getName()).log(Level.SEVERE, null, ex);
                    this.setErrorMsg("Erreur interne2.<br/>");
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(InsForm1.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne.<br/>");
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setPseudo("");
        this.setMotDePasse("");
        this.setMotDePasse2("");
        this.setEmail("");
        this.setNom("");
        this.setPrenom("");
        this.setAdresse("");
        this.setCodePostal("");
        this.setVille("");
        this.setCaptcha("");
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

    /**
     * @return the motDePasse2
     */
    public String getMotDePasse2() {
        return motDePasse2;
    }

    /**
     * @param motDePasse2 the motDePasse2 to set
     */
    public void setMotDePasse2(String motDePasse2) {
        this.motDePasse2 = motDePasse2;
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

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * @param adresse the adresse to set
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * @return the codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * @param codePostal the codePostal to set
     */
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * @return the ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * @param ville the ville to set
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * @param captcha the captcha to set
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
