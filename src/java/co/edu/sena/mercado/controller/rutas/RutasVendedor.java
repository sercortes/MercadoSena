/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.rutas;

import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author serfin
 */
public class RutasVendedor extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "/opt/lampp/htdocs/sergio";
    private static final long serialVersionUID = 1L;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException, SQLException {

        String direccion = request.getRequestURI();
        RequestDispatcher rd;
        
        usuarioDTO usuario = (usuarioDTO) request.getSession().getAttribute("USER");
        System.out.println(usuario.toString());
        
        HttpSession session = request.getSession();
        
        switch (direccion) {
            case "/MercadoSena/Products":
                    rd = request.getRequestDispatcher("/views/products/products.jsp");
                    rd.forward(request, response);  
                break;
            case "/MercadoSena/newProduct":
                rd = request.getRequestDispatcher("/views/products/addProduct.jsp");
                rd.forward(request, response);
                break;
            case "/MercadoSena/delProduct":
                delProduct(request, response);
                break;
            default:
                System.out.println("error de la ruta");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RutasVendedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RutasVendedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void delProduct(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException, SQLException {

        request.setCharacterEncoding("UTF-8");

        Conexion conexion = new Conexion();
        Connection cone = conexion.getConnection();
    
    // desactivando en autocommit
            if (cone.getAutoCommit()) {
                cone.setAutoCommit(false);
            }
            
             ProductoDAO productoDAO = new ProductoDAO(cone);
        
        try {
            
            
            productoDAO.disabledProduct(request.getParameter("id"));
            
            response.setContentType("application/json");

            cone.commit();
            new Gson().toJson(true, response.getWriter());

        } catch (SQLException ex) {
            cone.rollback();
            System.out.println(ex);
            new Gson().toJson(true, response.getWriter());

        }finally{
            productoDAO.CloseAll();
        }

    }

    private void delProductsImages(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            
            String id = request.getParameter("id");
            File tempFile = new File(UPLOAD_DIRECTORY + File.separator + id + File.separator);
            
            if (tempFile.exists()) {

              FileUtils.forceDelete(new File(UPLOAD_DIRECTORY + File.separator + id + File.separator));
//            FileUtils.cleanDirectory(new File(UPLOAD_DIRECTORY+File.separator+id+File.separator));
//            FileUtils.deleteDirectory(new File(UPLOAD_DIRECTORY+File.separator+id+File.separator));

            }
        
            
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(RutasVendedor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
