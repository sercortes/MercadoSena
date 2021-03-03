package co.edu.sena.mercado.controller.admin;

import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class admin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        if (user != null && user.getIdRol() == 5) {

            String direccion = request.getRequestURI();
            RequestDispatcher rd;

            switch (direccion) {

                case "/Store/getProductsCheck":

                    rd = request.getRequestDispatcher("/views/admin/products.jsp");
                    rd.forward(request, response);

                    break;

                default:

                    System.out.println("sesión invalida admin");
                    response.sendRedirect(request.getContextPath() + "/home");

                    break;

            }

        } else {
            System.out.println("sesión invalida admin");
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        if (user != null && user.getIdRol() == 5) {
          
             String direccion = request.getRequestURI();
            RequestDispatcher rd;

            switch (direccion) {

                case "/MercadoSena/getProductsForCheck":

                    getProducts(request, response);

                    break;
                    
               case "/MercadoSena/updateStatusProduct":

                    updateProduct(request, response);

                    break;

                default:

                    System.out.println("sesión invalida admin");
                    response.sendRedirect(request.getContextPath() + "/home");

                    break;

            }

            
        } else {
            System.out.println("sesión invalida admin");
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
    
    private void getProducts(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        ProductoDAO productoDAO = new ProductoDAO(conexion.getConnection());
        ArrayList<Producto> listaProductos = productoDAO.getProductsByDateTimeAscForCheck();
        response.setContentType("application/json");
        productoDAO.CloseAll();
        new Gson().toJson(listaProductos, response.getWriter());

    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String idProducto = request.getParameter("idProducto");
        String aprobar = request.getParameter("aprobar");
        String comentario = request.getParameter("comment");
        boolean estatus = Boolean.parseBoolean(aprobar);
        String idEstado = "";
        
        if (estatus) {
            idEstado = "2";
        }else{
            idEstado = "4";
        }
        
        request.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        Connection cone = conexion.getConnection();
        ProductoDAO productoDAO = null;
      try {
            if (cone.getAutoCommit()) {
                cone.setAutoCommit(false);
            }
            productoDAO = new ProductoDAO(cone);
            productoDAO.disabledProductWithAdmin(idProducto, idEstado, comentario);
            response.setContentType("application/json");
            cone.commit();
            new Gson().toJson(true, response.getWriter());

        } catch (Exception ex) {
            
            try {
                System.out.println("ROLL BACK DELETE PRODUCT");
                System.out.println(ex);
                cone.rollback();
                new Gson().toJson(false, response.getWriter());
            } catch (SQLException ex1) {
                System.out.println(ex1);
            }

        }finally{
            productoDAO.CloseAll();
        }
        
        
    }
    
        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
