package co.edu.sena.mercado.controller.ventas;

import co.edu.sena.mercado.dao.CompradorDAO;
import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dao.VentaDAO;
import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dao.estadoVentaDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.pedidoDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.productoImagenesDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
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
import javax.servlet.http.HttpSession;

public class gestionarPedidos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("gestionar pedidos no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String direccion = request.getRequestURI();

        switch (direccion) {

            case "/Store/getAllVentasByVendedor":

                getAllVentasVendedor(request, response);

                break;

            case "/Store/getAllVentasByVendedorFailed":

                getAllVentasByVendedorFailed(request, response);

                break;

            case "/Store/getAllVentasByCus":

                getAllVentasByCustomer(request, response);

                break;

            case "/Store/getAllVentasByCusFailed":

                getAllVentasByCusFailed(request, response);

                break;

            case "/Store/getVentasByComprador":

                getVentasByComprador(request, response);

                break;

            case "/Store/getVentasByCompradorFailed":

                getVentasByCompradorFailed(request, response);

                break;

            case "/Store/notifySales":

                notifySales(request, response);

                break;
                
            case "/Store/updateSale":

                updateSale(request, response);

                break;

            default:

                System.out.println("query basic no soporta GET");
                response.sendRedirect(request.getContextPath() + "/home");

                break;

        }
    }

    //Tipo de venta 1 vendedor
    private void getAllVentasVendedor(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String estado = request.getParameter("estado");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        ArrayList<VentaDTO> listaVentas = ventaDAO.getVentasByVendedor(estado, "1");
        listaVentas.forEach((item) -> {
            ArrayList<Producto> listaProductos
                    = ventaDAO.getProductosByPrice(item.getIdVenta());
            item.setListaProductos(listaProductos);
        });
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaVentas, response.getWriter());

    }

    //Tipo de venta 2 Cliente
    private void getAllVentasByCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String estado = request.getParameter("estado");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        ArrayList<VentaDTO> listaVentas = ventaDAO.getVentasByVendedor(estado, "2");
        listaVentas.forEach((item) -> {
            ArrayList<Producto> listaProductos
                    = ventaDAO.getProductosByPrice(item.getIdVenta());
            item.setListaProductos(listaProductos);
        });
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaVentas, response.getWriter());

    }

    //Ventas por cliente
    private void getVentasByComprador(HttpServletRequest request, HttpServletResponse response) throws IOException {

        usuarioDTO usuario = (usuarioDTO) request.getSession().getAttribute("USER");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String estado = request.getParameter("estado");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        ArrayList<VentaDTO> listaVentas = ventaDAO.getAllVentasByOnlyCustomer(estado, usuario.getIdUsuario());
        listaVentas.forEach((item) -> {
            ArrayList<Producto> listaProductos
                    = ventaDAO.getProductosByPrice(item.getIdVenta());
            item.setListaProductos(listaProductos);
        });
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaVentas, response.getWriter());

    }

    // ventas por cliente falladas
    private void getVentasByCompradorFailed(HttpServletRequest request, HttpServletResponse response) throws IOException {

        usuarioDTO usuario = (usuarioDTO) request.getSession().getAttribute("USER");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String estado = request.getParameter("estado");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        ArrayList<VentaDTO> listaVentas = ventaDAO.getAllVentasByOnlyCustomerFailed(usuario.getIdUsuario());
        listaVentas.forEach((item) -> {
            ArrayList<Producto> listaProductos
                    = ventaDAO.getProductosByPrice(item.getIdVenta());
            item.setListaProductos(listaProductos);
        });
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaVentas, response.getWriter());

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void getAllVentasByVendedorFailed(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        ArrayList<VentaDTO> listaVentas = ventaDAO.getVentasByVendedorFailed("1");
        listaVentas.forEach((item) -> {
            ArrayList<Producto> listaProductos
                    = ventaDAO.getProductosByPrice(item.getIdVenta());
            item.setListaProductos(listaProductos);
        });
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaVentas, response.getWriter());

    }

    private void getAllVentasByCusFailed(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        ArrayList<VentaDTO> listaVentas = ventaDAO.getVentasByVendedorFailed("2");
        listaVentas.forEach((item) -> {
            ArrayList<Producto> listaProductos
                    = ventaDAO.getProductosByPrice(item.getIdVenta());
            item.setListaProductos(listaProductos);
        });
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(listaVentas, response.getWriter());
    }

    private void notifySales(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexions.getConnection());
        int numero = 0;
        try {
            numero = ventaDAO.countSales();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(numero, response.getWriter());

    }

    private void updateSale(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        Conexion conexions = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexions.getConnection());
        boolean status = false;
        try {
            status = ventaDAO.compraVista(request.getParameter("idP"));
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(status, response.getWriter());
        
    }

}
