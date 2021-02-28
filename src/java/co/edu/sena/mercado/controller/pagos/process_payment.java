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

// controllerpagos
@WebServlet(name = "process_payment", urlPatterns = {"/process_payment"})
public class process_payment extends HttpServlet {

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("valor", request.getParameter("valor"));
        request.getRequestDispatcher("/views/car/pagoscar.jsp").forward(request, response);

    }

   
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
