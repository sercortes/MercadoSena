
package co.edu.sena.mercado.controller.seller;

import co.edu.sena.mercado.dao.CategorysDAO;
import co.edu.sena.mercado.dao.ProductoColorDAO;
import co.edu.sena.mercado.dao.VentaDAO;
import co.edu.sena.mercado.dto.ColorDTO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.ProductoColor;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SelectsTwo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("selectsTwos no soporta GET");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String direccion = request.getRequestURI();

        switch (direccion) {

            case "/Store/updateStockColors":

                updateStockColors(request, response);

                break;
                
            case "/Store/newColor":

                newColor(request, response);

                break;
                
            case "/Store/getInfoVenta":

                getInfoVenta(request, response);

                break;

        }

    }

    private void updateStockColors(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        usuarioDTO user = (usuarioDTO) request.getSession().getAttribute("USER");
        System.out.println("UPDATE STOCK COLOR");
        System.out.println(user.toString());
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String[] arreglo = request.getParameterValues("arrayP");
        String json = "";
        for (String item : arreglo) {
            json += item;
        }
        ProductoColor[] producctoDTOs = new Gson().fromJson(json, ProductoColor[].class);
        ProductoColorDAO productoColorDAO = null;
        try {
            productoColorDAO = new ProductoColorDAO(new Conexion().getConnection());
            for (ProductoColor item : producctoDTOs) {
                System.out.println(item.toString());
                productoColorDAO.updateColorsStock(item);
            }
            new Gson().toJson(1, response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
            new Gson().toJson(0, response.getWriter());
        } finally {
            productoColorDAO.CloseAll();
        }

    }
    
        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void newColor(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String idPr = request.getParameter("idPro");
        String idColor = request.getParameter("idColor");
        String cantidad = request.getParameter("cantidad");
        
        ProductoColorDAO productoColorDAO = null;
        try {
            productoColorDAO = new ProductoColorDAO(new Conexion().getConnection());
            ProductoColor productocolor = new ProductoColor();
            productocolor.setIdProductoFK(idPr);
            productocolor.setIdColorFK(idColor);
            productocolor.setCantidad(Integer.parseInt(cantidad));
            productoColorDAO.insertReturn(productocolor);
            new Gson().toJson(1, response.getWriter());
        } catch (Exception ex) {
            ex.printStackTrace();
            new Gson().toJson(0, response.getWriter());
            System.out.println(ex);
        } finally {
            productoColorDAO.CloseAll();
        }
        
    }

    private void getInfoVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
         request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        VentaDAO ventaDAO = new VentaDAO(conexion.getConnection());
        VentaDTO ventaDTO = ventaDAO.getInfoVenta(request.getParameter("idVenta"));
        ventaDTO.setListaProductos(ventaDAO.getProductosByPrice(ventaDTO.getIdVenta()));
        ventaDAO.CloseAll();
        response.setContentType("application/json");
        new Gson().toJson(ventaDTO, response.getWriter());
        
    }

}
