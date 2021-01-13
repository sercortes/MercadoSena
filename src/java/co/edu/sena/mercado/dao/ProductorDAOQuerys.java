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
public class ProductorDAOQuerys {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ProductorDAOQuerys(Connection conn) {
        this.conn = conn;
    }
    
    public ArrayList<Producto> getProductsRandom() {
        try {
            String sql = "SELECT PR.*, CP.nombreCategoria FROM producto PR "
                    + "INNER JOIN categoriaproducto CP ON PR.idCategoriaFK=CP.idCategoria "
                    + "INNER JOIN empresa EM ON PR.idEmpresaFK=EM.idEmpresa "
                    + "WHERE PR.estadoProducto = 2 AND PR.stockProducto > 0 "
                    + "ORDER by rand() LIMIT 12";
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
    
     public ArrayList<Producto> getProductsByDateTimeAsc() {
        try {
            String sql = "SELECT PR.*, CP.nombreCategoria FROM producto PR "
                    + "INNER JOIN categoriaproducto CP ON PR.idCategoriaFK=CP.idCategoria "
                    + "INNER JOIN empresa EM ON PR.idEmpresaFK=EM.idEmpresa "
                    + "WHERE PR.estadoProducto = 2 AND PR.stockProducto > 0 "
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
     
      public ArrayList<Producto> buscadorLike(String Text) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.nombreProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    
                    + "OR PR.marcaProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    
                    + "OR CP.nombreCategoria LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    
                    + "OR PR.descripcionProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + Text + "%");
            ps.setString(2, "%" + Text + "%");
            ps.setString(3, "%" + Text + "%");
            ps.setString(4, "%" + Text + "%");
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

      
    public ArrayList<Producto> searchCategory(String Categoria) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, Categoria);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<Producto> searchCity(String Ciudad) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, EM.idCiudadFK, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE EM.idCiudadFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, Ciudad);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<Producto> searchByEmpresa(String Empresa) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idEmpresaFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, Empresa );
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
    
      public ArrayList<Producto> getProductsByNameCategory(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND PR.nombreProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.descripcionProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.marcaProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, "%"+pro.getNombreProducto()+"%");
            ps.setString(3, pro.getNombreCategoria());
            ps.setString(4, "%"+pro.getNombreProducto()+"%");
            ps.setString(5, pro.getNombreCategoria());
            ps.setString(6, "%"+pro.getNombreProducto()+"%");
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
    
       public ArrayList<Producto> getProductsByCategoryCity(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, pro.getCiudad());
            ps.setString(3, pro.getNombreCategoria());
            ps.setString(4, pro.getCiudad());
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
      
       public ArrayList<Producto> getProductsByNameCategoryCity(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND PR.nombreProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.descripcionProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.marcaProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, "%"+pro.getNombreProducto()+"%");
            ps.setString(3, pro.getCiudad());
            ps.setString(4, pro.getNombreCategoria());
            ps.setString(5, "%"+pro.getNombreProducto()+"%");
            ps.setString(6, pro.getCiudad());
            ps.setString(7, pro.getNombreCategoria());
            ps.setString(8, "%"+pro.getNombreProducto()+"%");
            ps.setString(9, pro.getCiudad());
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
    
       
       public ArrayList<Producto> getProductsByCitySeller(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "AND PR.idEmpresaFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getCiudad());
            ps.setString(2, pro.getIdEmpresaFK());
            
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
    
    
          public ArrayList<Producto> getProductsByNameCitySeller(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.nombreProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "AND PR.idEmpresaFK = ? "
                    
                    + "OR PR.descripcionProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "AND PR.idEmpresaFK = ? "
                    
                    + "OR PR.marcaProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "AND PR.idEmpresaFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%"+pro.getNombreProducto()+"%");
            ps.setString(2, pro.getCiudad());
            ps.setString(3, pro.getIdEmpresaFK());
            
            ps.setString(4, "%"+pro.getNombreProducto()+"%");
            ps.setString(5, pro.getCiudad());
            ps.setString(6, pro.getIdEmpresaFK());
            
            ps.setString(7, "%"+pro.getNombreProducto()+"%");
            ps.setString(8, pro.getCiudad());
            ps.setString(9, pro.getIdEmpresaFK());
            
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
    
       
       public ArrayList<Producto> getProductsByNameCategoryCitySeller(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND PR.nombreProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "AND PR.idEmpresaFK = ? "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.descripcionProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "AND PR.idEmpresaFK = ? "
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.marcaProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "AND PR.idEmpresaFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, "%"+pro.getNombreProducto()+"%");
            ps.setString(3, pro.getCiudad());
            ps.setString(4, pro.getIdEmpresaFK());
            
            ps.setString(5, pro.getNombreCategoria());
            ps.setString(6, "%"+pro.getNombreProducto()+"%");
            ps.setString(7, pro.getCiudad());
            ps.setString(8, pro.getIdEmpresaFK());
            
            ps.setString(9, pro.getNombreCategoria());
            ps.setString(10, "%"+pro.getNombreProducto()+"%");
            ps.setString(11, pro.getCiudad());
            ps.setString(12, pro.getIdEmpresaFK());
            
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
    
         public ArrayList<Producto> getProductsByNameCity(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE EM.idCiudadFK = ? "
                    + "AND PR.nombreProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    
                    + "OR EM.idCiudadFK = ? "
                    + "AND PR.descripcionProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    
                    + "OR EM.idCiudadFK = ? "
                    + "AND PR.marcaProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0";
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getCiudad());
            ps.setString(2, "%"+pro.getNombreProducto()+"%");
            ps.setString(3, pro.getCiudad());
            ps.setString(4, "%"+pro.getNombreProducto()+"%");
            ps.setString(5, pro.getCiudad());
            ps.setString(6, "%"+pro.getNombreProducto()+"%");
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
         
         public ArrayList<Producto> getProductsByCategorySeller(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND PR.idEmpresaFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, pro.getIdEmpresaFK());
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
         
         public ArrayList<Producto> getProductsByNameCategorySeller(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND PR.nombreProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND PR.idEmpresaFK = ? "
                    
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.descripcionProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND PR.idEmpresaFK = ? "
                    
                    + "OR PR.idCategoriaFK = ? "
                    + "AND PR.marcaProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND PR.idEmpresaFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, "%"+pro.getNombreProducto()+"%");
            ps.setString(3, pro.getIdEmpresaFK());
            
            ps.setString(4, pro.getNombreCategoria());
            ps.setString(5, "%"+pro.getNombreProducto()+"%");
            ps.setString(6, pro.getIdEmpresaFK());
            
            ps.setString(7, pro.getNombreCategoria());
            ps.setString(8, "%"+pro.getNombreProducto()+"%");
            ps.setString(9, pro.getIdEmpresaFK());
            
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
         
         public ArrayList<Producto> getProductsByNameSeller(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.nombreProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND PR.idEmpresaFK = ? "
                    + "OR PR.descripcionProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND PR.idEmpresaFK = ? "
                    + "OR PR.marcaProducto LIKE ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND PR.idEmpresaFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%"+pro.getNombreProducto()+"%");
            ps.setString(2, pro.getIdEmpresaFK());
            
            ps.setString(3, "%"+pro.getNombreProducto()+"%");
            ps.setString(4, pro.getIdEmpresaFK());
            
            ps.setString(5, "%"+pro.getNombreProducto()+"%");
            ps.setString(6, pro.getIdEmpresaFK());
            
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }

    }
         
            public ArrayList<Producto> getProductsByCategoryCitySeller(Producto pro) {
        List<Producto> list = new ArrayList<Producto>();
        try {
            String sql = "SELECT PR.*, EM.idEmpresa, CP.nombreCategoria "
                    + "FROM producto PR INNER JOIN empresa EM "
                    + "ON PR.idEmpresaFK=EM.idEmpresa INNER JOIN categoriaproducto CP "
                    + "ON PR.idCategoriaFK=CP.idCategoria "
                    + "WHERE PR.idCategoriaFK = ? "
                    + "AND estadoProducto = 2 "
                    + "AND PR.stockProducto > 0 "
                    + "AND EM.idCiudadFK = ? "
                    + "AND PR.idEmpresaFK = ? ";
                    
            ps = conn.prepareStatement(sql);
            ps.setString(1, pro.getNombreCategoria());
            ps.setString(2, pro.getCiudad());
            ps.setString(3, pro.getIdEmpresaFK());
            
            System.out.println(ps.toString());
            rs = ps.executeQuery();
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
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar la busqueda del producto " + e);
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
