/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.Membre;
import classes.Message;
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
public class Message1 extends HttpServlet {
   
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
            out.println("<title>Servlet Message1</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Message1 at " + request.getContextPath () + "</h1>");
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
            if(request.getParameter("id")==null||request.getParameter("id").length()==0)
                processRequest(request, response);
            else {
                long idMembre=membre.getIdMembre();
                long idMessage=Long.parseLong(request.getParameter("id"));
                Message message=new Message();
                boolean flag=message.initRecu(idMembre, idMessage);
                if(!flag)
                    processRequest(request, response);
                else {
                    request.setAttribute("message", message);
                    RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/message1.jsp");
                    dispatch.forward(request, response);
                }
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Membre membre=new Membre();
        membre.testConnexion(request);
        if(membre.getIdMembre()==0)
           request.setAttribute("info", 1);
        else {
            if(request.getParameter("id")==null||request.getParameter("id").length()==0)
                processRequest(request, response);
            else {
                long idMembre=membre.getIdMembre();
                long idMessage=Long.parseLong(request.getParameter("id"));
                Message message=new Message();
                boolean flag=message.initRecu(idMembre, idMessage);
                if(!flag)
                    processRequest(request, response);
                else {
                    if(request.getParameter("objet")!=null)
                        message.setObjet(request.getParameter("objet"));
                    if(request.getParameter("contenu")!=null)
                        message.setContenu(request.getParameter("contenu"));
                    if(request.getParameter("captcha")!=null)
                        message.setCaptcha(request.getParameter("captcha").toLowerCase());
                    message.verif(request);
                    request.setAttribute("message", message);
                    RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/message1.jsp");
                    dispatch.forward(request, response);
                }
            }
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
