/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.ventas;

import co.edu.sena.mercado.dao.CompradorDAO;
import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.pedidoDTO;
import co.edu.sena.mercado.dto.productoImagenesDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
public class gestionarPedidos extends HttpServlet {

    Conexion conexion = new Conexion();
    Connection conn = conexion.getConnection();
    CompradorDAO compradorDAO = new CompradorDAO(conn);
    ArrayList<pedidoDTO> listaPedido = new ArrayList<>();
    usuarioDTO usuarioDTO = new usuarioDTO();
    pedidoDTO pedidoDTO;
    Producto productoDTO;
    ImagenesProductosDAO imagenesProductosDAO = new ImagenesProductosDAO(conn);
    productoImagenesDTO producImagen;
    ProductoDAO productoDAO=new ProductoDAO(conn);

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
                listaPedido = compradorDAO.consultaPedido(usuarioDTO.getEmpresa().getIdEmpresa(), "vendedor");

                for (int i = 0; i < listaPedido.size(); i++) {
                    System.out.println("..................." + i);
                    producImagen = new productoImagenesDTO();
                    pedidoDTO = new pedidoDTO();
                    pedidoDTO = listaPedido.get(i);
                    producImagen.setProducto(productoDAO.buscarProducto(Integer.parseInt((pedidoDTO.getProdPedidoDTO().getIdProductoFK()))));
                    producImagen.setImagenes( imagenesProductosDAO.getImagenesByProduc(pedidoDTO.getProdPedidoDTO().getIdProductoFK()));
                    pedidoDTO.setProdImagen(producImagen);
                    //imagenesProductosDAO.CloseAll();
                   // productoDAO.CloseAll();
                }
                //compradorDAO.CloseAll();
                response.setContentType("application/json");
                new Gson().toJson(listaPedido, response.getWriter());
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
