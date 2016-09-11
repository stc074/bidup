/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.AchImmForm2;
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
public class AchatImmediat2 extends HttpServlet {
   
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
            HttpSession session=request.getSession(true);
            Membre membre=new Membre();
            membre.testConnexion(request);
            if(membre.getIdMembre()==0) {
                request.setAttribute("info", 1);
            } else if(session.getAttribute("modePaiement")!=null&&session.getAttribute("idAnnonce")!=null) {
                long idMembre=membre.getIdMembre();
                long idAnnonce=Long.parseLong(session.getAttribute("idAnnonce").toString());
                int modePaiement=Integer.parseInt(session.getAttribute("modePaiement").toString());
                AchImmForm2 formulaire=new AchImmForm2();
                boolean flag=formulaire.init(idMembre, idAnnonce, modePaiement);
                if(flag) {
                    session.setAttribute("idAchatImmediat", formulaire.getIdAchatImmediat());
                    request.setAttribute("formulaire", formulaire);
                } else {
                    request.setAttribute("info", 2);
                }
            } else {
                request.setAttribute("info", 2);
            }
            RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/achatimmediat2.jsp");
            dispatch.forward(request, response);
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AchatImmediat2</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AchatImmediat2 at " + request.getContextPath () + "</h1>");
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
            if(membre.getIdMembre()==0) {
                request.setAttribute("info", 1);
            } else if(session.getAttribute("modePaiement")!=null&&session.getAttribute("idAnnonce")!=null&&session.getAttribute("idAchatImmediat")!=null) {
                long idMembre=membre.getIdMembre();
                long idAnnonce=Long.parseLong(session.getAttribute("idAnnonce").toString());
                int modePaiement=Integer.parseInt(session.getAttribute("modePaiement").toString());
                long idAchatImmediat=Long.parseLong(session.getAttribute("idAchatImmediat").toString());
                AchImmForm2 formulaire=new AchImmForm2();
                boolean flag=formulaire.init2(idMembre, idAnnonce, modePaiement);
                if(flag) {
                    formulaire.verif(idAchatImmediat);
                    request.setAttribute("formulaire", formulaire);
                } else {
                    request.setAttribute("info", 2);
                }
            } else {
                request.setAttribute("info", 2);
            }
            RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/achatimmediat2.jsp");
            dispatch.forward(request, response);
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
