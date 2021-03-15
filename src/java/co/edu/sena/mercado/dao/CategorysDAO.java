/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.Categorys;
import co.edu.sena.mercado.dto.Centro;
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
public class CategorysDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public CategorysDAO(Connection conn) {
        this.conn = conn;
    }
    
       public ArrayList<Categorys> getCategorys() {
        try {
            String sql = "SELECT idCategoria, nombreCategoria FROM categoriaproducto";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Categorys> list = new ArrayList<Categorys>();
            Categorys categorys;
            while (rs.next()) {
                categorys = new Categorys();
                categorys.setIdcategoria(rs.getString("idCategoria"));
                categorys.setNombreCategoria(rs.getString("nombreCategoria"));
                
                list.add(categorys);
            }
            return (ArrayList<Categorys>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
       
     public int insertReturnCategoria(String nombreCategoria) throws Exception{

        int idComprador = 0;

        String sql = "INSERT INTO categoriaproducto (nombreCategoria) "
                + "VALUES (?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, nombreCategoria);
            System.out.println(ps.toString());
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
     
      public int insertReturnMarca(String nombreMarca) throws Exception{

        int idComprador = 0;

        String sql = "INSERT INTO marcaProducto (nombreMarca) "
                + "VALUES (?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, nombreMarca);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idComprador = rs.getInt(1);
            }
            System.out.println("............................."+ps.toString());
            return idComprador;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }

    }
       
        public ArrayList<Centro> getCentros() {
        try {
            String sql = "SELECT idCentro, nombreCentro FROM centro WHERE idCentro <> 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Centro> list = new ArrayList<Centro>();
            Centro centro;
            while (rs.next()) {
                centro = new Centro();
                centro.setIdCentro(rs.getString("idCentro"));
                centro.setNombreCentro(rs.getString("nombreCentro"));
                
                list.add(centro);
            }
            return (ArrayList<Centro>) list;
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
