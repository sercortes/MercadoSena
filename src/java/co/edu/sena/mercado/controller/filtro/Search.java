
package co.edu.sena.mercado.controller.filtro;

import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dao.ProductorDAOQuerys;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.Producto;
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

public class Search extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("search no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String direccion = request.getRequestURI();

        switch (direccion) {

            case "/Store/getProductsRandom":

                getProductsRandom(request, response);

                break;

            case "/Store/getProductsByDateTime":

                getProductsByDateTime(request, response);

                break;

            case "/Store/getProductsByWord":

                getProductsByWord(request, response);

                break;

            case "/Store/getProductsByCategory":

                getProductsByCategory(request, response);

                break;

        }
    }

    private void getProductsRandom(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
        ArrayList<Producto> listaProductos = productoDAO.getProductsRandom();
        
        listaProductos.forEach((item) -> {
            ArrayList<ImagenesProducto> listaImagenes
                    = imagenesProductosDAO.getImagenesByProduc(item.getIdProducto());
            item.setListaImagenes(listaImagenes);
        });
        
        productoDAO.CloseAll();
        imagenesProductosDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void getProductsByDateTime(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
        ArrayList<Producto> listaProductos = productoDAO.getProductsByDateTimeAsc();
        
        listaProductos.forEach((item) -> {
            ArrayList<ImagenesProducto> listaImagenes
                    = imagenesProductosDAO.getImagenesByProduc(item.getIdProducto());
            item.setListaImagenes(listaImagenes);
        });
        
        imagenesProductosDAO.CloseAll();
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void getProductsByWord(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String palabra = request.getParameter("word");
        ArrayList<Producto> listaProductos = productoDAO.getProductsByWord(palabra);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());
    }

    private void getProductsByCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
        String categoria = request.getParameter("categorias");
        ArrayList<Producto> listaProductos = productoDAO.getProductsByCategory(categoria);
        productoDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaProductos, response.getWriter());

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
