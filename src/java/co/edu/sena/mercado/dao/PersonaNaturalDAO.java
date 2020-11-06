/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;


import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author equipo
 */
public class PersonaNaturalDAO {
    
   private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public PersonaNaturalDAO(Connection conn) {
        this.conn = conn;
    }

    public int registrarPersona(personaNaturalDTO persona) throws Exception, MySQLIntegrityConstraintViolationException {
        int idUsuario = 0;
        String consulta = "INSERT INTO personanatural(nombrePersona, apellidoPersona, correoPersona, urlImgPersona, idUsuarioFK, idCiudadFK) "
                + "VALUES(?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, persona.getNombrePer());
            ps.setString(2, persona.getApellidoPer());
            ps.setString(3, persona.getCorreoPer());
            ps.setString(4, persona.getUrlImg());
            ps.setInt(5, persona.getIdUsuario());
            ps.setInt(6, persona.getIdCiudad());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }
            return idUsuario;
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println(ex);
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            System.out.println("........error al relizar el registro pde personaDAO " + e);
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
