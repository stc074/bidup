/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Objet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class ChangeDepartement1 extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<div class=\"erreur\">Erreur interne.</div>");
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangeDepartement1</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeDepartement1 at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(request.getParameter("id")==null||request.getParameter("id").length()==0)
            processRequest(request, response);
        else {
            try {
                PrintWriter out = response.getWriter();
                String idDepartement = request.getParameter("id");
                Objet.getConnection();
                String query="SELECT id,commune,code_postal FROM table_communes WHERE id_departement=? ORDER BY commune ASC";
                PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                prepare.setString(1, idDepartement);
                ResultSet result=prepare.executeQuery();
                out.println("<div>Commune :</div>");
                out.println("<select name=\"idCommune\">");
                out.println("<option value=\"0\" selected=\"selected\">Choisissez</option>");
                while(result.next()) {
                    int idCommune=result.getInt("id");
                    String commune=result.getString("commune");
                    String codePostal=result.getString("code_postal");
                    out.println("<option value=\""+idCommune+"\">"+codePostal+" - "+commune+"</option>");
                }
                out.println("</select>");
                result.close();
                prepare.close();
                Objet.closeConnection();
                out.close();
            } catch (NamingException ex) {
                Logger.getLogger(ChangeDepartement1.class.getName()).log(Level.SEVERE, null, ex);
                processRequest(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(ChangeDepartement1.class.getName()).log(Level.SEVERE, null, ex);
                processRequest(request, response);
            }
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
