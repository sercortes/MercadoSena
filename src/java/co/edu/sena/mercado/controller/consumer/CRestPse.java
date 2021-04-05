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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sergio vera
 */
public class CRestPse {

    //llave privada TOKEN  
    private static final String URL_WOMPI = "https://production.wompi.co/v1/";
    private static final String PRV_WOMPI = "prv_prod_to8dJPkmqH3sBHfpi5UseCQasCae6Sel";
    private static final String PUB_WOMPI = "pub_prod_aXKqdG8ag1FfXpu2Gy4IjIbCytnYzeKL";

//    private static final String URL_WOMPI = "https://sandbox.wompi.co/v1/";
//    private static final String PRV_WOMPI = "prv_test_JnLqheUtGvTYbZfd1OQPr3FsfQy7t12Y";
//    private static final String PUB_WOMPI = "pub_test_Qfh69dnQv5nufuKaaUlqoqIGgsn37aof";
    //Tarjeta de credito
    public static String Tokenizartarjeta(String number, String cvc, String month, String year, String cardholder) {
        JSONObject json = new JSONObject();
        StringBuilder respuesta = new StringBuilder();
        JSONObject jsonresponse = null;
        String tokentarjeta = null;

        try {

            json.put("number", number);
            json.put("cvc", cvc);
            json.put("exp_month", month);
            json.put("exp_year", year);
            json.put("card_holder", cardholder);

            HttpURLConnection conn = POSTTokinizacionT("tokens/cards", "POST");

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {

                String acumuladorRespuesta = null;
                while ((acumuladorRespuesta = br.readLine()) != null) {
                    respuesta.append(acumuladorRespuesta.trim());
                }

                jsonresponse = new JSONObject(respuesta.toString());

                tokentarjeta = jsonresponse.getJSONObject("data").getString("id");

            } catch (Exception e) {

                System.err.println("ERROR" + e);
            }

        } catch (Exception e) {
            System.err.println("ERROR" + e);
        }

        return tokentarjeta;
    }

    public static JSONObject Posttajeta(String tokenpersona, String tokentarjeta, int valor, String email, String installments, String referencia, String cardholder) throws MalformedURLException {
        // DefaultHttpClient httpClient = new DefaultHttpClient();

        JSONObject jsons = null;
        String refpago = null;
        StringBuilder respuesta = new StringBuilder();
        try {

            JsonObject value = (JsonObject) Json.createObjectBuilder()
                    .add("acceptance_token", tokenpersona)
                    .add("amount_in_cents", valor)
                    .add("currency", "COP")
                    .add("customer_email", email)
                    .add("payment_method", Json.createObjectBuilder()
                            .add("type", "CARD")
                            .add("token", tokentarjeta)
                            .add("installments", installments)
                    )
                    .add("redirect_url", "https://carwaystore.com/Store/views/car/estadodepago.jsp")
                    .add("reference", referencia)
                    .add("customer_data", Json.createObjectBuilder()
                            .add("full_name", cardholder)
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

                String acumuladorRespuesta = null;
                while ((acumuladorRespuesta = br.readLine()) != null) {
                    respuesta.append(acumuladorRespuesta.trim());
                }

                jsons = new JSONObject(respuesta.toString());

                refpago = jsons.getJSONObject("data").getString("reference");

            } catch (Exception e) {

                System.err.println("ERROR" + e);
            }

            if (compra == true) {
                if (jsons.has("message") == false) {
                    if (jsons.has("id") == true) {
                        System.err.println("la compra se reliazo");
                    }
                } else if (jsons.has("id") == false && jsons.has("message") == true) {
                    // mensaje en el json que pailas
                }
            } else {
                // mensaje en el json que pailas
            }

            // Obtiene pogos por PSE
        } catch (Exception e) {
            System.err.println("ERROR" + e);
        }

        return consultarPagoTarjeta(refpago);
    }

    public static JSONObject getInformacion() {

        StringBuilder respuesta = new StringBuilder();

        JSONObject json = null;
        try {
            HttpURLConnection con = null;
            URL object = new URL("https://production.wompi.co/v1/merchants/pub_prod_aXKqdG8ag1FfXpu2Gy4IjIbCytnYzeKL");
//               URL object = new URL("https://sandbox.wompi.co/v1/merchants/pub_test_Qfh69dnQv5nufuKaaUlqoqIGgsn37aof");
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
        StringBuilder respuesta = new StringBuilder();
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
                    .add("redirect_url", "https://carwaystore.com/Store/")
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

                String acumuladorRespuesta = null;
                while ((acumuladorRespuesta = br.readLine()) != null) {
                    respuesta.append(acumuladorRespuesta.trim());
                }

                json = new JSONObject(respuesta.toString());

                idpago = json.getJSONObject("data").getString("reference");

            } catch (Exception e) {
                compra = false;
                System.err.println("ERROR" + e);
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
        String complement = "transactions?reference=";
        try {
            HttpURLConnection con = null;
            URL object = new URL(URL_WOMPI + complement + idpago);
            // Abrir la conexión e indicar que será de tipo GET
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + PRV_WOMPI);
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

            try {
                JSONArray jsonArray = json.getJSONArray("data");
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                ULRredirec = jsonObject.getJSONObject("payment_method").getJSONObject("extra").getString("async_payment_url");

            } catch (Exception ee) {
                
                System.out.println("Error URL Redirec" + ee);
            }

        } catch (Exception e) {          
             System.out.println("Error URL Redirec" + e);
        }
        return ULRredirec;
    }

    public static JSONObject consultarPagoTarjeta(String refpago) throws MalformedURLException {
        StringBuilder respuesta = new StringBuilder();
        JSONObject json = null;

        String ULRredirec = null;
        String complement = "transactions?reference=";
        try {
            HttpURLConnection con = null;
            URL object = new URL(URL_WOMPI + complement + refpago);
            // Abrir la conexión e indicar que será de tipo GET
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + PRV_WOMPI);
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

        } catch (Exception e) {

        }
        return json;
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
            con.setRequestProperty("Authorization", "Bearer " + PRV_WOMPI);

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
            con.setRequestProperty("Authorization", "Bearer " + PRV_WOMPI);
        } catch (Exception e) {
            System.err.println("Error de conexion" + e);
        }
        return con;
    }

    public static HttpURLConnection POSTTokinizacionT(String complement, String method) {
        HttpURLConnection con = null;
        try {
            URL object = new URL(URL_WOMPI + complement);
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + PUB_WOMPI);
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
            con.setRequestProperty("Authorization", "Bearer " + PRV_WOMPI);
        } catch (Exception e) {
        }
        return con;
    }
}
