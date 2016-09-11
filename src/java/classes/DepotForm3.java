/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class DepotForm3 extends Formulaire {
    private double prixDepart;
    private double pasEnchere;
    private double prixReserve;
    private double prixImmediat;
    private String dateFin;
    private String heureFin;
    private String minuteFin;
    private int remiseVenteVendu;
    private int remiseVenteInvendu;
    public DepotForm3() {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.prixDepart=0.0;
        this.pasEnchere=0.0;
        this.prixReserve=0.0;
        this.prixImmediat=0.0;
        this.dateFin="";
        this.heureFin="";
        this.minuteFin="";
        this.remiseVenteInvendu=0;
        this.remiseVenteVendu=0;
    }
    public void init(HttpServletRequest request) {
        HttpSession session=request.getSession(true);
        if(session.getAttribute("prixDepart")!=null)
            this.setPrixDepart(Double.parseDouble(session.getAttribute("prixDepart").toString()));
        if(session.getAttribute("pasEnchere")!=null)
            this.setPasEnchere(Double.parseDouble(session.getAttribute("pasEnchere").toString()));
        if(session.getAttribute("prixReserve")!=null)
            this.setPrixReserve(Double.parseDouble(session.getAttribute("prixReserve").toString()));
        if(session.getAttribute("prixImmediat")!=null)
            this.setPrixImmediat(Double.parseDouble(session.getAttribute("prixImmediat").toString()));
        if(session.getAttribute("dateFin")!=null)
            this.setDateFin(session.getAttribute("dateFin").toString());
        if(session.getAttribute("heureFin")!=null)
            this.setHeureFin(session.getAttribute("heureFin").toString());
        if(session.getAttribute("minuteFin")!=null)
            this.setMinuteFin(session.getAttribute("minuteFin").toString());
        if(session.getAttribute("remiseVenteInvendu")!=null)
            this.setRemiseVenteInvendu(Integer.parseInt(session.getAttribute("remiseVenteInvendu").toString()));
        if(session.getAttribute("remiseVenteVendu")!=null)
            this.setRemiseVenteVendu(Integer.parseInt(session.getAttribute("remiseVenteVendu").toString()));
    }
    public void verif(HttpServletRequest request) {
        if(this.getPasEnchere()==0)
            this.setErrorMsg("Votre PAS DE L'ENCHÈRE ne peut être nul.<br/>");
        Pattern p = Pattern.compile("^[0-3]{0,1}[0-9]{0,1}/[0-1]{0,1}[1-9]{0,1}/[0-9]{4}$");
        Matcher m = p.matcher(this.getDateFin());
        if(this.getDateFin()==null||this.getDateFin().length()==0)
            this.setErrorMsg("Champ DATE vide.<br/>");
        else if(m.matches()==false)
            this.setErrorMsg("Champ DATE non valide.<br/>");
        String[] arrayDate=this.getDateFin().split("/");
        if(this.getErrorMsg().length()==0) {
            if(arrayDate[0].length()!=2)
                arrayDate[0]="0"+arrayDate[0];
            if(arrayDate[1].length()!=2)
                arrayDate[1]="0"+arrayDate[1];
            if(arrayDate[2].length()!=4)
                arrayDate[2]="20"+arrayDate[2];
            String formatDate=arrayDate[2]+arrayDate[1]+arrayDate[0];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setLenient(false);
            Date date = null;
            try {
                date = dateFormat.parse(formatDate);
            } catch (ParseException ex) {
            this.setErrorMsg("DATE DE FIN non valide");
            }
        }
        if(this.getHeureFin()==null||this.getHeureFin().length()==0)
            this.setErrorMsg("Champ HEURE vide.<br/>");
        else {
            try {
                int h;
                h=Integer.parseInt(this.getHeureFin());
                if(h>23)
                    this.setErrorMsg("Champ HEURE non valide.<br/>");
            } catch(NumberFormatException ex) {
                this.setErrorMsg("Champ HEURE non valide.<br/>");
            }
        }
        if(this.getMinuteFin()==null||this.getMinuteFin().length()==0)
            this.setErrorMsg("Champ MINUTES vide.<br/>");
        else {
            try {
                int min;
                min=Integer.parseInt(this.getMinuteFin());
                if(min>59)
                    this.setErrorMsg("Champ MINUTES non valide.<br/>");
            } catch(NumberFormatException ex) {
                this.setErrorMsg("Champ MINUTES non valide.<br/>");
            }
        }
        if(this.getErrorMsg().length()==0) {
            Calendar cal=Calendar.getInstance();
            long timestampActuel=cal.getTimeInMillis();
            cal.set(Integer.parseInt(arrayDate[2]), Integer.parseInt(arrayDate[1])-1, Integer.parseInt(arrayDate[0]), Integer.parseInt(this.getHeureFin()), Integer.parseInt(this.getMinuteFin()), 0);
            long timestampFin=cal.getTimeInMillis();
            if((timestampFin-timestampActuel)<(24*60*60*1000))
                this.setErrorMsg("Votre ENCHÈRE doit durer au moins 24 Heures");
            //this.setErrorMsg(""+(timestampFin-timestampActuel));
        }
        if(this.getErrorMsg().length()==0) {
            this.setTest(1);
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.setPrixDepart(0.0);
        this.setPasEnchere(0.0);
        this.setPrixReserve(0.0);
        this.setPrixImmediat(0.0);
        this.setDateFin("");
        this.setHeureFin("");
        this.setMinuteFin("");
        this.setRemiseVenteInvendu(0);
        this.setRemiseVenteVendu(0);
    }

    /**
     * @return the prixDepart
     */
    public double getPrixDepart() {
        return prixDepart;
    }

    /**
     * @param prixDepart the prixDepart to set
     */
    public void setPrixDepart(double prixDepart) {
        this.prixDepart = prixDepart;
    }

    /**
     * @return the pasEnchere
     */
    public double getPasEnchere() {
        return pasEnchere;
    }

    /**
     * @param pasEnchere the pasEnchere to set
     */
    public void setPasEnchere(double pasEnchere) {
        this.pasEnchere = pasEnchere;
    }

    /**
     * @return the prixReserve
     */
    public double getPrixReserve() {
        return prixReserve;
    }

    /**
     * @param prixReserve the prixReserve to set
     */
    public void setPrixReserve(double prixReserve) {
        this.prixReserve = prixReserve;
    }

    /**
     * @return the prixImmediat
     */
    public double getPrixImmediat() {
        return prixImmediat;
    }

    /**
     * @param prixImmediat the prixImmediat to set
     */
    public void setPrixImmediat(double prixImmediat) {
        this.prixImmediat = prixImmediat;
    }

    /**
     * @return the dateFin
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * @param dateFin the dateFin to set
     */
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * @return the heureFin
     */
    public String getHeureFin() {
        return heureFin;
    }

    /**
     * @param heureFin the heureFin to set
     */
    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    /**
     * @return the minuteFin
     */
    public String getMinuteFin() {
        return minuteFin;
    }

    /**
     * @param minuteFin the minuteFin to set
     */
    public void setMinuteFin(String minuteFin) {
        this.minuteFin = minuteFin;
    }

    /**
     * @return the remiseVenteVendu
     */
    public int getRemiseVenteVendu() {
        return remiseVenteVendu;
    }

    /**
     * @param remiseVenteVendu the remiseVenteVendu to set
     */
    public void setRemiseVenteVendu(int remiseVenteVendu) {
        this.remiseVenteVendu = remiseVenteVendu;
    }

    /**
     * @return the remiseVenteInvendu
     */
    public int getRemiseVenteInvendu() {
        return remiseVenteInvendu;
    }

    /**
     * @param remiseVenteInvendu the remiseVenteInvendu to set
     */
    public void setRemiseVenteInvendu(int remiseVenteInvendu) {
        this.remiseVenteInvendu = remiseVenteInvendu;
    }
}
