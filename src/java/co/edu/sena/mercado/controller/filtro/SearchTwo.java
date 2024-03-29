package co.edu.sena.mercado.controller.filtro;

import static co.edu.sena.mercado.controller.filtro.Search.isValidUsername;
import static co.edu.sena.mercado.controller.filtro.Search.validCategoriaMarca;
import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductorDAOQuerys;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.empresaDTO;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("SearchThree no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String direccion = request.getServletPath();

        switch (direccion) {

            case "/getProductsByNameCategory":

                getProductsByNameCategory(request, response);

                break;

            case "/getProductsByMarca":

                getProductsByMarca(request, response);

                break;

            case "/getProductsByNameMarca":

                getProductsByNameMarca(request, response);

                break;

            case "/getProductsByCategoryMarca":

                getProductsByCategoryMarca(request, response);

                break;

            case "/getProductsByNameCategoryMarca":

                getProductsByNameCategoryMarca(request, response);

                break;

        }

    }

    private void getProductsByNameCategory(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String palabra = request.getParameter("word");
        String categoria = request.getParameter("categorias");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setNombreCategoria(categoria);

        if (!isValidUsername(palabra) || !validCategoriaMarca(categoria)) {

            new Gson().toJson(null, response.getWriter());

        } else {

            Conexion conexion = new Conexion();
            ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
            ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
            ArrayList<Producto> listaProductos = productoDAO.getProductsByNameCategory(producto);

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
        
    }

    private void getProductsByMarca(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String marca = request.getParameter("marca");

        if (!validCategoriaMarca(marca)) {

            new Gson().toJson(null, response.getWriter());

        } else {

            Conexion conexion = new Conexion();
            ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
            ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
            ArrayList<Producto> listaProductos = productoDAO.getProductsByMarca(marca);

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
        
    }

    private void getProductsByNameMarca(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String marca = request.getParameter("marca");
        String word = request.getParameter("word");

        if (!isValidUsername(word) || !validCategoriaMarca(marca)) {

            new Gson().toJson(null, response.getWriter());

        } else {

            Conexion conexion = new Conexion();
            ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
            ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
            ArrayList<Producto> listaProductos = productoDAO.getProductsByNameMarca(marca, word);

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

    }

    private void getProductsByCategoryMarca(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String marca = request.getParameter("marca");
        String cartegory = request.getParameter("categorias");

        if (!validCategoriaMarca(marca) || !validCategoriaMarca(cartegory)) {

            new Gson().toJson(null, response.getWriter());

        } else {

            Conexion conexion = new Conexion();
            ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
            ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
            ArrayList<Producto> listaProductos = productoDAO.getProductsByCategoryMarca(marca, cartegory);

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

    }

    private void getProductsByNameCategoryMarca(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String palabra = request.getParameter("word");
        String categoria = request.getParameter("categorias");
        String marca = request.getParameter("marca");
        Producto producto = new Producto();
        producto.setNombreProducto(palabra);
        producto.setNombreCategoria(categoria);
        producto.setMarcaProducto(marca);

        if (!isValidUsername(palabra) || !validCategoriaMarca(categoria) || !validCategoriaMarca(marca)) {

            new Gson().toJson(null, response.getWriter());

        } else {

            Conexion conexion = new Conexion();
            ProductorDAOQuerys productoDAO = new ProductorDAOQuerys(conexion.getConnection());
            ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conexion.getConnection());
            ArrayList<Producto> listaProductos = productoDAO.getProductsByNameCategoryMarca(producto);

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

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
