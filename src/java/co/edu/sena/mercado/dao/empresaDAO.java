/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.empresaDTO;
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
public class empresaDAO {

    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    Conexion con = new Conexion();
    String consulta;
    empresaDTO empresaDTO = new empresaDTO();
    ArrayList<empresaDTO> listaEmpresa = new ArrayList<>();

    public boolean registroEmpresa(empresaDTO empresaDTO) {
        con=new Conexion();
        consulta = "INSERT INTO empresa( esEmpresa, nombreEmpresa, direccionEmpresa, telefonoEmpresa, celularEmpresa, correoEmpresa, idCiudadFK, idUsuarioFK) VALUES (?,?,?,?,?,?,?,?)";
        try {
            cn=con.getConnection();
            ps=cn.prepareStatement(consulta);
            ps.setInt(1, empresaDTO.getEsEmpresa());
            ps.setString(2, empresaDTO.getNombreEmpresa());
            ps.setString(3, empresaDTO.getDirEmpresa());
            ps.setString(4, empresaDTO.getTelEmpresa());
            ps.setString(5, empresaDTO.getCelEmpresa());
            ps.setString(6, empresaDTO.getCorreoEmpresa());
            ps.setInt(7, empresaDTO.getIdCiudad());
            ps.setInt(8, empresaDTO.getIdUsuario());
            ps.executeUpdate();
            System.out.println("..... registro de empresa realizado consulta "+ps.toString());
            return true;
          

        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al registrar empresa "+e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta "+ps.toString());
            return false;
        }

    }

}
