/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author equipo
 */
public class UsuarioDAOLogin {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public UsuarioDAOLogin(Connection conn) {
        this.conn = conn;
    }

    public boolean isUser(String correo) {
        try {
            boolean estado = false;
            String sql = "SELECT idUsuario, emailUsuario FROM usuario WHERE emailUsuario = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                estado = true;
            }
            return estado;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public usuarioDTO login(usuarioDTO usuario) {

        try {

            String sql = "SELECT * FROM usuario WHERE emailUsuario = ? AND passwordUsuario"
                    + " = md5(?) limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);
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
        }

    }

    public boolean updateHashPassword(String clave, String correo) throws Exception {
        try {

            String sql = "UPDATE usuario set codActivacion = md5(?) "
                    + "WHERE emailusuario = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, clave);
            ps.setString(2, correo);
            
            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            System.out.println("Error edit " + ex.getMessage());
            throw new Exception();
        }
    }
    
    public usuarioDTO getHash(String correo, String hash) {

        try {

            String sql = "SELECT idUsuario, codActivacion FROM usuario WHERE emailUsuario = ? AND codActivacion = md5(?) LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, hash);
            ResultSet rs = ps.executeQuery();
            usuarioDTO usua = new usuarioDTO();
            while (rs.next()) {

                usua.setIdUsuario(rs.getInt("idUsuario"));
                usua.setCodigo(rs.getString("codActivacion"));

            }
            return usua;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
    
     public boolean resetPass(String correo, String pass) {
        try {

            String sql = "UPDATE usuario set passwordUsuario = md5(?), codActivacion = '', fechaPassword = NOW() "
                    + "WHERE emailusuario = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, pass);
            ps.setString(2, correo);

            System.out.println(ps.toString());

            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            System.out.println("Error edit " + ex.getMessage());
            return false;
        }
    }
    
    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
