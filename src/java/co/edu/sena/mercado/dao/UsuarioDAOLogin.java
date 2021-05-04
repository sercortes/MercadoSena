/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.empresaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.dto.usuarioDTO;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            e.printStackTrace();
            System.out.println(ps.toString());
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
            e.printStackTrace();
            System.out.println(ps.toString());
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
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }

    }

    public boolean resetPass(String correo, String pass) {
        try {

            String sql = "UPDATE usuario set passwordUsuario = md5(?), codActivacion = '', fechaPassword = NOW(), estadoUsuario = 1 "
                    + "WHERE emailusuario = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, pass);
            ps.setString(2, correo);
            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ps.toString());
            return false;
        }
    }

    public personaNaturalDTO getDataById(String id) {

        try {
            String sql = "SELECT * FROM personanatural WHERE idUsuarioFK = ? ORDER BY idPersona LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            personaNaturalDTO persona = new personaNaturalDTO();
            while (rs.next()) {

                persona.setIdPer(rs.getInt("idPersona"));
                persona.setNumeroDocPer(rs.getString("documentoPersona"));
                persona.setNombrePer(rs.getString("nombrePersona"));
                persona.setApellidoPer(rs.getString("apellidoPersona"));
                persona.setCorreoPer(rs.getString("correoPersona"));
                persona.setDireccionPer(rs.getString("direccionPersona"));
                persona.setNumCelularPer(rs.getString("celularPersona"));
                persona.setTelPer(rs.getString("telefonoPersona"));
                persona.setIdCiudad(rs.getInt("idCiudadFK"));
                persona.setIdTipoDoc(rs.getInt("idTipoDocFK"));
                persona.setIdGenero(rs.getInt("idGeneroFK"));
                persona.setUrlImg(rs.getString("urlImgPersona"));
                persona.setModifiData(rs.getTimestamp("modiData"));

            }
            return persona;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }

    }

    public empresaDTO buscarEmpresa(int idUsuario) {
        String query = "SELECT * FROM empresa WHERE idUsuarioFK = ? limit 1";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            empresaDTO empresaDTO = null;
            while (rs.next()) {
                empresaDTO = new empresaDTO();
                empresaDTO.setIdEmpresa(rs.getInt("idEmpresa"));
                empresaDTO.setEsCentro(rs.getString("esCentro"));
                empresaDTO.setNombreEmpresa(rs.getString("nombreEmpresa"));
                empresaDTO.setDirEmpresa(rs.getString("direccionEmpresa"));
                empresaDTO.setTelEmpresa(rs.getString("telefonoEmpresa"));
                empresaDTO.setCelEmpresa(rs.getString("celularEmpresa"));
                empresaDTO.setCorreoEmpresa(rs.getString("correoEmpresa"));
//                empresaDTO.setCentro(rs.getString("idCentro"));
                empresaDTO.setIdCiudad(rs.getInt("idCiudadFK"));
                empresaDTO.setIdUsuario(rs.getInt("idUsuarioFK"));

            }
            //cerrarCon(ps, cn, rs);
            return empresaDTO;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            // cerrarCon(ps, cn, rs);
            return null;
        }

    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
