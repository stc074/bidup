/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author pj
 */
public class Objet {
    private static Connection conn;

    /**
     * @return the conn
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * @param aConn the conn to set
     */
    public static void setConn(Connection aConn) {
        conn = aConn;
    }
    private int test;
    private String errorMsg;
    public Objet() {
        this.test=0;
        this.errorMsg="";
    }
    public static void getConnection() throws NamingException, SQLException {
        Context initCtx = new InitialContext();
        DataSource ds=(DataSource) initCtx.lookup("java:comp/env/jdbc/MyDB");
        setConn(ds.getConnection());
    }
    public static void closeConnection() throws SQLException {
        getConn().close();
    }
    public static String getEncoded(String key) throws NoSuchAlgorithmException {
    	byte[] uniqueKey = key.getBytes();
	byte[] hash = null;
	hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
	StringBuilder hashString = new StringBuilder();
	for ( int i = 0; i < hash.length; ++i ) {
	String hex = Integer.toHexString(hash[i]);
	if ( hex.length() == 1 ) {
	hashString.append('0');
	hashString.append(hex.charAt(hex.length()-1));
	} else {
	hashString.append(hex.substring(hex.length()-2));
	}
	}
	return hashString.toString();
	}
    public static void setCookie(long id, HttpServletResponse response) throws NamingException, SQLException {
        String cookieCode = "";
        String cookieCodeCrypte="";
        String query;
        int i;
        int nb=0;
        Objet.getConnection();
        do {
            try {
                for (i = 1; i <= 6; i++) {
                    cookieCode += Datas.arrayChars[(int) (Math.random()*(Datas.arrayChars.length))];
                }
                cookieCodeCrypte = Objet.getEncoded(cookieCode);
                query = "UPDATE table_membres SET cookie_code=? WHERE id=?";
                PreparedStatement prepare = conn.prepareStatement(query);
                prepare.setString(1, cookieCodeCrypte);
                prepare.setLong(2, id);
                prepare.executeUpdate();
                query = "SELECT COUNT(id) AS nb FROM table_membres WHERE cookie_code=?";
                prepare = conn.prepareStatement(query);
                prepare.setString(1, cookieCodeCrypte);
                ResultSet result = prepare.executeQuery();
                result.next();
                nb = result.getInt("nb");
                result.close();
                prepare.close();
            } catch (SQLException ex) {
                Logger.getLogger(Objet.class.getName()).log(Level.SEVERE, null, ex);
                cookieCodeCrypte="";
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Objet.class.getName()).log(Level.SEVERE, null, ex);
                cookieCodeCrypte="";
            }
            } while(nb>1);
        Cookie cookie=new Cookie("bidupcook", cookieCodeCrypte);
        cookie.setMaxAge(60*60*24*1000*300);
        response.addCookie(cookie);
    }
    public static HttpSession initSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String idSession=session.getId();
        Cookie cookie=new Cookie("JSESSIONID", idSession);
        cookie.setMaxAge(60*60*24*300*1000);
        response.addCookie(cookie);
        return session;
    }
    public static String encodeTitre(String titre) {
        titre=titre.toLowerCase();
        for(int i=0;i<Datas.arrayReplace1.length;i++) {
            titre=titre.replaceAll(Datas.arrayReplace1[i], Datas.arrayReplace2[i]);
            }
        return titre;
    }
    public static boolean testExtension(String extension) {
        extension=extension.toLowerCase();
        if(extension.equals(".png")||extension.equals(".gif")||extension.equals(".jpg")||extension.equals(".jpeg"))
            return true;
        else
            return false;
    }
    public static double convertDouble(double x) {
        x=x*100;
        double y=Math.floor(x);
        double z=y/100;
        return z;
    }
    /**
     * @return the test
     */
    public int getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(int test) {
        this.test = test;
    }

    /**
     * @return the ErrorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param ErrorMsg the ErrorMsg to set
     */
    public void setErrorMsg(String ErrorMsg) {
        this.errorMsg += ErrorMsg;
    }
    public void setErrorMsg2(String ErrorMsg) {
        this.errorMsg = ErrorMsg;
    }
}
