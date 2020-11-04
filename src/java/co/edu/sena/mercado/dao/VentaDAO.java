/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.VentaDTO;
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
public class VentaDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public VentaDAO(Connection conn) {
        this.conn = conn;
    }

    public int insertReturn(VentaDTO ventaDTO) throws Exception{

        int idComprador = 0;

        String sql = "INSERT INTO ventas (valorVenta, contacto, idCompradorFK, idCiudadFK) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setDouble(1, ventaDTO.getValorVenta());
            ps.setString(2, ventaDTO.getContactoVenta());
            ps.setString(3, ventaDTO.getIdCompradorFK());
            ps.setString(4, ventaDTO.getIdCiudadFK());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idComprador = rs.getInt(1);
            }
            System.out.println("............................."+ps.toString());
            return idComprador;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }

    }

    public Boolean actualizarVenta(VentaDTO ventaDTO) {

        String sql = "UPDATE ventas SET idEstadoVentasFK=? WHERE idVenta=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ventaDTO.getIdEstadoVentaFK());
            ps.setString(2, ventaDTO.getIdVenta());
            ps.executeUpdate();

            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al realizar la actualizacion de estado venta" + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta" + ps.toString());
            return false;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al realizar la actualizacion de estado venta" + e);

            return false;
        }

    }
   

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
