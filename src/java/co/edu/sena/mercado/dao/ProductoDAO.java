/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.Categorys;
import co.edu.sena.mercado.dto.ImagenesProducto;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.productoImagenesDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author serfin
 */
public class ProductoDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ProductoDAO(Connection conn) {
        this.conn = conn;
    }

    public int insertReturn(Producto producto) throws SQLException, Exception{
        int idActividad = 0;
        String sql = "INSERT INTO producto (valorProductoVendedor, referencia, nombreProducto, valorProducto, marcaProductoFK, "
                + "descripcionProducto, diasEnvioProducto, medidasProducto, empaqueProducto, "
                + "embalajeProducto, ventajasProducto, "
                + "idEmpresaFK, idCategoriaFK, garantia) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, producto.getPrecioVendedor());
            ps.setString(2, producto.getReferencia());
            ps.setString(3, producto.getNombreProducto());
            ps.setDouble(4, producto.getValorProducto());
            ps.setString(5, producto.getMarcaProducto());
            ps.setString(6, producto.getDescripcionProducto());
            ps.setString(7, producto.getDiasEnvios());
            ps.setString(8, producto.getMedidaProducto());
            ps.setString(9, producto.getEmpaqueProducto());
            ps.setString(10, producto.getEmbalajeProducto());
            ps.setString(11, producto.getVentajaProducto());
            ps.setString(12, producto.getIdEmpresaFK());
            ps.setString(13, producto.getIdCategoriaFK());
            ps.setString(14, producto.getGarantia());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idActividad = rs.getInt(1);
            }
            return idActividad;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            e.printStackTrace();
            throw new SQLException();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            throw new Exception();
        }

    }

    public boolean delete(String id) {
        try {
            String sql = "DELETE FROM producto WHERE idProducto = ?";
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

    public int buscaStoctok(Producto productoDTO) {
        int stock = 0;
        try {
            String sqlls = "SELECT stockProducto FROM producto WHERE idProducto = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sqlls);
            ps.setString(1, productoDTO.getIdProducto());
            rs = ps.executeQuery();

            while (rs.next()) {
                stock = rs.getInt("stockProducto");
            }

        } catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX error al realizar actualizarCantidad PrpductoDAO " + e);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX consulta " + ps.toString());
        }
        return stock;
    }

    public boolean actualizarCantidad(Producto productoDTO) {
        try {

            String sql1 = "SELECT stockProducto FROM producto WHERE idProducto = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setString(1, productoDTO.getIdProducto());
            rs = ps.executeQuery();

            int actual = 0;

            while (rs.next()) {
                actual = rs.getInt("stockProducto");
            }

            productoDTO.setStockProducto(actual - productoDTO.getStockProducto());

            String sql = "UPDATE producto SET stockProducto = ? WHERE idProducto=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productoDTO.getStockProducto());
            ps.setString(2, productoDTO.getIdProducto());
            ps.executeUpdate();
            System.out.println("..........................." + ps.toString());
            return true;
        } catch (Exception ex) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX error al realizar actualizarCantidad PrpductoDAO " + ex);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX consulta " + ps.toString());
            return false;
        }
    }

    public boolean updateProduct(Producto producto) throws Exception {
        try {

            String sql = "UPDATE producto set nombreProducto = ?, valorProducto = ?, "
                    + "marcaProductoFK = ?, descripcionProducto = ?, "
                    + "diasEnvioProducto = ?, medidasProducto = ?, empaqueProducto = ?,"
                    + "embalajeProducto = ?, ventajasProducto = ?, referencia = ?, "
                    + "valorProductoVendedor = ?, garantia = ?, idCategoriaFK = ? "
                    + "WHERE idProducto = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, producto.getNombreProducto());
            ps.setDouble(2, producto.getValorProducto());
            ps.setString(3, producto.getMarcaProducto());
            ps.setString(4, producto.getDescripcionProducto());
            ps.setString(5, producto.getDiasEnvios());
            ps.setString(6, producto.getMedidaProducto());
            ps.setString(7, producto.getEmpaqueProducto());
            ps.setString(8, producto.getEmbalajeProducto());
            ps.setString(9, producto.getVentajaProducto());
            ps.setString(10, producto.getReferencia());
            ps.setDouble(11, producto.getPrecioVendedor());
            ps.setString(12, producto.getGarantia());
            ps.setString(13, producto.getIdCategoriaFK());
            
            ps.setString(14, producto.getIdProducto());

            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            System.out.println("Error edit " + ex.getMessage());
            throw new Exception();
        }
    }

    public ArrayList<Producto> getProductsByDateTimeAscForCheck() {
        try {
            String sql = "SELECT PR.*, CP.nombreCategoria FROM producto PR "
                    + "INNER JOIN categoriaproducto CP ON PR.idCategoriaFK=CP.idCategoria "
                    + "INNER JOIN empresa EM ON PR.idEmpresaFK=EM.idEmpresa "
                    + "WHERE PR.estadoProducto = 1 AND PR.stockProducto > 0 "
                    + "ORDER BY PR.agregado DESC LIMIT 100";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<Producto> list = new ArrayList<Producto>();
            Producto producto;
            Categorys categorys;
            while (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("stockProducto"));
                producto.setMarcaProducto(rs.getString("marcaProductoFK"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setDiasEnvios(rs.getString("diasEnvioProducto"));
                producto.setMedidaProducto(rs.getString("medidasProducto"));
                producto.setEmpaqueProducto(rs.getString("empaqueProducto"));
                producto.setEmbalajeProducto(rs.getString("embalajeProducto"));
                producto.setVentajaProducto(rs.getString("ventajasProducto"));
                producto.setIdEmpresaFK(rs.getString("idEmpresaFK"));

                categorys = new Categorys();
                categorys.setNombreCategoria(rs.getString("CP.nombreCategoria"));
                producto.setCategorys(categorys);

                list.add(producto);
            }
            return (ArrayList<Producto>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public ArrayList<Producto> getProductsBySeller(String id) {
        try {
          String sql = "SELECT COUNT(*) 'cantidadColores', PR.idProducto, PR.nombreProducto, PR.valorProducto, PR.descripcionProducto, "
                    + "C.nombreColor, PC.stockProducto, PC.idProductoColor,  PR.referencia FROM producto PR "
                    + "INNER JOIN ProductoColor PC ON PR.idProducto=PC.productoFK "
                    + "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "
                    + "INNER JOIN empresa EM ON PR.idEmpresaFK=EM.idEmpresa "
                    + "WHERE PR.estadoProducto =  2  "
                    + "AND PC.stockProducto > 0  "
                    + "AND  EM.idEmpresa = ? GROUP BY PR.idProducto "
                    + "ORDER BY PR.idProducto DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            List<Producto> list = new ArrayList<Producto>();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("PC.stockProducto"));
                producto.setReferencia(rs.getString("PR.referencia"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidadColores(rs.getString("cantidadColores"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
    
     public Producto buscarProductoComplete(int idProducto) {
        try {
            String sql = "SELECT MP.nombreMarca, P.diasEnvioProducto, P.medidasProducto, P.empaqueProducto, "
                    + "P.embalajeProducto, P.ventajasProducto, P.garantia, P.referencia, C.nombreCategoria, "
                    + "P.idCategoriaFK, P.marcaProductoFK, P.valorProductoVendedor "
                    + "FROM producto P "
                    + "INNER JOIN marcaProducto MP ON P.marcaProductoFK=MP.idMarca "
                    + "INNER JOIN categoriaproducto C ON P.idCategoriaFK=C.idCategoria "
                    + "WHERE P.idProducto = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            Producto producto = new Producto();
            while (rs.next()) {
                producto = new Producto();
                producto.setIdMarca(rs.getString("P.marcaProductoFK"));
                producto.setMarcaProducto(rs.getString("MP.nombreMarca"));
                producto.setDiasEnvios(rs.getString("P.diasEnvioProducto"));
                producto.setMedidaProducto(rs.getString("P.medidasProducto"));
                producto.setEmpaqueProducto(rs.getString("P.empaqueProducto"));
                producto.setEmbalajeProducto(rs.getString("P.embalajeProducto"));
                producto.setVentajaProducto(rs.getString("P.ventajasProducto"));
                producto.setGarantia(rs.getString("P.garantia"));
                producto.setReferencia(rs.getString("P.referencia"));
                producto.setPrecioVendedor(rs.getDouble("P.valorProductoVendedor"));
                Categorys categorys = new Categorys();
                categorys.setIdcategoria(rs.getString("P.idCategoriaFK"));
                categorys.setNombreCategoria(rs.getString("nombreCategoria"));
                producto.setCategorys(categorys);
            }
            return producto;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la consulta del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }
    }

    public Producto buscarProducto(int idProducto) {
        try {
            String sql = "SELECT MP.nombreMarca, P.diasEnvioProducto, P.medidasProducto, P.empaqueProducto, "
                    + "P.embalajeProducto, P.ventajasProducto, P.garantia, P.referencia "
                    + "FROM producto P "
                    + "INNER JOIN marcaProducto MP ON P.marcaProductoFK=MP.idMarca "
                    + "WHERE P.idProducto = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            Producto producto = new Producto();
            while (rs.next()) {
                producto = new Producto();
                producto.setMarcaProducto(rs.getString("MP.nombreMarca"));
                producto.setDiasEnvios(rs.getString("P.diasEnvioProducto"));
                producto.setMedidaProducto(rs.getString("P.medidasProducto"));
                producto.setEmpaqueProducto(rs.getString("P.empaqueProducto"));
                producto.setEmbalajeProducto(rs.getString("P.embalajeProducto"));
                producto.setVentajaProducto(rs.getString("P.ventajasProducto"));
                producto.setGarantia(rs.getString("P.garantia"));
                producto.setReferencia(rs.getString("P.referencia"));
            }
            return producto;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la consulta del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }
    }

    public boolean disabledProduct(String id) throws Exception {
        try {

            String sql = "UPDATE producto set estadoProducto = ? "
                    + "WHERE idProducto = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "4");
            ps.setString(2, id);
            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            System.out.println(ps.toString());
            System.out.println("Error edit " + ex.getMessage());
            throw new Exception();
        }
    }

    public boolean disabledProductWithAdmin(String id, String idEstado, String comentario) throws Exception {
        try {

            String sql = "UPDATE producto set estadoProducto = ?, nota = ? "
                    + "WHERE idProducto = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, idEstado);
            ps.setString(2, comentario);
            ps.setString(3, id);

            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            System.out.println("Error edit " + ex.getMessage());
            throw new Exception();
        }
    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
