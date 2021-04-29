
package co.edu.sena.mercado.controller.checkBuy;

import co.edu.sena.mercado.controller.consumer.CRestPse;
import co.edu.sena.mercado.dao.VentaDAO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.pagoJSON;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class CheckBuy extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("selectsTwos no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String direccion = request.getRequestURI();

            switch (direccion) {

                case "/Store/CheckBuys":

                    CheckBuys(request, response);

                    break;
                    
                case "/Store/CheckBuysAdmin":

                    CheckBuysAdmin(request, response);

                    break;

            }

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    private void CheckBuys(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        int id = usu.getPersona().getIdPer();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        ArrayList<VentaDTO> lista = ventaDAO.getProductsNotUpdate(id);
        pagoJSON user = null;
        
        for(VentaDTO item: lista){
            
            System.out.println("CHECK BUY");
            System.out.println(item.toString());
            user = CRestPse.consultarPagoDos(item.getReferencia());
            
            // other status PENDING
             if (user.getStatusCard().equalsIgnoreCase("APPROVED")) {
                item.setIdEstadoVentaFK("2");
                ventaDAO.actualizarVenta(item);
                        List<Producto> listaP = new ArrayList<Producto>();
                        listaP = ventaDAO.getProductosByPrice(item.getIdVenta());
                        for (Producto items : listaP) {
                            try {
                                System.out.println(item.toString());
                                ventaDAO.actualizarCantidad(items);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
            }else if (user.getStatusCard().equalsIgnoreCase("DECLINED")) {
                item.setIdEstadoVentaFK("3");
                ventaDAO.actualizarVenta(item);
            }else if (user.getStatusCard().equalsIgnoreCase("ERROR")) {
                item.setIdEstadoVentaFK("4");
                ventaDAO.actualizarVenta(item);
            }else if (user.getStatusCard().equalsIgnoreCase("VOIDED")) {
                item.setIdEstadoVentaFK("5");
                ventaDAO.actualizarVenta(item);
            }
             
        }
        
        response.setContentType("application/json");
        ventaDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void CheckBuysAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        ArrayList<VentaDTO> lista = ventaDAO.getBuysNotUpdate();
        pagoJSON user = null;
        
        for(VentaDTO item: lista){
            
            System.out.println("CHECK BUY ADMIN");
            user = CRestPse.consultarPagoDos(item.getReferencia());
            System.out.println(user.toString());
            
            // other status PENDING
             if (user.getStatusCard().equalsIgnoreCase("APPROVED")) {
                item.setIdEstadoVentaFK("2");
                ventaDAO.actualizarVenta(item);
                        List<Producto> listaP = new ArrayList<Producto>();
                        listaP = ventaDAO.getProductosByPrice(item.getIdVenta());
                        for (Producto items : listaP) {
                            try {
                                System.out.println(item.toString());
                                ventaDAO.actualizarCantidad(items);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }
            }else if (user.getStatusCard().equalsIgnoreCase("DECLINED")) {
                item.setIdEstadoVentaFK("3");
                ventaDAO.actualizarVenta(item);
            }else if (user.getStatusCard().equalsIgnoreCase("ERROR")) {
                item.setIdEstadoVentaFK("4");
                ventaDAO.actualizarVenta(item);
            }else if (user.getStatusCard().equalsIgnoreCase("VOIDED")) {
                item.setIdEstadoVentaFK("5");
                ventaDAO.actualizarVenta(item);
            }
        }
        
        response.setContentType("application/json");
        ventaDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());
        
    }

}
