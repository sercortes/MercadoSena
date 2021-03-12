/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.ventas;

import co.edu.sena.mercado.dao.CompradorDAO;
import co.edu.sena.mercado.dao.EmpresaPedidoDAO;
import co.edu.sena.mercado.dao.PersonasNaturalDAO;
import co.edu.sena.mercado.dao.ProductosPedidosDAO;
import co.edu.sena.mercado.dao.VentaDAO;
import co.edu.sena.mercado.dto.CompradorDTO;
import co.edu.sena.mercado.dto.EmpresaPedidoDTO;
import co.edu.sena.mercado.dto.MetodoPago;
import co.edu.sena.mercado.dto.ProducctoDTO;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.productoPedidosDTO;
import co.edu.sena.mercado.dto.tipoDocumentoDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author equipo
 */
public class generateSaleByVendedor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("generateSale no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        
        if (request.getSession().getAttribute("USER") != null) {
            
            try {
                String direccion = request.getRequestURI();

                switch (direccion) {

                    case "/Store/generateSaleByVendedor":

                        generateSaleByVendedor(request, response);

                        break;

                    case "/Store/getMetodos":

                        getMetodos(request, response);

                        break;

                    case "/Store/getPersona":

                        getPersona(request, response);

                        break;

                    case "/Store/getTiposDocumento":

                        getTiposDocumento(request, response);

                        break;

                    default:

                        System.out.println("generateSale no soporta GET");
                        response.sendRedirect(request.getContextPath() + "/home");

                        break;

                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else {
            new Gson().toJson(false, response.getWriter());
            System.out.println("NO VALORES DE SESIón");
        }
    }

    private void getMetodos(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        response.setContentType("application/json");
        CompradorDAO compradorDAO = new CompradorDAO(conexion.getConnection());
        ArrayList<MetodoPago> lista = compradorDAO.getMetodos();
        compradorDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

    private void getPersona(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        response.setContentType("application/json");
        PersonasNaturalDAO personaDAO = new PersonasNaturalDAO(conexion.getConnection());
        personaNaturalDTO perNaturalDTO = personaDAO.getPersona(request.getParameter("idUser"));
        personaDAO.CloseAll();
        new Gson().toJson(perNaturalDTO, response.getWriter());

    }

    private void getTiposDocumento(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, Exception {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PersonasNaturalDAO personasNaturalDAO = new PersonasNaturalDAO(new Conexion().getConnection());
        ArrayList<tipoDocumentoDTO> lista = personasNaturalDAO.getTiposDocumento();
        personasNaturalDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

    private void generateSaleByVendedor(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String[] arreglo = request.getParameterValues("arrayP");
        String metodo = request.getParameter("metodoPago");
        double descuento = Double.parseDouble(request.getParameter("descuento"));
        
        Gson gson = new Gson();
        String json = "";
        String idEmpresa = "";
        for (String item : arreglo) {
            json += item;
        }
        ProducctoDTO[] producctoDTOs = gson.fromJson(json, ProducctoDTO[].class);
        
        for(ProducctoDTO item :producctoDTOs){
            System.out.println(item.toString());
        }

        CompradorDAO compradorDAO = null;
        VentaDAO ventaDAO = null;
        EmpresaPedidoDAO empresaPedidoDAO = null;
        ProductosPedidosDAO productosPedidosDAO = null;
        PersonasNaturalDAO personasNaturalDAO = null;

        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();

        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }

        try {

            compradorDAO = new CompradorDAO(conn);
            empresaPedidoDAO = new EmpresaPedidoDAO(conn);
            productosPedidosDAO = new ProductosPedidosDAO(conn);
            ventaDAO = new VentaDAO(conn);
            personasNaturalDAO = new PersonasNaturalDAO(conn);

            for (ProducctoDTO item : producctoDTOs) {
                if (!productosPedidosDAO.checkProducts(item)) {
                    new Gson().toJson(0, response.getWriter());
                    throw new Exception();
                }
            }

            int crear = Integer.parseInt(request.getParameter("crear"));
            int idComprador = 0;
            personaNaturalDTO personaNaturalDTO = new personaNaturalDTO();
            if (crear == 1) {
                personaNaturalDTO.setIdTipoDoc(Integer.parseInt(request.getParameter("tipoD")));
                personaNaturalDTO.setNumeroDocPer(request.getParameter("documentoUsu"));
                personaNaturalDTO.setNombrePer(request.getParameter("nombre"));
                personaNaturalDTO.setApellidoPer(request.getParameter("apellido"));
                personaNaturalDTO.setDireccionPer(request.getParameter("direccion"));
                personaNaturalDTO.setIdUsuario(usu.getIdUsuario());
                idComprador = personasNaturalDAO.registrarPersonaNaturalByVendedor(personaNaturalDTO);
            } else {
                idComprador = Integer.parseInt(request.getParameter("idUserCompra"));
                personaNaturalDTO = personasNaturalDAO.getPersonaByIdPersona(Integer.toString(idComprador));
            }

            idEmpresa = compradorDAO.getIdEmpresa(producctoDTOs[0].getIdProducto());
            CompradorDTO compradorDTO = new CompradorDTO(Integer.toString(idComprador), idEmpresa);
            int idCompra = compradorDAO.insertReturn(compradorDTO);

            double total = 0.0;
            for (ProducctoDTO item : producctoDTOs) {
                total += item.getValorUnitario() * item.getCantidad();
            }

            VentaDTO ventaDTO = new VentaDTO();
            ventaDTO.setIdCompradorFK(Integer.toString(idCompra));
            ventaDTO.setValorVenta(total);
            ventaDTO.setFormaPago(metodo);
            ventaDTO.setDescuento(descuento);
            ventaDTO.setNombreFormaPago(getNombreFormadePago(metodo));
            ventaDTO.setIdCiudadFK(Integer.toString(usu.getPersona().getIdCiudad()));
            int idVenta = ventaDAO.insertReturn(ventaDTO);
            ventaDTO.setIdVenta(Integer.toString(idVenta));

            EmpresaPedidoDTO empresaPedidoDTO = new EmpresaPedidoDTO();
            empresaPedidoDTO.setIdEmpresaFK(idEmpresa);
            empresaPedidoDTO.setIdVentaFK(Integer.toString(idVenta));
            empresaPedidoDAO.insertReturn(empresaPedidoDTO);

            for (ProducctoDTO item : producctoDTOs) {
                productoPedidosDTO pedidosDTO = new productoPedidosDTO();
                pedidosDTO.setIdVentaFK(Integer.toString(idVenta));
                pedidosDTO.setCantidad(item.getCantidad());
                pedidosDTO.setIdProductoFK(item.getIdProducto());
                productosPedidosDAO.insertReturn(pedidosDTO);
                productosPedidosDAO.actualizarCantidad(item);
            }
            ventaDTO.setPerDTO(personaNaturalDTO);
            conn.commit();
            new Gson().toJson(ventaDTO, response.getWriter());
        } catch (SQLException ex) {
            conn.rollback();
            ex.printStackTrace();
            System.out.println("ROLL BACK GENERATE SALE");
            System.out.println(ex);
            new Gson().toJson(01, response.getWriter());
        } catch (Exception ex) {
            conn.rollback();
            System.out.println("ROLL BACK GENERATE SALE");
            ex.printStackTrace();
            System.out.println(ex);
            new Gson().toJson(0, response.getWriter());
        } finally {
            compradorDAO.CloseAll();
            ventaDAO.CloseAll();
            empresaPedidoDAO.CloseAll();
            productosPedidosDAO.CloseAll();
            personasNaturalDAO.CloseAll();
        }

    }

    public String getNombreFormadePago(String number) {

        int numero = Integer.parseInt(number);
        String nombre = "";

        switch (numero) {
            case 1:
                nombre = "Efectivo";
                break;
            case 2:
                nombre = "Transferencia bancaria";
                break;
            case 3:
                nombre = "Tarjeta de crédito";
                break;
            case 4:
                nombre = "PSE";
                break;
            default:
                nombre = "null";
                break;
        }
        
        return nombre;

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
