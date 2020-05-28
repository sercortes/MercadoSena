/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.personaNaturalDTO;
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
        }
    }

}
