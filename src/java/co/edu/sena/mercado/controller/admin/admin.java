package co.edu.sena.mercado.controller.admin;

import co.edu.sena.mercado.dao.CategorysDAO;
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
        System.out.println("sesión invalida admin");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        if (user != null && user.getIdRol() == 3) {

            String direccion = request.getRequestURI();
            RequestDispatcher rd;

            switch (direccion) {

                case "/Store/createCategory":

                    createCategory(request, response);

                    break;

                case "/Store/createBrand":

                    createBrand(request, response);

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

    private void createCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        CategorysDAO categoriaDao = null;
        Connection cone = null;

        try {

            cone = conexion.getConnection();
            if (cone.getAutoCommit()) {
                cone.setAutoCommit(false);
            }
            categoriaDao = new CategorysDAO(cone);
            categoriaDao.insertReturnCategoria(request.getParameter("nombre"));
            response.setContentType("application/json");
            cone.commit();
            new Gson().toJson(true, response.getWriter());

        } catch (Exception ex) {

            ex.printStackTrace();
            try {
                cone.rollback();
                ex.printStackTrace();
                new Gson().toJson(false, response.getWriter());
                System.out.println("ROLL BACK DELETE PRODUCT");
                System.out.println(ex);
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } finally {
            categoriaDao.CloseAll();
        }

    }

    private void createBrand(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        CategorysDAO categoriaDao = null;
        Connection cone = null;

        try {

            cone = conexion.getConnection();
            if (cone.getAutoCommit()) {
                cone.setAutoCommit(false);
            }
            categoriaDao = new CategorysDAO(cone);
            categoriaDao.insertReturnMarca(request.getParameter("nombre"));
            response.setContentType("application/json");
            cone.commit();
            new Gson().toJson(true, response.getWriter());

        } catch (Exception ex) {

            ex.printStackTrace();
            try {
                cone.rollback();
                ex.printStackTrace();
                new Gson().toJson(false, response.getWriter());
                System.out.println("ROLL BACK DELETE PRODUCT");
                System.out.println(ex);
            } catch (SQLException ex1) {
                ex1.printStackTrace();
                Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } finally {
            categoriaDao.CloseAll();
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
