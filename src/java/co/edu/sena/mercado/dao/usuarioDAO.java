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
        consulta = "INSERT INTO usuario(emailusuario, passwordUsuario, fechaPassword, estadoUsuario, fkRol,codActivacion) VALUES (?,md5(?), now(),?,?,md5(?))";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, datosUsu.getCorreoUsu());
            ps.setString(2, datosUsu.getClaveUsu());
            ps.setString(3, datosUsu.getEstadoUsu());
            ps.setInt(4, datosUsu.getIdRol());
            ps.setString(5,datosUsu.getCodigo());
            ps.executeUpdate();
            System.out.println("registro usuario realizado consulta " + ps.toString());
            return true;
        } catch (SQLException e) {
            System.out.println("error al registrar usuario " + e);
            System.out.println("consulta " + ps.toString());
            return false;
       }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
    
    
    public usuarioDTO Enecuentracontraseña(usuarioDTO usuario) {

        try {
            con = new Conexion();
            String sql = "SELECT * FROM usuario WHERE emailUsuario = ? AND passwordUsuario"
                    + " = md5(?) AND estadoUsuario = 1 limit 1";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, usuario.getCorreoUsu());
            ps.setString(2, usuario.getClaveUsu());
            ResultSet rs = ps.executeQuery();
            usuarioDTO usua = new usuarioDTO();
            while (rs.next()) {

                usua.setIdUsuario(rs.getInt("idUsuario"));
                usua.setCorreoUsu(rs.getString("emailUsuario"));
                usua.setEstadoUsu(rs.getString("estadoUsuario"));
                usua.setIdRol(rs.getInt("fkRol"));
                usua.setNumIngreso(rs.getInt("numeroIngreso"));

            }
            return usua;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }
    
    public boolean actualizarUsuario(usuarioDTO datosUsu) {
        con = new Conexion();
        consulta = "UPDATE usuario SET emailusuario=?,passwordUsuario=md5(?),fechaPassword=now() WHERE idUsuario=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, datosUsu.getCorreoUsu());
            ps.setString(2, datosUsu.getClaveUsu());
            ps.setInt(3, datosUsu.getIdUsuario());
            
            ps.executeUpdate();
           
            return true;
        } catch (SQLException e) {
            System.out.println("error al registrar usuario " + e);
            System.out.println("consulta " + ps.toString());
            return false;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
    
    
    
       public usuarioDTO login(usuarioDTO usuario) {

        try {
            con = new Conexion();
            String sql = "SELECT * FROM usuario WHERE emailUsuario = ? AND passwordUsuario"
                    + " = md5(?) AND estadoUsuario = 1 limit 1";
            PreparedStatement ps = con.getConnection().prepareStatement(sql);
            ps.setString(1, usuario.getCorreoUsu());
            ps.setString(2, usuario.getClaveUsu());
            ResultSet rs = ps.executeQuery();
            usuarioDTO usua = new usuarioDTO();
            while (rs.next()) {

                usua.setIdUsuario(rs.getInt("idUsuario"));
                usua.setCorreoUsu(rs.getString("emailUsuario"));
                usua.setEstadoUsu(rs.getString("estadoUsuario"));
                usua.setIdRol(rs.getInt("fkRol"));
                usua.setNumIngreso(rs.getInt("numeroIngreso"));

            }
            return usua;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
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
            usuarioDTO.setCodigo(rs.getString("codActivacion"));
            usuarioDTO.setNumIngreso(rs.getInt("numeroIngreso"));
           
            
            }
           
            System.out.println("usuario encontrado consulta " + ps.toString());
            System.out.println("... usuario " + usuarioDTO.toString());
            return usuarioDTO;
        } catch (SQLException e) {
            System.out.println("error al consultar  usuario " + e);
            System.out.println("consulta " + ps.toString());
            return null;
         }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
   
    public boolean activarUsuario(String correo, String codigo) {
        con = new Conexion();
        consulta="UPDATE usuario SET estadoUsuario=1 WHERE codActivacion=md5(?) and emailusuario=? AND estadoUSUARIO = 0";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, codigo);
            ps.setString(2, correo);
            int count = ps.executeUpdate();
            return  (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("consulta " + ps.toString());
            return false;
         }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
    public boolean actEnteda(int numIngreso, int id) {
        con = new Conexion();
     
        
        consulta="UPDATE usuario SET numeroIngreso=?  WHERE idUsuario=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, numIngreso);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("error al actualizar numero de ingreso usuario " + e);
            System.out.println("consulta " + ps.toString());
            return false;
         }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
    public boolean eliminarUsuario(String correo, String clave) {
        con = new Conexion();
     
        
        consulta="delete from usuario where passwordUsuario=md5(?) and  emailusuario=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, clave);
            ps.setString(2, correo);
            ps.executeUpdate();
           
           
            System.out.println("usuario eliminado consulta " + ps.toString());
          
            return true;
        } catch (SQLException e) {
            System.out.println("error al eliminar  usuario " + e);
            System.out.println("consulta " + ps.toString());
            return false;
         }finally{
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
}
