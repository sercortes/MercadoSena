/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.filtro;

import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author equipo
 */
public class Search extends HttpServlet {

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
      String direccion = request.getRequestURI();

        switch (direccion) {

            case "/MercadoSena/getProductsByDateTime":

                getProductsByDateTime(request, response);

                break;
                
           case "/MercadoSena/getProductsByWord":

                getProductsByWord(request, response);

                break;

        }
    }

private void getProductsByDateTime(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());
        String id = "";
        usuarioDTO user;
        
        if (request.getSession().getAttribute("USER") == null) {
            id = "0"   ;
        }else{
            user = (usuarioDTO) request.getSession().getAttribute("USER");
            id = Integer.toString(user.getEmpresa().getIdEmpresa());
        }
        ArrayList<Producto> listaProductos = productoDAO.getProductsByDateTimeAsc(id);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

private void getProductsByWord(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());
        String id = "";
        String palabra = request.getParameter("word");
        usuarioDTO user;
        if (request.getSession().getAttribute("USER") == null) {
            id = "0"   ;
        }else{
            user = (usuarioDTO) request.getSession().getAttribute("USER");
            id = Integer.toString(user.getEmpresa().getIdEmpresa());
        }
        
        ArrayList<Producto> listaProductos = productoDAO.buscadorLike(palabra, id);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());
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
