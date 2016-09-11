/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.InsForm1;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pj
 */
public class Inscription extends HttpServlet {
    private InsForm1 formulaire;
   
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
            if(this.formulaire==null)
                this.formulaire=new InsForm1();
            request.setAttribute("formulaire", this.formulaire);
            RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/inscription.jsp");
            dispatch.forward(request, response);
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Inscription</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Inscription at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        this.formulaire=new InsForm1();
        this.formulaire.setPseudo(request.getParameter("pseudo"));
        this.formulaire.setMotDePasse(request.getParameter("motDePasse"));
        this.formulaire.setMotDePasse2(request.getParameter("motDePasse2"));
        this.formulaire.setEmail(request.getParameter("email").toLowerCase());
        this.formulaire.setNom(request.getParameter("nom"));
        this.formulaire.setPrenom(request.getParameter("prenom"));
        this.formulaire.setAdresse(request.getParameter("adresse"));
        this.formulaire.setCodePostal(request.getParameter("codePostal"));
        this.formulaire.setVille(request.getParameter("ville"));
        this.formulaire.setCaptcha(request.getParameter("captcha").toLowerCase());
        this.formulaire.verif(request, response);
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
