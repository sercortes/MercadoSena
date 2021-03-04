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
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sergio vera
 */
public class CRestPse {

    private static final String TOKEN = "TEST-5137551104556241-022318-63009cafbdcb1e0dade50d297f5768ac-719428996";
    private static final String URL_MERCADOPAGO = "https://api.mercadopago.com/v1/";
    private static final String URL_WOMPI = "https://sandbox.wompi.co/v1/";
    private static final String TOKE_WOMPO = "pub_test_IDSDN4xfXPLj9A5R5UiKaaQKqySh0JNY";

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
                                            .add("unit_price", valor)
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

            // Obtiene pogos por PSE
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

    public static JSONObject getInformacion() {

        StringBuilder respuesta = new StringBuilder();

        JSONObject json = null;
        try {
            HttpURLConnection con = null;
            URL object = new URL("https://sandbox.wompi.co/v1/merchants/pub_test_IDSDN4xfXPLj9A5R5UiKaaQKqySh0JNY");
            // Abrir la conexión e indicar que será de tipo GET
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {

                String acumuladorRespuesta = null;
                while ((acumuladorRespuesta = br.readLine()) != null) {
                    respuesta.append(acumuladorRespuesta.trim());
                }

                json = new JSONObject(respuesta.toString());

            } catch (Exception ee) {
                System.err.println("Error" + ee);
            }

        } catch (Exception e) {
        }

        return json;

    }

    // PSE 
    public static String Postpse(String token, int valor, int Tipodepersona, String Tipodedoc, String document, String code, String descriptions, String emails, String referencia, String NombreApellido) throws MalformedURLException {
        // DefaultHttpClient httpClient = new DefaultHttpClient();
        int id = 0;
        JSONObject json = null;
        String idpago = null;

        try {

            JsonObject value = (JsonObject) Json.createObjectBuilder()
                    .add("acceptance_token", token)
                    .add("amount_in_cents", valor)
                    .add("currency", "COP")
                    .add("customer_email", emails)
                    .add("payment_method", Json.createObjectBuilder()
                            .add("type", "PSE")
                            .add("user_type", Tipodepersona)
                            .add("user_legal_id_type", Tipodedoc)
                            .add("user_legal_id", document)
                            .add("financial_institution_code", code)
                            .add("payment_description", descriptions)
                    )
                    .add("redirect_url", "http://localhost:8080/Store/")
                    .add("reference", referencia)
                    .add("customer_data", Json.createObjectBuilder()
                            .add("full_name", NombreApellido)
                    )
                    //                    .add("shipping_address", Json.createObjectBuilder()
                    //                            .add("address_line_1", "Calle 34 # 56 - 78")
                    //                            .add("address_line_2", "Apartamento 502, Torre I")
                    //                            .add("country", "CO")
                    //                            .add("region", "Cundinamarca")
                    //                            .add("city", "Bogotá")
                    //                            .add("name", "Pepe Perez")
                    //                            .add("phone_number", "573109999999")
                    //                            .add("postal_code", "111111")
                    //                    )
                    .build();

            // Obtenemos el OutputStream para agregar el json de la petición.
            HttpURLConnection conn = POSTPSEbank("transactions", "POST");
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

                idpago = json.getJSONObject("data").getString("id");

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

            // Obtiene pogos por PSE
        } catch (Exception e) {
            System.err.println("ERROR" + e);
        }

        return consultarPago(idpago);
    }

    public static String consultarPago(String idpago) throws MalformedURLException {
        StringBuilder respuesta = new StringBuilder();
        JSONObject json = null;

        String ULRredirec = null;

        try {
            HttpURLConnection con = null;
            URL object = new URL("https://sandbox.wompi.co/v1/transactions/" + idpago);
            // Abrir la conexión e indicar que será de tipo GET
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            boolean encontrado = false;

            while (encontrado == false) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {

                    String acumuladorRespuesta = null;
                    while ((acumuladorRespuesta = br.readLine()) != null) {
                        respuesta.append(acumuladorRespuesta.trim());
                        if (respuesta != null) {
                            encontrado = true;
                            break;
                        }
                    }
                }
            }

            json = new JSONObject(respuesta.toString());

            ULRredirec = json.getJSONObject("data").getJSONObject("payment_method").getJSONObject("extra").getString("async_payment_url");

        } catch (Exception e) {

        }
        return ULRredirec;
    }

    public static JSONObject listbancosPSE() {

        JSONObject jarray = getlistabancosPSE("pse/financial_institutions", "GET");

        return jarray;

    }

    public static JSONObject getlistabancosPSE(String complement, String method) {
        StringBuilder respuesta = new StringBuilder();
        HttpURLConnection con = null;
        JSONObject json = null;

        try {
            // Crear un objeto de tipo URL
            URL object = new URL(URL_WOMPI + complement);
            // Abrir la conexión e indicar que será de tipo GET
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            //pasamos el token
            con.setRequestProperty("Authorization", "Bearer " + TOKE_WOMPO);

            // Obtener la respuesta
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {

                String acumuladorRespuesta = null;
                while ((acumuladorRespuesta = br.readLine()) != null) {
                    respuesta.append(acumuladorRespuesta.trim());
                }

                json = new JSONObject(respuesta.toString());

            } catch (Exception ee) {
                System.err.println("Error" + ee);
            }

        } catch (Exception e) {
            System.err.println("Error" + e);
        }

        return json;

    }

    public static HttpURLConnection POSTPSEbank(String complement, String method) {
        HttpURLConnection con = null;
        try {
            URL object = new URL(URL_WOMPI + complement);
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + TOKE_WOMPO);
        } catch (Exception e) {
        }
        return con;
    }

    public static HttpURLConnection GetTOKEN(String complement, String method) {
        HttpURLConnection con = null;
        try {
            URL object = new URL(URL_WOMPI + complement);
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + TOKE_WOMPO);
        } catch (Exception e) {
        }
        return con;
    }
}
