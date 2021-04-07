package co.edu.sena.mercado.controller.informes;

import co.edu.sena.mercado.dao.CompradorDAO;
import co.edu.sena.mercado.dao.InformesDAO;
import co.edu.sena.mercado.dao.ProductosPedidosDAO;
import co.edu.sena.mercado.dto.InformeDTO;
import co.edu.sena.mercado.dto.informePedidosDTO;
import co.edu.sena.mercado.dto.informeProductosDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class informes extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("search no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("search no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {

            String direccion = request.getRequestURI();

            switch (direccion) {

                case "/Store/graphicDays":

                    graphicDays(request, response);

                    break;

                case "/Store/graphicMonth":

                    graphicMonth(request, response);

                    break;

                case "/Store/graphicTotalMoth":

                    graphicTotalMonth(request, response);

                    break;

                case "/Store/graphicTotalDays":

                    graphicTotalDays(request, response);

                    break;

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    private void graphicDays(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        InformeDTO informeDTO = new InformeDTO();
        System.out.println(request.getParameter("fechaI"));
        informeDTO.setFechaI(request.getParameter("fechaI"));
        informeDTO.setFechaF(request.getParameter("fechaFinal"));
        informeDTO.setTipoV(request.getParameter("tipo"));
        System.out.println(informeDTO.toString());

        Conexion conexion = new Conexion();
        InformesDAO informesDAO = new InformesDAO(conexion.getConnection());
        ArrayList<?> lista = informesDAO.getQueryByDays(informeDTO);
        response.setContentType("application/json");
        informesDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void graphicMonth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        InformeDTO informeDTO = new InformeDTO();
        System.out.println(request.getParameter("fechaI"));
        informeDTO.setFechaI(request.getParameter("fechaI"));
        informeDTO.setFechaF(request.getParameter("fechaFinal"));
        informeDTO.setTipoV(request.getParameter("tipo"));
        System.out.println(informeDTO.toString());

        Conexion conexion = new Conexion();
        InformesDAO informesDAO = new InformesDAO(conexion.getConnection());
        ArrayList<?> lista = informesDAO.getQueryByMoth(informeDTO);
        response.setContentType("application/json");
        informesDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

    private void graphicTotalMonth(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        InformeDTO informeDTO = new InformeDTO();
        System.out.println(request.getParameter("fechaI"));
        informeDTO.setFechaI(request.getParameter("fechaI"));
        informeDTO.setFechaF(request.getParameter("fechaFinal"));
        System.out.println(informeDTO.toString());

        Conexion conexion = new Conexion();
        InformesDAO informesDAO = new InformesDAO(conexion.getConnection());
        ArrayList<?> lista = informesDAO.getQueryByTotalMoth(informeDTO);
        response.setContentType("application/json");
        informesDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

    private void graphicTotalDays(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        InformeDTO informeDTO = new InformeDTO();
        System.out.println(request.getParameter("fechaI"));
        informeDTO.setFechaI(request.getParameter("fechaI"));
        informeDTO.setFechaF(request.getParameter("fechaFinal"));
        System.out.println(informeDTO.toString());

        Conexion conexion = new Conexion();
        InformesDAO informesDAO = new InformesDAO(conexion.getConnection());
        ArrayList<?> lista = informesDAO.getQueryByTotalDays(informeDTO);
        response.setContentType("application/json");
        informesDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

}
