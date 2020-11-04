/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.ImagenesProducto;
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
 * @author serfin
 */
public class ImagenesProductosDAO {
    
     private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ImagenesProductosDAO(Connection conn) {
        this.conn = conn;
    }
    
     public boolean insertReturn(ImagenesProducto imagenes) throws Exception{

        String sql = "INSERT INTO imagenesproductos (urlProducto, idProductoImageFK) "
                + "VALUES (?, ?)";

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, imagenes.getUrl());
            ps.setString(2, imagenes.getIdProductoFK());

            
            System.out.println(ps.toString());
            ps.executeUpdate();
            
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }

    }
     
       public ArrayList<ImagenesProducto> getImagenesByProduc(String id) {
           List<ImagenesProducto> list = new ArrayList<ImagenesProducto>();
        try {
            String sql = "SELECT idImagenPro, urlProducto, idProductoImageFK FROM imagenesproductos IP "
                    + "inner join producto P ON IP.idProductoImageFK = P.idProducto WHERE IP.idProductoImageFK = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            ImagenesProducto imagenesProducto;
            while (rs.next()) {
                imagenesProducto = new ImagenesProducto();
                imagenesProducto.setIdImagen(rs.getString("idImagenPro"));
                imagenesProducto.setUrl(rs.getString("urlProducto"));
                imagenesProducto.setIdProductoFK(rs.getString("idProductoImageFK"));
                
                list.add(imagenesProducto);
            }
            return (ArrayList<ImagenesProducto>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
       public ArrayList<ImagenesProducto> consultarTodas() {
           List<ImagenesProducto> list = new ArrayList<ImagenesProducto>();
        try {
            String sql = "SELECT * FROM imagenesproductos";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ImagenesProducto imagenesProducto;
            while (rs.next()) {
                imagenesProducto = new ImagenesProducto();
                imagenesProducto.setIdImagen(rs.getString("idImagenPro"));
                imagenesProducto.setUrl(rs.getString("urlProducto"));
                imagenesProducto.setIdProductoFK(rs.getString("idProductoImageFK"));
                
                list.add(imagenesProducto);
            }
            return (ArrayList<ImagenesProducto>) list;
        } catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX error al consultar todas las imagenes "+e);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX consulta "+ps.toString());
            return null;
        }
    }
     
       public ArrayList<ImagenesProducto> getImagenesByEmpresa(String id) {
           List<ImagenesProducto> list = new ArrayList<ImagenesProducto>();
        try {
            String sql = "SELECT idImagenPro, urlProducto, idProductoImageFK FROM imagenesproductos IP "
                    + "inner join producto P ON IP.idProductoImageFK = P.idProducto WHERE P.idEmpresaFK = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            ImagenesProducto imagenesProducto;
            while (rs.next()) {
                imagenesProducto = new ImagenesProducto();
                imagenesProducto.setIdImagen(rs.getString("idImagenPro"));
                imagenesProducto.setUrl(rs.getString("urlProducto"));
                imagenesProducto.setIdProductoFK(rs.getString("idProductoImageFK"));
                
                list.add(imagenesProducto);
            }
            return (ArrayList<ImagenesProducto>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
       
       public boolean deleteByidImagen(String id) throws Exception{
        try {
            String sql = "DELETE FROM imagenesproductos WHERE idImagenPro = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception();
        }
    }
       
        public boolean delete(String id) {
        try {
            String sql = "DELETE FROM imagenesproductos WHERE idProductoImageFK = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
       

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
    }

    
}
