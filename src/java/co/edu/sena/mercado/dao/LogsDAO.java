/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.LogDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LogsDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public LogsDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertReturn(LogDTO LogDTO) throws Exception {

        String sql = "INSERT INTO logs (usuario, idEvento, tipoFK) "
                + "VALUES (?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, LogDTO.getUsuario());
            ps.setString(2, LogDTO.getIdEvento());
            ps.setString(3, LogDTO.getTipoFK());
            ps.executeUpdate();
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            throw new Exception();
        }

    }

    public ArrayList<LogDTO> getLogs() {
        try {
            String sql = "SELECT * FROM logs l INNER JOIN tipoLog tl ON l.tipoFK=tl.idTipo "
                    + "ORDER BY idLog DESC LIMIT 10";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<LogDTO> list = new ArrayList<LogDTO>();
            LogDTO logDTO;
            while (rs.next()) {
                logDTO = new LogDTO();
                logDTO.setIdLog(rs.getString("idLog"));
                logDTO.setUsuario(rs.getString("usuario"));
                logDTO.setIdEvento(rs.getString("idEvento"));
                logDTO.setTipoFK(rs.getString("tipoFK"));
                logDTO.setNombreTipoFK(rs.getString("tl.nombre"));
                logDTO.setFecha(rs.getTimestamp("fecha"));

                list.add(logDTO);
            }
            return (ArrayList<LogDTO>) list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }
    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
