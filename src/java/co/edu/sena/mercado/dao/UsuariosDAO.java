/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author equipo
 */
public class UsuariosDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public UsuariosDAO(Connection conn) {
        this.conn = conn;
    }

    public int registroUsuario(usuarioDTO datosUsu) throws Exception, MySQLIntegrityConstraintViolationException {
        int idUsuario = 0;
        String consulta = "INSERT INTO usuario(emailusuario, passwordUsuario, fechaPassword, estadoUsuario, fkRol,codActivacion) VALUES (?,md5(?), now(),?,?,md5(?))";
        try {
            ps = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, datosUsu.getCorreoUsu());
            ps.setString(2, datosUsu.getClaveUsu());
            ps.setString(3, datosUsu.getEstadoUsu());
            ps.setInt(4, datosUsu.getIdRol());
            ps.setString(5,datosUsu.getCodigo());
            System.out.println(ps.toString());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }
            return idUsuario;
        }catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println(ex);
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            System.out.println("........error al relizar el registro registroUsuario " + e);
            System.out.println("........ consulta " + ps.toString());
            throw new Exception();
        }
    }
    
         public void CloseAll(){
          Conexion.close(conn);
          Conexion.close(ps);
          Conexion.close(rs);
    }
    
}
