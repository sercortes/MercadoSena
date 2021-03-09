/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;


import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.tipoDocumentoDTO;
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

    public ArrayList<tipoDocumentoDTO> getTiposDocumento(){
    
         try {
            String sql = "SELECT idTipoDoc, nombreTipoDoc FROM tipodocumento LIMIT 10";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<tipoDocumentoDTO> list = new ArrayList<tipoDocumentoDTO>();
            tipoDocumentoDTO tipo;
            while (rs.next()) {
                tipo = new tipoDocumentoDTO();
                tipo.setIdTipoDoc(rs.getInt("idTipoDoc"));
                tipo.setTipoDoc(rs.getString("nombreTipoDoc"));
                
                list.add(tipo);
            }
            return (ArrayList<tipoDocumentoDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
    
    public personaNaturalDTO getPersonaByIdPersona(String idPersona){
    
         try {
            String sql = "SELECT idPersona, documentoPersona, nombrePersona, apellidoPersona, direccionPersona FROM personanatural "
                    + "WHERE idPersona = ? AND idTipoPersonaFK = 1 LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idPersona);
             System.out.println(ps.toString());
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
    
    public personaNaturalDTO getPersona(String idPersona){
    
         try {
            String sql = "SELECT idPersona, documentoPersona, nombrePersona, apellidoPersona, direccionPersona FROM personanatural "
                    + "WHERE documentoPersona = ? AND idTipoPersonaFK = 1 LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idPersona);
             System.out.println(ps.toString());
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
    
    public int registrarPersonaNaturalByVendedor(personaNaturalDTO persona) throws Exception, MySQLIntegrityConstraintViolationException {
        int idUsuario = 0;
        String consulta = "INSERT INTO personanatural(idTipoDocFK, documentoPersona, nombrePersona, apellidoPersona, direccionPersona, idTipoPersonaFK, idUsuarioFK) "
                + "VALUES(?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, persona.getIdTipoDoc());
            ps.setString(2, persona.getNumeroDocPer());
            ps.setString(3, persona.getNombrePer());
            ps.setString(4, persona.getApellidoPer());
            ps.setString(5, persona.getDireccionPer());
            ps.setString(6, "1");
            ps.setInt(7, persona.getIdUsuario());
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
        String consulta = "UPDATE personanatural SET documentoPersona = ?, direccionPersona = ?, "
                + "celularPersona = ?, telefonoPersona = ? WHERE idUsuarioFK = ?";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, persona.getNumeroDocPer());
            ps.setString(2, persona.getDireccionPer());
            ps.setString(3, persona.getNumCelularPer());
            ps.setString(4, persona.getTelPer());
            ps.setInt(5, idUsuario);
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
