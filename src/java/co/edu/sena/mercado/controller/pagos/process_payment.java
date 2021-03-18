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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
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

        request.getRequestDispatcher("/views/car/pagoscar.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        Object jsonarray = CRestPse.getPSE();
        String accion = request.getParameter("accionT");

        HttpSession sessionvalor = request.getSession();
        HttpSession sessiontoken = request.getSession();

        if (accion != null) {

            switch (accion) {

                case "Guardarprecio":
                    String valor = request.getParameter("valor");

                    sessionvalor.setAttribute("SESSIONVALOR", valor);
                    break;

                case "listadebancos":

                    JSONObject tokenwoppi = CRestPse.getInformacion();

                    String tokenapi = tokenwoppi.getJSONObject("data").getJSONObject("presigned_acceptance").getString("acceptance_token");
                    sessiontoken.setAttribute("TOKEN", tokenapi);

                    JSONObject jsonbancos = CRestPse.listbancosPSE();

                    response.getWriter().write(jsonbancos.getJSONArray("data").toString());

                    break;

                case "pagarpse":
                    String id = (String) request.getSession().getAttribute("IDVENTA");

                    String valorpagar = sessionvalor.getAttribute("SESSIONVALOR") + "";
                    String valopse = valorpagar.replace(".", "");
                    int valors = Integer.parseInt(valopse + 0 + 0);
//                String descriptionss = request.getParameter("description");
                    String descriptions = "Producto de CARWAY";
                    String Tipodepersona = request.getParameter("TPersona");
                    String Nombre = request.getParameter("Nombre");
                    String Apellido = request.getParameter("Apellido");
                    String NombreApellido = Nombre + " " + Apellido;
                    int tipodepersona = Integer.parseInt(Tipodepersona);
                    String Tipodedoc = request.getParameter("docTypes");
                    String document = request.getParameter("docNumbers");
                    String code = request.getParameter("selectdebanco");
                    String tokes = sessiontoken.getAttribute("TOKEN").toString();
                    String emails = request.getParameter("email");

                    String referencia = String.valueOf(Math.random() * 15 + 1);

                    String PSEbanck = CRestPse.Postpse(tokes, valors, tipodepersona, Tipodedoc, document, code, descriptions, emails, referencia, NombreApellido);

                    VentaDAO ventaDAO = new VentaDAO(new Conexion().getConnection());
                    VentaDTO ventaDTO = new VentaDTO();

                    if (PSEbanck == null) {

                        ventaDTO.setIdEstadoVentaFK("3");
                        ventaDTO.setIdVenta(id);
                        ventaDAO.actualizarVenta(ventaDTO);
                        ventaDAO.CloseAll();
                        request.getSession().removeAttribute("IDVENTA");

                        request.getRequestDispatcher("/views/car/recahzodelpago.jsp").forward(request, response);
                    } else {
                        ventaDTO.setIdEstadoVentaFK("2");
                        ventaDTO.setIdVenta(id);
                        ventaDAO.actualizarVenta(ventaDTO);
                        ventaDAO.CloseAll();
                        request.getSession().removeAttribute("IDVENTA");

                        response.sendRedirect(PSEbanck);
                    }

                    break;

                case "Tarjetadecredito":

                    try {

                        JSONObject tokenperson = CRestPse.getInformacion();

                        String tokenper = tokenperson.getJSONObject("data").getJSONObject("presigned_acceptance").getString("acceptance_token");
//
                        String cardhold = request.getParameter("cardhold");
                        String cardExpirationMonth = request.getParameter("cardExpirationMonth");
                        String cardExpirationYear = request.getParameter("cardExpirationYear");
                        String cardNumber = request.getParameter("cardNumber");
                        String CVV = request.getParameter("CVV");

                        String valorpagart = sessionvalor.getAttribute("SESSIONVALOR") + "";
                        String valort = valorpagart.replace(".", "");
                        int valorr = Integer.parseInt(valort + 0 + 0);
                        String email = request.getParameter("emails");
                        String installments = "1";
                        String reference = String.valueOf(Math.random() * 15 + 1);

                        String tokentarjeta = CRestPse.Tokenizartarjeta(cardNumber, CVV, cardExpirationMonth, cardExpirationYear, cardhold);

                        if (tokentarjeta != null) {

                            JSONObject jsontar = CRestPse.Posttajeta(tokenper, tokentarjeta, valorr, email, installments, reference, cardhold);
                            if (jsontar != null) {

                                try {
                                    JSONArray jsonArray = jsontar.getJSONArray("data");
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                    request.setAttribute("id", jsonObject.get("id"));
                                    request.setAttribute("reference", jsonObject.get("reference"));

                                    String imputtex = jsonObject.get("created_at").toString();
                                    DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);
                                    Date date = inputFormat.parse(imputtex);
                                    String outputText = outputFormat.format(date);
                                    request.setAttribute("created_at", outputText);
                                    request.setAttribute("brand", jsonObject.getJSONObject("payment_method").getJSONObject("extra").get("brand"));
                                    request.setAttribute("last_four", jsonObject.getJSONObject("payment_method").getJSONObject("extra").get("last_four"));
                                    request.setAttribute("full_name", jsonObject.getJSONObject("customer_data").get("full_name"));
                                    String idV = (String) request.getSession().getAttribute("IDVENTA");
                                    VentaDAO ventaDAOs = new VentaDAO(new Conexion().getConnection());
                                    VentaDTO ventaDTOs = new VentaDTO();
                                    String Aprobado = "Aprobado";
                                    String status = jsonObject.get("status").toString();

                                    if (status.equals("PENDING")) {

                                        while (status.equalsIgnoreCase("PENDING")) {
                                            JSONObject jsonconsulta = CRestPse.consultarPagoTarjeta(reference);
                                            JSONArray jsonarray = jsonconsulta.getJSONArray("data");
                                            JSONObject jsonobjects = jsonarray.getJSONObject(0);
                                            status = jsonobjects.get("status").toString();

//                                            if (status.equals("APPROVED")) {
//                                                break;
//                                            }
                                        }
                                        if (status.equals("APPROVED")) {
                                            ventaDTOs.setIdEstadoVentaFK("2");
                                            ventaDTOs.setIdVenta(idV);
                                            ventaDAOs.actualizarVenta(ventaDTOs);
                                            ventaDAOs.CloseAll();
                                            request.getSession().removeAttribute("IDVENTA");
                                            request.setAttribute("status", Aprobado);
                                            request.setAttribute("amount_in_cents", valorpagart);
                                            request.getRequestDispatcher("/views/car/estadodepago.jsp").forward(request, response);
                                        } else {
                                            ventaDTOs.setIdEstadoVentaFK("3");
                                            ventaDTOs.setIdVenta(idV);
                                            ventaDAOs.actualizarVenta(ventaDTOs);
                                            ventaDAOs.CloseAll();
                                            request.getSession().removeAttribute("IDVENTA");

                                            request.getRequestDispatcher("/views/car/recahzodelpago.jsp").forward(request, response);
                                        }

                                    } else if (status.equals("APPROVED")) {
                                        ventaDTOs.setIdEstadoVentaFK("2");
                                        ventaDTOs.setIdVenta(idV);
                                        ventaDAOs.actualizarVenta(ventaDTOs);
                                        ventaDAOs.CloseAll();
                                        request.getSession().removeAttribute("IDVENTA");
                                        request.setAttribute("status", Aprobado);
                                        request.setAttribute("amount_in_cents", valorpagart);
                                        request.getRequestDispatcher("/views/car/estadodepago.jsp").forward(request, response);
                                    } else {
                                        ventaDTOs.setIdEstadoVentaFK("3");
                                        ventaDTOs.setIdVenta(idV);
                                        ventaDAOs.actualizarVenta(ventaDTOs);
                                        ventaDAOs.CloseAll();
                                        request.getSession().removeAttribute("IDVENTA");

                                        request.getRequestDispatcher("/views/car/recahzodelpago.jsp").forward(request, response);
                                    }

                                } catch (Exception e) {
                                    System.err.println("Error" + e);
                                }
                            } else {
                                request.getRequestDispatcher("/views/car/recahzodelpago.jsp").forward(request, response);
                            }

                        } else {
                            request.getRequestDispatcher("/views/car/recahzodelpago.jsp").forward(request, response);
                        }

                    } catch (Exception e) {

                        System.err.println("Eorr a traer datos" + e);

                    }

                    sessionvalor.removeAttribute("valor");

                    break;

            }

        }

    }
}
