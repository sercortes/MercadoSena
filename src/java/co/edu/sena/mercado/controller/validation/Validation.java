package co.edu.sena.mercado.controller.validation;

import co.edu.sena.mercado.dao.ProductosPedidosDAO;
import co.edu.sena.mercado.dto.ProducctoDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Validation extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("validation no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("USER") != null) {

            try {
                String direccion = request.getRequestURI();
                
                switch (direccion) {
                    
                    case "/Store/getIdEmpresa":
                        
                        getIdEmpresa(request, response);
                        
                        break;
                        
                    case "/Store/getRol":
                        
                        getRol(request, response);
                        
                        break;
                        
                        
                    case "/Store/checkProducts":
                        
                        checkProducts(request, response);
                        
                        break;
                        
                    default:
                        
                        response.sendRedirect(request.getContextPath() + "/home");
                        
                        break;
                }
            }  catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else {
            new Gson().toJson(0, response.getWriter());
        }
    }

    private void getIdEmpresa(HttpServletRequest request, HttpServletResponse response) throws IOException {
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        response.setContentType("application/json");
        String idPersona = "";
        idPersona = Integer.toString(usu.getPersona().getIdPer());
        new Gson().toJson(idPersona, response.getWriter());
    }
    
    private void getRol(HttpServletRequest request, HttpServletResponse response) throws IOException {
        usuarioDTO usu = (usuarioDTO) request.getSession().getAttribute("USER");
        response.setContentType("application/json");
        String idPersona = "";
        idPersona = Integer.toString(usu.getIdRol());
        new Gson().toJson(idPersona, response.getWriter());
    }

    private void checkProducts(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, SQLException, IOException {
       
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        System.out.println("CHECK PRODUCTO VALIDATION......");
        System.out.println(user.toString());
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String[] arreglo = request.getParameterValues("arrayP");
        Gson gson = new Gson();
        String json = "";
        for (String item : arreglo) {
            json += item;
        }
        ProducctoDTO[] producctoDTOs = gson.fromJson(json, ProducctoDTO[].class);
        ProductosPedidosDAO productosPedidosDAO = null;
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();
        
        try {
            productosPedidosDAO = new ProductosPedidosDAO(conn);
            boolean estatus = true;
             double total = 0.0;
            
            for (ProducctoDTO item : producctoDTOs) {
                if (!productosPedidosDAO.checkProductsCustomer(item)) {
                    estatus = false;
                    System.out.println("ERROR verificación venta");
//                    new Gson().toJson(0, response.getWriter());
                }
            }
            
            if (producctoDTOs.length <= 0) {
                System.out.println("ARREGLO VACIO VENTA");
                estatus = false;
            }
            
             for (ProducctoDTO item : producctoDTOs) {
                total += item.getValorUnitario() * item.getCantidad();
                if (item.getValorUnitario() <= 0) {
                    estatus = false;
                }
            }

            if (total <= 0 || !estatus) {
                System.out.println("Error Productos Valen 0");
                estatus = false;
//                new Gson().toJson(0, response.getWriter());
            }
            
            System.out.println("");
            if (estatus) {
                new Gson().toJson(true, response.getWriter());    
            }else{
                new Gson().toJson(false, response.getWriter());    
            }
            
        } catch (Exception ex) {
            conn.rollback();
            ex.printStackTrace();
            new Gson().toJson(0, response.getWriter());
        } finally {
            productosPedidosDAO.CloseAll();
        }
        
    }
    
        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
