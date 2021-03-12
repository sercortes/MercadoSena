/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.Categorys;
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
public class ProductoDAOQuerysBa {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ProductoDAOQuerysBa(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Producto> getProductsByDateTimeAscBasic() {
        try {
            
            String sql = "SELECT COUNT(*) 'cantidadColores', "
            + "(SELECT urlProducto FROM imagenesproductos WHERE idProductoImageFK = PR.idProducto LIMIT 1) 'imagen', "
            + "PR.idProducto, PR.nombreProducto, PR.valorProductoVendedor, PR.descripcionProducto, "
            + "C.nombreColor, PC.stockProducto, PC.idProductoColor FROM producto PR "
            + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
            + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
            + "WHERE PR.estadoProducto =  2  "
            + "AND PC.stockProducto > 0  "
            + "GROUP BY PR.idProducto "
            + "ORDER by rand() LIMIT 100";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Producto> list = new ArrayList<Producto>();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setImagenUnitaria(rs.getString("imagen"));
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProductoVendedor"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
