/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Ctc;
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
public class Contact extends HttpServlet {
   
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
            out.println("<title>Servlet Contact</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Contact at " + request.getContextPath () + "</h1>");
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
        else if(request.getParameter("id")==null||request.getParameter("id").length()==0)
            processRequest(request, response);
        else {
            long idMembreExpediteur=membre.getIdMembre();
            long idMembreDestinataire=Long.parseLong(request.getParameter("id"));
            Ctc contact=new Ctc();
            boolean flag=contact.verif(idMembreExpediteur, idMembreDestinataire);
            if(!flag)
                processRequest(request, response);
            else if(flag) {
                request.setAttribute("contact", contact);
            }
        }
                RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/contact.jsp");
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
        HttpSession session=request.getSession(true);
        if(session.getAttribute("idMembre")==null)
            request.setAttribute("info", 1);
        else if(request.getParameter("id")==null||request.getParameter("id").length()==0)
            processRequest(request, response);
        else {
            long idMembreExpediteur=Long.parseLong(session.getAttribute("idMembre").toString());
            long idMembreDestinataire=Long.parseLong(request.getParameter("id"));
            Ctc contact=new Ctc();
            boolean flag=contact.verif(idMembreExpediteur, idMembreDestinataire);
            if(!flag)
                processRequest(request, response);
            else if(flag) {
                if(request.getParameter("objet")!=null)
                    contact.setObjet(request.getParameter("objet"));
                if(request.getParameter("contenu")!=null)
                    contact.setContenu(request.getParameter("contenu"));
                if(request.getParameter("captcha")!=null)
                    contact.setCaptcha(request.getParameter("captcha"));
                contact.verifFormulaire(request);
                request.setAttribute("contact", contact);
            }
        }
                RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/contact.jsp");
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
