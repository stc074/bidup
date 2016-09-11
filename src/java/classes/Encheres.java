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
import javax.naming.NamingException;

/**
 *
 * @author pj
 */
public class Encheres extends Objet {
    public Encheres() {
        super();
    }
    public void verif() {
        Calendar cal=Calendar.getInstance();
        long timestamp=cal.getTimeInMillis();
        Mail mail;
        try {
            Objet.getConnection();
            String query="UPDATE table_annonces SET etat='1' WHERE timestamp_fin<=? AND etat='0'";
            PreparedStatement prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, timestamp);
            prepare.executeUpdate();
            prepare.close();
            query="SELECT t1.id,t1.id_membre,t1.titre,t1.prix_depart,t1.prix_reserve,t1.remise_vente_invendu,t1.remise_vente_vendu,t1.frais_expe,t1.ecart_timestamp,t2.pseudo,t2.email FROM table_annonces AS t1,table_membres AS t2 WHERE t2.id=t1.id_membre AND t1.etat='1' AND timestamp_fin<=?";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, timestamp);
            ResultSet result=prepare.executeQuery();
            boolean flag=false;
            while(result.next()) {
                boolean invendu=true;
                long idAnnonce=result.getLong("id");
                long idMembreVendeur=result.getLong("id_membre");
                String titre=result.getString("titre");
                double prixDepart=result.getDouble("prix_depart");
                double prixReserve=result.getDouble("prix_reserve");
                int remiseVenteInvendu=result.getInt("remise_vente_invendu");
                int remiseVenteVendu=result.getInt("remise_vente_vendu");
                double fraisExpe=result.getDouble("frais_expe");
                long ecartTimestamp=result.getLong("ecart_timestamp");
                String pseudoVendeur=result.getString("pseudo");
                String emailVendeur=result.getString("email");
                String query2="SELECT t1.id_membre,t1.montant_enchere,t2.pseudo,t2.email FROM historiques_encheres AS t1,table_membres AS t2 WHERE t1.id_enchere_finie='0' AND t2.id=t1.id_membre AND t1.id_annonce=? ORDER BY t1.montant_enchere DESC LIMIT 0,1";
                PreparedStatement prepare2=Objet.getConn().prepareStatement(query2);
                prepare2.setLong(1, idAnnonce);
                ResultSet result2=prepare2.executeQuery();
                flag=result2.next();
                if(flag) {
                    invendu=false;
                    long idMembreAcheteur=result2.getLong("id_membre");
                    double montantEnchere=result2.getDouble("montant_enchere");
                    double montantTotal=Objet.convertDouble(montantEnchere+fraisExpe);
                    String pseudoAcheteur=result2.getString("pseudo");
                    String emailAcheteur=result2.getString("email");
                    boolean vendu=false;
                    if(montantEnchere>=prixReserve) {
                    String query3="INSERT INTO table_encheres_finies (id_annonce,id_membre_vendeur,id_membre_acheteur,montant,montant_total,frais_expe,timestamp,etat) VALUES (?,?,?,?,?,?,?,'0')";
                    PreparedStatement prepare3=Objet.getConn().prepareStatement(query3);
                    prepare3.setLong(1, idAnnonce);
                    prepare3.setLong(2, idMembreVendeur);
                    prepare3.setLong(3, idMembreAcheteur);
                    prepare3.setDouble(4, montantEnchere);
                    prepare3.setDouble(5, montantTotal);
                    prepare3.setDouble(6, fraisExpe);
                    prepare3.setLong(7, timestamp);
                    prepare3.executeUpdate();
                    prepare3.close();
                    query3="SELECT LAST_INSERT_ID() AS idEnchereFinie FROM table_encheres_finies WHERE id_annonce=? AND id_membre_acheteur=? LIMIT 0,1";
                    prepare3=Objet.getConn().prepareStatement(query3);
                    prepare3.setLong(1, idAnnonce);
                    prepare3.setLong(2, idMembreAcheteur);
                    ResultSet result3=prepare3.executeQuery();
                    result3.next();
                    long idEnchereFinie=result3.getLong("idEnchereFinie");
                    result3.close();
                    prepare3.close();
                    query3="UPDATE historiques_encheres SET id_enchere_finie=? WHERE id_annonce=? AND id_enchere_finie='0'";
                    prepare3=Objet.getConn().prepareStatement(query3);
                    prepare3.setLong(1, idEnchereFinie);
                    prepare3.setLong(2, idAnnonce);
                    prepare3.executeUpdate();
                    prepare3.close();
                    mail=new Mail(emailAcheteur, pseudoAcheteur, Datas.TITRESITE+" - Objet remporté !");
                    mail.initMailEnch2(pseudoAcheteur, pseudoVendeur, titre, montantEnchere, idEnchereFinie, idMembreVendeur);
                    mail.send();
                    mail=new Mail(emailVendeur, pseudoVendeur, Datas.TITRESITE+" - Objet vendu");
                    mail.initMailEnch3(pseudoAcheteur, pseudoVendeur, titre, montantEnchere, idMembreAcheteur);
                    mail.send();
                    vendu=true;
                    invendu=false;
                    if(remiseVenteVendu==1) {
                        cal.setTimeInMillis(timestamp+ecartTimestamp);
                        String dateFin=cal.get(Calendar.DATE)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);
                        int heureFin=cal.get(Calendar.HOUR_OF_DAY);
                        int minuteFin=cal.get(Calendar.MINUTE);
                        query3="UPDATE table_annonces SET etat='0',date_fin=?,heure_fin=?,minute_fin=?,timestamp_enregistrement=?,timestamp_fin=? WHERE id=?";
                        prepare3=Objet.getConn().prepareStatement(query3);
                        prepare3.setString(1, dateFin);
                        prepare3.setString(2, ""+heureFin);
                        prepare3.setString(3, ""+minuteFin);
                        prepare3.setLong(4, timestamp);
                        prepare3.setLong(5, timestamp+ecartTimestamp);
                        prepare3.setLong(6, idAnnonce);
                        //this.setErrorMsg(prepare.toString());
                        prepare3.executeUpdate();
                        prepare3.close();
                        query3="UPDATE table_encheres SET prix_actuel=?,nombre_encheres='0' WHERE id_annonce=?";
                        prepare3=Objet.getConn().prepareStatement(query3);
                        prepare3.setDouble(1, prixDepart);
                        prepare3.setLong(2, idAnnonce);
                        prepare3.executeUpdate();
                        prepare3.close();
                    } else {
                        query3="UPDATE table_annonces SET etat='2' WHERE id=?";
                        prepare3=Objet.getConn().prepareStatement(query3);
                        prepare3.setLong(1, idAnnonce);
                        prepare3.executeUpdate();
                        prepare3.close();
                    }
                    } else {
                        invendu=true;
                    }
                    PreparedStatement prepare3;
                    if(vendu) {
                        String query3="SELECT t1.montant_enchere,t2.pseudo,t2.email FROM historiques_encheres AS t1,table_membres AS t2 WHERE t1.id_annonce=? AND t1.id_membre!=? AND t2.id=t1.id_membre";
                        prepare3=Objet.getConn().prepareStatement(query3);
                        prepare3.setLong(1, idAnnonce);
                        prepare3.setLong(2, idMembreAcheteur);
                        } else {
                        String query3="SELECT t1.montant_enchere,t2.pseudo,t2.email FROM historiques_encheres AS t1,table_membres AS t2 WHERE t1.id_annonce=? AND t2.id=t1.id_membre";
                        prepare3=Objet.getConn().prepareStatement(query3);
                        prepare3.setLong(1, idAnnonce);
                        }
                    ResultSet result3=prepare3.executeQuery();
                    String[] arrayEmails = new String[10000];
                    for(int i=0;i<arrayEmails.length;i++)
                        arrayEmails[i]="";
                    int i=0;
                    while(result3.next()) {
                        double montant=result3.getDouble("montant_enchere");
                        String pseudo=result3.getString("pseudo");
                        String email=result3.getString("email");
                        boolean ok=true;
                        for(int j=0;j<=i;j++) {
                            if(arrayEmails[j].equals(email))
                                ok=false;
                        }
                        if(ok) {
                            mail=new Mail(email, pseudo, Datas.TITRESITE+" - Objet non remporté");
                            mail.initMailEnch4(pseudo, pseudoVendeur, titre, montant, montantEnchere);
                            mail.send();
                            arrayEmails[i]=email;
                            i++;
                        }
                    }
                    result3.close();
                    prepare3.close();
                }
                result2.close();
                prepare2.close();
                    if(invendu==true&&remiseVenteInvendu==1) {
                        String query3="UPDATE table_annonces SET etat='0',timestamp_enregistrement=?,timestamp_fin=? WHERE id=?";
                        PreparedStatement prepare3=Objet.getConn().prepareStatement(query3);
                        prepare3.setLong(1, timestamp);
                        prepare3.setLong(2, timestamp+ecartTimestamp);
                        prepare3.setLong(3, idAnnonce);
                        prepare3.executeUpdate();
                        prepare3.close();
                        query3="UPDATE table_encheres SET prix_actuel=?,nombre_encheres='0' WHERE id_annonce=?";
                        prepare3=Objet.getConn().prepareStatement(query3);
                        prepare3.setDouble(1, prixDepart);
                        prepare3.setLong(2, idAnnonce);
                        prepare3.executeUpdate();
                        prepare3.close();
                    } else if(invendu==true&&remiseVenteInvendu==0) {
                        String query3="UPDATE table_annonces SET etat='3' WHERE id=?";
                        PreparedStatement prepare3=Objet.getConn().prepareStatement(query3);
                        prepare3.setLong(1, idAnnonce);
                        prepare3.executeUpdate();
                        prepare3.close();
                    }
            }
            result.close();
            prepare.close();
            cal=Calendar.getInstance();
            long timestampLimite=cal.getTimeInMillis()-(1000*60*60);
            query="UPDATE table_annonces AS t1,table_achats_immediats AS t2 SET t1.etat='0' WHERE t2.timestamp<? AND t2.etat='0' AND t1.etat='1' AND t1.id=t2.id_annonce";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, timestampLimite);
            prepare.executeUpdate();
            prepare.close();
            query="DELETE FROM table_achats_immediats WHERE timestamp<? AND etat='0'";
            prepare=Objet.getConn().prepareStatement(query);
            prepare.setLong(1, timestampLimite);
            this.setErrorMsg(prepare.toString());
            prepare.executeUpdate();
            prepare.close();
            Objet.closeConnection();
        } catch (NamingException ex) {
            Logger.getLogger(Encheres.class.getName()).log(Level.SEVERE, null, ex);
            int i=1/0;
        } catch (SQLException ex) {
            Logger.getLogger(Encheres.class.getName()).log(Level.SEVERE, null, ex);
            int i=1/0;
        }
    }
}
