/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.preguntasDTO;
import co.edu.sena.mercado.dto.respuestaDTO;
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
public class preguntasDAO {

    
    PreparedStatement ps;
    ResultSet rs;
    Conexion con;
    String consulta="";
    preguntasDTO preguntaDTO = new preguntasDTO();
    ArrayList<preguntasDTO> listaPregunta = new ArrayList<>();

    public boolean resgistroPregunta(preguntasDTO preguntasDTO) {
        Connection cn=null;
        con = new Conexion();
        consulta = "INSERT INTO preguntas( pregunta,estadoPregunta, idUsuarioPreguntaFK, idProductoFK) VALUES (?,?,?,?)";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setString(1, preguntasDTO.getPregunta());
            ps.setInt(2, preguntasDTO.getEstadoPregunta());
            ps.setInt(3, preguntasDTO.getIdUsuarioPregunta());
            ps.setInt(4, preguntasDTO.getIdProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxx error al registrar la pregunta " + e);
            System.out.println("xxxxxxxxxxxxxx consulta " + ps.toString());
            return false;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }

    public boolean marcarVistaPregunta(int idUsuario) {
         Connection cn=null;
        con = new Conexion();
        consulta = "UPDATE preguntas SET vista=1 WHERE idUsuarioPreguntaFK=? AND estadoPregunta=1;";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxx error al actualizar el visto de la pregunta " + e);
            System.out.println("xxxxxxxxxxxxxx consulta " + ps.toString());
            return false;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }

    public boolean responderPregunta(int estado, int idPregunta) {
         Connection cn=null;
        con = new Conexion();
        consulta = "update preguntas set estadoPregunta=? where idPregunta=?";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, estado);
            ps.setInt(2, idPregunta);

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxx error al cambiar estado de la pregunta " + e);
            System.out.println("xxxxxxxxxxxxxx consulta " + ps.toString());
            return false;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }

    public ArrayList<preguntasDTO> listarPregustas(int idEmpresa) {
         Connection cn=null;
        con = new Conexion();
        listaPregunta = new ArrayList<>();
        consulta = "SELECT pre.idPregunta, pre.pregunta, pre.estadoPregunta, pre.vista, pre.idUsuarioPreguntaFK, pre.idProductoFK,per.nombrePersona,per.apellidoPersona FROM preguntas pre INNER join usuario usu ON pre.idUsuarioPreguntaFK=usu.idUsuario INNER JOIN personanatural per ON usu.idUsuario=per.idUsuarioFK WHERE (SELECT pro.idEmpresaFK FROM producto pro WHERE idProductoFK=pro.idProducto )=? ORDER by idPregunta DESC";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, idEmpresa);
            rs = ps.executeQuery();
            while (rs.next()) {
                preguntaDTO = new preguntasDTO();
                preguntaDTO.setEstadoPregunta(rs.getInt("estadoPregunta"));
                preguntaDTO.setIdPregunta(rs.getInt("idPregunta"));
                preguntaDTO.setIdProducto(rs.getInt("idProductoFK"));
                preguntaDTO.setIdUsuarioPregunta(rs.getInt("idUsuarioPreguntaFK"));
                preguntaDTO.setPregunta(rs.getString("pregunta"));
                preguntaDTO.setNombreUsuarioPregunta(rs.getString("nombrePersona"));
                preguntaDTO.setApellidoUsuarioPregunta(rs.getString("apellidoPersona"));
                listaPregunta.add(preguntaDTO);
            }
            return listaPregunta;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxx error al consultar preguntas " + e);
            System.out.println("xxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }

    public int consultaNotiPreguntas(int idEmpresa) {
         Connection cn=null;
        int respuesta = 0;
        con = new Conexion();
        listaPregunta = new ArrayList<>();
        consulta = "SELECT COUNT(pregunta) as respuesta FROM preguntas WHERE (SELECT pro.idEmpresaFK FROM producto pro WHERE idProductoFK=pro.idProducto )=? AND estadoPregunta=0";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, idEmpresa);
            rs = ps.executeQuery();
            if (rs != null) {
            while (rs.next()) {
                
                    respuesta =Integer.parseInt(rs.getString("respuesta"));
                }
            }
            //System.out.println("............................ consultaNotiPreguntas "+ps.toString());
            return respuesta;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxx error al consultar numero preguntas " + e);
            System.out.println("xxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return  0;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }

    public ArrayList<preguntasDTO> listarPregustasRespuesta(int idUsusario) {
         Connection cn=null;
        con = new Conexion();
        listaPregunta = new ArrayList<>();
        consulta = "SELECT pre.idPregunta, pre.pregunta, pre.estadoPregunta, pre.idUsuarioPreguntaFK, pre.idProductoFK,res.respuesta,emp.nombreEmpresa FROM preguntas pre INNER JOIN respuesta res on pre.idPregunta=res.idPreguntaFK INNER JOIN empresa emp ON res.idEmpresaFK=emp.idEmpresa WHERE pre.idUsuarioPreguntaFK=? AND pre.estadoPregunta=1 ORDER by pre.idPregunta DESC";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, idUsusario);
            rs = ps.executeQuery();
            while (rs.next()) {
                preguntaDTO = new preguntasDTO();
                preguntaDTO.setEstadoPregunta(rs.getInt("estadoPregunta"));
                preguntaDTO.setIdPregunta(rs.getInt("idPregunta"));
                preguntaDTO.setIdProducto(rs.getInt("idProductoFK"));
                preguntaDTO.setIdUsuarioPregunta(rs.getInt("idUsuarioPreguntaFK"));
                preguntaDTO.setPregunta(rs.getString("pregunta"));
                preguntaDTO.setRespuesta(rs.getString("respuesta"));
                preguntaDTO.setUsuarioResponde(rs.getString("nombreEmpresa"));
                listaPregunta.add(preguntaDTO);
            }
            return listaPregunta;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxx error al consultar preguntas con respuesta " + e);
            System.out.println("xxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }

    public int consultaNotiRespuestas(int idUsusario) {
         Connection cn=null;
        int respuestaNot = 0;
        con = new Conexion();
        listaPregunta = new ArrayList<>();
        consulta = "SELECT COUNT(pregunta) as respuesta FROM preguntas pre INNER JOIN respuesta res on pre.idPregunta=res.idPreguntaFK WHERE pre.idUsuarioPreguntaFK=? and vista=0";
        try {
            cn = con.getConnection();
            ps = cn.prepareStatement(consulta);
            ps.setInt(1, idUsusario);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {

                    respuestaNot =Integer.parseInt(rs.getString("respuesta"));
                }
            }
            return respuestaNot;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxxxxx error al consultar numero preguntas con respuesta " + e);
            System.out.println("xxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return 0;
        } finally {
            Conexion.close(cn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
    }
}
