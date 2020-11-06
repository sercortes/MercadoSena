/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.rutas;

import co.edu.sena.mercado.dao.ciudadDAO;
import co.edu.sena.mercado.dao.generoDAO;
import co.edu.sena.mercado.dao.tipoDocumentoDAO;
import co.edu.sena.mercado.dao.usuarioDAO;
import co.edu.sena.mercado.dto.ciudadDTO;
import co.edu.sena.mercado.dto.generoDTO;
import co.edu.sena.mercado.dto.tipoDocumentoDTO;
import co.edu.sena.mercado.util.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
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

    usuarioDAO usuarioDAO = new usuarioDAO();
    ArrayList<ciudadDTO> listaCiudad;
    generoDAO generoDAO = new generoDAO();
    ArrayList<generoDTO> listaGenero;
    tipoDocumentoDAO tipoDocDAO = new tipoDocumentoDAO();
    ArrayList<tipoDocumentoDTO> listaTipoDoc;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String direccion = request.getRequestURI();
        RequestDispatcher rd;

        switch (direccion) {
            case "/MercadoSena/Searching...":
                rd = request.getRequestDispatcher("/views/searching/search.jsp");
                rd.forward(request, response);
                break;
            case "/MercadoSena/activarCuenta":
                boolean activa;
                String usuario = request.getParameter("usuario");
                String codigo = request.getParameter("codigo");
                activa = usuarioDAO.activarUsuario(usuario, codigo);
                if (activa) {
                    request.setAttribute("ACTIVA", activa);
                    rd = request.getRequestDispatcher("/views/activarCuenta.jsp");
                    rd.forward(request, response);
                }else{
                    rd = request.getRequestDispatcher("/views/activarCuenta.jsp");
                    rd.forward(request, response);
                }
                break;
            case "/MercadoSena/logout":
                request.getSession().removeAttribute("USER");
                request.getSession().invalidate();
                response.sendRedirect("/MercadoSena/");
                break;
            case "/MercadoSena/home":
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
                break;
            default:
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error de la ruta");
                response.sendRedirect(request.getContextPath() + "/home");
                break;
        }
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Registro no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
