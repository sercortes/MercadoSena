/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.ColorDTO;
import co.edu.sena.mercado.dto.ProductoColor;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author equipo
 */
public class ProductoColorDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ProductoColorDAO(Connection conn) {
        this.conn = conn;
    }

    public int insertReturn(ProductoColor productocolor) throws Exception{

        int idComprador = 0;

        String sql = "INSERT INTO ProductoColor (productoFK, colorFK, stockProducto) "
                + "VALUES (?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, productocolor.getIdProductoFK());
            ps.setString(2, productocolor.getIdColorFK());
            ps.setInt(3, productocolor.getCantidad());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idComprador = rs.getInt(1);
            }
            System.out.println("............................."+ps.toString());
            return idComprador;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            e.printStackTrace();
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            throw new Exception();
        }

    }
    
    public ArrayList<ColorDTO> getColors() {
        try {
            String sql = "SELECT idColor, nombreColor FROM colorProducto";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<ColorDTO> list = new ArrayList<ColorDTO>();
            ColorDTO colorDTO;
            while (rs.next()) {
                colorDTO = new ColorDTO();
                colorDTO.setIdColor(rs.getString("idColor"));
                colorDTO.setNombreColor(rs.getString("nombreColor"));
                
                list.add(colorDTO);
            }
            return (ArrayList<ColorDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
     public ArrayList<ColorDTO> getColorsByProduct(String idProducto) {
        try {
            String sql = "SELECT idColor, nombreColor, PC.colorFK, PC.productoFK FROM colorProducto C INNER JOIN ProductoColor PC ON C.idColor=PC.colorFK "
                    + "WHERE PC.productoFK = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            List<ColorDTO> list = new ArrayList<ColorDTO>();
            ColorDTO colorDTO;
            while (rs.next()) {
                colorDTO = new ColorDTO();
                colorDTO.setIdColor(rs.getString("idColor"));
                colorDTO.setNombreColor(rs.getString("nombreColor"));
                
                list.add(colorDTO);
            }
            return (ArrayList<ColorDTO>) list;
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
