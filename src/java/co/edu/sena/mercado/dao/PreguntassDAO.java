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
 * @author equipo
 */
public class PreguntassDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public PreguntassDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean resgistroPregunta(preguntasDTO preguntasDTO) throws SQLException {

        String consulta = "INSERT INTO preguntas( pregunta,estadoPregunta, idUsuarioPreguntaFK, idProductoFK) VALUES (?,?,?,?)";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, preguntasDTO.getPregunta());
            ps.setInt(2, preguntasDTO.getEstadoPregunta());
            ps.setInt(3, preguntasDTO.getIdUsuarioPregunta());
            ps.setInt(4, preguntasDTO.getIdProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("error resgistroPregunta " + e);
            System.out.println("consulta " + ps.toString());
            throw new SQLException();
        }
    }
    
    public ArrayList<preguntasDTO> getAllPreguntas()  {
        ArrayList<preguntasDTO> listaPregunta = new ArrayList<>();
        String consulta = "SELECT count(*), pn.nombrePersona, pn.apellidoPersona, p.idProductoFK, p.idUsuarioPreguntaFK, pr.nombreProducto, "
                + "pn.urlImgPersona, p.fecha, max(p.fecha)"
                + "FROM preguntas p "
                + "INNER JOIN usuario u ON p.idUsuarioPreguntaFK = u.idUsuario "
                + "INNER JOIN personanatural pn ON u.idUsuario=pn.idUsuarioFK "
                + "INNER JOIN producto pr ON p.idProductoFK=pr.idProducto "
                + "GROUP BY(p.idProductoFK) "
                + "ORDER by idPregunta DESC;";
        try {
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                preguntasDTO preDTO = new preguntasDTO();
                preDTO.setFechaPublicada(rs.getTimestamp("max(p.fecha)"));
                preDTO.setUrlPersona(rs.getString("pn.urlImgPersona"));
                preDTO.setNombreProducto(rs.getString("pr.nombreProducto"));
                preDTO.setNombreUsuario(rs.getString("pn.nombrePersona")+" "+rs.getString("pn.apellidoPersona"));
                preDTO.setIdProducto(rs.getInt("p.idProductoFK"));
                preDTO.setIdUsuarioPregunta(rs.getInt("p.idUsuarioPreguntaFK"));
                listaPregunta.add(preDTO);
            }
            return listaPregunta;
        } catch (SQLException e) {
            System.out.println("error getPreguntas " + e);
            System.out.println("consulta " + ps.toString());
            return null;
        }
    }

    public ArrayList<preguntasDTO> getPreguntaByUser(String id)  {
        ArrayList<preguntasDTO> listaPregunta = new ArrayList<>();
        String consulta = "SELECT count(*), pn.nombrePersona, pn.apellidoPersona, p.idProductoFK, p.idUsuarioPreguntaFK, pr.nombreProducto, "
                + "pn.urlImgPersona, p.fecha, max(p.fecha)"
                + "FROM preguntas p "
                + "INNER JOIN usuario u ON p.idUsuarioPreguntaFK = u.idUsuario "
                + "INNER JOIN personanatural pn ON u.idUsuario=pn.idUsuarioFK "
                + "INNER JOIN producto pr ON p.idProductoFK=pr.idProducto "
                + "WHERE p.idUsuarioPreguntaFK = ?"
                + "GROUP BY(p.idProductoFK) "
                + "ORDER by idPregunta DESC;";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                preguntasDTO preDTO = new preguntasDTO();
                preDTO.setFechaPublicada(rs.getTimestamp("max(p.fecha)"));
                preDTO.setUrlPersona(rs.getString("pn.urlImgPersona"));
                preDTO.setNombreProducto(rs.getString("pr.nombreProducto"));
                preDTO.setNombreUsuario(rs.getString("pn.nombrePersona")+" "+rs.getString("pn.apellidoPersona"));
                preDTO.setIdProducto(rs.getInt("p.idProductoFK"));
                preDTO.setIdUsuarioPregunta(rs.getInt("p.idUsuarioPreguntaFK"));
                listaPregunta.add(preDTO);
            }
            return listaPregunta;
        } catch (SQLException e) {
            System.out.println("error getPreguntas " + e);
            System.out.println("consulta " + ps.toString());
            return null;
        }
    }
    
     public ArrayList<preguntasDTO> getPreguntasIndivi(String idUsu, String idPro)  {
        ArrayList<preguntasDTO> listaPregunta = new ArrayList<>();
        String consulta = "SELECT p.idPregunta, p.fecha, p.pregunta FROM preguntas p " 
              + "WHERE idUsuarioPreguntaFK = ? AND idProductoFK = ? "
              + "ORDER BY p.idPregunta ASC";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, idUsu);
            ps.setString(2, idPro);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                preguntasDTO preDTO = new preguntasDTO();
                preDTO.setIdPregunta(rs.getInt("p.idPregunta"));
                preDTO.setFechaPublicada(rs.getTimestamp("p.fecha"));
                preDTO.setPregunta(rs.getString("p.pregunta"));
                listaPregunta.add(preDTO);
            }
            return listaPregunta;
        } catch (SQLException e) {
            System.out.println("error getPreguntas " + e);
            System.out.println("consulta " + ps.toString());
            return null;
        }
    }

      public ArrayList<respuestaDTO> getRespuestasByQuestion(String idPregunta)  {
        ArrayList<respuestaDTO> listaPregunta = new ArrayList<>();
        String consulta = "SELECT r.idRespuesta, r.respuesta, r.fecha, r.idPreguntaFK FROM respuesta r " 
              + "WHERE r.idPreguntaFK = ? ORDER BY r.idRespuesta ASC";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setString(1, idPregunta);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                respuestaDTO resDTO = new respuestaDTO();
                resDTO.setIdRespuesta(rs.getInt("r.idRespuesta"));
                resDTO.setFecha(rs.getTimestamp("r.fecha"));
                resDTO.setRespuesta(rs.getString("r.respuesta"));
                listaPregunta.add(resDTO);
            }
            return listaPregunta;
        } catch (SQLException e) {
            System.out.println("error getPreguntas " + e);
            System.out.println("consulta " + ps.toString());
            return null;
        }
    }

      public boolean marcarVistaPregunta(int idUsuario) {

        String consulta = "UPDATE preguntas SET vista=1 WHERE idUsuarioPreguntaFK=? AND estadoPregunta=1;";
        try {
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("xxxxxxxxxxxxxx error al actualizar el visto de la pregunta " + e);
            System.out.println("xxxxxxxxxxxxxx consulta " + ps.toString());
            return false;
        } 
    }
      
     
    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
