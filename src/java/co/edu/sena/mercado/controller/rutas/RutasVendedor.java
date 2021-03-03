/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.rutas;

import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dao.ciudadDAO;
import co.edu.sena.mercado.dto.ciudadDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private static final long serialVersionUID = 1L;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException, SQLException {
        
        String direccion = request.getRequestURI();
        RequestDispatcher rd = null;

        if (request.getSession().getAttribute("USER") != null) {

        usuarioDTO usuario = (usuarioDTO) request.getSession().getAttribute("USER");
        System.out.println(usuario.toString());
        
        switch (direccion) {
            case "/Store/realizarVenta":
                rd = request.getRequestDispatcher("/views/realizarVenta/productosVendedor.jsp");
                rd.forward(request, response);  
                break;
            case "/Store/Products":
                rd = request.getRequestDispatcher("/views/products/products.jsp");
                rd.forward(request, response);  
                break;
            case "/Store/newProduct":
                rd = request.getRequestDispatcher("/views/products/addProduct.jsp");
                rd.forward(request, response);
                break;
            case "/Store/delProduct":
                delProduct(request, response);
                break;
                
            case "/Store/preguntas":
                rd = request.getRequestDispatcher("/views/preguntas/preguntas.jsp");
                rd.forward(request, response);
                break;
            case "/Store/usuario":
                 usuario(request, response, rd); 
                break;
            case "/Store/ventasVendedor":
                request.getRequestDispatcher("/views/ventas/ventasVendedor.jsp").forward(request, response);
                break;
            case "/Store/misPedidos":
                request.getRequestDispatcher("/views/ventas/pedidosUsuario.jsp").forward(request, response);
                break;
            case "/Store/realizarInformes":
                request.getRequestDispatcher("/views/informes/informes.jsp").forward(request, response);
                break;
            default:
                System.out.println("error de la ruta");
                break;
        }
        
        }else{
            System.out.println("NO valores de sesión RUTAS VENDEDOR");
            response.sendRedirect(request.getContextPath() + "/home");
        }
        
    }

    private void delProduct(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException, SQLException {

        request.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        Connection cone = conexion.getConnection();
    
            if (cone.getAutoCommit()) {
                cone.setAutoCommit(false);
            }
            
             ProductoDAO productoDAO = new ProductoDAO(cone);
        
        try {
            
            productoDAO.disabledProduct(request.getParameter("id"));
            response.setContentType("application/json");
            cone.commit();
            new Gson().toJson(true, response.getWriter());

        } catch (Exception ex) {
            
            System.out.println("ROLL BACK DELETE PRODUCT");
            cone.rollback();
            System.out.println(ex);
            new Gson().toJson(false, response.getWriter());

        }finally{
            productoDAO.CloseAll();
        }

    }

    private void usuario(HttpServletRequest request, HttpServletResponse response, RequestDispatcher rd) throws ServletException, IOException {
        
                ArrayList<ciudadDTO> listaCiudad = new ArrayList<>();
                Conexion conexion = new Conexion();
                Connection conn = conexion.getConnection();
                ciudadDAO cDAO = new ciudadDAO(conn);
                listaCiudad = cDAO.ListCiudades();

//                listaTipoDoc = new ArrayList<>();
//                listaTipoDoc = tipoDocDAO.listarTipoDoc();
//                listaGenero = new ArrayList<>();
//                listaGenero = generoDAO.listarGenero();

                request.setAttribute("listaCiudad", listaCiudad);

//                request.setAttribute("listaTipoDoc", listaTipoDoc);
//                request.setAttribute("listaGenero", listaGenero);

                cDAO.CloseAll();
                rd = request.getRequestDispatcher("/views/actualizar/actualizarDatos.jsp");
                rd.forward(request, response);
        
    }

            @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
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

    
}
