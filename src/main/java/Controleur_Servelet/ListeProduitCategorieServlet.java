/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur_Servelet;

import Modele.DAO;
import Modele.DataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author camilleclaret
 */
@WebServlet(name = "ListeProduitCategorieServlet", urlPatterns = {"/ListeProduitCategorieServlet"})
public class ListeProduitCategorieServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Trouver la valeur du paramètre HTTP state
        String categorie = request.getParameter("categorie");
        // Créér le DAO avec sa source de données
        DAO dao = new DAO(DataSourceFactory.getDataSource());
        // Properties est une Map<clé, valeur> pratique pour générer du JSON
        Properties resultat = new Properties();
        
        try{
            resultat.put("records", dao.categoryProduct(categorie));
        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultat.put("records", Collections.EMPTY_LIST);
            resultat.put("message", ex.getMessage());
        }
        
        try (PrintWriter out = response.getWriter()) {
            // On spécifie que la servlet va générer du JSON
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            out.println(gson.toJson(resultat));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
