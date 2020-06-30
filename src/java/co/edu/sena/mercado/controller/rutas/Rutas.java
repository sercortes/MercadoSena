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
import java.io.IOException;
import java.io.PrintWriter;
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
    ciudadDAO ciudadDAO = new ciudadDAO();
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
                request.setAttribute("activa", activa);
                rd = request.getRequestDispatcher("/views/activarCuenta.jsp");
                rd.forward(request, response);
                break;
            case "/MercadoSena/logout":
                request.getSession().removeAttribute("USER");
                request.getSession().invalidate();
                response.sendRedirect("/MercadoSena/home");
                break;
            case "/MercadoSena/preguntas":
                rd = request.getRequestDispatcher("/views/preguntas/preguntas.jsp");
                rd.forward(request, response);
                break;
            case "/MercadoSena/home":
                rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
                break;
            case "/MercadoSena/usuario":

                listaCiudad = new ArrayList<>();
                listaCiudad = ciudadDAO.listarCiudad();

//                listaTipoDoc = new ArrayList<>();
//                listaTipoDoc = tipoDocDAO.listarTipoDoc();
//
//                listaGenero = new ArrayList<>();
//                listaGenero = generoDAO.listarGenero();
                request.setAttribute("listaCiudad", listaCiudad);
//                request.setAttribute("listaTipoDoc", listaTipoDoc);
//                request.setAttribute("listaGenero", listaGenero);

                rd = request.getRequestDispatcher("/views/actualizar/actualizarDatos.jsp");
                rd.forward(request, response);
//
                break;
            case "/MercadoSena/ventasVendedor":
                request.getRequestDispatcher("/views/ventas/ventasVendedor.jsp").forward(request, response);
                break;
            default:
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error de la ruta");
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
