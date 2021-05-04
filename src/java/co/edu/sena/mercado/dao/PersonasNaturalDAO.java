/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;


import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.tipoDocumentoDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }
        
    }
    
    public personaNaturalDTO getPersonaByIdPersona(String idPersona){
    
         try {
            String sql = "SELECT idPersona, documentoPersona, nombrePersona, apellidoPersona, direccionPersona, celularPersona FROM personanatural "
                    + "WHERE idPersona = ? AND idTipoPersonaFK = 1 LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idPersona);
             System.out.println(ps.toString());
            rs = ps.executeQuery();
            personaNaturalDTO persona;
            persona = new personaNaturalDTO();
            while (rs.next()) {
                persona.setIdUsuario(rs.getInt("idPersona"));
                persona.setNumeroDocPer(rs.getString("documentoPersona"));
                persona.setNombrePer(rs.getString("nombrePersona"));
                persona.setApellidoPer(rs.getString("apellidoPersona"));
                persona.setDireccionPer(rs.getString("direccionPersona"));
                persona.setNumCelularPer(rs.getString("celularPersona"));
            }
            return persona;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return null;
        }
        
    }
    
    public personaNaturalDTO getPersona(String idPersona){
    
         try {
            String sql = "SELECT idPersona, documentoPersona, nombrePersona, apellidoPersona, direccionPersona, celularPersona FROM personanatural "
                    + "WHERE documentoPersona = ? AND idTipoPersonaFK = 1 LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idPersona);
            rs = ps.executeQuery();
            personaNaturalDTO persona = null;
            while (rs.next()) {
                persona = new personaNaturalDTO();
                persona.setIdUsuario(rs.getInt("idPersona"));
                persona.setNumeroDocPer(rs.getString("documentoPersona"));
                persona.setNombrePer(rs.getString("nombrePersona"));
                persona.setApellidoPer(rs.getString("apellidoPersona"));
                persona.setDireccionPer(rs.getString("direccionPersona"));
                persona.setNumCelularPer(rs.getString("celularPersona"));
            }
            return persona;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }
        
    }
    
    public int registrarPersonaNaturalByVendedor(personaNaturalDTO persona) throws Exception, MySQLIntegrityConstraintViolationException {
        int idUsuario = 0;
        String consulta = "INSERT INTO personanatural(celularPersona, telefonoPersona, idTipoDocFK, documentoPersona, nombrePersona, apellidoPersona, direccionPersona, idTipoPersonaFK, idUsuarioFK) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, persona.getNumCelularPer());
            ps.setString(2, persona.getTelPer());
            ps.setInt(3, persona.getIdTipoDoc());
            ps.setString(4, persona.getNumeroDocPer());
            ps.setString(5, persona.getNombrePer());
            ps.setString(6, persona.getApellidoPer());
            ps.setString(7, persona.getDireccionPer());
            ps.setString(8, "1");
            ps.setInt(9, persona.getIdUsuario());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idUsuario = rs.getInt(1);
            }
            return idUsuario;
        } catch (MySQLIntegrityConstraintViolationException ex) {
            ex.printStackTrace();
            System.out.println(ex);
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            e.printStackTrace();
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
            ex.printStackTrace();
            System.out.println("........ consulta " + ps.toString());
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            e.printStackTrace();
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
            if (persona.getTelPer().equals("")) {
                ps.setNull(4, 4);
            }else{
                ps.setString(4, persona.getTelPer());
            }
            ps.setInt(5, idUsuario);
            ps.executeUpdate();
            return true;
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println(ex);
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("........ consulta " + ps.toString());
            throw new Exception();
        }
    }
     
      public int getComprasIncomplete(String idUsuario) throws Exception, MySQLIntegrityConstraintViolationException  {
             int cantidad = 0;
          String consulta = "SELECT count(*) 'Compras' FROM ventas V " +
                    "WHERE V.idEstadoVentasFK IN(3,4,5) AND V.idCompradorFK = ? " +
                    "AND V.fechaVenta >= curdate() AND V.fechaVenta < curdate() + interval 1 day " +
                    "group by V.idCompradorFK";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, idUsuario);
            rs = ps.executeQuery();
            while (rs.next()) {
                cantidad = rs.getInt(1);
            }
            return cantidad;
        } catch (SQLException e) {
            System.out.println("error getPreguntas " + e);
            System.out.println("consulta " + ps.toString());
            throw new SQLException();
        }
    }
     
       public boolean updateData(personaNaturalDTO persona) throws MySQLIntegrityConstraintViolationException, Exception {
        String consulta = "UPDATE personanatural SET nombrePersona = ?, apellidoPersona = ?, "
                + "idCiudadFK = ?, idGeneroFK = ?, celularPersona = ?, telefonoPersona = ?, "
                + "direccionPersona = ?, urlImgPersona = ?, modiData = NOW() WHERE idUsuarioFK = ?";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, persona.getNombrePer());
            ps.setString(2, persona.getApellidoPer());
            ps.setInt(3, persona.getIdCiudad());
            ps.setInt(4, persona.getIdGenero());
            ps.setString(5, persona.getNumCelularPer());
            ps.setString(6, persona.getTelPer());
            ps.setString(7, persona.getDireccionPer());
            ps.setString(8, persona.getUrlImg());
            ps.setInt(9, persona.getIdUsuario());
            ps.executeUpdate();
            return true;
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println(ex);
            throw new MySQLIntegrityConstraintViolationException();     
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("........ consulta " + ps.toString());
            throw new Exception();
        }
    }
      
     public boolean updatePass(usuarioDTO datosUsu) throws MySQLIntegrityConstraintViolationException, Exception {
  
        String consulta = "UPDATE usuario SET passwordUsuario=md5(?),fechaPassword=now() WHERE idUsuario=?";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, datosUsu.getClaveUsu());
            ps.setInt(2, datosUsu.getIdUsuario());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("consulta " + ps.toString());
            throw new Exception();
        }
    }
    
      
    public usuarioDTO Enecuentracontraseña(usuarioDTO usuario) throws MySQLIntegrityConstraintViolationException, Exception {

        try {
            String sql = "SELECT * FROM usuario WHERE emailUsuario = ? AND passwordUsuario"
                    + " = md5(?) AND estadoUsuario = 1 limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario.getCorreoUsu());
            ps.setString(2, usuario.getClaveUsu());
            ResultSet rs = ps.executeQuery();
            usuarioDTO usua = new usuarioDTO();
            while (rs.next()) {

                usua.setIdUsuario(rs.getInt("idUsuario"));
                usua.setCorreoUsu(rs.getString("emailUsuario"));
                usua.setEstadoUsu(rs.getString("estadoUsuario"));
                usua.setIdRol(rs.getInt("fkRol"));
                usua.setNumIngreso(rs.getInt("numeroIngreso"));

            }
            return usua;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }

    }
       
    public Timestamp getModifiData(String id) {

        try {
            Timestamp tiempo = null;
            String sql = "SELECT modiData FROM personanatural WHERE idUsuarioFK = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tiempo = rs.getTimestamp("modiData");
            }
            return tiempo;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }

    }
    
    public void CloseAll(){
          Conexion.close(conn);
          Conexion.close(ps);
          Conexion.close(rs);
    }
    
}
