/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.ciudadDTO;
import co.edu.sena.mercado.dto.estadoVentaDTO;
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
public class estadoVentaDAO {

    Conexion con = new Conexion();
    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    String consulta;
    estadoVentaDTO estadoVentaDTO;
    ArrayList<estadoVentaDTO> listaEstadoVentas = new ArrayList<>();

    public ArrayList<estadoVentaDTO> listarEstadoVenta() {
        listaEstadoVentas= new ArrayList<>();
        con = new Conexion();
        consulta = "select * from estadoventas";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                estadoVentaDTO=new estadoVentaDTO();
                estadoVentaDTO.setIdEstado(rs.getInt("idEstadoVentas"));
                estadoVentaDTO.setNombreEstado(rs.getString("nombreEstado"));
                listaEstadoVentas.add(estadoVentaDTO);

            }
           
            return listaEstadoVentas;
        } catch (SQLException e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXx Error al listar estado ventas " + e);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXxXXXXXX consulta" + ps.toString());
            return null;
         }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
}

    
    
