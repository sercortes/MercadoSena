/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.rutas;

import co.edu.sena.mercado.dao.UsuarioDAOLogin;
import co.edu.sena.mercado.dao.usuarioDAO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import co.edu.sena.mercado.util.correo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author serfin
 */
public class Rutas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String direccion = request.getRequestURI();
        RequestDispatcher rd;

        switch (direccion) {
            case "/Store/Searching...":
                rd = request.getRequestDispatcher("/views/searching/search.jsp");
                rd.forward(request, response);
                break;
            case "/Store/Terminos":
                rd = request.getRequestDispatcher("/views/searching/terminos.jsp");
                rd.forward(request, response);
                break;
            case "/Store/logout":
                request.getSession().removeAttribute("USER");
                request.getSession().invalidate();
                response.sendRedirect("/Store/");
                break;
            case "/Store/home":
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
                break;
            case "/Store/activarCuenta":
                activateAccount(request, response);
                break;
            case "/Store/activate":
                rd = request.getRequestDispatcher("/views/activate/activarCuenta.jsp");
                rd.forward(request, response);
                break;
                
            case "/Store/noActivate":
                rd = request.getRequestDispatcher("/views/activate/noActivate.jsp");
                rd.forward(request, response);
                break;
                 case "/Store/resetFull":
                rd = request.getRequestDispatcher("/views/reset/reset.jsp");
                rd.forward(request, response);
                break;
            case "/Store/noReset":
                rd = request.getRequestDispatcher("/views/reset/noReset.jsp");
                rd.forward(request, response);
                break;
            case "/Store/reset":
                reset(request, response);
                break;
            default:
                System.out.println("error de la ruta RUTAS");
                response.sendRedirect(request.getContextPath() + "/home");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("error de la ruta POST RUTAS");
        response.sendRedirect(request.getContextPath() + "/home");

    }

    private void activateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        boolean activa;
        String usuario = request.getParameter("usuario");
        String codigo = request.getParameter("codigo");
        usuarioDAO usuarioDAO = new usuarioDAO();
        activa = usuarioDAO.activarUsuario(usuario, codigo);
        System.out.println("USUARIO " + usuario);
        if (activa) {
            System.out.println("ACTIVADO");
            request.setAttribute("ACTIVA", activa);
            response.sendRedirect("./activate");
        } else {
            System.out.println("Enlace vencido");
            response.sendRedirect("./noActivate");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void reset(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String usuario = request.getParameter("usuario");
        String hash = request.getParameter("codigo");
        System.out.println("RESET ACCOUNT");
        System.out.println(usuario);
        System.out.println(hash);
        RequestDispatcher rd;
        String pass = "";

        UsuarioDAOLogin usuarioDAOLogin = null;
        Connection conn = null;

        Conexion conexion = new Conexion();
        conn = conexion.getConnection();

        try {

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            usuarioDAOLogin = new UsuarioDAOLogin(conn);
            usuarioDTO usDTO = usuarioDAOLogin.getHash(usuario, hash);

            if (usDTO.getIdUsuario() == 0) {
                System.out.println("NO CAMBIADA");
                response.sendRedirect("./noReset");
            } else {
                pass = generatePassword();
                usuarioDAOLogin.resetPass(usuario, pass);
                correo co = new correo();
                co.passCorreo(usuario, pass);
                conn.commit();
                System.out.println("CLAVE CAMBIADA");
                response.sendRedirect("./resetFull");
            }
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        } catch (MessagingException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            usuarioDAOLogin.CloseAll();
        }

    }

    public String generatePassword() {
        return RandomStringUtils.random(10, 0, 20, true, true, "qw32rfHIJk9iQ8Ud7h0X".toCharArray());
    }

}
