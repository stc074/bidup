/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.DepotForm1;
import classes.Membre;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pj
 */
public class DeposerAnnonce1 extends HttpServlet {
    private DepotForm1 formulaire;
   
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
            Membre membre=new Membre();
            membre.testConnexion(request);
            if(membre.getIdMembre()==0)
                request.setAttribute("info", 1);
            if(this.formulaire==null) {
                this.formulaire=new DepotForm1();
            }
                this.formulaire.init(request, response);
            request.setAttribute("formulaire", this.formulaire);
            RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/deposerannonce1.jsp");
            dispatch.forward(request, response);
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeposerAnnonce1</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeposerAnnonce1 at " + request.getContextPath () + "</h1>");
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
        HttpSession session=request.getSession(true);
        Membre membre=new Membre();
        membre.testConnexion(request);
        if(membre.getIdMembre()==0)
            request.setAttribute("info", 1);
        else {
        this.formulaire=new DepotForm1();
        this.formulaire.init(request, response);
        if(request.getParameter("idCategorie")!=null) {
            this.formulaire.setIdCategorie(Integer.parseInt(request.getParameter("idCategorie")));
            session.setAttribute("idCategorie",this.formulaire.getIdCategorie());
        }
        if(request.getParameter("idSousCategorie")!=null) {
            this.formulaire.setIdSousCategorie(Integer.parseInt(request.getParameter("idSousCategorie")));
            session.setAttribute("idSousCategorie", this.formulaire.getIdSousCategorie());
        }
        this.formulaire.verif(request, response);
        }
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
