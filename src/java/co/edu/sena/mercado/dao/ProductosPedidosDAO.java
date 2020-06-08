/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.productoPedidosDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author serfin
 */
public class ProductosPedidosDAO {
    
      private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public ProductosPedidosDAO(Connection conn){
        this.conn = conn;
    }
    
    public boolean insertReturn(productoPedidosDTO pedidosDTO) {

        String sql = "INSERT INTO productospedidos (idProductoFK, idVentaFK, cantidadProductoVenta) "
                + "VALUES (?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pedidosDTO.getIdProductoFK());
            ps.setString(2, pedidosDTO.getIdVentaFK());
            ps.setInt(3, pedidosDTO.getCantidad());
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
        Conexion.close(rs);
    }
    
}
