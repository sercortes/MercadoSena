package co.edu.sena.mercado.controller.informes;

import co.edu.sena.mercado.dao.CompradorDAO;
import co.edu.sena.mercado.dao.ProductosPedidosDAO;
import co.edu.sena.mercado.dto.informePedidosDTO;
import co.edu.sena.mercado.dto.informeProductosDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author DELL
 */
public class informes extends HttpServlet {

    usuarioDTO usuarioDTO;
    ArrayList<informeProductosDTO> listaInformeProd;
    ArrayList<informePedidosDTO> listaInformePedidos;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        HttpSession sesion = request.getSession();
        String fechaIni, fechaFin, tipo;

        switch (accion) {
            case "consultaProductosVendedor":
                fechaIni = request.getParameter("fechaInicial");
                fechaFin = request.getParameter("fechaFinal");
                tipo = request.getParameter("tipoConsulta");
                usuarioDTO = new usuarioDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                Conexion con = new Conexion();
                Connection conn = con.getConnection();
                ProductosPedidosDAO productosPedidosDAO = new ProductosPedidosDAO(conn);
                listaInformeProd = new ArrayList<>();
                listaInformeProd = productosPedidosDAO.informeProductosVendidos(tipo, fechaIni, fechaFin, usuarioDTO.getEmpresa().getIdEmpresa());
                response.setContentType("application/json");
                new Gson().toJson(listaInformeProd, response.getWriter());
                productosPedidosDAO.CloseAll();
                break;
            case "consultaPedidosVendedor":

                fechaIni = request.getParameter("fechaInicial");
                fechaFin = request.getParameter("fechaFinal");
                tipo = request.getParameter("tipoConsulta");
                usuarioDTO = new usuarioDTO();
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                Conexion cone = new Conexion();
                Connection c = cone.getConnection();
                CompradorDAO compradorDAO=new CompradorDAO(c);
                listaInformePedidos=new ArrayList<>();
                listaInformePedidos=compradorDAO.informePedidos(tipo, fechaIni, fechaFin, usuarioDTO.getEmpresa().getIdEmpresa());
                response.setContentType("application/json");
                new Gson().toJson(listaInformePedidos,response.getWriter());
                compradorDAO.CloseAll();
                break;
            default:
                throw new AssertionError("XXXXXXXXXXXXXXXXXXXx esa accion no existe");
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
