/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.registro;

import co.edu.sena.mercado.dao.ciudadDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
import co.edu.sena.mercado.dao.*;
import co.edu.sena.mercado.dto.ciudadDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.*;
import co.edu.sena.mercado.util.Conexion;
import co.edu.sena.mercado.util.correo;
import co.edu.sena.mercado.util.codActivacion;
import co.edu.sena.mercado.util.datosSesion;
import com.google.gson.Gson;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class registro extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Registro no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        switch (accion) {
            
            case "consultaGenero":

                consultaGenero(request, response);

                break;
            case "consultaCiudad":

                generateCiudades(request, response);

                break;
            case "listarCategorias":

                generateCategorias(request, response);

                break;

            case "registrarUsuario":

                registrarUsuario(request, response);

                break;

        }

    }

    private void generateCiudades(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Conexion conexion = new Conexion();
        ArrayList<ciudadDTO> listaCiudad = new ArrayList<>();
        Connection conn = conexion.getConnection();
        ciudadDAO ciDAO = new ciudadDAO(conn);
        listaCiudad = ciDAO.ListCiudades();
        ciDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaCiudad, response.getWriter());

    }

    private void generateCategorias(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Conexion conexion = new Conexion();
        ArrayList<Categorys> listaCategoria = new ArrayList<>();
        Connection conn = conexion.getConnection();
        CategorysDAO categoriasDAO = new CategorysDAO(conn);
        listaCategoria = categoriasDAO.getCategorys();
        categoriasDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaCategoria, response.getWriter());

    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

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
            usuarioDTO.setClaveUsu(request.getParameter("clave1"));
            usuarioDTO.setCorreoUsu(request.getParameter("correoUsuario"));
            usuarioDTO.setEstadoUsu("0");
            String clave = usuarioDTO.getClaveUsu();
            codActivacion codigo = new codActivacion();
            usuarioDTO.setCodigo(codigo.generarCod());
            usuarioDTO.setIdRol(2);

            int idUser = usuarioDAO.registroUsuario(usuarioDTO);
            usuarioDTO.setIdUsuario(idUser);
            personaNaturalDTO personaNaturalDTO = new personaNaturalDTO();
            personaNaturalDTO.setApellidoPer(request.getParameter("apellidoUsuario"));
            personaNaturalDTO.setCorreoPer(request.getParameter("correoUsuario"));
            personaNaturalDTO.setNumCelularPer(request.getParameter("celularUsuario"));
            personaNaturalDTO.setNombrePer(request.getParameter("nombreUsuario"));
            personaNaturalDTO.setUrlImg("./assets/images/usuario/imagenDefecto.png");
            personaNaturalDTO.setIdUsuario(idUser);

            personaNaturalDAO.registrarPersona(personaNaturalDTO);
            correo enviar = new correo();

            enviar.envCorreo(usuarioDTO.getCorreoUsu(), clave, usuarioDTO.getCodigo());
            System.out.println("REGISTRADO");
            System.out.println(personaNaturalDTO.toString());
            System.out.println(usuarioDTO.toString());
            System.out.println("");
            conn.commit();
            new Gson().toJson(1, response.getWriter());
        } catch (MySQLIntegrityConstraintViolationException ex1) {
            try {
                conn.rollback();
            } catch (SQLException ex3) {
                System.out.println(ex3);
            }
            System.out.println("ROLL BACK CONSTRAINT EXCEPTION");
            ex1.printStackTrace();
            new Gson().toJson(2, response.getWriter());
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            System.out.println("ROLL BACK SQL EXCEPTION REGISTRO");
            ex.printStackTrace();
            new Gson().toJson(3, response.getWriter());
        } catch (Exception ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                System.out.println(ex1);
            }
            System.out.println("ROLL BACK GENERAL EXCEPTION");
            ex.printStackTrace();
            new Gson().toJson(3, response.getWriter());
        } finally {
            usuarioDAO.CloseAll();
            personaNaturalDAO.CloseAll();
            empresaDAO.CloseAll();
        }

    }

    private empresaDTO getEmpresa(HttpServletRequest request, HttpServletResponse response) {

        empresaDTO empresaDTO = new empresaDTO();
        empresaDTO.setNombreEmpresa(request.getParameter("nombreEmpresa"));
        empresaDTO.setCelEmpresa(request.getParameter("celularEmpresa"));
        empresaDTO.setTelEmpresa(request.getParameter("telefonoEmpresa"));
//        empresaDTO.setCorreoEmpresa(request.getParameter("correoEmpresa"));
        empresaDTO.setDirEmpresa(request.getParameter("direccionEmpresa"));
        empresaDTO.setIdCiudad(Integer.parseInt(request.getParameter("idCiudadEmpresa")));
        empresaDTO.setEsEmpresa(1);
        return empresaDTO;

    }

    private usuarioDTO getDatosSession(usuarioDTO usuarioDTO, empresaDTO empresaDTO) {

        usuarioDTO.getEmpresa().setNombreEmpresa(empresaDTO.getNombreEmpresa());
        usuarioDTO.getEmpresa().setCelEmpresa(empresaDTO.getCelEmpresa());
        usuarioDTO.getEmpresa().setTelEmpresa(empresaDTO.getTelEmpresa());
        usuarioDTO.getEmpresa().setDirEmpresa(empresaDTO.getDirEmpresa());
        usuarioDTO.getEmpresa().setCorreoEmpresa(empresaDTO.getCorreoEmpresa());
        usuarioDTO.getEmpresa().setIdCiudad(empresaDTO.getIdCiudad());

        return usuarioDTO;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void consultaGenero(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Conexion conexion = new Conexion();
        ArrayList<generoDTO> generos = new ArrayList<>();
        Connection conn = conexion.getConnection();
        generoDAO geAO = new generoDAO(conn);
        generos = geAO.listarGenero();
        geAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(generos, response.getWriter());

    }

}
