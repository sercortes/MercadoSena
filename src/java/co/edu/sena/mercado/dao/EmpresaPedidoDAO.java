/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.EmpresaPedidoDTO;
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
public class EmpresaPedidoDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public EmpresaPedidoDAO(Connection conn){
        this.conn = conn;
    }
    
    public boolean insertReturn(EmpresaPedidoDTO empresaPedidoDTO) {

        String sql = "INSERT INTO empresaspedido (idEmpresaFK, idVentaFK) "
                + "VALUES (?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, empresaPedidoDTO.getIdEmpresaFK());
            ps.setString(2, empresaPedidoDTO.getIdVentaFK());
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
