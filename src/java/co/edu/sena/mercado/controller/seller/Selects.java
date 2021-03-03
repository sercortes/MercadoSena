/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.seller;

import co.edu.sena.mercado.dao.CategorysDAO;
import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dao.empresaDAO;
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

            case "/Store/getImages":

                getImages(request, response);

                break;

            case "/Store/getImagesByProduct":

                getImagesByProduct(request, response);

                break;

            case "/Store/getInfoCompanyByProduct":

                getInfoCompanyByProduct(request, response);

                break;
            case "/Store/obtenerProducto":

                obtenerProducto(request, response);

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
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        ArrayList<Producto> listaProductos = productoDAO.getProductsBySeller(Integer.toString(user.getEmpresa().getIdEmpresa()));
        response.setContentType("application/json");
        productoDAO.CloseAll();
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void getImages(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");

        Conexion conexion = new Conexion();
        ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());

        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        ArrayList<ImagenesProducto> listaImagenes = imagenesProductosDAO.getImagenesByEmpresa(Integer.toString(user.getEmpresa().getIdEmpresa()));

        response.setContentType("application/json");
        imagenesProductosDAO.CloseAll();
        new Gson().toJson(listaImagenes, response.getWriter());

    }

    private void getImagesByProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
        ArrayList<ImagenesProducto> listaImagenes
                = imagenesProductosDAO.getImagenesByProduc(request.getParameter("id"));

        response.setContentType("application/json");
        imagenesProductosDAO.CloseAll();
        new Gson().toJson(listaImagenes, response.getWriter());

    }

    private void getInfoCompanyByProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        empresaDAO empresaDao = new empresaDAO();
        empresaDTO empresaDto
                = empresaDao.buscarEmpresaXProducto(request.getParameter("idProducto"));

        response.setContentType("application/json");
        new Gson().toJson(empresaDto, response.getWriter());

    }

    private void obtenerProducto(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");

        Conexion conexion = new Conexion();
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());
        productoImagenesDTO productoImagenesDTO = new productoImagenesDTO();
        Producto producto = new Producto();

        producto = productoDAO.buscarProducto(Integer.parseInt(request.getParameter("idProducto")));
        ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
        ArrayList<ImagenesProducto> listaImagenes= imagenesProductosDAO.getImagenesByProduc(producto.getIdProducto());
        
        productoImagenesDTO.setProducto(producto);
        productoImagenesDTO.setImagenes(listaImagenes);
        
        response.setContentType("application/json");
        imagenesProductosDAO.CloseAll();
        productoDAO.CloseAll();
        
        new Gson().toJson(productoImagenesDTO, response.getWriter());

    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
