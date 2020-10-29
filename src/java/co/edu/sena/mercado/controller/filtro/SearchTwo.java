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

public class SearchTwo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String direccion = request.getServletPath();

        switch (direccion) {

            case "/getProductsByNameCategory":

                getProductsByNameCategory(request, response);

                break;

            case "/getProductsByNameCategoryCity":

                getProductsByNameCategoryCity(request, response);

                break;

            case "/getProductsByCategoryCity":

                getProductsByCategoryCity(request, response);

                break;

            case "/getProductsByNameCity":

                getProductsByNameCity(request, response);

                break;

            case "/getProductsByCitySeller":

                getProductsByCitySeller(request, response);

                break;

            case "/getProductsByNameCitySeller":

                getProductsByNameCitySeller(request, response);

                break;

        }

    }

    private void getProductsByNameCategory(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String palabra = request.getParameter("word");
        String categoria = request.getParameter("categorias");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setNombreCategoria(categoria);
//        String ciudad = request.getParameter("ciudad");
//        String vendedores = request.getParameter("vendedores");
        ArrayList<Producto> listaProductos = productoDAO.getProductsByNameCategory(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void getProductsByNameCategoryCity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String palabra = request.getParameter("word");
        String categoria = request.getParameter("categorias");
        String ciudad = request.getParameter("ciudades");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setNombreCategoria(categoria);
        producto.setCiudad(ciudad);
//        String vendedores = request.getParameter("vendedores");
        ArrayList<Producto> listaProductos = productoDAO.getProductsByNameCategoryCity(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

  

    private void getProductsByCategoryCity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
//        String palabra = request.getParameter("word");
        String categoria = request.getParameter("categorias");
        String ciudad = request.getParameter("ciudades");
//        String vendedores = request.getParameter("vendedores");
        Producto producto = new Producto();
        producto.setNombreCategoria(categoria);
        producto.setCiudad(ciudad);
        ArrayList<Producto> listaProductos = productoDAO.getProductsByCategoryCity(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void getProductsByNameCity(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String palabra = request.getParameter("word");
//        String categoria = request.getParameter("categorias");
        String ciudad = request.getParameter("ciudades");
//        String vendedores = request.getParameter("vendedores");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setCiudad(ciudad);
        ArrayList<Producto> listaProductos = productoDAO.getProductsByNameCity(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void getProductsByCitySeller(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
//        String palabra = request.getParameter("word");
//        String categoria = request.getParameter("categorias");
        String ciudad = request.getParameter("ciudades");
        String vendedores = request.getParameter("vendedores");
        Producto producto = new Producto();
        producto.setCiudad(ciudad);
        producto.setIdEmpresaFK(vendedores);
        ArrayList<Producto> listaProductos = productoDAO.getProductsByCitySeller(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void getProductsByNameCitySeller(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String palabra = request.getParameter("word");
//        String categoria = request.getParameter("categorias");
        String ciudad = request.getParameter("ciudades");
        String vendedores = request.getParameter("vendedores");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setCiudad(ciudad);
        producto.setIdEmpresaFK(vendedores);
        ArrayList<Producto> listaProductos = productoDAO.getProductsByNameCitySeller(producto);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
