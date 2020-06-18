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
 * @author DELL
 */
public class rutaUsuario extends HttpServlet {

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

                break;
            default:
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error de la ruta");
                break;
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
