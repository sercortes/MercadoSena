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

//    personaNaturalDAO personaNaturalDAO = new personaNaturalDAO();
//    personaNaturalDTO personaNaturalDTO = new personaNaturalDTO();
    ArrayList<personaNaturalDTO> listaPersona = new ArrayList<>();
    ArrayList<ciudadDTO> listaCiudad = new ArrayList<>();
    tipoDocumentoDAO tipoDocDAO = new tipoDocumentoDAO();
    tipoDocumentoDTO tipoDocDTO = new tipoDocumentoDTO();
    ArrayList<tipoDocumentoDTO> listaTipoDoc = new ArrayList<>();
    generoDAO generoDAO = new generoDAO();
    generoDTO generoDTO = new generoDTO();
    ArrayList<generoDTO> listaGenero = new ArrayList<>();
    rolUsuarioDAO rolDAO = new rolUsuarioDAO();
    rolDTO rolDTO = new rolDTO();
    ArrayList<rolDTO> listaRol = new ArrayList<>();
    usuarioDTO usuarioDTO = new usuarioDTO();
//    usuarioDAO usuarioDAO = new usuarioDAO();
    ArrayList<usuarioDTO> listaUsuario = new ArrayList<>();
    correo enviar = new correo();
    codActivacion codigo = new codActivacion();
    empresaDAO empresaDAO = new empresaDAO();
    empresaDTO empresaDTO = new empresaDTO();
    ArrayList<empresaDTO> listaEmpresa = new ArrayList<>();
    preguntasDAO preguntaDAO = new preguntasDAO();
    preguntasDTO preguntaDTO = new preguntasDTO();
    ArrayList<preguntasDTO> listaPregunta = new ArrayList<>();
    respuestaDAO respuestaDAO = new respuestaDAO();
    respuestaDTO respuestaDTO = new respuestaDTO();
    ArrayList<respuestaDTO> listaRespuesta = new ArrayList<>();
    datosSesion datSesion = new datosSesion();

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
        HttpSession sesion = (HttpSession) request.getSession();
        String accion = request.getParameter("accion");

        switch (accion) {
            case "consultaTipoDoc":
                listaTipoDoc = new ArrayList<>();
                listaTipoDoc = tipoDocDAO.listarTipoDoc();
                System.out.println(new Gson().toJson(listaTipoDoc));
                response.setContentType("application/json");
                new Gson().toJson(listaTipoDoc, response.getWriter());

                break;
            case "consultaGenero":
                listaGenero = new ArrayList<>();
                listaGenero = generoDAO.listarGenero();
                response.setContentType("application/json");
                new Gson().toJson(listaGenero, response.getWriter());
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

            case "registroEmpresa":

                updateDatosCompany(request, response);

                break;
            case "listarPreguntas":
                response.setContentType("application/json");
                listaPregunta = new ArrayList<>();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                try {
                    listaPregunta = preguntaDAO.listarPregustas(usuarioDTO.getEmpresa().getIdEmpresa());
                    if (listaPregunta != null) {
                        new Gson().toJson(listaPregunta, response.getWriter());
                    }
                } catch (Exception e) {
                    System.out.println("XXXXXXXXXXXXXXXXXXXXxxxx error al listarPreguntas ln 277 registro " + e);
                }
                break;
            case "listarPreguntasRespuesta":
                response.setContentType("application/json");
                listaPregunta = new ArrayList<>();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                try {
                    listaPregunta = preguntaDAO.listarPregustasRespuesta(usuarioDTO.getIdUsuario());
                    preguntaDAO.marcarVistaPregunta(usuarioDTO.getIdUsuario());
                    new Gson().toJson(listaPregunta, response.getWriter());
                } catch (Exception e) {
                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX error al listarPreguntasRespuesta registro ln 306" + e);
                }
                break;
            case "consultaNotiPreguntas":

                usuarioDTO = new usuarioDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                // if(usuarioDTO.getEmpresa().getIdEmpresa()==5){
                int notPreguntas = 0;

                try {
                    notPreguntas = preguntaDAO.consultaNotiPreguntas(usuarioDTO.getEmpresa().getIdEmpresa());
                } catch (Exception e) {
                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX error al consultaNotiPreguntas regitro ln 313 " + e);
                }

                //System.out.println("........................preguntas " + notPreguntas);
                response.getWriter().print(notPreguntas);//}
                break;
            case "consultaNotiRespuestas":
                usuarioDTO = new usuarioDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                int notRespuestas = 0;
                try {
                    notRespuestas = preguntaDAO.consultaNotiRespuestas(usuarioDTO.getIdUsuario());
                } catch (Exception e) {
                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX error al consultaNotiRespuestas ln 328 registro " + e);
                }

                // System.out.println("........................" + notRespuestas);
                response.getWriter().print(notRespuestas);
                break;

            default:
                throw new AssertionError("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx Esa accion no existe");

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

            usuarioDTO = new usuarioDTO();
            usuarioDTO.setClaveUsu(request.getParameter("clave1"));
            usuarioDTO.setCorreoUsu(request.getParameter("correoUsuario"));
            usuarioDTO.setEstadoUsu("0");
            String clave = usuarioDTO.getClaveUsu();
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

    private void updateDatosCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession sesion = (HttpSession) request.getSession();

        usuarioDTO usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
        empresaDTO empresaDTO = getEmpresa(request, response);
        empresaDTO.setIdUsuario(usuarioDTO.getIdUsuario());

        Connection conn = null;
        EmpresasDAO empresaDAO = null;

        try {

            Conexion conexion = new Conexion();
            conn = conexion.getConnection();
            empresaDAO = new EmpresasDAO(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            empresaDAO.updateCompanyFirst(empresaDTO, usuarioDTO.getIdUsuario());
            conn.commit();

            usuarioDTO = getDatosSession(usuarioDTO, empresaDTO);
            sesion.setAttribute("USER", usuarioDTO);
            new Gson().toJson(1, response.getWriter());

        } catch (MySQLIntegrityConstraintViolationException ex1) {

            System.out.println("ROLL BACK CONSTRAINT EXCEPTION");
            System.out.println(ex1);
            try {
                conn.rollback();
            } catch (SQLException ex3) {
                System.out.println(ex3);
            }
            new Gson().toJson(2, response.getWriter());

        } catch (Exception ex) {

            System.out.println("ROLL BACK GENERAL EXCEPTION");
            System.out.println(ex);
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                System.out.println(ex1);
            }
            new Gson().toJson(3, response.getWriter());

        } finally {
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

}
