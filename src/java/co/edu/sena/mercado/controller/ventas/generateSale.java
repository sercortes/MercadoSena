/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.ventas;

import co.edu.sena.mercado.dao.CompradorDAO;
import co.edu.sena.mercado.dao.EmpresaPedidoDAO;
import co.edu.sena.mercado.dao.ProductosPedidosDAO;
import co.edu.sena.mercado.dao.VentaDAO;
import co.edu.sena.mercado.dto.CompradorDTO;
import co.edu.sena.mercado.dto.EmpresaPedidoDTO;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.productoPedidosDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author serfin
 */
public class generateSale extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("application/json");
        
        if (request.getSession().getAttribute("USER") != null) {
        
        try {
            String direccion = request.getRequestURI();

            switch (direccion) {

                case "/MercadoSena/generateSale":

                    generateSales(request, response);

                    break;

                case "/MercadoSena/checkProducts":

                    checkProducts(request, response);

                    break;
                    
                case "/MercadoSena/checkSession":

                    checkSession(request, response);

                    break;

                default:

                    break;

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        }else{
            new Gson().toJson(false, response.getWriter());
            System.out.println("NO VALORES DE SESIón");
        }
    }

    private void generateSales(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        request.setCharacterEncoding("UTF-8");
        String cantidad = request.getParameter("cantidad");
        String contacto = request.getParameter("contacto");
        String idEmpre = request.getParameter("idEmpresa");
        String idPro = request.getParameter("idProducto");
        Double precio = Double.parseDouble(request.getParameter("precioProducto"));

        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        CompradorDTO compradorDTO = new CompradorDTO(Integer.toString(usu.getPersona().getIdPer()), idEmpre);

        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setValorVenta(precio * Double.parseDouble(cantidad));
        ventaDTO.setContactoVenta(contacto);
        ventaDTO.setIdCiudadFK(Integer.toString(usu.getPersona().getIdCiudad()));

        EmpresaPedidoDTO empresaPedidoDTO = new EmpresaPedidoDTO();
        empresaPedidoDTO.setIdEmpresaFK(idEmpre);

        productoPedidosDTO pedidosDTO = new productoPedidosDTO();
        pedidosDTO.setIdProductoFK(idPro);
        pedidosDTO.setCantidad(Integer.parseInt(cantidad));

        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();

        response.setContentType("application/json");

        CompradorDAO compradorDAO = null;
         VentaDAO ventaDAO = null;
         EmpresaPedidoDAO empresaPedidoDAO = null;
         ProductosPedidosDAO productosPedidosDAO = null;
        
        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }
        
   try {
       
        compradorDAO = new CompradorDAO(conn);
        empresaPedidoDAO = new EmpresaPedidoDAO(conn);
        productosPedidosDAO = new ProductosPedidosDAO(conn);
        ventaDAO = new VentaDAO(conn);
        
        int idCompra = compradorDAO.insertReturn(compradorDTO);
        ventaDTO.setIdCompradorFK(Integer.toString(idCompra));
        int idVenta = ventaDAO.insertReturn(ventaDTO);
        empresaPedidoDTO.setIdVentaFK(Integer.toString(idVenta));
        empresaPedidoDAO.insertReturn(empresaPedidoDTO);
        pedidosDTO.setIdVentaFK(Integer.toString(idVenta));
        productosPedidosDAO.insertReturn(pedidosDTO);

            conn.commit();
            new Gson().toJson(true, response.getWriter());
        } catch (Exception ex) {
            conn.rollback();
            System.out.println("ROLL BACK GENERATE SALE");
            System.out.println(ex);
            new Gson().toJson(false, response.getWriter());
        } finally {
            compradorDAO.CloseAll();
            ventaDAO.CloseAll();
            empresaPedidoDAO.CloseAll();
            productosPedidosDAO.CloseAll();
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void checkProducts(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, Exception {

        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();

        response.setContentType("application/json");

        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }
        
        CompradorDAO compradorDAO = new CompradorDAO(conn);
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        String idPersona = "";
        
        if (request.getSession().getAttribute("USER") == null) {
            idPersona = "0";
        }else{
            idPersona = Integer.toString(usu.getPersona().getIdPer());
        }
        
        String idProducto = request.getParameter("idProducto");
        
        System.out.println("REVISAR IDProducto "+idProducto + " IDPERsona "+idPersona);
        boolean estado = compradorDAO.checkProducts(idPersona, idProducto);
        compradorDAO.CloseAll();
        new Gson().toJson(estado, response.getWriter());

    }

    private void checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();
        
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");

        response.setContentType("application/json");
        
        String idPersona = "";
        
        idPersona = Integer.toString(usu.getPersona().getIdPer());
      
        new Gson().toJson(idPersona, response.getWriter());
    }

}
