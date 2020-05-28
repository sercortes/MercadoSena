/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.*;
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
public class usuarioDAO {

    Conexion con = new Conexion();
    Connection cn;
    PreparedStatement ps;
    ResultSet rs;
    String consulta;
    usuarioDTO usuarioDTO = new usuarioDTO();
    ArrayList<usuarioDTO> listaUsuario = new ArrayList<>();

    public boolean registroUsuario(usuarioDTO datosUsu) {
        con = new Conexion();
        consulta = "INSERT INTO usuario(emailusuario, passwordUsuario, fechaPassword, estadoUsuario, fkRol) VALUES (?,md5(?), now(),?,?)";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, datosUsu.getCorreoUsu());
            ps.setString(2, datosUsu.getClaveUsu());
            ps.setString(3, datosUsu.getEstadoUsu());
            ps.setInt(4, datosUsu.getIdRol());
            ps.executeUpdate();
            System.out.println("registro usuario realizado consulta " + ps.toString());
            return true;
        } catch (SQLException e) {
            System.out.println("error al registrar usuario " + e);
            System.out.println("consulta " + ps.toString());
            return false;
        }
    }
    public usuarioDTO buscarUsuario(String correo, String clave) {
        con = new Conexion();
     
        
        consulta="select * from usuario where passwordUsuario=md5(?) and  emailusuario=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, clave);
            ps.setString(2, correo);
            rs= ps.executeQuery();
            while(rs.next()){
            usuarioDTO=new usuarioDTO();
            usuarioDTO.setCorreoUsu(rs.getString("emailusuario"));
            usuarioDTO.setEstadoUsu(rs.getString("estadoUsuario"));
            usuarioDTO.setFechaClave(rs.getString("fechaPassword"));
            usuarioDTO.setIdRol(rs.getInt("fkRol"));
            usuarioDTO.setIdUsuario(rs.getInt("idUsuario"));
           
            
            }
           
            System.out.println("usuario encontrado consulta " + ps.toString());
            System.out.println("... usuario " + usuarioDTO.toString());
            return usuarioDTO;
        } catch (SQLException e) {
            System.out.println("error al consultar  usuario " + e);
            System.out.println("consulta " + ps.toString());
            return null;
        }
    }
    public boolean eliminarUsuario(String correo, String clave) {
        con = new Conexion();
     
        
        consulta="delete * from usuario where passwordUsuario=md5(?) and  emailusuario=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.executeUpdate();
           
           
            System.out.println("usuario eliminado consulta " + ps.toString());
          
            return true;
        } catch (SQLException e) {
            System.out.println("error al eliminar  usuario " + e);
            System.out.println("consulta " + ps.toString());
            return false;
        }
    }
}
