/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.AchImmForm1;
import classes.Membre;
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
public class AchatImmediat extends HttpServlet {
   
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
            RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/pagevierge.jsp");
            dispatch.forward(request, response);
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AchatImmediat</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AchatImmediat at " + request.getContextPath () + "</h1>");
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
       Membre membre=new Membre();
       membre.testConnexion(request);
        if(membre.getIdMembre()==0)
            request.setAttribute("info", 1);
        else {
            long idMembre=membre.getIdMembre();
            if(request.getParameter("id")==null||request.getParameter("id").length()==0)
               processRequest(request, response);
            else {
                long idAnnonce=Long.parseLong(request.getParameter("id"));
                AchImmForm1 formulaire=new AchImmForm1();
                boolean flag=formulaire.init(idMembre, idAnnonce);
                if(!flag)
                    request.setAttribute("info", 2);
                request.setAttribute("formulaire", formulaire);
            }
        }
                RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/achatimmediat.jsp");
                dispatch.forward(request, response);
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
       Membre membre=new Membre();
       membre.testConnexion(request);
        if(membre.getIdMembre()==0)
            request.setAttribute("info", 1);
        else {
            long idMembre=membre.getIdMembre();
            if(request.getParameter("id")==null||request.getParameter("id").length()==0)
               processRequest(request, response);
            else {
                long idAnnonce=Long.parseLong(request.getParameter("id"));
                AchImmForm1 formulaire=new AchImmForm1();
                boolean flag=formulaire.init(idMembre, idAnnonce);
                if(!flag)
                    request.setAttribute("info", 2);
                else {
                    if(request.getParameter("modePaiement")!=null&&request.getParameter("modePaiement").length()!=0)
                        formulaire.setModePaiement(Integer.parseInt(request.getParameter("modePaiement")));
                    formulaire.verif(request);
                    request.setAttribute("formulaire", formulaire);
                    }
            }
        }
                RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/achatimmediat.jsp");
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
