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

                    case "/Store/getComprasIncomplete":

                        getComprasIncomplete(request, response);

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
        System.out.println("CHECK UPDATE DATA USU");
        System.out.println(usu.toString());
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
        System.out.println(usu.toString());
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
            ex.printStackTrace();
        } finally {
            personasNaturalDAO.CloseAll();
        }

    }

    private void getComprasIncomplete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        System.out.println("CHECK COMPRAS IMCOMPLETE");
        System.out.println(usu.toString());
        PersonasNaturalDAO personasNaturalDAO = null;
        int numero = 0;

        try {

            if (request.getSession().getAttribute("BLOQUEO") != null) {
                new Gson().toJson(3, response.getWriter());
                throw new Exception();
            }

            personasNaturalDAO = new PersonasNaturalDAO(new Conexion().getConnection());
            numero = personasNaturalDAO.getComprasIncomplete(Integer.toString(usu.getPersona().getIdPer()));
            System.out.println("Compras incomplete "+numero);
            System.out.println("");
            if (numero >= 4) {
                request.getSession().setAttribute("BLOQUEO", 1);
            } else {
                request.getSession().removeAttribute("BLOQUEO");
            }
            new Gson().toJson(numero, response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
            new Gson().toJson(0, response.getWriter());
        } finally {
            personasNaturalDAO.CloseAll();
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
