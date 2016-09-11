/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.DepotForm4;
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
public class DeposerAnnonce4 extends HttpServlet {
    private DepotForm4 formulaire;
   
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
                this.formulaire=new DepotForm4();
            this.formulaire.init(request);
            request.setAttribute("formulaire", this.formulaire);
            }
             RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/deposerannonce4.jsp");
             dispatch.forward(request, response);
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeposerAnnonce4</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeposerAnnonce4 at " + request.getContextPath () + "</h1>");
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
            RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/deposerannonce4.jsp");
            dispatch.forward(request, response);
       } else {
        this.formulaire=new DepotForm4();
        if(request.getParameter("idRegion")!=null&&request.getParameter("idRegion").length()!=0) {
            this.formulaire.setIdRegion(request.getParameter("idRegion"));
            session.setAttribute("idRegion", this.formulaire.getIdRegion());
        }
        if(request.getParameter("idDepartement")!=null&&request.getParameter("idDepartement").length()!=0) {
            this.formulaire.setIdDepartement(request.getParameter("idDepartement"));
            session.setAttribute("idDepartement", this.formulaire.getIdDepartement());
        }
        if(request.getParameter("idCommune")!=null&&request.getParameter("idCommune").length()!=0) {
            this.formulaire.setIdCommune(Integer.parseInt(request.getParameter("idCommune")));
            session.setAttribute("idCommune", this.formulaire.getIdCommune());
        }
        if(request.getParameter("typeEnvoi")!=null) {
            this.formulaire.setTypeEnvoi(Integer.parseInt(request.getParameter("typeEnvoi")));
            session.setAttribute("typeEnvoi", this.formulaire.getTypeEnvoi());
            if(this.formulaire.getTypeEnvoi()==2) {
                String field="";
                if(request.getParameter("fraisExpe")!=null&&request.getParameter("fraisExpe").length()!=0) {
                    field=request.getParameter("fraisExpe");
                    field=field.replaceAll(",", ".");
                    try {
                        this.formulaire.setFraisExpe(Double.parseDouble(field));
                        session.setAttribute("fraisExpe", this.formulaire.getFraisExpe());
                    } catch(NumberFormatException ex) {
                    this.formulaire.setErrorMsg("FRAIS D'EXPÉDITION : ce n'est pas un nombre.<br/>");
                    }
                }
                if(request.getParameter("noteExpe")!=null) {
                    this.formulaire.setNoteExpe(request.getParameter("noteExpe"));
                    session.setAttribute("noteExpe", this.formulaire.getNoteExpe());
                    }
            } else if(this.formulaire.getTypeEnvoi()==1) {
                this.formulaire.setFraisExpe(0.0);
                session.setAttribute("fraisExpe", this.formulaire.getFraisExpe());
            }
        }
        if(request.getParameter("paypal")!=null) {
            this.formulaire.setPaypal(request.getParameter("paypal"));
            session.setAttribute("paypal",this.formulaire.getPaypal());
        }
        if(request.getParameter("typePaiementCheque")!=null&&request.getParameter("typePaiementCheque").length()!=0) {
            this.formulaire.setTypePaiementCheque(1);
            session.setAttribute("typePaiementCheque", 1);
        } else {
            this.formulaire.setTypePaiementCheque(0);
            session.setAttribute("typePaiementCheque", 0);
        }
        if(request.getParameter("typePaiementEspece")!=null&&request.getParameter("typePaiementEspece").length()!=0) {
            this.formulaire.setTypePaiementEspece(1);
            session.setAttribute("typePaiementEspece", 1);
        } else {
            this.formulaire.setTypePaiementEspece(0);
            session.setAttribute("typePaiementEspece", 0);
        }
        if(request.getParameter("typePaiementTimbre")!=null&&request.getParameter("typePaiementTimbre").length()!=0) {
            this.formulaire.setTypePaiementTimbre(1);
            session.setAttribute("typePaiementTimbre", 1);
        } else {
            this.formulaire.setTypePaiementTimbre(0);
            session.setAttribute("typePaiementTimbre", 0);
        }
        if(membre.getIdMembre()!=0)
            this.formulaire.setIdMembre(membre.getIdMembre());
        else
            this.formulaire.setErrorMsg("Veuillez vous reconnecter SVP.<br/>");
        this.formulaire.verif(request);
        processRequest(request, response);
        }
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
