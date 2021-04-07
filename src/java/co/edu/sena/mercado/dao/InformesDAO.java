
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.InformeDTO;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class InformesDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public InformesDAO(Connection conn) {
        this.conn = conn;
    }
    
    public ArrayList<InformeDTO> getQueryByDays(InformeDTO inf) {
        try {
            String sql = "select count(*) 'valor', fechaVenta from ventas where idEstadoVentasFK = 2 AND tipoVentaFK = ? AND fechaVenta " +
                    "between ? AND ? group by DAY(fechaVenta)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, inf.getTipoV());
            ps.setString(2, inf.getFechaI());
            ps.setString(3, inf.getFechaF());
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            List<InformeDTO> list = new ArrayList<InformeDTO>();
            InformeDTO informeDTO;
            while (rs.next()) {
                informeDTO = new InformeDTO();
                informeDTO.setLabel(rs.getString("fechaVenta").substring(0, 10));
                informeDTO.setValue(rs.getString("valor"));
                list.add(informeDTO);
            }
            return (ArrayList<InformeDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    public ArrayList<InformeDTO> getQueryByMoth(InformeDTO inf) {
        try {
             String sql = "SET lc_time_names = 'es_ES'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            sql = "select count(*) 'valor', MONTHNAME(fechaVenta) 'mes' from ventas where idEstadoVentasFK = 2 AND tipoVentaFK = ? AND fechaVenta " +
                    "between ? AND ? group by MONTH(fechaVenta)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, inf.getTipoV());
            ps.setString(2, inf.getFechaI());
            ps.setString(3, inf.getFechaF());
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            List<InformeDTO> list = new ArrayList<InformeDTO>();
            InformeDTO informeDTO;
            while (rs.next()) {
                informeDTO = new InformeDTO();
                informeDTO.setLabel(rs.getString("mes"));
                informeDTO.setValue(rs.getString("valor"));
                list.add(informeDTO);
            }
            return (ArrayList<InformeDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
     
    
     public ArrayList<InformeDTO> getQueryByTotalMoth(InformeDTO inf) {
        try {
             String sql = "SET lc_time_names = 'es_ES'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            sql = "select count(*) 'valor', MONTHNAME(fechaVenta) 'mes' from ventas where idEstadoVentasFK = 2 AND fechaVenta " +
                    "between ? AND ? group by MONTH(fechaVenta)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, inf.getFechaI());
            ps.setString(2, inf.getFechaF());
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            List<InformeDTO> list = new ArrayList<InformeDTO>();
            InformeDTO informeDTO;
            while (rs.next()) {
                informeDTO = new InformeDTO();
                informeDTO.setLabel(rs.getString("mes"));
                informeDTO.setValue(rs.getString("valor"));
                list.add(informeDTO);
            }
            return (ArrayList<InformeDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
     
     public ArrayList<InformeDTO> getQueryByTotalDays(InformeDTO inf) {
        try {
            String sql = "select count(*) 'valor', fechaVenta from ventas where idEstadoVentasFK = 2 AND fechaVenta " +
            ">= ? AND fechaVenta <= ? group by DAY(fechaVenta)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, inf.getFechaI());
            ps.setString(2, inf.getFechaF());
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            List<InformeDTO> list = new ArrayList<InformeDTO>();
            InformeDTO informeDTO;
            while (rs.next()) {
                informeDTO = new InformeDTO();
                informeDTO.setLabel(rs.getString("fechaVenta").substring(0, 10));
                informeDTO.setValue(rs.getString("valor"));
                list.add(informeDTO);
            }
            return (ArrayList<InformeDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
        public void CloseAll(){
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    } 
    
}
