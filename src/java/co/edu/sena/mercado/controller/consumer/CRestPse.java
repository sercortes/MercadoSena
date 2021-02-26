/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.controller.consumer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 *
 * @author sergio vera
 */
public class CRestPse {

    private static final String TOKEN = "TEST-5137551104556241-022318-63009cafbdcb1e0dade50d297f5768ac-719428996";
    private static final String URL_MERCADOPAGO = "https://api.mercadopago.com/v1/";

    // ba1dce34592f7865102f21a23046c20
    public static JSONObject getListabancos(String token, String correo, int valor, String description, int cuotas, String paymentMethodId, String docType, String docNumber) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        int id = 0;
        JSONObject json = null;

        try {

            JsonObject value = (JsonObject) Json.createObjectBuilder()
                    .add("token", token)
                    .add("installments", cuotas)
                    .add("transaction_amount", valor)
                    .add("description", description)
                    .add("payment_method_id", paymentMethodId)
                    .add("payer", Json.createObjectBuilder()
                            .add("email", correo)
                            .add("identification", Json.createObjectBuilder()
                                    .add("number", docNumber)
                                    .add("type", docType))
                    )
                    .add("notification_url", "https://www.suaurl.com/notificacoes/")
                    .addNull("sponsor_id")
                    .add("binary_mode", false)
                    .add("external_reference", "MP0001")
                    .add("statement_descriptor", "MercadoPago")
                    .add("additional_info", Json.createObjectBuilder()
                            .add("items", Json.createArrayBuilder()
                                    .add(Json.createObjectBuilder()
                                            .add("id", "PR0001")
                                            .add("title", "Point Mini")
                                            .add("description", "Producto Point para cobros con tarjetas mediante bluetooth")
                                            .add("picture_url", "https://http2.mlstatic.com/resources/frontend/statics/growth-sellers-landings/device-mlb-point-i_medium@2x.png")
                                            .add("category_id", "electronics")
                                            .add("quantity", 1)
                                            .add("unit_price", 58.80)
                                    ))
                            .add("payer", Json.createObjectBuilder()
                                    .add("first_name", "Nome")
                                    .add("last_name", "Sobrenome")
                                    .add("address", Json.createObjectBuilder()
                                            .add("zip_code", "06233-200")
                                            .add("street_name", "Av das Nacoes Unidas")
                                            .add("street_number", 3003)
                                    )
                                    .add("registration_date", "2019-01-01T12:01:01.000-03:00")
                                    .add("phone", Json.createObjectBuilder()
                                            .add("area_code", "011")
                                            .add("number", "987654321")
                                    )
                            )
                            .add("shipments", Json.createObjectBuilder()
                                    .add("receiver_address", Json.createObjectBuilder()
                                            .add("street_name", "Av das Nacoes Unidas")
                                            .add("street_number", 3003)
                                            .add("zip_code", "06233200")
                                            .add("city_name", "Buzios")
                                            .add("state_name", "Rio de Janeiro")
                                    )
                            )
                    )
                    .build();

            // Obtenemos el OutputStream para agregar el json de la petición.
            HttpURLConnection conn = getConnectionHTTP("payments", "POST");
            boolean compra = true;

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = value.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // Obtener la respuesta
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder respuesta = new StringBuilder();
                String acumuladorRespuesta = null;
                while ((acumuladorRespuesta = br.readLine()) != null) {
                    respuesta.append(acumuladorRespuesta.trim());
                }

                json = new JSONObject(respuesta.toString());

            } catch (Exception e) {
                compra = false;
            }

            if (compra == true) {
                if (json.has("message") == false) {
                    if (json.has("id") == true) {
                        System.err.println("la compra se reliazo");
                    }
                } else if (json.has("id") == false && json.has("message") == true) {
                    // mensaje en el json que pailas
                }
            } else {
                // mensaje en el json que pailas
            }

        } catch (Exception e) {
            System.err.println("ERROR" + e);
        }

        return json;
    }

    public static HttpURLConnection getConnectionHTTP(String complement, String method) {
        HttpURLConnection con = null;
        try {
            URL object = new URL(URL_MERCADOPAGO + complement);
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + TOKEN);
        } catch (Exception e) {
        }
        return con;
    }
}
