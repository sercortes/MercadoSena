/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.validation;

import co.edu.sena.mercado.dto.usuarioDTO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author equipo
 */
public class Validation extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("generateSale no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("USER") != null) {

            String direccion = request.getRequestURI();

            switch (direccion) {

                case "/MercadoSena/getIdEmpresa":

                    getIdEmpresa(request, response);

                    break;
                    
                case "/MercadoSena/getRol":

                    getRol(request, response);

                    break;

                default:
                    
                    break;
            }

        } else {
            new Gson().toJson(0, response.getWriter());
        }
    }

    private void getIdEmpresa(HttpServletRequest request, HttpServletResponse response) throws IOException {
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        response.setContentType("application/json");
        String idPersona = "";
        idPersona = Integer.toString(usu.getPersona().getIdPer());
        new Gson().toJson(idPersona, response.getWriter());
    }
    
    private void getRol(HttpServletRequest request, HttpServletResponse response) throws IOException {
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        response.setContentType("application/json");
        String idPersona = "";
        idPersona = Integer.toString(usu.getIdRol());
        new Gson().toJson(idPersona, response.getWriter());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
