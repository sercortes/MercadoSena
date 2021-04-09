package co.edu.sena.mercado.util;

import co.edu.sena.mercado.controller.consumer.CRestPse;
import co.edu.sena.mercado.dto.pagoJSON;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONString;

public class Conexion {

    private static String bd = "mercadosena";
    private static String user = "root";
    private static String pass = "";
    private static String url = "jdbc:mysql://localhost/" + bd + "?useUnicode=true&amp;characterEncoding=utf8";
    private Connection conn = null;

    public Conexion() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);

        } catch (SQLException e) {
            System.out.println(e);
            e.getMessage();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            e.getMessage();
        }

    }

    public Connection getConnection() {
        return conn;
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

}

//    public static void main(String[] args) {
//        
//        try {
//            pagoJSON user = CRestPse.consultarPagoDos("110027-1617996623-45373");
//            System.out.println(user.toString());
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//}
        
//        try {
//            JSONObject consultarpagopse = CRestPse.consultarPago("13.04113399208306");
//            System.out.println(consultarpagopse);
//            String data = consultarpagopse.get("data").toString().substring(1);
//            data = data.substring(0, data.length() - 1);
//            Gson gson = new Gson(); 
//            pagoJSON user = gson.fromJson(data, pagoJSON.class);
//            System.out.println(user.toString());
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
//        }

//    }
//}
