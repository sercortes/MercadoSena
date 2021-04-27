package co.edu.sena.mercado.controller.admin;

import co.edu.sena.mercado.dao.CategorysDAO;
import co.edu.sena.mercado.dao.EmpresasDAO;
import co.edu.sena.mercado.dao.PersonasNaturalDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dao.UsuariosDAO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import co.edu.sena.mercado.util.codActivacion;
import co.edu.sena.mercado.util.correo;
import com.google.gson.Gson;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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
                    
                case "/Store/createSeller":

                    createSeller(request, response);

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

    private void createSeller(HttpServletRequest request, HttpServletResponse response) throws IOException {
       
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        codActivacion codigo = new codActivacion();
         correo enviar = new correo();
        Connection conn = null;
        UsuariosDAO usuarioDAO = null;
        PersonasNaturalDAO personaNaturalDAO = null;
        EmpresasDAO empresaDAO = null;
        try {

            Conexion conexion = new Conexion();
            conn = conexion.getConnection();

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            usuarioDAO = new UsuariosDAO(conn);
            personaNaturalDAO = new PersonasNaturalDAO(conn);
            empresaDAO = new EmpresasDAO(conn);

            usuarioDTO usuarioDTO = new usuarioDTO();
            usuarioDTO.setCorreoUsu(request.getParameter("correoUsuario"));
            usuarioDTO.setEstadoUsu("1");
            String clave = codigo.generarCod();
            usuarioDTO.setClaveUsu(clave);

            usuarioDTO.setIdRol(3);

            int idUser = usuarioDAO.registroVendedor(usuarioDTO);
            usuarioDTO.setIdUsuario(idUser);
            System.out.println(usuarioDTO.toString());
            personaNaturalDTO personaNaturalDTO = new personaNaturalDTO();
            personaNaturalDTO.setApellidoPer(request.getParameter("apellidoUsuario"));
            personaNaturalDTO.setCorreoPer(request.getParameter("correoUsuario"));
//            personaNaturalDTO.setIdCiudad(Integer.parseInt(request.getParameter("ciudadUsuario")));
//            personaNaturalDTO.setNumCelularPer(request.getParameter("celularUsuario"));
            personaNaturalDTO.setNombrePer(request.getParameter("nombreUsuario"));
            personaNaturalDTO.setUrlImg("./assets/images/usuario/imagenDefecto.png");
            personaNaturalDTO.setIdUsuario(idUser);

            personaNaturalDAO.registrarPersona(personaNaturalDTO);
            System.out.println(personaNaturalDTO.toString());

            enviar.envCorreoVendedor(usuarioDTO.getCorreoUsu(), clave);

            conn.commit();
            new Gson().toJson(1, response.getWriter());
        } catch (MySQLIntegrityConstraintViolationException ex1) {
            try {
                conn.rollback();
            } catch (SQLException ex3) {
                System.out.println(ex3);
            }
            System.out.println("ROLL BACK CONSTRAINT EXCEPTION");
            System.out.println(ex1);
            new Gson().toJson(2, response.getWriter());
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                System.out.println(ex1);
            }
            System.out.println("ROLL BACK SQL EXCEPTION REGISTRO");
            System.out.println(ex);
            new Gson().toJson(3, response.getWriter());
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                System.out.println(ex1);
            }
            System.out.println("ROLL BACK GENERAL EXCEPTION");
            System.out.println(ex);
            new Gson().toJson(3, response.getWriter());
        } finally {
            usuarioDAO.CloseAll();
            personaNaturalDAO.CloseAll();
            empresaDAO.CloseAll();
        }
        
    }

        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
