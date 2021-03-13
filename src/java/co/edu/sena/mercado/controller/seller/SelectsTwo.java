/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.seller;

import co.edu.sena.mercado.dao.CategorysDAO;
import co.edu.sena.mercado.dao.ProductoColorDAO;
import co.edu.sena.mercado.dto.ColorDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SelectsTwo extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("selectsTwos no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
          String direccion = request.getRequestURI();

        switch (direccion) {
                
//            case "/Store/getStockProduct":
//
//                getStockProduct(request, response);
//
//                break;

        }
        
    }

    private void getStockProduct(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        ProductoColorDAO categorysDAO = new ProductoColorDAO(new Conexion().getConnection());
//        int cantidad = categorysDAO.getStockProduct(request.getParameter("idProductoColor"));
//        response.setContentType("application/json");
//        categorysDAO.CloseAll();
//        new Gson().toJson(cantidad, response.getWriter());
        
    }
    
        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
