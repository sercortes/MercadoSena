/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.filtro;

import co.edu.sena.mercado.dao.ProductorDAOQuerys;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.empresaDTO;
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

/**
 *
 * @author equipo
 */
public class SearchThree extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           System.out.println("SearchThree no soporta GET");
           response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          System.out.println("SearchThree no soporta GET");
            response.sendRedirect(request.getContextPath() + "/home");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
