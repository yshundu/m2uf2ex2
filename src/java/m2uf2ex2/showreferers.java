/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2uf2ex2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alumne
 */
public class showreferers extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            //Array que guarda els urls
            List<String> urlsAcumulades = (List<String>)request.getSession().getAttribute("urlsAcumulades");
            
            //Si es el primer cop crea una array
            if (urlsAcumulades == null) {
                urlsAcumulades = new ArrayList<>();
            }
            //Agafa el url de la pagina de on ve
            URL url = new URL(request.getHeader("Referer"));
            String urlProcedencia;
            urlProcedencia = url.getProtocol() + "://" + url.getAuthority() + url.getPath();
            //Si no existeix ho crea, en cas contrari no fa res
            if (!urlsAcumulades.contains(urlProcedencia)) {
                urlsAcumulades.add(urlProcedencia);
            }
            //El for per imprimir els elements
            out.println("<link rel='stylesheet' href='./css/styles.css' type='text/css'/>");
            for (String urls : urlsAcumulades) {
                out.println(urls);
            }
            //Ho guarda en la array la actual
            request.getSession().setAttribute("urlsAcumulades", urlsAcumulades);
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
