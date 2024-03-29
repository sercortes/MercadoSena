package co.edu.sena.mercado.controller.ventas;

import co.edu.sena.mercado.dao.CompradorDAO;
import co.edu.sena.mercado.dao.EmpresaPedidoDAO;
import co.edu.sena.mercado.dao.PersonasNaturalDAO;
import co.edu.sena.mercado.dao.ProductosPedidosDAO;
import co.edu.sena.mercado.dao.VentaDAO;
import co.edu.sena.mercado.dao.personaNaturalDAO;
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

                    case "/Store/generateSale":

                        generateSales(request, response);

                        break;

                    case "/Store/checkSession":

                        checkSession(request, response);

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

    private void generateSales(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        System.out.println("GENERATE SALE");
        System.out.println(usu.toString());

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String[] arreglo = request.getParameterValues("arrayP");
        String metodo = request.getParameter("metodo");
        Gson gson = new Gson();
        String json = "";
        for (String item : arreglo) {
            json += item;
        }
        ProducctoDTO[] producctoDTOs = gson.fromJson(json, ProducctoDTO[].class);

        VentaDAO ventaDAO = null;
        ProductosPedidosDAO productosPedidosDAO = null;

        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();

        if (conn.getAutoCommit()) {
            conn.setAutoCommit(false);
        }

        try {

            productosPedidosDAO = new ProductosPedidosDAO(conn);
            ventaDAO = new VentaDAO(conn);
            boolean estatus = true;

            for (ProducctoDTO item : producctoDTOs) {
                if (!productosPedidosDAO.checkProductsCustomer(item)) {
                    estatus = false;
                    System.out.println("Error stock/precio productos");
                    System.out.println(item.toString());
                    new Gson().toJson(0, response.getWriter());
                }
            }
//            
            if (producctoDTOs.length <= 0) {
                System.out.println("Error tañaño arreglo generate sale");
                estatus = false;
                new Gson().toJson(0, response.getWriter());
            }

            double total = 0.0;

            for (ProducctoDTO item : producctoDTOs) {
                total += item.getValorUnitario() * item.getCantidad();
                if (item.getValorUnitario() <= 0) {
                    estatus = false;
                }
            }

            if (!estatus) {
                System.out.println("Error un producto Vale 0");
                estatus = false;
                new Gson().toJson(0, response.getWriter());
            }

            if (total <= 0) {
                System.out.println("Error Productos Valen 0");
                estatus = false;
                new Gson().toJson(0, response.getWriter());
            }

            if (estatus) {

                VentaDTO ventaDTO = new VentaDTO();
                ventaDTO.setIdCompradorFK(Integer.toString(usu.getPersona().getIdPer()));
                ventaDTO.setValorVenta(total);
                ventaDTO.setFormaPago(metodo);
                ventaDTO.setTipoVenta("2");
                ventaDTO.setIdEstadoVentaFK("1");
                ventaDTO.setIdCiudadFK(Integer.toString(usu.getPersona().getIdCiudad()));
                ventaDTO.setVisto("0");
                int idVenta = ventaDAO.insertReturn(ventaDTO);
                for (ProducctoDTO item : producctoDTOs) {
                    productoPedidosDTO pedidosDTO = new productoPedidosDTO();
                    pedidosDTO.setIdVentaFK(Integer.toString(idVenta));
                    pedidosDTO.setCantidad(item.getCantidad());
                    pedidosDTO.setIdProductoFK(item.getIdProductoColor());
                    pedidosDTO.setValorProductoVenta(item.getValorUnitario());
                    productosPedidosDAO.insertReturn(pedidosDTO);
                }
                conn.commit();
                request.getSession().setAttribute("IDVENTA", Integer.toString(idVenta));
                new Gson().toJson(idVenta, response.getWriter());
                System.out.println("");
            }
        } catch (Exception ex) {
            conn.rollback();
            ex.printStackTrace();
            new Gson().toJson(0, response.getWriter());
        } finally {
            ventaDAO.CloseAll();
            productosPedidosDAO.CloseAll();
        }

    }

    private void checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        response.setContentType("application/json");
        String idPersona = "";
        idPersona = Integer.toString(usu.getPersona().getIdPer());
        new Gson().toJson(idPersona, response.getWriter());
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
