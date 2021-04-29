/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.ciudadDTO;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ciudadDAO {

       private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ciudadDAO(Connection conn) {
        this.conn = conn;
    }
    
     public ArrayList<ciudadDTO> ListCiudades() {
        try {
            String sql = "SELECT idCiudad, nombreCiudad FROM ciudad";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<ciudadDTO> list = new ArrayList<ciudadDTO>();
            ciudadDTO ciudad;
            while (rs.next()) {
                ciudad = new ciudadDTO();
                ciudad.setIdCiudad(rs.getInt("idCiudad"));
                ciudad.setNombreCiudad(rs.getString("nombreCiudad"));
                list.add(ciudad);
            }
            return (ArrayList<ciudadDTO>) list;
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
