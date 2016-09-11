/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.DepotForm3;
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
public class DeposerAnnonce3 extends HttpServlet {
    private DepotForm3 formulaire;
   
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
            else {
            if(this.formulaire==null)
                this.formulaire=new DepotForm3();
            this.formulaire.init(request);
            request.setAttribute("formulaire", this.formulaire);
            }
             RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/deposerannonce3.jsp");
             dispatch.forward(request, response);
           /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeposerAnnonce3</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeposerAnnonce3 at " + request.getContextPath () + "</h1>");
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
        if(membre.getIdMembre()==0) {
            request.setAttribute("info", 1);
             RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/deposerannonce3.jsp");
             dispatch.forward(request, response);
        }
        this.formulaire=new DepotForm3();
        String field="";
        if(request.getParameter("prixDepart")!=null) {
            field=request.getParameter("prixDepart");
            field=field.replaceAll(",",".");
            try {
                this.formulaire.setPrixDepart(Double.parseDouble(field));
                session.setAttribute("prixDepart", this.formulaire.getPrixDepart());
            } catch(NumberFormatException ex) {
                this.formulaire.setErrorMsg("Erreur PRIX DÉPART : ce n'est pas un nombre.<br/>");
            }
        }
        if(request.getParameter("pasEnchere")!=null) {
            field=request.getParameter("pasEnchere");
            field=field.replaceAll(",",".");
            try {
                this.formulaire.setPasEnchere(Double.parseDouble(field));
                session.setAttribute("pasEnchere", this.formulaire.getPasEnchere());
            } catch(NumberFormatException ex) {
                this.formulaire.setErrorMsg("Erreur PAS DE L'ENCHÈRE : ce n'est pas un nombre.<br/>");
            }
        }
        if(request.getParameter("prixReserve")!=null) {
            field=request.getParameter("prixReserve");
            field=field.replaceAll(",",".");
            try {
                this.formulaire.setPrixReserve(Double.parseDouble(field));
                session.setAttribute("prixReserve", this.formulaire.getPrixReserve());
            } catch(NumberFormatException ex) {
                this.formulaire.setErrorMsg("Erreur PRIX DE RÉSERVE : ce n'est pas un nombre.<br/>");
            }
        }
        if(request.getParameter("prixImmediat")!=null) {
            field=request.getParameter("prixImmediat");
            field=field.replaceAll(",",".");
            try {
                this.formulaire.setPrixImmediat(Double.parseDouble(field));
                session.setAttribute("prixImmediat", this.formulaire.getPrixImmediat());
            } catch(NumberFormatException ex) {
                this.formulaire.setErrorMsg("Erreur PRIX ACHAT IMMÉDIAT : ce n'est pas un nombre.<br/>");
            }
        }
        if(request.getParameter("dateFin")!=null) {
            this.formulaire.setDateFin(request.getParameter("dateFin"));
            session.setAttribute("dateFin", this.formulaire.getDateFin());
        }
        if(request.getParameter("heureFin")!=null) {
            this.formulaire.setHeureFin(request.getParameter("heureFin"));
            session.setAttribute("heureFin", this.formulaire.getHeureFin());
        }
        if(request.getParameter("minuteFin")!=null) {
            this.formulaire.setMinuteFin(request.getParameter("minuteFin"));
            session.setAttribute("minuteFin", this.formulaire.getMinuteFin());
        }
        if(request.getParameter("remiseVenteInvendu")!=null) {
            this.formulaire.setRemiseVenteInvendu(1);
            session.setAttribute("remiseVenteInvendu", 1);
        } else {
            this.formulaire.setRemiseVenteInvendu(0);
            session.setAttribute("remiseVenteInvendu", 0);
        }
        if(request.getParameter("remiseVenteVendu")!=null) {
            this.formulaire.setRemiseVenteVendu(1);
            session.setAttribute("remiseVenteVendu", 1);
        } else {
            this.formulaire.setRemiseVenteVendu(0);
            session.setAttribute("remiseVenteVendu", 0);
        }
        this.formulaire.verif(request);
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
