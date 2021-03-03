/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.rutas;

import co.edu.sena.mercado.dao.usuarioDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            default:
                System.out.println("error de la ruta RUTAS");
                response.sendRedirect(request.getContextPath() + "/home");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String direccion = request.getRequestURI();

        switch (direccion) {
            case "/Store/activarCuenta":
                activateAccount(request, response);
                break;
            default:
                System.out.println("error de la ruta POST RUTAS");
                response.sendRedirect(request.getContextPath() + "/home");
                break;
        }

    }

    private void activateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RequestDispatcher rd;
        boolean activa;
        String usuario = request.getParameter("usuario");
        String codigo = request.getParameter("codigo");
        usuarioDAO usuarioDAO = new usuarioDAO();
        activa = usuarioDAO.activarUsuario(usuario, codigo);
        if (activa) {
            request.setAttribute("ACTIVA", activa);
            rd = request.getRequestDispatcher("/views/activarCuenta.jsp");
            rd.forward(request, response);
        } else {
            rd = request.getRequestDispatcher("/views/activarCuenta.jsp");
            rd.forward(request, response);
        }

    }
    
        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
