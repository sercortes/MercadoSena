/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.filtro;

import co.edu.sena.mercado.dao.ImagenesProductosDAO;
import co.edu.sena.mercado.dao.ProductoDAO;
import co.edu.sena.mercado.dao.ProductosPedidosDAO;
import co.edu.sena.mercado.dao.empresaDAO;
import co.edu.sena.mercado.dto.Centro;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.MarcaDTO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.productoImagenesDTO;
import co.edu.sena.mercado.dto.productoPedidosDTO;
import co.edu.sena.mercado.dto.productosMasSolicitadosDTO;
import co.edu.sena.mercado.util.Conexion;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class filtro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        switch (accion) {
 
            case "listarMarcas":
                
                listaMarcas(request, response);

                break;

            default:
                System.out.println("ACCION SERVLET FILTRO NO EXISTE");
               
        }

    }

    private void listaMarcas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                empresaDAO empresaDAO = new empresaDAO();
                ArrayList<MarcaDTO> listaEmpresa = new ArrayList<>();
                listaEmpresa = empresaDAO.listarMarcas();
                response.setContentType("application/json");
                new Gson().toJson(listaEmpresa, response.getWriter());
        
    }
    
        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
