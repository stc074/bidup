/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import classes.EditForm2;
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
public class EditAnnonce2 extends HttpServlet {
   
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
            out.println("<title>Servlet EditAnnonce2</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditAnnonce2 at " + request.getContextPath () + "</h1>");
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
                EditForm2 formulaire=new EditForm2();
                boolean flag=formulaire.init(idAnnonce, idMembre);
                if(!flag)
                    request.setAttribute("info", 2);
                else
                    request.setAttribute("formulaire", formulaire);
                RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/editannonce2.jsp");
                dispatch.forward(request, response);
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
            long idMembre=membre.getIdMembre();
            if(request.getParameter("id")==null||request.getParameter("id").length()==0)
                processRequest(request, response);
            else {
                long idAnnonce=Long.parseLong(request.getParameter("id"));
                EditForm2 formulaire=new EditForm2();
                boolean flag=formulaire.init(idAnnonce, idMembre);
                if(!flag)
                    request.setAttribute("info", 2);
                else {
                                        if(request.getParameter("idCategorie")!=null) {
                        formulaire.setIdCategorie(Integer.parseInt(request.getParameter("idCategorie")));
                        formulaire.setIdSousCategorie(0);
                        }
                    if(request.getParameter("idSousCategorie")!=null)
                        formulaire.setIdSousCategorie(Integer.parseInt(request.getParameter("idSousCategorie")));
                    if(request.getParameter("titre")!=null)
                        formulaire.setTitre(request.getParameter("titre"));
                    if(request.getParameter("description")!=null)
                        formulaire.setDescription(request.getParameter("description"));
        String field="";
        if(request.getParameter("prixDepart")!=null) {
            field=request.getParameter("prixDepart");
            field=field.replaceAll(",",".");
            try {
                formulaire.setPrixDepart(Double.parseDouble(field));
            } catch(NumberFormatException ex) {
                formulaire.setErrorMsg("Erreur PRIX DÉPART : ce n'est pas un nombre.<br/>");
            }
        }
        if(request.getParameter("pasEnchere")!=null) {
            field=request.getParameter("pasEnchere");
            field=field.replaceAll(",",".");
            try {
                formulaire.setPasEnchere(Double.parseDouble(field));
            } catch(NumberFormatException ex) {
                formulaire.setErrorMsg("Erreur PAS DE L'ENCHÈRE : ce n'est pas un nombre.<br/>");
            }
        }
        if(request.getParameter("prixReserve")!=null) {
            field=request.getParameter("prixReserve");
            field=field.replaceAll(",",".");
            try {
                formulaire.setPrixReserve(Double.parseDouble(field));
            } catch(NumberFormatException ex) {
                formulaire.setErrorMsg("Erreur PRIX DE RÉSERVE : ce n'est pas un nombre.<br/>");
            }
        }
        if(request.getParameter("prixImmediat")!=null) {
            field=request.getParameter("prixImmediat");
            field=field.replaceAll(",",".");
            try {
                formulaire.setPrixImmediat(Double.parseDouble(field));
            } catch(NumberFormatException ex) {
                formulaire.setErrorMsg("Erreur PRIX ACHAT IMMÉDIAT : ce n'est pas un nombre.<br/>");
            }
        }
        if(request.getParameter("dateFin")!=null) {
            formulaire.setDateFin(request.getParameter("dateFin"));
        }
        if(request.getParameter("heureFin")!=null) {
            formulaire.setHeureFin(request.getParameter("heureFin"));
        }
        if(request.getParameter("minuteFin")!=null) {
            formulaire.setMinuteFin(request.getParameter("minuteFin"));
        }
        if(request.getParameter("remiseVenteInvendu")!=null) {
            formulaire.setRemiseVenteInvendu(1);
        } else {
            formulaire.setRemiseVenteInvendu(0);
        }
        if(request.getParameter("remiseVenteVendu")!=null) {
            formulaire.setRemiseVenteVendu(1);
        } else {
            formulaire.setRemiseVenteVendu(0);
        }
        if(request.getParameter("idRegion")!=null)
            formulaire.setIdRegion(request.getParameter("idRegion"));
        if(request.getParameter("idDepartement")!=null)
            formulaire.setIdDepartement(request.getParameter("idDepartement"));
        if(request.getParameter("idCommune")!=null)
            formulaire.setIdCommune(Integer.parseInt(request.getParameter("idCommune")));
        if(request.getParameter("typeEnvoi")!=null)
            formulaire.setTypeEnvoi(Integer.parseInt(request.getParameter("typeEnvoi")));
        if(request.getParameter("typeEnvoi")!=null) {
            formulaire.setTypeEnvoi(Integer.parseInt(request.getParameter("typeEnvoi")));
            if(formulaire.getTypeEnvoi()==2) {
                field="";
                if(request.getParameter("fraisExpe")!=null&&request.getParameter("fraisExpe").length()!=0) {
                    field=request.getParameter("fraisExpe");
                    field=field.replaceAll(",", ".");
                    try {
                        formulaire.setFraisExpe(Double.parseDouble(field));
                   } catch(NumberFormatException ex) {
                    formulaire.setErrorMsg("FRAIS D'EXPÉDITION : ce n'est pas un nombre.<br/>");
                    }
                }
                if(request.getParameter("noteExpe")!=null)
                    formulaire.setNoteExpe(request.getParameter("noteExpe"));
            } else if(formulaire.getTypeEnvoi()==1) {
                formulaire.setFraisExpe(0.0);
            }
        }
        if(request.getParameter("paypal")!=null) {
            formulaire.setPaypal(request.getParameter("paypal"));
        }
        if(request.getParameter("typePaiementCheque")!=null&&request.getParameter("typePaiementCheque").length()!=0) {
            formulaire.setTypePaiementCheque(1);
        } else {
            formulaire.setTypePaiementCheque(0);
        }
        if(request.getParameter("typePaiementEspece")!=null&&request.getParameter("typePaiementEspece").length()!=0) {
            formulaire.setTypePaiementEspece(1);
        } else {
            formulaire.setTypePaiementEspece(0);
        }
        if(request.getParameter("typePaiementTimbre")!=null&&request.getParameter("typePaiementTimbre").length()!=0) {
            formulaire.setTypePaiementTimbre(1);
        } else {
            formulaire.setTypePaiementTimbre(0);
        }
        formulaire.verif(request);
                    request.setAttribute("formulaire", formulaire);
                }
                RequestDispatcher dispatch = request.getRequestDispatcher("./scripts/editannonce2.jsp");
                dispatch.forward(request, response);
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
