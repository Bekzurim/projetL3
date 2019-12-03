/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur_Servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Modele.DAO;
import Modele.DataSourceFactory;
import Modele.ProductEntity;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedago
 */
@WebServlet(name = "Affichage", urlPatterns = {"/Affichage"})
public class Affichage extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        DAO dao = new DAO(DataSourceFactory.getDataSource()); 
        String LibelCat = (String) request.getParameter("categories");
        if(LibelCat == null)
        {
            LibelCat=dao.getCategorie().get(0).getLibelle();
        }
        
        List<ProductEntity> ProduitsInCategories = dao.categoryProduct(LibelCat);
/*        List<Integer> Ref = new LinkedList<Integer>();
        List<String> Nom = new LinkedList<String>();
        List<String> Four = new LinkedList<String>();
        for(int i=0;i<ProduitsInCategories.size();i++){ Ref.add(Integer.parseInt(ProduitsInCategories.get(i).getRef()));
        Four.add(ProduitsInCategories.get(i).getFourni());
        Nom.add(ProduitsInCategories.get(i).getNom());}

        request.setAttribute("Ref", Ref);
        request.setAttribute("Nom", Nom);
        request.setAttribute("Four", Four);
*/      
        request.setAttribute("Produits", ProduitsInCategories);
        request.setAttribute("Categories",dao.getCategorie());
        request.setAttribute("CatSelect",LibelCat);
        request.getRequestDispatcher("index.jsp").forward(request, response);
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
          throws ServletException, IOException{
        try {
            processRequest(request, response);
        }catch (SQLException ex) {
            Logger.getLogger(Affichage.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            throws ServletException, IOException{
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Affichage.class.getName()).log(Level.SEVERE, null, ex);
        }
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
