/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author equipo
 */
public class ProductorDAOQuerys {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ProductorDAOQuerys(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Producto> getProductsRandom() {
        try {

            String sql = 
              "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "WHERE PR.estadoProducto =  2  "
            + "AND PC.stockProducto > 0  "
            + "GROUP BY PR.idProducto "
            + "ORDER by rand() LIMIT 750";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Producto> list = new ArrayList<Producto>();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;
        } catch (Exception e) {
            System.out.println(ps.toString());
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Producto> getProductsByDateTimeAsc() {
        try {
            String sql =
              "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "WHERE PR.estadoProducto =  2  "
            + "AND PC.stockProducto > 0  "
            + "GROUP BY PR.idProducto "
            + "ORDER by PR.idProducto DESC LIMIT 1000";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Producto> list = new ArrayList<Producto>();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ps.toString());
            return null;
        }
    }

    public ArrayList<Producto> getProductsByWord(String Text) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql =
              "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "INNER JOIN marcaProducto m ON PR.marcaProductoFK = m.idMarca "
            + "INNER JOIN categoriaproducto CP ON PR.idCategoriaFK = CP.idCategoria "
            + "WHERE PR.nombreProducto LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "OR m.nombreMarca LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "OR PR.descripcionProducto LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "GROUP BY PR.idProducto LIMIT 200";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + Text + "%");
            ps.setString(2, "%" + Text + "%");
            ps.setString(3, "%" + Text + "%");
            rs = ps.executeQuery();
            Producto producto;
            while (rs.next()) {
                 producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<Producto> getProductsByCategory(String Categoria) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            
              String sql =
              "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "WHERE PR.idCategoriaFK = ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "GROUP BY PR.idProducto LIMIT 200";
    
            ps = conn.prepareStatement(sql);
            ps.setString(1, Categoria);
            rs = ps.executeQuery();
            Producto producto;
            while (rs.next()) {
                 producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<Producto> getProductsByNameCategory(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql =
              "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "INNER JOIN marcaProducto m ON PR.marcaProductoFK = m.idMarca "
            + "INNER JOIN categoriaproducto CP ON PR.idCategoriaFK = CP.idCategoria "
            + "WHERE PR.idCategoriaFK = ? "
            + "AND PR.nombreProducto LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "OR PR.idCategoriaFK = ? "
            + "AND PR.descripcionProducto LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "OR PR.idCategoriaFK = ? "
            + "AND PR.marcaProductoFK LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "GROUP BY PR.idProducto LIMIT 200";

            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, "%" + pro.getNombreProducto() + "%");
            ps.setString(3, pro.getNombreCategoria());
            ps.setString(4, "%" + pro.getNombreProducto() + "%");
            ps.setString(5, pro.getNombreCategoria());
            ps.setString(6, "%" + pro.getNombreProducto() + "%");
            rs = ps.executeQuery();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<Producto> getProductsByMarca(String marca) {
        List<Producto> list = new ArrayList<Producto>();
        try {
              String sql =
              "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "WHERE PR.marcaProductoFK = ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "GROUP BY PR.idProducto LIMIT 200";
              
            ps = conn.prepareStatement(sql);
            ps.setString(1, marca);
            rs = ps.executeQuery();
            Producto producto;
            while (rs.next()) {
              producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<Producto> getProductsByNameMarca(String marca, String name) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql =
              "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "INNER JOIN marcaProducto m ON PR.marcaProductoFK = m.idMarca "
            + "INNER JOIN categoriaproducto CP ON PR.idCategoriaFK = CP.idCategoria "
            + "WHERE PR.marcaProductoFK = ? "
            + "AND PR.nombreProducto LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "OR PR.marcaProductoFK = ? "
            + "AND PR.descripcionProducto LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "OR PR.marcaProductoFK = ? "
            + "AND PR.marcaProductoFK LIKE ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "GROUP BY PR.idProducto LIMIT 200";
            ps = conn.prepareStatement(sql);
            ps.setString(1, marca);
            ps.setString(2, "%" + name + "%");
            ps.setString(3, marca);
            ps.setString(4, "%" + name + "%");
            ps.setString(5, marca);
            ps.setString(6, "%" + name + "%");
            rs = ps.executeQuery();
            Producto producto;
            while (rs.next()) {
               producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<Producto> getProductsByCategoryMarca(String marca, String categoria) {
        List<Producto> list = new ArrayList<Producto>();
        try {
           String sql =
              "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "WHERE PR.marcaProductoFK = ? "
            + "AND PR.idCategoriaFK = ? "
            + "AND estadoProducto = 2 "
            + "AND PC.stockProducto > 0 "
            + "GROUP BY PR.idProducto LIMIT 200";
            ps = conn.prepareStatement(sql);
            ps.setString(1, marca);
            ps.setString(2, categoria);
            rs = ps.executeQuery();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<Producto> getProductsByNameCategoryMarca(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
           String sql =   "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor, MC.nombreMarca FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "INNER JOIN marcaProducto MC ON PR.marcaProductoFK = MC.idMarca "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND PR.nombreProducto LIKE ? "
                    + "AND PR.marcaProductoFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PC.stockProducto > 0 "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.descripcionProducto LIKE ? "
                    + "AND PR.marcaProductoFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PC.stockProducto > 0 "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND MC.nombreMarca LIKE ? "
                    + "AND PR.marcaProductoFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PC.stockProducto > 0 "
                    + "GROUP BY PR.idProducto LIMIT 200";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, "%" + pro.getNombreProducto() + "%");
            ps.setString(3, pro.getMarcaProducto());

            ps.setString(4, pro.getNombreCategoria());
            ps.setString(5, "%" + pro.getNombreProducto() + "%");
            ps.setString(6, pro.getMarcaProducto());

            ps.setString(7, pro.getNombreCategoria());
            ps.setString(8, "%" + pro.getNombreProducto() + "%");
            ps.setString(9, pro.getMarcaProducto());

            rs = ps.executeQuery();
            Producto producto;
            while (rs.next()) {
                 producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
