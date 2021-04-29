package co.edu.sena.mercado.controller.log;

import co.edu.sena.mercado.dao.LogsDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dto.LogDTO;
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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logs extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("sesión invalida admin");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        if (user != null && user.getIdRol() == 3) {

            String direccion = request.getRequestURI();

            switch (direccion) {

                case "/Store/getLogs":

                    getLogs(request, response);

                    break;

                default:

                    System.out.println("sesión invalida admin");
                    response.sendRedirect(request.getContextPath() + "/home");

                    break;

            }

        } else {
            System.out.println("sesión invalida admin");
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    private void getLogs(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Conexion conexion = new Conexion();
        Connection cone = null;
        LogsDAO logsDAO = null;
        try {

            cone = conexion.getConnection();
            logsDAO = new LogsDAO(cone);
            ArrayList<LogDTO> lista = logsDAO.getLogs();
            new Gson().toJson(lista, response.getWriter());

        } catch (Exception ex) {
            
            ex.printStackTrace();
            new Gson().toJson(null, response.getWriter());

        } finally {
            logsDAO.CloseAll();
        }

    }
    
        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
