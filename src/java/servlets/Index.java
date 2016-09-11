/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.RechForm1;
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
public class Index extends HttpServlet {
    private RechForm1 formulaire;
   
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
                this.formulaire=new RechForm1();
            this.formulaire.init(request);
            this.formulaire.initRecherche();
            request.setAttribute("formulaire", this.formulaire);
            RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/ind.jsp");
            dispatch.forward(request, response);
           /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Index</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Index at " + request.getContextPath () + "</h1>");
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
        HttpSession session=request.getSession(true);
        this.formulaire=new RechForm1();
        if(request.getParameter("idCategorie")!=null) {
            this.formulaire.setIdCategorie(Integer.parseInt(request.getParameter("idCategorie").toString()));
            session.setAttribute("idCategorie", this.formulaire.getIdCategorie());
            this.formulaire.setIdSousCategorie(0);
            session.setAttribute("idSousCategorie", 0);
            this.formulaire.setPage(0);
            session.setAttribute("page", 0);
        }
        if(request.getParameter("idSousCategorie")!=null) {
            this.formulaire.setIdSousCategorie(Integer.parseInt(request.getParameter("idSousCategorie").toString()));
            session.setAttribute("idSousCategorie", this.formulaire.getIdSousCategorie());
            this.formulaire.setPage(0);
            session.setAttribute("page", 0);
        }
        if(request.getParameter("type1")!=null) {
            this.formulaire.setType1(Integer.parseInt(request.getParameter("type1")));
            session.setAttribute("type1", this.formulaire.getType1());
            this.formulaire.setPage(0);
            session.setAttribute("page", 0);
        }
        if(request.getParameter("type2")!=null) {
            this.formulaire.setType2(Integer.parseInt(request.getParameter("type2")));
            session.setAttribute("type2", this.formulaire.getType2());
            this.formulaire.setPage(0);
            session.setAttribute("page", 0);
        }
        /*if(request.getParameter("type3")!=null) {
            this.formulaire.setType3(Integer.parseInt(request.getParameter("type3")));
            session.setAttribute("type3", this.formulaire.getType3());
        }*/
        if(request.getParameter("idRegion")!=null) {
            this.formulaire.setIdRegion(request.getParameter("idRegion"));
            this.formulaire.setIdDepartement("0");
            this.formulaire.setIdCommune(0);
            session.setAttribute("idDepartement", "0");
            session.setAttribute("idCommune", 0);
            session.setAttribute("idRegion", this.formulaire.getIdRegion());
            this.formulaire.setPage(0);
            session.setAttribute("page", 0);
        }
        if(request.getParameter("idDepartement")!=null) {
            this.formulaire.setIdDepartement(request.getParameter("idDepartement"));
            this.formulaire.setIdCommune(0);
            session.setAttribute("idDepartement", this.formulaire.getIdDepartement());
            session.setAttribute("idCommune", 0);
            this.formulaire.setPage(0);
            session.setAttribute("page", 0);
        }
        if(request.getParameter("idCommune")!=null) {
            this.formulaire.setIdCommune(Integer.parseInt(request.getParameter("idCommune")));
            session.setAttribute("idCommune", this.formulaire.getIdCommune());
            this.formulaire.setPage(0);
            session.setAttribute("page", 0);
        }
        if(request.getParameter("page")!=null) {
            this.formulaire.setPage(Integer.parseInt(request.getParameter("page")));
            session.setAttribute("page", this.formulaire.getPage());
        }
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
        this.formulaire=new RechForm1();
        if(request.getParameter("recherche")!=null) {
            this.formulaire.setRecherche(request.getParameter("recherche"));
            session.setAttribute("recherche", this.formulaire.getRecherche());
        }
        if(request.getParameter("kermit")!=null)
            this.formulaire.reset(request);
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
