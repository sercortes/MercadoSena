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
import javax.servlet.http.HttpSession;

/**
 *
 * @author serfin
 */
public class RutasVendedor extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String direccion = request.getRequestURI();
        RequestDispatcher rd;
        
        HttpSession session = request.getSession();
        session.setAttribute("ISEMPRESA", 1);
        
        switch (direccion) {
             case "/MercadoSena/Products":
                rd = request.getRequestDispatcher("/views/products/products.jsp");
                rd.forward(request, response);
                break;
            case "/MercadoSena/newProduct":
                rd = request.getRequestDispatcher("/views/products/addProduct.jsp");
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
