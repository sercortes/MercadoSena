/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author equipo
 */
public class EmpresasDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public EmpresasDAO(Connection conn) {
        this.conn = conn;
    }
    
     public boolean registroEmpresa(empresaDTO empresaDTO, int idUsuario) throws MySQLIntegrityConstraintViolationException, SQLException {
        String consulta = "INSERT INTO empresa( esCentro, direccionEmpresa, telefonoEmpresa, celularEmpresa, correoEmpresa, idCiudadFK, idUsuarioFK) VALUES "
                + "(?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, "0");
            ps.setString(2, empresaDTO.getDirEmpresa());
            ps.setString(3, empresaDTO.getTelEmpresa());
            ps.setString(4, empresaDTO.getCelEmpresa());
            ps.setString(5, empresaDTO.getCorreoEmpresa());
            ps.setInt(6, empresaDTO.getIdCiudad());
            ps.setInt(7, empresaDTO.getIdUsuario());
            ps.executeUpdate();
            System.out.println("..... registro de empresa realizado consulta " + ps.toString());
            //cerrarCon(ps, cn, rs);
            return true;

        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println(ex);
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al registrar empresa " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            // cerrarCon(ps, cn, rs);
            throw new SQLException();
        } 

    }
     
      public boolean actualizarDatosFaltantes(empresaDTO empresaDTO, int idUsuario) throws MySQLIntegrityConstraintViolationException, SQLException {
        String consulta = "UPDATE empresa SET nombreEmpresa = ?, direccionEmpresa = ?, telefonoEmpresa = ?, "
                + "celularEmpresa = ? WHERE idUsuarioFK = ?";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, empresaDTO.getNombreEmpresa());
            ps.setString(2, empresaDTO.getDirEmpresa());
            ps.setString(3, empresaDTO.getTelEmpresa());
            ps.setString(4, empresaDTO.getCelEmpresa());
            ps.setInt(5, idUsuario);
            ps.executeUpdate();
            return true;

        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println(ex);
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        } 

    }
      
       public boolean updateCompanyFirst(empresaDTO empresaDTO, int idUsuario) throws MySQLIntegrityConstraintViolationException, SQLException {
        String consulta = "UPDATE empresa SET nombreEmpresa = ?, direccionEmpresa = ?, "
                + "telefonoEmpresa = ?, celularEmpresa = ?, "
                + "idCiudadFK = ? "
                + "WHERE idUsuarioFK = ?";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, empresaDTO.getNombreEmpresa());
            ps.setString(2, empresaDTO.getDirEmpresa());
            ps.setString(3, empresaDTO.getTelEmpresa());
            ps.setString(4, empresaDTO.getCelEmpresa());
            ps.setInt(5, empresaDTO.getIdCiudad());
            ps.setInt(6, idUsuario);
            ps.executeUpdate();
            return true;

        }  catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println(ex);
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            System.out.println(e);
            throw new SQLException();
        } 

    }
     
         public void CloseAll(){
          Conexion.close(conn);
          Conexion.close(ps);
          Conexion.close(rs);
    }
    
}
