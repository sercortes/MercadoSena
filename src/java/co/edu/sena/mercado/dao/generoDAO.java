/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.generoDTO;
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
public class generoDAO {
    
   private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public generoDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<generoDTO> listarGenero() {
        ArrayList listaGenero= new ArrayList<>();
        String consulta = "select idGenero, nombreGenero from genero";
        try {
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            generoDTO generoDTO = new generoDTO();
            while (rs.next()) {
               generoDTO =new generoDTO();
               generoDTO.setIdGenero(rs.getInt("idGenero"));
               generoDTO.setGenero(rs.getString("nombreGenero"));
               listaGenero.add(generoDTO);
            }
            return listaGenero;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("......... consulta" + ps.toString());
            return null;
         }
    }
    
        public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }
    
}
