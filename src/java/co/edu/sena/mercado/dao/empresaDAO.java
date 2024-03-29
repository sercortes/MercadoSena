/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.Centro;
import co.edu.sena.mercado.dto.MarcaDTO;
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

    public boolean registroEmpresa(empresaDTO empresaDTO, int idUsuario) {
        con = new Conexion();
        consulta = "INSERT INTO empresa( esEmpresa, nombreEmpresa, direccionEmpresa, telefonoEmpresa, celularEmpresa, correoEmpresa, idCiudadFK, idUsuarioFK) VALUES (?,?,?,?,?,?,?,?)";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, empresaDTO.getEsEmpresa());
            ps.setString(2, empresaDTO.getNombreEmpresa());
            ps.setString(3, empresaDTO.getDirEmpresa());
            ps.setString(4, empresaDTO.getTelEmpresa());
            ps.setString(5, empresaDTO.getCelEmpresa());
            ps.setString(6, empresaDTO.getCorreoEmpresa());
            ps.setInt(7, empresaDTO.getIdCiudad());
            ps.setInt(8, empresaDTO.getIdUsuario());
            ps.executeUpdate();
            System.out.println("..... registro de empresa realizado consulta " + ps.toString());
            //cerrarCon(ps, cn, rs);
            return true;

        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al registrar empresa " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            // cerrarCon(ps, cn, rs);
            return false;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }

    public boolean actualizarEmpresa(empresaDTO empresaDTO, int idUsuario) {
        con = new Conexion();
        consulta = "UPDATE empresa SET nombreEmpresa=?,direccionEmpresa=?,telefonoEmpresa=?,celularEmpresa=?,correoEmpresa=?,idCiudadFK=? WHERE idUsuarioFK=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, empresaDTO.getNombreEmpresa());
            ps.setString(2, empresaDTO.getDirEmpresa());
            ps.setString(3, empresaDTO.getTelEmpresa());
            ps.setString(4, empresaDTO.getCelEmpresa());
            ps.setString(5, empresaDTO.getCorreoEmpresa());
            ps.setInt(6, empresaDTO.getIdCiudad());
            ps.setInt(7, idUsuario);
            ps.executeUpdate();
            System.out.println("..... actualizacion de empresa realizado consulta " + ps.toString());
            //cerrarCon(ps, cn, rs);
            return true;

        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al actualizar empresa " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            // cerrarCon(ps, cn, rs);
            return false;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }

    public boolean actualizarDatosFaltantes(empresaDTO empresaDTO, int idUsuario) {
        con = new Conexion();
        consulta = "UPDATE empresa SET nombreEmpresa = ?, direccionEmpresa=?,telefonoEmpresa=?, "
                + "celularEmpresa=? WHERE idUsuarioFK=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, empresaDTO.getNombreEmpresa());
            ps.setString(2, empresaDTO.getDirEmpresa());
            ps.setString(3, empresaDTO.getTelEmpresa());
            ps.setString(4, empresaDTO.getCelEmpresa());
            ps.setInt(5, idUsuario);
            ps.executeUpdate();
            System.out.println("..... actualizacion de empresa realizado consulta " + ps.toString());
            //cerrarCon(ps, cn, rs);
            return true;

        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al actualizar empresa " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            // cerrarCon(ps, cn, rs);
            return false;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }

    public empresaDTO buscarEmpresa(int idUsuario) {
        con = new Conexion();
        consulta = "SELECT * FROM empresa WHERE idUsuarioFK = ? limit 1";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
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
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }

    public ArrayList<MarcaDTO> listarMarcas() {
        con = new Conexion();
        ArrayList<MarcaDTO> lista = new ArrayList<MarcaDTO>();
        consulta = "SELECT m.idMarca, m.nombreMarca FROM marcaProducto m";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);

            rs = ps.executeQuery();
            MarcaDTO marcaDTO;
            while (rs.next()) {
                marcaDTO = new MarcaDTO();
                marcaDTO.setIdMarca(rs.getString("idMarca"));
                marcaDTO.setNombreMarca(rs.getString("nombreMarca"));
                lista.add(marcaDTO);

            }
            //cerrarCon(ps, cn, rs);
            return lista;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            // cerrarCon(ps, cn, rs);
            return null;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }

    
    public ArrayList<empresaDTO> listarEmpresas() {
        con = new Conexion();
        listaEmpresa = new ArrayList<>();
        consulta = "SELECT * FROM empresa WHERE esEmpresa = 1 ORDER by rand() LIMIT 5";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);

            rs = ps.executeQuery();
            while (rs.next()) {
                empresaDTO = new empresaDTO();
                empresaDTO.setIdEmpresa(rs.getInt("idEmpresa"));
                empresaDTO.setEsEmpresa(rs.getInt("esEmpresa"));
                empresaDTO.setNombreEmpresa(rs.getString("nombreEmpresa"));
                empresaDTO.setDirEmpresa(rs.getString("direccionEmpresa"));
                empresaDTO.setTelEmpresa(rs.getString("telefonoEmpresa"));
                empresaDTO.setCelEmpresa(rs.getString("celularEmpresa"));
                empresaDTO.setCorreoEmpresa(rs.getString("correoEmpresa"));
                empresaDTO.setIdCiudad(rs.getInt("idCiudadFK"));
                empresaDTO.setIdUsuario(rs.getInt("idUsuarioFK"));
                listaEmpresa.add(empresaDTO);

            }

            //cerrarCon(ps, cn, rs);
            return listaEmpresa;

        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al buscar empresa " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            // cerrarCon(ps, cn, rs);
            return null;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }

    public empresaDTO buscarEmpresaXProducto(String idProducto) {
        con = new Conexion();
        consulta = "SELECT emp.*,ciud.nombreCiudad,persona.nombrepersona,persona.apellidoPersona FROM empresa emp INNER JOIN ciudad ciud ON ciud.idCiudad=emp.idCiudadFK INNER JOIN personanatural persona ON persona.idUsuarioFK =emp.idUsuarioFK  WHERE idEmpresa = ?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, idProducto);
            rs = ps.executeQuery();
            while (rs.next()) {
                empresaDTO = new empresaDTO();
                empresaDTO.setIdEmpresa(rs.getInt("idEmpresa"));
                empresaDTO.setEsCentro(rs.getString("esCentro"));
                empresaDTO.setNombreEmpresa(rs.getString("nombreEmpresa"));
                empresaDTO.setNombrePer(rs.getString("nombrePersona"));
                empresaDTO.setApellidoPer(rs.getString("apellidoPersona"));
                empresaDTO.setDirEmpresa(rs.getString("direccionEmpresa"));
                empresaDTO.setTelEmpresa(rs.getString("telefonoEmpresa"));
                empresaDTO.setCelEmpresa(rs.getString("celularEmpresa"));
                empresaDTO.setCorreoEmpresa(rs.getString("correoEmpresa"));
                empresaDTO.setIdCiudad(rs.getInt("idCiudadFK"));
                empresaDTO.setIdUsuario(rs.getInt("idUsuarioFK"));
                empresaDTO.setNombreCiudad(rs.getString("nombreCiudad"));

            }
            System.out.println(".....  empresa encontrada consulta " + ps.toString());
            System.out.println(".....  empresa encontrada  " + empresaDTO.toString());
            //cerrarCon(ps, cn, rs);
            return empresaDTO;

        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al buscar empresa " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            // cerrarCon(ps, cn, rs);
            return null;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }

    }

//    public void cerrarCon(PreparedStatement ps, Connection con, ResultSet rs) {
//        try {
//            if (con != null) {
//                con.close();
//            }
//            if (ps != null) {
//                ps.close();
//            }
//            if (rs != null) {
//                rs.close();
//            }
//
//            // System.out.println("conexxion cerrada");
//        } catch (SQLException e) {
//            System.out.println("error al cerrar conexion " + e);
//        }
//
//    }
}
