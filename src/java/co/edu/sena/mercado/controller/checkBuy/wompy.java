/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.checkBuy;

import co.edu.sena.mercado.controller.consumer.CRestPse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class wompy extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("idscc") != null) {
            String refe = request.getParameter("idscc");
            String refes = "";
            refes = CRestPse.getReferencia(refe);
//            response.sendRedirect("https://transaction-redirect.wompi.co/check?id=" + refes);
            response.sendRedirect("https://transaction-redirect.wompi.co/check?env=test&id=" + refes);
        }else{
            
            System.out.println("wompy");
            response.sendRedirect(request.getContextPath() + "/home");
            
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("wompy");
        response.sendRedirect(request.getContextPath() + "/home");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
