/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.preguntasDTO;
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
public class preguntasDAO {

    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Conexion con;
    String consulta;
    preguntasDTO preguntaDTO = new preguntasDTO();
    ArrayList<preguntasDTO> listaPregunta = new ArrayList<>();

    public boolean resgistroPregunta(preguntasDTO preguntasDTO) {
        con = new Conexion();
        consulta = "INSERT INTO preguntas( pregunta,estadoPregunta, idUsuarioPreguntaFK, idProductoFK) VALUES (?,?,?,?)";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, consulta);
            ps.setString(1, preguntasDTO.getPregunta());
            ps.setInt(2, preguntasDTO.getEstadoPregunta());
            ps.setInt(3, preguntasDTO.getIdUsuarioPregunta());
            ps.setInt(4, preguntasDTO.getIdProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxx error al registrar la pregunta " + e);
            System.out.println("xxxxxxxxxxxxxx consulta " + ps.toString());
            return false;
        }
    }

    public ArrayList<preguntasDTO> listarPregustas(int idEmpresa) {
        con = new Conexion();
        listaPregunta=new ArrayList<>();
        consulta = "SELECT * FROM preguntas WHERE (SELECT pro.idEmpresaFK FROM producto pro WHERE idProductoFK=pro.idProducto )=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, idEmpresa);
            rs = ps.executeQuery();
            while (rs.next()) {
                preguntaDTO=new preguntasDTO();
                preguntaDTO.setEstadoPregunta(rs.getInt("estadoPregunta"));
                preguntaDTO.setIdPregunta(rs.getInt("idPregunta"));
                preguntaDTO.setIdProducto(rs.getInt("idProductoFK"));
                preguntaDTO.setIdUsuarioPregunta(rs.getInt("idUsuarioPreguntaFK"));
                preguntaDTO.setPregunta(rs.getString("pregunta"));
                listaPregunta.add(preguntaDTO);
            }
            return listaPregunta;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxx error al consultar preguntas "+e);
            System.out.println("xxxxxxxxxxxxxxxxx consulta "+ps.toString());
            return null;
        }
    }
}
