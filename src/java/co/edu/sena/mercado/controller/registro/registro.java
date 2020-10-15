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
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

/**
 *
 * @author DELL
 */
public class registro extends HttpServlet {

    personaNaturalDAO personaNaturalDAO = new personaNaturalDAO();
    personaNaturalDTO personaNaturalDTO = new personaNaturalDTO();
    ArrayList<personaNaturalDTO> listaPersona = new ArrayList<>();
    ciudadDAO ciudadDAO = new ciudadDAO();
    ciudadDTO ciudadDTO = new ciudadDTO();
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
    usuarioDAO usuarioDAO = new usuarioDAO();
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
                listaCiudad = new ArrayList<>();
                listaCiudad = ciudadDAO.listarCiudad();
                response.setContentType("application/json");
                new Gson().toJson(listaCiudad, response.getWriter());
                break;
            case "listarCategorias":
                Conexion conexion = new Conexion();
                ArrayList<Categorys> listaCategoria = new ArrayList<>();

                Connection conn = conexion.getConnection();
                CategorysDAO categoriasDAO = new CategorysDAO(conn);
                listaCategoria = categoriasDAO.getCategorys();
                categoriasDAO.CloseAll();
                response.setContentType("application/json");

                new Gson().toJson(listaCategoria, response.getWriter());
                break;
            case "registrarUsuario":
                boolean respuesta;
                usuarioDTO = new usuarioDTO();
                personaNaturalDTO = new personaNaturalDTO();
                listaRol = rolDAO.listarRol();
                usuarioDTO.setClaveUsu(request.getParameter("clave1"));
                usuarioDTO.setCorreoUsu(request.getParameter("correoUsuario"));
                usuarioDTO.setEstadoUsu("0");
                String clave = usuarioDTO.getClaveUsu();
                usuarioDTO.setCodigo(codigo.generarCod());
                String[] correo = usuarioDTO.getCorreoUsu().split("@");
                String dominio = correo[1];
                for (rolDTO rol : listaRol) {
                    if (dominio.equalsIgnoreCase("misena.edu.co") || dominio.equalsIgnoreCase("sena.edu.co")) {
                        if (rol.getRol().equalsIgnoreCase("misena")) {
                            usuarioDTO.setIdRol(rol.getIdRol());
                        }
                    } else {
                        if (rol.getRol().equalsIgnoreCase("comprador")) {
                            usuarioDTO.setIdRol(rol.getIdRol());
                        }
                    }
                }

                if (usuarioDAO.registroUsuario(usuarioDTO)) {
                    //datos persona
                    personaNaturalDTO.setApellidoPer(request.getParameter("apellidoUsuario"));
                    personaNaturalDTO.setCorreoPer(request.getParameter("correoUsuario"));
                    personaNaturalDTO.setIdCiudad(Integer.parseInt(request.getParameter("ciudadUsuario")));
                    personaNaturalDTO.setIdGenero(Integer.parseInt(request.getParameter("generoUsuario")));
                    personaNaturalDTO.setIdTipoDoc(Integer.parseInt(request.getParameter("tipoDocUsuario")));
                    personaNaturalDTO.setNumCelularPer(request.getParameter("celularUsuario"));
                    personaNaturalDTO.setNombrePer(request.getParameter("nombreUsuario"));                                  
                    personaNaturalDTO.setUrlImg("./assets/images/usuario/imagenDefecto.png");

                    usuarioDTO = usuarioDAO.buscarUsuario(personaNaturalDTO.getCorreoPer(), usuarioDTO.getClaveUsu());
                    personaNaturalDTO.setIdUsuario(usuarioDTO.getIdUsuario());
                    respuesta = true;
                    if (personaNaturalDAO.registrarPersona(personaNaturalDTO)) {
                        respuesta = true;
                        if (enviar.envCorreo(usuarioDTO.getCorreoUsu(), clave, usuarioDTO.getCodigo())) {
                            respuesta = true;

//                            if (usuarioDTO.getIdRol() == 3) {
//                                empresaDTO = new empresaDTO();
//                                empresaDTO.setCelEmpresa(personaNaturalDTO.getNumCelularPer());
//                                empresaDTO.setCorreoEmpresa(personaNaturalDTO.getCorreoPer());
//                                empresaDTO.setDirEmpresa(personaNaturalDTO.getDireccionPer());
//                                empresaDTO.setEsEmpresa(1);
//                                empresaDTO.setIdCiudad(personaNaturalDTO.getIdCiudad());
//                                empresaDTO.setIdUsuario(usuarioDTO.getIdUsuario());
//                                empresaDTO.setNombreEmpresa(personaNaturalDTO.getNombrePer());
//                                empresaDTO.setTelEmpresa(personaNaturalDTO.getTelPer());
//                                empresaDTO.setEsEmpresa(0);
//                                if (empresaDAO.registroEmpresa(empresaDTO)) {
//                                    respuesta = true;
//
//                                } else {
//                                    respuesta = false;
//
//                                }
//
//                            }
                        } else {
                            //borrar usuario
                            respuesta = false;

                        }

                    } else {
                        usuarioDAO.eliminarUsuario(personaNaturalDTO.getCorreoPer(), request.getParameter("correoUsuario"));
                        respuesta = false;

                    }
                } else {
                    respuesta = false;
                    usuarioDTO = new usuarioDTO();

                }

                response.getWriter().print(respuesta);
                //System.out.println("......."+usuarioDTO.toString());
                break;

            case "registroEmpresa":
                empresaDTO = new empresaDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                empresaDTO.setNombreEmpresa(request.getParameter("nombreEmpresa"));
                empresaDTO.setCelEmpresa(request.getParameter("celularEmpresa"));
                empresaDTO.setTelEmpresa(request.getParameter("telefonoEmpresa"));
                empresaDTO.setCorreoEmpresa(request.getParameter("correoEmpresa"));
                empresaDTO.setDirEmpresa(request.getParameter("direccionEmpresa"));
                empresaDTO.setIdCiudad(Integer.parseInt(request.getParameter("idCiudadEmpresa")));
                empresaDTO.setEsEmpresa(1);
                //de la sesion
                empresaDTO.setIdUsuario(usuarioDTO.getIdUsuario());
                if (empresaDAO.registroEmpresa(empresaDTO, usuarioDTO.getIdUsuario())) {

                    // sesion.removeAttribute("USER");
                    response.getWriter().print(true);

                    usuarioDTO = datSesion.consultarDatos(usuarioDTO);
                    sesion.setAttribute("USER", usuarioDTO);
                } else {
                    response.getWriter().print(false);
                }

                break;
            case "registroPregunta":
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                preguntaDTO = new preguntasDTO();
                preguntaDTO.setEstadoPregunta(0);
                preguntaDTO.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));
                preguntaDTO.setIdUsuarioPregunta(usuarioDTO.getIdUsuario());
                preguntaDTO.setPregunta(request.getParameter("mensaje"));
                // System.out.println("......."+preguntaDTO.toString());
                if (preguntaDAO.resgistroPregunta(preguntaDTO)) {
                    response.getWriter().print(true);
                } else {
                    response.getWriter().print(false);
                }
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
            case "registroRespuesta":
                respuestaDTO = new respuestaDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                respuestaDTO.setIdEmpresa(usuarioDTO.getEmpresa().getIdEmpresa());
                respuestaDTO.setIdPregunta(Integer.parseInt(request.getParameter("idPregunta")));
                respuestaDTO.setRespuesta(request.getParameter("respuesta"));

                if (respuestaDAO.resgistroRespuesta(respuestaDTO)) {
                    if (preguntaDAO.responderPregunta(1, respuestaDTO.getIdPregunta())) {
                        response.getWriter().print(true);
                    } else {
                        response.getWriter().print(false);
                    }
                } else {
                    response.getWriter().print(false);
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
                }catch(Exception e){
                    System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX error al listarPreguntasRespuesta registro ln 306"+e);
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
