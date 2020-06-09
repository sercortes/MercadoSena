/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.respuestaDTO;
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
public class respuestaDAO {

    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Conexion con;
    String consulta;
    respuestaDTO respuestaDTO = new respuestaDTO();
    ArrayList<respuestaDTO> listaRespuesta = new ArrayList<>();

    public boolean resgistroRespuesta(respuestaDTO respuestaDTO) {
        con = new Conexion();
        consulta = "INSERT INTO respuesta( respuesta, idEmpresaFK, idPreguntaFK) VALUES (?,?,?)";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, consulta);
            ps.setString(1, respuestaDTO.getRespuesta());
            ps.setInt(2, respuestaDTO.getIdEmpresa());
            ps.setInt(3, respuestaDTO.getIdPregunta());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxx error al registrar la respuesta " + e);
            System.out.println("xxxxxxxxxxxxxx consulta " + ps.toString());
            return false;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }

}
