/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.filtro;

import co.edu.sena.mercado.dao.ProductorDAOQuerys;
import co.edu.sena.mercado.dto.Producto;
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

/**
 *
 * @author equipo
 */
public class SearchThree extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           String direccion = request.getServletPath();

        switch (direccion) {

            case "/getProductsByCategorySeller":

                getProductsByCategorySeller(request, response);

                break;
                
            case "/getProductsByNameCategorySeller":

                getProductsByNameCategorySeller(request, response);

                break;
                
            case "/getProductsByNameSeller":

                getProductsByNameSeller(request, response);

                break;
            
            case "/getProductsByNameCategoryCitySeller":

                getProductsByNameCategoryCitySeller(request, response);

                break;

        }

    }
   

    private void getProductsByCategorySeller(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String categoria = request.getParameter("categorias");
        String vendedores = request.getParameter("vendedores");
        Producto producto = new Producto();
        producto.setNombreCategoria(categoria);
        producto.setIdEmpresaFK(vendedores);
        ArrayList<Producto> listaProductos = productoDAO.getProductsByCategorySeller(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());
        
    }


    private void getProductsByNameCategorySeller(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
                request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String palabra = request.getParameter("word");
        String categoria = request.getParameter("categorias");
//        String ciudad = request.getParameter("ciudades");
        String vendedores = request.getParameter("vendedores");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setNombreCategoria(categoria);
//        producto.setCiudad(ciudad);
        producto.setIdEmpresaFK(vendedores);
        ArrayList<Producto> listaProductos = productoDAO.getProductsByNameCategorySeller(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());
    }

    private void getProductsByNameSeller(HttpServletRequest request, HttpServletResponse response) throws IOException {
                   request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String palabra = request.getParameter("word");
//        String categoria = request.getParameter("categorias");
//        String ciudad = request.getParameter("ciudades");
        String vendedores = request.getParameter("vendedores");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setIdEmpresaFK(vendedores);
        ArrayList<Producto> listaProductos = productoDAO.getProductsByNameSeller(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());
    }
  
  private void getProductsByNameCategoryCitySeller(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String palabra = request.getParameter("word");
        String categoria = request.getParameter("categorias");
        String ciudad = request.getParameter("ciudades");
        String vendedores = request.getParameter("vendedores");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setNombreCategoria(categoria);
        producto.setCiudad(ciudad);
        producto.setIdEmpresaFK(vendedores);
        ArrayList<Producto> listaProductos = productoDAO.getProductsByNameCategoryCitySeller(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }
  
      @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
}
