/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.rutas;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author serfin
 */
public class Rutas extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String direccion = request.getRequestURI();
        RequestDispatcher rd;

        switch (direccion) {
            case "/MercadoSena/ProductoDetails":
                rd = request.getRequestDispatcher("/views/products/productoDetails.jsp");
                rd.forward(request, response);
                break;
            case "/MercadoSena/Searching...":
                 rd = request.getRequestDispatcher("/views/searching/search.jsp");
                rd.forward(request, response);
                break;
            default:
                System.out.println("error de la ruta");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}