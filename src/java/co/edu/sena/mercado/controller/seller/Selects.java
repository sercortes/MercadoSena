/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.seller;

import co.edu.sena.mercado.dao.CategorysDAO;
import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoColorDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dto.ColorDTO;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.productoImagenesDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
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

public class Selects extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("selects no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String direccion = request.getRequestURI();

        switch (direccion) {

            case "/Store/getCategorys":

                getCategorys(request, response);

                break;

            case "/Store/getProducts":

                getProducts(request, response);

                break;

            case "/Store/obtenerProducto":

                obtenerProducto(request, response);

                break;
                
             case "/Store/getProductoComplete":

                getProductoComplete(request, response);

                break;
            
            case "/Store/getColors":

                getColors(request, response);

                break;

        }

    }

    private void getCategorys(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        CategorysDAO categorysDAO = new CategorysDAO(conexion.getConnection());
        ArrayList<?> lista = categorysDAO.getCategorys();
        response.setContentType("application/json");
        categorysDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

    private void getProducts(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());
        ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
        
        ArrayList<Producto> listaProductos = productoDAO.getProductsBySeller(Integer.toString(user.getEmpresa().getIdEmpresa()));
        
        listaProductos.forEach((item) -> {
            ArrayList<ImagenesProducto> listaImagenes
                    = imagenesProductosDAO.getImagenesByProduc(item.getIdProducto());
            item.setListaImagenes(listaImagenes);
        });
        
        response.setContentType("application/json");
        productoDAO.CloseAll();
        imagenesProductosDAO.CloseAll();
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void getColors(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductoColorDAO color = new ProductoColorDAO(conexion.getConnection());
        ArrayList<?> lista = color.getColors();
        response.setContentType("application/json");
        color.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

    private void obtenerProducto(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());
        ProductoColorDAO productoColorDAO = new ProductoColorDAO(new Conexion().getConnection());
        Producto producto = new Producto();
        producto = productoDAO.buscarProducto(Integer.parseInt(request.getParameter("idProducto")));
        ArrayList<ColorDTO> lista = productoColorDAO.getColorsByProduct(request.getParameter("idProducto"));
        producto.setListaColores(lista);   
        response.setContentType("application/json");
        productoDAO.CloseAll();
        new Gson().toJson(producto, response.getWriter());

    }

    private void getProductoComplete(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());
        ProductoColorDAO productoColorDAO = new ProductoColorDAO(new Conexion().getConnection());
        Producto producto = new Producto();
        producto = productoDAO.buscarProductoComplete(Integer.parseInt(request.getParameter("idProducto")));
        ArrayList<ColorDTO> lista = productoColorDAO.getColorsByProduct(request.getParameter("idProducto"));
        producto.setListaColores(lista);   
        response.setContentType("application/json");
        productoDAO.CloseAll();
        new Gson().toJson(producto, response.getWriter());
        
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
