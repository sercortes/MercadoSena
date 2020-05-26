
package co.edu.sena.mercado.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {

    private static String bd = "mercadoSena";
    private static String user = "root";
    private static String pass = "";
    private static String url = "jdbc:mysql://localhost/" + bd +"?useUnicode=true&amp;characterEncoding=utf8";
    private Connection conn = null;
    
    public Conexion(){
       try{
           
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection(url, user, pass);
           
       }catch(SQLException e){
           e.getMessage();
       }catch(ClassNotFoundException e){
           e.getMessage();
       }
       
    }
    
    public Connection getConnection(){
       return conn; 
    }
    
     public static void close(ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void close(PreparedStatement stmt){
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void close(Connection conn){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
           ex.printStackTrace(System.out);
        }
    }
    
    
}
