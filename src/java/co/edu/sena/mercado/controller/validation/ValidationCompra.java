/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.validation;

import co.edu.sena.mercado.dao.PersonasNaturalDAO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidationCompra extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("vali No soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");

        if (request.getSession().getAttribute("USER") != null) {

            try {
                String direccion = request.getRequestURI();

                switch (direccion) {

                    case "/Store/checkUpdateData":

                        checkUpdateData(request, response);

                        break;

                    case "/Store/UpdateDataPerson":

                        UpdateDataPerson(request, response);

                        break;

                    default:

                        System.out.println("generateSale no soporta GET");
                        response.sendRedirect(request.getContextPath() + "/home");

                        break;

                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else {
            new Gson().toJson(false, response.getWriter());
            System.out.println("NO VALORES DE SESIón");
        }
    }

    private void checkUpdateData(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        personaNaturalDTO perDTO = usu.getPersona();

        if (StringUtils.isEmptyOrWhitespaceOnly(perDTO.getNombrePer())
                || StringUtils.isEmptyOrWhitespaceOnly(perDTO.getApellidoPer())
                || StringUtils.isEmptyOrWhitespaceOnly(perDTO.getCorreoPer())
                || StringUtils.isEmptyOrWhitespaceOnly(perDTO.getNumeroDocPer())
                || StringUtils.isEmptyOrWhitespaceOnly(perDTO.getDireccionPer())
                || StringUtils.isEmptyOrWhitespaceOnly(perDTO.getNumCelularPer())) {
            new Gson().toJson(true, response.getWriter());
        } else {

            new Gson().toJson(false, response.getWriter());

        }

    }

    private void UpdateDataPerson(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        personaNaturalDTO perDTO = usu.getPersona();
        perDTO.setNumeroDocPer(request.getParameter("documento"));
        perDTO.setDireccionPer(request.getParameter("direccion"));
        perDTO.setNumCelularPer(request.getParameter("celular"));
        perDTO.setTelPer(request.getParameter("telefono"));
        System.out.println("ACTUALIZAR");
        perDTO.toString();
        PersonasNaturalDAO personasNaturalDAO = null;
        
        try { 
          personasNaturalDAO = new PersonasNaturalDAO(new Conexion().getConnection());
          personasNaturalDAO.actualizarDatosFaltantes(perDTO, usu.getIdUsuario());
          new Gson().toJson(true, response.getWriter());
          usu.setPersona(perDTO);
          request.getSession().setAttribute("USER", usu);
        } catch (Exception ex) {
            new Gson().toJson(false, response.getWriter());
            System.out.println(ex);
        }finally{
            personasNaturalDAO.CloseAll();
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
