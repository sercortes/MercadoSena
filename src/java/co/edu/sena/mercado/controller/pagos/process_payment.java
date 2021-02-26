/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.pagos;

import co.edu.sena.mercado.controller.consumer.CRestPse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author sergi
 */
// controllerpagos
@WebServlet(name = "process_payment", urlPatterns = {"/process_payment"})
public class process_payment extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("valor", request.getParameter("valor"));
        request.getRequestDispatcher("/views/car/pagoscar.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String transactionAmount = request.getParameter("transactionAmount");
            int valor = Integer.parseInt(transactionAmount);
            String token = request.getParameter("token"); //
            String description = request.getParameter("description");
            String installments = request.getParameter("installments");
            int cuotas = Integer.parseInt(installments);
            String paymentMethodId = request.getParameter("paymentMethodId");
            String docType = request.getParameter("docType");
            String docNumber = request.getParameter("docNumber");
            String email = request.getParameter("email");

            JSONObject jsonrta = CRestPse.getListabancos(token, email, valor, description, cuotas, paymentMethodId, docType, docNumber);

            // Enviar atributos del estado de la compra
            request.setAttribute("id", jsonrta.get("id"));
            request.setAttribute("date_created", jsonrta.get("date_created"));
            request.setAttribute("date_approved", jsonrta.get("date_approved"));
            request.setAttribute("date_last_updated", jsonrta.get("date_last_updated"));
            request.setAttribute("payment_method_id", jsonrta.get("payment_method_id"));
            request.setAttribute("payment_type_id", jsonrta.get("payment_type_id"));
            request.setAttribute("status", jsonrta.get("status"));
            request.setAttribute("status_detail", jsonrta.get("status_detail"));
            request.setAttribute("description", jsonrta.get("description"));
            request.setAttribute("transaction_amount", jsonrta.get("transaction_amount"));
            request.setAttribute("statement_descriptor", jsonrta.get("statement_descriptor"));

            request.getRequestDispatcher("/views/car/estadodepago.jsp").forward(request, response);

        } catch (Exception e) {

            System.err.println("Eorr a traer datos" + e);

        }
    }
}
