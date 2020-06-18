/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class personaNaturalDAO {

    Conexion con = new Conexion();
    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    String consulta;
    personaNaturalDTO personaDTO = new personaNaturalDTO();
    ArrayList<personaNaturalDTO> listaPersonas = new ArrayList<>();
    
    public boolean registrarPersona(personaNaturalDTO persona){
    con=new Conexion();
    consulta="INSERT INTO personanatural( documentoPersona, nombrePersona, apellidoPersona, correoPersona, direccionPersona, celularPersona, telefonoPersona, urlImgPersona, idUsuarioFK, idGeneroFK, idTipoDocFK, idCiudadFK) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            cn=con.getConnection();
            ps=cn.prepareStatement(consulta);
            ps.setString(1, persona.getNumeroDocPer());
            ps.setString(2, persona.getNombrePer());
            ps.setString(3, persona.getApellidoPer());
            ps.setString(4, persona.getCorreoPer());
            ps.setString(5, persona.getDireccionPer());
            ps.setString(6, persona.getNumCelularPer());
            ps.setString(7, persona.getTelPer());
            ps.setString(8, persona.getUrlImg());
            ps.setInt(9, persona.getIdUsuario());
            ps.setInt(10, persona.getIdGenero());
            ps.setInt(11, persona.getIdTipoDoc());
            ps.setInt(12, persona.getIdCiudad());
            ps.executeUpdate();
            System.out.println(".........registro persona realizado consulta "+ps.toString());
            return true;
        } catch (SQLException e) {
            System.out.println("........error al relizar el registro pde personaDAO "+e);
            System.out.println("........ consulta "+ps.toString());
            return false;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
    public boolean actualizarPersona(personaNaturalDTO persona){
    con=new Conexion();
    consulta="UPDATE personanatural SET documentoPersona=?,nombrePersona=?,apellidoPersona=?,direccionPersona=?,celularPersona=?,telefonoPersona=?,urlImgPersona=?,idUsuarioFK=?,idGeneroFK=?,idTipoDocFK=?,idCiudadFK=? WHERE idPersona=?";
        try {
            cn=con.getConnection();
            ps=cn.prepareStatement(consulta);
            ps.setString(1, persona.getNumeroDocPer());
            ps.setString(2, persona.getNombrePer());
            ps.setString(3, persona.getApellidoPer());
            ps.setString(4, persona.getDireccionPer());
            ps.setString(5, persona.getNumCelularPer());
            ps.setString(6, persona.getTelPer());
            ps.setString(7, persona.getUrlImg());
            ps.setInt(8, persona.getIdUsuario());
            ps.setInt(9, persona.getIdGenero());
            ps.setInt(10, persona.getIdTipoDoc());
            ps.setInt(11, persona.getIdCiudad());
            ps.setInt(12, persona.getIdPer());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("........error al relizar actualizar  personaDAO "+e);
            System.out.println("........ consulta "+ps.toString());
            return false;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
    
      public personaNaturalDTO getDataById(String id) {

        try {
            con = new Conexion();
            String sql = "SELECT * FROM personanatural WHERE idUsuarioFK = ? LIMIT 1";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            personaNaturalDTO persona = new personaNaturalDTO();
            while (rs.next()) {

                persona.setIdPer(rs.getInt("idPersona"));
                persona.setNumeroDocPer(rs.getString("documentoPersona"));
                persona.setNombrePer(rs.getString("nombrePersona"));
                persona.setApellidoPer(rs.getString("apellidoPersona"));
                persona.setCorreoPer(rs.getString("correoPersona"));
                persona.setDireccionPer(rs.getString("direccionPersona"));
                persona.setNumCelularPer(rs.getString("celularPersona"));
                persona.setTelPer(rs.getString("telefonoPersona"));
                persona.setIdCiudad(rs.getInt("idCiudadFK"));
                persona.setIdTipoDoc(rs.getInt("idTipoDocFK"));
                persona.setIdGenero(rs.getInt("idGeneroFK"));
                persona.setUrlImg(rs.getString("urlImgPersona"));

            }
            return persona;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }
    
 public usuarioDTO buscarRecu(String documento, String tipoDoc) {
        con = new Conexion();
    usuarioDTO usuarioDTO = new usuarioDTO();
    usuarioDTO = null;
        
        consulta="SELECT usu.* FROM personanatural per INNER JOIN usuario usu on per.idUsuarioFK=usu.idUsuario  WHERE per.idTipoDocFK=? AND per.documentoPersona=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, tipoDoc);
            ps.setString(2, documento);
            rs= ps.executeQuery();
            while(rs.next()){
            usuarioDTO=new usuarioDTO();
            usuarioDTO.setCorreoUsu(rs.getString("emailusuario"));
            usuarioDTO.setEstadoUsu(rs.getString("estadoUsuario"));
            usuarioDTO.setFechaClave(rs.getString("fechaPassword"));
            usuarioDTO.setIdRol(rs.getInt("fkRol"));
            usuarioDTO.setIdUsuario(rs.getInt("idUsuario"));
            usuarioDTO.setCodigo(rs.getString("codActivacion"));
            usuarioDTO.setNumIngreso(rs.getInt("numeroIngreso"));
           
            
            }
           
           
            return usuarioDTO;
        } catch (SQLException e) {
            System.out.println("error al consultar  usuario " + e);
            System.out.println("consulta " + ps.toString());
            return null;
         }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
      
}
