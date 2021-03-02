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
import java.util.List;

/**
 *
 * @author equipo
 */
public class PersonasNaturalDAO {
    
   private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    public PersonasNaturalDAO(Connection conn) {
        this.conn = conn;
    }

    public personaNaturalDTO getPersona(String idPersona){
    
         try {
            String sql = "SELECT idPersona, documentoPersona, nombrePersona, apellidoPersona, direccionPersona FROM personanatural "
                    + "WHERE documentoPersona = ? LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idPersona);
            rs = ps.executeQuery();
            personaNaturalDTO persona;
            persona = new personaNaturalDTO();
            while (rs.next()) {
                persona.setIdUsuario(rs.getInt("idPersona"));
                persona.setIdPer(rs.getInt("documentoPersona"));
                persona.setNombrePer(rs.getString("nombrePersona"));
                persona.setApellidoPer(rs.getString("apellidoPersona"));
                persona.setDireccionPer(rs.getString("direccionPersona"));
            }
            return persona;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
    
    public int registrarPersona(personaNaturalDTO persona) throws Exception, MySQLIntegrityConstraintViolationException {
        int idUsuario = 0;
        String consulta = "INSERT INTO personanatural(nombrePersona, apellidoPersona, correoPersona, urlImgPersona, idUsuarioFK) "
                + "VALUES(?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, persona.getNombrePer());
            ps.setString(2, persona.getApellidoPer());
            ps.setString(3, persona.getCorreoPer());
            ps.setString(4, persona.getUrlImg());
            ps.setInt(5, persona.getIdUsuario());
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
    
     public boolean actualizarDatosFaltantes(personaNaturalDTO persona, int idUsuario) throws MySQLIntegrityConstraintViolationException, Exception {
        String consulta = "UPDATE personanatural SET direccionPersona = ?, "
                + "celularPersona = ?, telefonoPersona = ? WHERE idUsuarioFK = ?";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, persona.getDireccionPer());
            ps.setString(2, persona.getNumCelularPer());
            ps.setString(3, persona.getTelPer());
            ps.setInt(4, idUsuario);
            ps.executeUpdate();
            return true;
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
