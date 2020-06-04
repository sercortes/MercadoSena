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

/**
 *
 * @author DELL
 */
public class ciudadDAO {

    Conexion con = new Conexion();
    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    String consulta;
    ciudadDTO ciudadDTO = new ciudadDTO();
    ArrayList<ciudadDTO> listaCiudad = new ArrayList<>();

    public ArrayList<ciudadDTO> listarCiudad() {
        listaCiudad = new ArrayList<>();
        con = new Conexion();
        consulta = "select * from ciudad";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                ciudadDTO = new ciudadDTO();
                ciudadDTO.setIdCiudad(rs.getInt("idCiudad"));
                ciudadDTO.setIdPais(rs.getInt("idPaisFK"));
                ciudadDTO.setNombreCiudad(rs.getString("nombreCiudad"));
                listaCiudad.add(ciudadDTO);

            }
           // System.out.println(".........resultado " + listaCiudad.toString());
           // System.out.println("......... consulta " + ps.toString());
            return listaCiudad;
        } catch (SQLException e) {
            System.out.println(".........Error al listar cidades " + e);
            System.out.println("......... consulta" + ps.toString());
            return null;
         }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
}
