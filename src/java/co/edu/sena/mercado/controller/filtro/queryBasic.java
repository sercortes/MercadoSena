/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.filtro;

import co.edu.sena.mercado.dao.ProductoDAOQuerysBa;
import co.edu.sena.mercado.dao.ProductorDAOQuerys;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class queryBasic extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("query basic no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String direccion = request.getRequestURI();

        switch (direccion) {

            case "/MercadoSena/getProductsByDateTimeBasic":

                getProductsByDateTimeBasic(request, response);

                break;

            default:
                
                System.out.println("query basic no soporta GET");
                response.sendRedirect(request.getContextPath() + "/home");

                break;

        }
    }

    private void getProductsByDateTimeBasic(HttpServletRequest request, HttpServletResponse response) throws IOException {
       
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductoDAOQuerysBa productoDAO = new ProductoDAOQuerysBa(conexion.getConnection());
        ArrayList<Producto> listaProductos = productoDAO.getProductsByDateTimeAscBasic();
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());
        
    }

        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
