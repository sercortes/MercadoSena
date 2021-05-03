
package co.edu.sena.mercado.controller.chat;

import co.edu.sena.mercado.dao.PreguntassDAO;
import co.edu.sena.mercado.dao.preguntasDAO;
import co.edu.sena.mercado.dao.respuestaDAO;
import co.edu.sena.mercado.dto.preguntasDTO;
import co.edu.sena.mercado.dto.respuestaDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

                case "/Store/registrarPregunta":

                    registrarPregunta(request, response);

                    break;

                case "/Store/registrarRespuesta":

                    registrarRespuesta(request, response);

                    break;

                case "/Store/getPreguntaByUser":

                    getPreguntaByUser(request, response);

                    break;

                case "/Store/getAllPreguntas":

                    getAllPreguntas(request, response);

                    break;

                case "/Store/getPreguntasIndivi":

                    getPreguntasIndivi(request, response);

                    break;

                case "/Store/getRespuestasByQuestion":

                    getRespuestasByQuestion(request, response);

                    break;
                    
                case "/Store/notifyNormal":

                    notifyNormal(request, response);

                    break;
                    
                case "/Store/updateQuestion":

                    updateQuestion(request, response);

                    break;
                    
                 case "/Store/updateAnswer":

                    updateAnswer(request, response);

                    break;

                default:

                    break;
            }

        } else {

            new Gson().toJson(0, response.getWriter());

        }

    }

    private void registrarPregunta(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        preguntasDTO preguntaDTOs = new preguntasDTO();
        Conexion conexion = new Conexion();
        PreguntassDAO preguntassDAO = new PreguntassDAO(conexion.getConnection());
        
        preguntaDTOs.setEstadoPregunta(0);
        preguntaDTOs.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));
        preguntaDTOs.setIdUsuarioPregunta(usu.getIdUsuario());
        preguntaDTOs.setPregunta(request.getParameter("mensaje"));
        
        
        System.out.println("----------");
        System.out.println("PREGUNTA");
        System.out.println(preguntaDTOs.toString());
        System.out.println(request.getParameter("mensaje"));
        System.out.println(usu.toString());
        System.out.println("-----------");
        
        
        try {
            preguntassDAO.resgistroPregunta(preguntaDTOs);
            response.getWriter().print(1);
        } catch (SQLException ex) {
            response.getWriter().print(10);
            ex.printStackTrace();
        } catch (Exception ex) {
            response.getWriter().print(11);
            ex.printStackTrace();
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
        
        PreguntassDAO preguntasdao = null;
        Connection cone  = null;
        try {
            usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
            request.setCharacterEncoding("UTF-8");
            Conexion conexions = new Conexion();
            cone = conexions.getConnection();
            if (cone.getAutoCommit()) {
                cone.setAutoCommit(false);
            }
            preguntasdao = new PreguntassDAO(cone);
            int id = usu.getIdUsuario();
            ArrayList<preguntasDTO> autores = preguntasdao.getPreguntaByUser(Integer.toString(id));
//            for(preguntasDTO item: autores){
//                preguntasdao.marcarVistaPregunta(item.getIdPregunta());
//            }
//            cone.commit();
            response.setContentType("application/json");
            new Gson().toJson(autores, response.getWriter());
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }finally{
           preguntasdao.CloseAll();
        }

    }

    private void registrarRespuesta(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        respuestaDTO respuestaDTO = new respuestaDTO();
        usuarioDTO usuarioDTO = new usuarioDTO();
        respuestaDAO respuestaDAO = new respuestaDAO();
        preguntasDAO preguntaDAO = new preguntasDAO();
        usuarioDTO = (usuarioDTO) request.getSession().getAttribute("USER");
        respuestaDTO.setIdEmpresa(1);
        respuestaDTO.setIdPregunta(Integer.parseInt(request.getParameter("idPregunta")));
        respuestaDTO.setRespuesta(request.getParameter("respuesta"));
        
        System.out.println("----------");
        System.out.println("RESPUESTA");
        System.out.println(request.getParameter("respuesta"));
        System.out.println(usuarioDTO.toString());
        System.out.println("-----------");

        if (respuestaDAO.resgistroRespuesta(respuestaDTO)) {
            if (preguntaDAO.responderPregunta(1, respuestaDTO.getIdPregunta())) {
                response.getWriter().print(true);
            } else {
                response.getWriter().print(false);
            }
        } else {
            response.getWriter().print(false);
        }

    }

        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void updateQuestion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        PreguntassDAO instructoresDAO = new PreguntassDAO(conexions.getConnection());
        boolean status = false;
        try {
            status = instructoresDAO.preguntaVista(request.getParameter("idP"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        instructoresDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(status, response.getWriter());
        
    }

    private void notifyNormal(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        PreguntassDAO instructoresDAO = new PreguntassDAO(conexions.getConnection());
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        int numero = 0;
        int rol = usu.getIdRol();
        try {
            if (rol == 3) {
                 numero = instructoresDAO.countPreguntas();
            }else{
                numero = instructoresDAO.countPreguntasComprador(usu.getIdUsuario());                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            instructoresDAO.CloseAll();
        }
        response.setContentType("application/json");
        new Gson().toJson(numero, response.getWriter());
        
    }

    private void updateAnswer(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        PreguntassDAO instructoresDAO = new PreguntassDAO(conexions.getConnection());
        boolean status = false;
        try {
            status = instructoresDAO.respuestaVista(request.getParameter("idP"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            instructoresDAO.CloseAll();
        }
        response.setContentType("application/json");
        new Gson().toJson(status, response.getWriter());
        
    }
    
}
