/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.chat;

import co.edu.sena.mercado.dao.PreguntassDAO;
import co.edu.sena.mercado.dto.preguntasDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author equipo
 */
public class chat extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("chat no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getSession().getAttribute("USER") != null) {

            String direccion = request.getRequestURI();

            switch (direccion) {

                case "/MercadoSena/registrarPregunta":

                    registrarPregunta(request, response);

                    break;

                case "/MercadoSena/getPreguntaByUser":

                    getPreguntaByUser(request, response);

                    break;

                case "/MercadoSena/getAllPreguntas":

                    getAllPreguntas(request, response);

                    break;

                case "/MercadoSena/getPreguntasIndivi":

                    getPreguntasIndivi(request, response);

                    break;

                case "/MercadoSena/getRespuestasByQuestion":

                    getRespuestasByQuestion(request, response);

                    break;

                default:

                    break;
            }

        } else {

            new Gson().toJson(0, response.getWriter());

        }

    }

    private void registrarPregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {

        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        preguntasDTO preguntaDTOs = new preguntasDTO();
        Conexion conexion = new Conexion();
        PreguntassDAO preguntassDAO = new PreguntassDAO(conexion.getConnection());
        preguntaDTOs.setEstadoPregunta(0);
        preguntaDTOs.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));
        preguntaDTOs.setIdUsuarioPregunta(usu.getIdUsuario());
        preguntaDTOs.setPregunta(request.getParameter("mensaje"));
        try {
            preguntassDAO.resgistroPregunta(preguntaDTOs);
            response.getWriter().print(true);
        } catch (SQLException ex) {
            response.getWriter().print(false);
            System.out.println(ex);
        } finally {
            preguntassDAO.CloseAll();
        }

    }

    private void getAllPreguntas(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        PreguntassDAO instructoresDAO = new PreguntassDAO(conexions.getConnection());
        ArrayList<?> autores = instructoresDAO.getAllPreguntas();
        instructoresDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(autores, response.getWriter());

    }

    private void getPreguntasIndivi(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        PreguntassDAO instructoresDAO = new PreguntassDAO(conexions.getConnection());
        String idUsu = request.getParameter("idUsuario");
        String idPro = request.getParameter("idProducto");
        ArrayList<?> autores = instructoresDAO.getPreguntasIndivi(idUsu, idPro);
        instructoresDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(autores, response.getWriter());

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void getRespuestasByQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        PreguntassDAO instructoresDAO = new PreguntassDAO(conexions.getConnection());
        String idPregunta = request.getParameter("idPregunta");
        ArrayList<?> autores = instructoresDAO.getRespuestasByQuestion(idPregunta);
        instructoresDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(autores, response.getWriter());
    }

    private void getPreguntaByUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        PreguntassDAO instructoresDAO = new PreguntassDAO(conexions.getConnection());
        int id = usu.getIdUsuario();
        ArrayList<?> autores = instructoresDAO.getPreguntaByUser(Integer.toString(id));
        instructoresDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(autores, response.getWriter());

    }

}