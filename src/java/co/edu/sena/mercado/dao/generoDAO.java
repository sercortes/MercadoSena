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
    
      Conexion con = new Conexion();
    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    String consulta;
    generoDTO generoDTO = new generoDTO();
    ArrayList<generoDTO> listaGenero = new ArrayList<>();

    public ArrayList<generoDTO> listarGenero() {
        listaGenero= new ArrayList<>();
        con = new Conexion();
        consulta = "select * from genero";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
               generoDTO =new generoDTO();
               generoDTO.setIdGenero(rs.getInt("idGenero"));
               generoDTO.setGenero(rs.getString("nombreGenero"));
               listaGenero.add(generoDTO);

            }
            System.out.println(".........resultado " + listaGenero.toString());
            System.out.println("......... consulta " + ps.toString());
            return listaGenero;
        } catch (SQLException e) {
            System.out.println(".........Error al listar generos " + e);
            System.out.println("......... consulta" + ps.toString());
            return null;
        }
    }
    
    
}
