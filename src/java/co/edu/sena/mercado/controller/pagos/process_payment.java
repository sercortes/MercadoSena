/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.pagos;

import co.edu.sena.mercado.controller.consumer.CRestPse;
import co.edu.sena.mercado.dao.VentaDAO;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.util.Conexion;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

//        Object jsonarray = CRestPse.getPSE();
        String accion = request.getParameter("accionT");
        HttpSession sessiontoken = request.getSession();
        switch (accion) {

            case "listadebancos":

                JSONObject tokenwoppi = CRestPse.getInformacion();

                String tokenapi = tokenwoppi.getJSONObject("data").getJSONObject("presigned_acceptance").getString("acceptance_token");
                sessiontoken.setAttribute("TOKEN", tokenapi);

                JSONObject jsonbancos = CRestPse.listbancosPSE();

                response.getWriter().write(jsonbancos.getJSONArray("data").toString());

                break;

            case "pagarpse":

                String valorpagar = request.getParameter("valorpagar");
                String valopse = valorpagar.replace(".", "");
                int valors = Integer.parseInt(valopse + 0 + 0);
                String descriptionss = request.getParameter("description");
                String descriptions = "Producto de CARWAY";
                String Tipodepersona = request.getParameter("TPersona");
                String Nombre = request.getParameter("Nombre");
                String Apellido = request.getParameter("Apellido");
                String NombreApellido = Nombre +" "+ Apellido;
                int tipodepersona = Integer.parseInt(Tipodepersona);
                String Tipodedoc = request.getParameter("docTypes");
                String document = request.getParameter("docNumbers");
                String code = request.getParameter("selectdebanco");
                String tokes = sessiontoken.getAttribute("TOKEN").toString();
                String emails = request.getParameter("emails");

                String referencia = String.valueOf(Math.random() * 15 + 1);

                String PSEbanck = CRestPse.Postpse(tokes, valors, tipodepersona, Tipodedoc, document, code, descriptions, emails, referencia, NombreApellido);
                if (PSEbanck == null) {
                    request.getRequestDispatcher("/views/car/recahzodelpago.jsp").forward(request, response);
                } else {
                    VentaDAO ventaDAO = new VentaDAO(new Conexion().getConnection());
                    VentaDTO ventaDTO = new VentaDTO();
                    ventaDTO.setIdEstadoVentaFK("2");
                    ventaDTO.setIdVenta(request.getParameter("ventaId"));
                    ventaDAO.actualizarVenta(ventaDTO);
                    ventaDAO.CloseAll();
                    response.sendRedirect(PSEbanck);
                }

                break;

            case "Tarjetadecredito":

                try {
//
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
                    String imputtex = jsonrta.get("date_approved").toString();
                    DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);
                    Date date = inputFormat.parse(imputtex);
                    String outputText = outputFormat.format(date);
                    request.setAttribute("date_approved", outputText);
                    String metodopago = jsonrta.get("payment_method_id").toString();
                    String TarjteaM = "Mastercard";
                    String TarjetaV = "Visa";
                    String Otro = "Otro";
                    if (metodopago.equals("master") || metodopago.equals("visa")) {
                        if (metodopago.equals("master")) {
                            request.setAttribute("payment_method_id", TarjteaM);
                        } else if (metodopago.equals("visa")) {
                            request.setAttribute("payment_method_id", TarjetaV);
                        } else {
                            request.setAttribute("payment_method_id", Otro);
                        }
                    }
                    String Aprobado = "Aprobado";
                    String status = jsonrta.get("status").toString();
                    if (status.equals("approved")) {
                        VentaDAO ventaDAO = new VentaDAO(new Conexion().getConnection());
                        VentaDTO ventaDTO = new VentaDTO();
                        ventaDTO.setIdEstadoVentaFK("2");
                        ventaDTO.setIdVenta(request.getParameter("ventaId"));
                        ventaDAO.actualizarVenta(ventaDTO);
                        ventaDAO.CloseAll();
                        request.setAttribute("status", Aprobado);
                    }
                    request.setAttribute("status_detail", jsonrta.get("status_detail"));
                    request.setAttribute("description", jsonrta.get("description"));
                    Object totalget = jsonrta.get("transaction_amount");
                    DecimalFormat formatea = new DecimalFormat("###,###.##");
                    String totalset = formatea.format(totalget);
                    request.setAttribute("transaction_amount", totalset);
                    request.setAttribute("statement_descriptor", jsonrta.get("statement_descriptor"));
                    request.setAttribute("last_four_digits", jsonrta.getJSONObject("card").getString("last_four_digits"));
                    request.setAttribute("name", jsonrta.getJSONObject("card").getJSONObject("cardholder").getString("name"));
                    request.setAttribute("number", jsonrta.getJSONObject("card").getJSONObject("cardholder").getJSONObject("identification").getString("number"));
                    request.setAttribute("type", jsonrta.getJSONObject("card").getJSONObject("cardholder").getJSONObject("identification").getString("type"));
//                    request.setAttribute("last_four_digits", jsonrta.getJSONObject("card").getString("last_four_digits"));
                    request.getRequestDispatcher("/views/car/estadodepago.jsp").forward(request, response);

                } catch (Exception e) {

                    System.err.println("Eorr a traer datos" + e);

                }

                break;

        }

    }
}
