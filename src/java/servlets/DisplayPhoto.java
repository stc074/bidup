/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Datas;
import classes.Objet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class DisplayPhoto extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        try {
            //out.print("photo");
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DisplayPhoto</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DisplayPhoto at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            //out.close();
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
        String idAnnonce;
        String extension;
        if(request.getParameter("id")==null)
            processRequest(request, response);
        else
        {
            String mini="";
            String index="";
            String indexTxt="";
            if(request.getParameter("mini")!=null)
                mini="mini"+request.getParameter("mini")+"_";
            if(request.getParameter("index")!=null) {
                index=request.getParameter("index");
                indexTxt="_"+index;
            }
            try {
                idAnnonce = request.getParameter("id");
                Objet.getConnection();
                String query = "SELECT extension"+index+" FROM table_annonces WHERE id=?";
                PreparedStatement prepare = Objet.getConn().prepareStatement(query);
                prepare.setLong(1, Long.parseLong(idAnnonce));
                ResultSet result=prepare.executeQuery();
                result.next();
                String filename=mini+idAnnonce+indexTxt+result.getString("extension"+index);
                result.close();
                prepare.close();
                Objet.closeConnection();
                String vraiFichier=Datas.DIR+"photos/"+filename;
                File file=new File(vraiFichier);
                extension=(vraiFichier.substring(vraiFichier.lastIndexOf("."))).toLowerCase();
                extension=extension.substring(1);
                BufferedImage buffer = ImageIO.read(file);
                //Graphics2D g = buffer.createGraphics();
                String format;
                if(extension.equals("jpg"))
                {
                    response.setContentType("image/jpeg");
                    format="JPEG";
                }
                else
                {
                    response.setContentType("image/"+extension);
                    format=extension.toUpperCase();
                }
                OutputStream os = response.getOutputStream();
                ImageIO.write(buffer, format, os);
                os.close();
            } catch (NamingException ex) {
                Logger.getLogger(DisplayPhoto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DisplayPhoto.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse  response)
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
