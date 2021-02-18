/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.preguntasDTO;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author equipo
 */
public class PreguntassDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public PreguntassDAO(Connection conn) {
        this.conn = conn;
    }

     public boolean resgistroPregunta(preguntasDTO preguntasDTO) throws SQLException {

        String consulta = "INSERT INTO preguntas( pregunta,estadoPregunta, idUsuarioPreguntaFK, idProductoFK) VALUES (?,?,?,?)";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, preguntasDTO.getPregunta());
            ps.setInt(2, preguntasDTO.getEstadoPregunta());
            ps.setInt(3, preguntasDTO.getIdUsuarioPregunta());
            ps.setInt(4, preguntasDTO.getIdProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("error resgistroPregunta " + e);
            System.out.println("consulta " + ps.toString());
            throw new SQLException();
        } 
    }
    
    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
