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
            return idComprador;
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
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }
    }
    
     public ArrayList<ColorDTO> getColorsByProduct(String idProducto) {
        try {
            String sql = "SELECT nombreColor, PC.idProductoColor, PC.stockProducto FROM colorProducto C "
                    + "INNER JOIN ProductoColor PC ON C.idColor=PC.colorFK "
                    + "WHERE PC.productoFK = ? AND PC.stockProducto > 0 ORDER BY PC.idProductoColor ASC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);
            rs = ps.executeQuery();
            List<ColorDTO> list = new ArrayList<ColorDTO>();
            ColorDTO colorDTO;
            while (rs.next()) {
                colorDTO = new ColorDTO();
                colorDTO.setIdColor(rs.getString("idProductoColor"));
                colorDTO.setNombreColor(rs.getString("nombreColor"));
                colorDTO.setCantidad(rs.getInt("PC.stockProducto"));
                list.add(colorDTO);
            }
            return (ArrayList<ColorDTO>) list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }
    }
     
     public int getStockProduct(String idProducto) {
        try {
            int cantidad = 0;
            String sql = "SELECT stockProducto FROM ProductoColor WHERE idProductoColor = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                cantidad = rs.getInt("stockProducto");
            }
            return cantidad;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }
    
     
       public boolean updateColorsStock(ProductoColor productoColor) throws Exception {
        try {

            String sql = "UPDATE ProductoColor set stockProducto = ? "
                    + "WHERE idProductoColor = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, productoColor.getCantidad());
            ps.setString(2, productoColor.getIdProductoColor());

            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ps.toString());
            throw new Exception();
        }
    }
         
      public void CloseAll(){
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    } 
    
}
