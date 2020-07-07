/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author DELL
 */
public class gestionarPedidos extends HttpServlet {

    Conexion conexion;
    //Connection conn = conexion.getConnection();
    //CompradorDAO compradorDAO = new CompradorDAO(conn);
    ArrayList<pedidoDTO> listaPedido = new ArrayList<>();
    usuarioDTO usuarioDTO = new usuarioDTO();
    pedidoDTO pedidoDTO;
    Producto productoDTO;
    //ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conn);
    productoImagenesDTO producImagen;
    // ProductoDAO productoDAO = new ProductoDAO(conn);
    personaNaturalDAO personaDAO = new personaNaturalDAO();
    estadoVentaDAO estadoPedDAO = new estadoVentaDAO();
    empresaDAO empresaDAO = new empresaDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        HttpSession sesion = (HttpSession) request.getSession();
        usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");

        switch (accion) {
            case "pedidosVendedor":
                conexion = new Conexion();
                Connection conn = conexion.getConnection();
                CompradorDAO compradorDAO = new CompradorDAO(conn);

                String tipoUsuCon = request.getParameter("tipoUsu");
                if (tipoUsuCon.equals("vendedor")) {
                    listaPedido = compradorDAO.consultaPedido(usuarioDTO.getEmpresa().getIdEmpresa(), tipoUsuCon);
                } else if (tipoUsuCon.equals("comprador")) {
                    listaPedido = compradorDAO.consultaPedido(usuarioDTO.getPersona().getIdPer(), tipoUsuCon);
                }

                for (int i = 0; i < listaPedido.size(); i++) {
                    conexion = new Conexion();
                    Connection con = conexion.getConnection();
                    ProductoDAO productoDAO = new ProductoDAO(con);
                    ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(con);

                    producImagen = new productoImagenesDTO();
                    pedidoDTO = new pedidoDTO();
                    pedidoDTO = listaPedido.get(i);
                    producImagen.setProducto(productoDAO.buscarProducto(Integer.parseInt((pedidoDTO.getProdPedidoDTO().getIdProductoFK()))));
                    producImagen.setImagenes(imagenesProductosDAO.getImagenesByProduc(pedidoDTO.getProdPedidoDTO().getIdProductoFK()));
                    pedidoDTO.setProdImagen(producImagen);
                    pedidoDTO.setListaEstadoPedido(estadoPedDAO.listarEstadoVenta());

                    //con.close();
                    imagenesProductosDAO.CloseAll();
                    productoDAO.CloseAll();

                }

                compradorDAO.CloseAll();
                response.setContentType("application/json");
                new Gson().toJson(listaPedido, response.getWriter());
                break;
            case "consultaUsu":

                String tipoUsu = request.getParameter("tipoUsu");
                String id = request.getParameter("idUsuario");
                personaNaturalDTO personaDTO = new personaNaturalDTO();
                empresaDTO empresaDTO = new empresaDTO();
                if (tipoUsu.equals("comprador")) {
                    response.setContentType("application/json");
                    personaDTO = personaDAO.buscarPersona(id);
                    //System.out.println("....................."+personaDTO);
                    new Gson().toJson(personaDTO, response.getWriter());
                }
                if (tipoUsu.equals("vendedor")) {
                    response.setContentType("application/json");
                    empresaDTO = empresaDAO.buscarEmpresaXProducto(id);
                    //System.out.println("....................."+personaDTO);
                    new Gson().toJson(empresaDTO, response.getWriter());
                }
                break;
            case "actEstPed":

                conexion = new Conexion();
                Connection co = conexion.getConnection();

                VentaDAO ventaDAO = new VentaDAO(co);
                VentaDTO ventaDTO = new VentaDTO();

                ProductoDAO productoDAO = new ProductoDAO(co);

                ventaDTO.setIdVenta(request.getParameter("idVenta"));
                ventaDTO.setIdEstadoVentaFK(request.getParameter("idEstado"));

                if (ventaDTO.getIdEstadoVentaFK().equalsIgnoreCase("2")) {
                    System.out.println("....................... actualizar cantidad");
                    productoDTO = new Producto();
                    productoDTO.setStockProducto(Integer.parseInt(request.getParameter("cantidadVendida")));
                    productoDTO.setIdProducto(request.getParameter("idProducto"));

                    if (ventaDAO.actualizarVenta(ventaDTO) && productoDAO.actualizarCantidad(productoDTO)) {
                        response.getWriter().print("true");
                    } else {
                        response.getWriter().print("false");
                    }
                } else {
                    if (ventaDAO.actualizarVenta(ventaDTO)) {
                        response.getWriter().print("true");
                    } else {
                        response.getWriter().print("false");
                    }
                }
                productoDAO.CloseAll();
                ventaDAO.CloseAll();
                break;

            case "consultaNotiPedidos":
                String datRec = request.getParameter("idEmpresa");
                int idEmpresa = 0;
                usuarioDTO = (usuarioDTO) sesion.getAttribute("USER");
                if (usuarioDTO.getIdRol() == 3) {
                    if (datRec.equalsIgnoreCase("no")) {
                        idEmpresa = usuarioDTO.getEmpresa().getIdEmpresa();
                    } else {
                        idEmpresa = Integer.parseInt(datRec);
                    }

                    if (idEmpresa == usuarioDTO.getEmpresa().getIdEmpresa()) {
                        conexion = new Conexion();
                        Connection c = conexion.getConnection();
                        CompradorDAO compradoDAO = new CompradorDAO(c);
                        try {
                            int nroVentas = compradoDAO.consultaNotiPedidos(idEmpresa);
                            response.getWriter().print(nroVentas);
                        } catch (Exception e) {
                            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXx error al realizar la consulta de nro Ventas");
                        }
                        compradoDAO.CloseAll();
                    }
                }
                break;
            default:
                throw new AssertionError("XXXXXXXXXXXXXXXXXXX esa accion no existe");
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
