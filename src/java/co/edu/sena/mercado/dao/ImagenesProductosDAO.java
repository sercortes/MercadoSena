/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author serfin
 */
public class ImagenesProductosDAO {
    
     private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ImagenesProductosDAO(Connection conn) {
        this.conn = conn;
    }
    
     public boolean insertReturn(ImagenesProducto imagenes) {

        String sql = "INSERT INTO imagenesProductos (urlProducto, idProductoImageFK) "
                + "VALUES (?, ?)";

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, imagenes.getUrl());
            ps.setString(2, imagenes.getIdProductoFK());

            ps.executeUpdate();
            
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
    }

    
}
