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

/**
 *
 * @author serfin
 */
public class Selects extends HttpServlet {

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

            case "/MercadoSena/getCategorys":

                getCategorys(request, response);

                break;

            case "/MercadoSena/getProducts":

                getProducts(request, response);

                break;

            case "/MercadoSena/getImages":

                getImages(request, response);

                break;

            case "/MercadoSena/getProductsByDateTime":

                getProductsByDateTime(request, response);

                break;

            case "/MercadoSena/getImagesByProduct":

                getImagesByProduct(request, response);

                break;

            case "/MercadoSena/getInfoCompanyByProduct":

                getInfoCompanyByProduct(request, response);

                break;
            case "/MercadoSena/obtenerProducto":

                obtenerProducto(request, response);

                break;

        }

    }

    private void getCategorys(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");

        Conexion conexion = new Conexion();
        CategorysDAO categorysDAO = new CategorysDAO(conexion.getConnection());

        ArrayList<?> lista = categorysDAO.getCategorys();

        response.setContentType("application/json");

        categorysDAO.CloseAll();

        new Gson().toJson(lista, response.getWriter());

    }

    private void getProducts(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");

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

    private void getProductsByDateTime(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");

        Conexion conexion = new Conexion();
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());

        ArrayList<Producto> listaProductos = productoDAO.getProductsByDateTimeAsc();

        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

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
