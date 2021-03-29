/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.filtro;

import co.edu.sena.mercado.dao.BannerDAO;
import co.edu.sena.mercado.dto.Banner;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class banner extends HttpServlet {

   
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
                
                case "/Store/banner":
                    
                    getBanners(request, response);
                    
                    break;
                    
                case "/Store/editBanner":
                    
                    editBanner(request, response);
                    
                    break;
                    
            }
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
        } 
        
    }

    private void getBanners(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Conexion conexion = new Conexion();
        BannerDAO bannerDAO = new BannerDAO(conexion.getConnection());
        ArrayList<?> lista = bannerDAO.getMenu();
        response.setContentType("application/json");
        bannerDAO.CloseAll();
        new Gson().toJson(lista, response.getWriter());
        
    }

    private void editBanner(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, SQLException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String[] arreglo = request.getParameterValues("arrayP");
        Gson gson = new Gson();
        String json = "";
        for (String item : arreglo) {
            json += item;
        }
        Banner[] banners = gson.fromJson(json, Banner[].class);       
        BannerDAO bannerDAO = null;
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConnection();
        
        try {
            if (conn.getAutoCommit()) {
             conn.setAutoCommit(false);
            }
            bannerDAO = new BannerDAO(conn);
            for(Banner ban : banners){
                bannerDAO.editBanner(ban);
            }
            conn.commit();
            new Gson().toJson(1, response.getWriter());
        } catch (Exception ex) {
            conn.rollback();
            ex.printStackTrace();
            new Gson().toJson(0, response.getWriter());
        } finally {
            bannerDAO.CloseAll();
        }
        
    }
    
            @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
