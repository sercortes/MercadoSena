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

    public int insertReturn(Producto producto) {
        int idActividad = 0;
        String sql = "INSERT INTO producto (nombreProducto, valorProducto, stockProducto, marcaProducto, "
                + "descripcionProducto, diasEnvioProducto, medidasProducto, empaqueProducto, "
                + "embalajeProducto, ventajasProducto, "
                + "idEmpresaFK, idCategoriaFK) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, producto.getNombreProducto());
            ps.setDouble(2, producto.getValorProducto());
            ps.setInt(3, producto.getStockProducto());
            ps.setString(4, producto.getMarcaProducto());
            ps.setString(5, producto.getDescripcionProducto());
            ps.setString(6, producto.getDiasEnvios());
            ps.setString(7, producto.getMedidaProducto());
            ps.setString(8, producto.getEmpaqueProducto());
            ps.setString(9, producto.getEmbalajeProducto());
            ps.setString(10, producto.getVentajaProducto());
            ps.setString(11, producto.getIdEmpresaFK());
            ps.setString(12, producto.getIdCategoriaFK());

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idActividad = rs.getInt(1);
            }
            return idActividad;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
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
    public boolean actualizarCantidad(Producto productoDTO) {
        try {
            String sql = "UPDATE producto SET stockProducto=(SELECT stockProducto from producto WHERE idProducto=?)-? WHERE idProducto=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,productoDTO.getIdProducto());
            ps.setInt(2,productoDTO.getStockProducto());
            ps.setString(3,productoDTO.getIdProducto());
           ps.executeUpdate();
            System.out.println("..........................."+ps.toString());
            return true;
        } catch (Exception ex) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX error al realizar actualizarCantidad PrpductoDAO "+ex);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX consulta "+ps.toString());
            return false;
        }
    }

    public boolean updateProduct(Producto producto) {
        try {

            String sql = "UPDATE producto set nombreProducto = ?, valorProducto = ?, "
                    + "stockProducto = ?, marcaProducto = ?, descripcionProducto = ?, "
                    + "diasEnvioProducto = ?, medidasProducto = ?, empaqueProducto = ?,"
                    + "embalajeProducto = ?, ventajasProducto = ? "
                    + "WHERE idProducto = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, producto.getNombreProducto());
            ps.setDouble(2, producto.getValorProducto());
            ps.setInt(3, producto.getStockProducto());
            ps.setString(4, producto.getMarcaProducto());
            ps.setString(5, producto.getDescripcionProducto());
            ps.setString(6, producto.getDiasEnvios());
            ps.setString(7, producto.getMedidaProducto());
            ps.setString(8, producto.getEmpaqueProducto());
            ps.setString(9, producto.getEmbalajeProducto());
            ps.setString(10, producto.getVentajaProducto());

            ps.setString(11, producto.getIdProducto());

            int rows = ps.executeUpdate();
            boolean estado = rows > 0;
            return estado;
        } catch (Exception ex) {
            System.out.println("Error edit " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Producto> getProductsBySeller(String id) {
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria FROM producto PR "
                    + "INNER JOIN empresa EM ON PR.idEmpresaFK=EM.idEmpresa "
                    + "INNER JOIN categoriaproducto CP ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE EM.idEmpresa = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
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
                producto.setMarcaProducto(rs.getString("marcaProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setIdCategoriaFK(rs.getString("idCategoriaFK"));
                producto.setDiasEnvios(rs.getString("diasEnvioProducto"));
                producto.setMedidaProducto(rs.getString("medidasProducto"));
                producto.setEmpaqueProducto(rs.getString("empaqueProducto"));
                producto.setEmbalajeProducto(rs.getString("embalajeProducto"));
                producto.setVentajaProducto(rs.getString("ventajasProducto"));

//                if (!StringUtils.isNullOrEmpty(rs.getString("fechaVencimiento"))) {
//                     producto.setFechaVencimiento(rs.getString("fechaVencimiento"));
//                }
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

    public ArrayList<Producto> getProductsByDateTimeAsc() {
        try {
            String sql = "SELECT PR.*, CP.nombreCategoria FROM producto PR "
                    + "INNER JOIN categoriaproducto CP ON PR.idCategoriaFK=CP.idCategoria "
                    + "ORDER BY PR.agregado ASC";
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
                producto.setMarcaProducto(rs.getString("marcaProducto"));
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
    public ArrayList<Producto> todosProductosConVendedor() {
        try {
            String sql = "SELECT pro.*,emp.nombreEmpresa,emp.idCiudadFK,CP.nombreCategoria FROM producto pro INNER join empresa emp on pro.idEmpresaFK=emp.idEmpresa INNER JOIN categoriaproducto CP on pro.idCategoriaFK=CP.idCategoria ORDER by rand()";
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
                producto.setMarcaProducto(rs.getString("marcaProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setDiasEnvios(rs.getString("diasEnvioProducto"));
                producto.setMedidaProducto(rs.getString("medidasProducto"));
                producto.setEmpaqueProducto(rs.getString("empaqueProducto"));
                producto.setEmbalajeProducto(rs.getString("embalajeProducto"));
                producto.setVentajaProducto(rs.getString("ventajasProducto"));
                producto.setIdEmpresaFK(rs.getString("idEmpresaFK"));
                producto.setNombreEmpresa(rs.getString("nombreEmpresa"));
                producto.setIdCiudad(rs.getInt("idCiudadFK"));

                categorys = new Categorys();
                categorys.setNombreCategoria(rs.getString("nombreCategoria"));
                categorys.setIdcategoria(rs.getString("idCategoriaFK"));
                producto.setCategorys(categorys);

                list.add(producto);
            }
            return (ArrayList<Producto>) list;
        } catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX errror al consultar todos los productos con vendedor "+e);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX consulta "+ps.toString());
            return null;
        }
    }

    public Producto buscarProducto(int idProducto) {
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria FROM producto PR INNER JOIN empresa EM ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP ON PR.idCategoriaFK=CP.idCategoria WHERE PR.idProducto= ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            Producto producto= new Producto();
            Categorys categorys;
            while (rs.next()) {
                producto = new Producto();
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setStockProducto(rs.getInt("stockProducto"));
                producto.setMarcaProducto(rs.getString("marcaProducto"));
                producto.setDescripcionProducto(rs.getString("descripcionProducto"));
                producto.setIdCategoriaFK(rs.getString("idCategoriaFK"));
                producto.setDiasEnvios(rs.getString("diasEnvioProducto"));
                producto.setMedidaProducto(rs.getString("medidasProducto"));
                producto.setEmpaqueProducto(rs.getString("empaqueProducto"));
                producto.setEmbalajeProducto(rs.getString("embalajeProducto"));
                producto.setVentajaProducto(rs.getString("ventajasProducto"));
                producto.setNombreCategoria(rs.getString("CP.nombreCategoria"));

//                if (!StringUtils.isNullOrEmpty(rs.getString("fechaVencimiento"))) {
//                     producto.setFechaVencimiento(rs.getString("fechaVencimiento"));
//                }
                categorys = new Categorys();
                categorys.setNombreCategoria(rs.getString("CP.nombreCategoria"));
                producto.setCategorys(categorys);

               
            }
            return producto;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la consulta del producto "+e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta "+ps.toString());
            return null;
        }
    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
