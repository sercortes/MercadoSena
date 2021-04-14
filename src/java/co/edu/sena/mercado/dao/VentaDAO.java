/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.ProducctoDTO;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
import co.edu.sena.mercado.util.Conexion;
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
public class VentaDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public VentaDAO(Connection conn) {
        this.conn = conn;
    }

    public int insertReturn(VentaDTO ventaDTO) throws Exception{

        int idComprador = 0;

        String sql = "INSERT INTO ventas (tipoVentaFK, descuento, valorVenta, idCompradorFK, idCiudadFK, formaPagoVenta, idEstadoVentasFK) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, ventaDTO.getTipoVenta());
            ps.setDouble(2, ventaDTO.getDescuento());
            ps.setDouble(3, ventaDTO.getValorVenta());
            ps.setString(4, ventaDTO.getIdCompradorFK());
            ps.setString(5, ventaDTO.getIdCiudadFK());
            ps.setString(6, ventaDTO.getFormaPago());
            ps.setString(7, ventaDTO.getIdEstadoVentaFK());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idComprador = rs.getInt(1);
            }
            return idComprador;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }

    }

    public Boolean actualizarVenta(VentaDTO ventaDTO) {

        String sql = "UPDATE ventas SET idEstadoVentasFK=? WHERE idVenta=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ventaDTO.getIdEstadoVentaFK());
            ps.setString(2, ventaDTO.getIdVenta());
            System.out.println("VENTA ACTUALIZADA");
            System.out.println(ps.toString());
            ps.executeUpdate();

            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al realizar la actualizacion de estado venta" + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta" + ps.toString());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al realizar la actualizacion de estado venta" + e);
            e.printStackTrace();
            return false;
        }

    }
    
        public Boolean actualizarReferencia(VentaDTO ventaDTO) {

        String sql = "UPDATE ventas SET referencia=? WHERE idVenta=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, ventaDTO.getReferencia());
            ps.setString(2, ventaDTO.getIdVenta());
            System.out.println("VENTA ACTUALIZADA");
            System.out.println(ps.toString());
            ps.executeUpdate();

            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al realizar la actualizacion de estado venta" + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta" + ps.toString());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al realizar la actualizacion de estado venta" + e);
            e.printStackTrace();
            return false;
        }

    }
     
       public ArrayList<VentaDTO> getVentasByVendedor(String estado, String tipo) {
        try {
          String sql = "SELECT V.referencia, V.idVenta, V.fechaVenta, V.valorVenta, V.descuento, V.formaPagoVenta, V.idEstadoVentasFK, " +
                    "V.idCompradorFK, M.nombre, E.nombreEstado, P.documentoPersona, P.nombrePersona, P.apellidoPersona, " +
                    "P.correoPersona, P.direccionPersona, P.telefonoPersona, P.celularPersona FROM ventas V " +
                    "INNER JOIN metodopago M ON V.formaPagoVenta = M.idMetodoPago " +
                    "INNER JOIN estadoventas E ON V.idEstadoVentasFK = E.idEstadoVentas " +
                    "INNER JOIN personanatural P ON V.idCompradorFK = P.idPersona "+
                    "WHERE V.idEstadoVentasFK = ? AND V.tipoVentaFK = ? ORDER BY V.idVenta DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setString(2, tipo);
            rs = ps.executeQuery();
            List<VentaDTO> list = new ArrayList<VentaDTO>();
            VentaDTO ventaDTO;
            while (rs.next()) {
                ventaDTO = new VentaDTO();
                ventaDTO.setIdVenta(rs.getString("V.idVenta"));
                ventaDTO.setFechaVenta(rs.getTimestamp("V.fechaVenta"));
                ventaDTO.setValorVenta(rs.getDouble("V.valorVenta"));
                ventaDTO.setDescuento(rs.getDouble("V.descuento"));
                ventaDTO.setFormaPago(rs.getString("M.nombre"));
                ventaDTO.setReferencia(rs.getString("V.referencia"));
                ventaDTO.setIdEstadoVentaFK(rs.getString("E.nombreEstado"));
                personaNaturalDTO perDTO = new personaNaturalDTO();
                perDTO.setNumeroDocPer(rs.getString("P.documentoPersona"));
                perDTO.setNombrePer(rs.getString("P.nombrePersona"));
                perDTO.setApellidoPer(rs.getString("P.apellidoPersona"));
                perDTO.setCorreoPer(rs.getString("P.correoPersona"));
                perDTO.setDireccionPer(rs.getString("P.direccionPersona"));
                perDTO.setTelPer(rs.getString("P.telefonoPersona"));
                perDTO.setNumCelularPer(rs.getString("P.celularPersona"));
                ventaDTO.setPerDTO(perDTO);
                list.add(ventaDTO);
            }
            return (ArrayList<VentaDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
       
         public ArrayList<VentaDTO> getVentasByVendedorFailed(String tipo) {
        try {
          String sql = "SELECT V.referencia, V.idVenta, V.fechaVenta, V.valorVenta, V.descuento, V.formaPagoVenta, V.idEstadoVentasFK, " +
                    "V.idCompradorFK, M.nombre, E.nombreEstado, P.documentoPersona, P.nombrePersona, P.apellidoPersona, " +
                    "P.correoPersona, P.direccionPersona, P.telefonoPersona, P.celularPersona FROM ventas V " +
                    "INNER JOIN metodopago M ON V.formaPagoVenta = M.idMetodoPago " +
                    "INNER JOIN estadoventas E ON V.idEstadoVentasFK = E.idEstadoVentas " +
                    "INNER JOIN personanatural P ON V.idCompradorFK = P.idPersona "+
                    "WHERE V.idEstadoVentasFK IN(3,4,5) AND V.tipoVentaFK = ? ORDER BY V.idVenta DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tipo);
            rs = ps.executeQuery();
            List<VentaDTO> list = new ArrayList<VentaDTO>();
            VentaDTO ventaDTO;
            while (rs.next()) {
                ventaDTO = new VentaDTO();
                ventaDTO.setIdVenta(rs.getString("V.idVenta"));
                ventaDTO.setFechaVenta(rs.getTimestamp("V.fechaVenta"));
                ventaDTO.setValorVenta(rs.getDouble("V.valorVenta"));
                ventaDTO.setDescuento(rs.getDouble("V.descuento"));
                ventaDTO.setReferencia(rs.getString("V.referencia"));
                ventaDTO.setFormaPago(rs.getString("M.nombre"));
                ventaDTO.setIdEstadoVentaFK(rs.getString("E.nombreEstado"));
                personaNaturalDTO perDTO = new personaNaturalDTO();
                perDTO.setNumeroDocPer(rs.getString("P.documentoPersona"));
                perDTO.setNombrePer(rs.getString("P.nombrePersona"));
                perDTO.setApellidoPer(rs.getString("P.apellidoPersona"));
                perDTO.setCorreoPer(rs.getString("P.correoPersona"));
                perDTO.setDireccionPer(rs.getString("P.direccionPersona"));
                perDTO.setTelPer(rs.getString("P.telefonoPersona"));
                perDTO.setNumCelularPer(rs.getString("P.celularPersona"));
                ventaDTO.setPerDTO(perDTO);
                list.add(ventaDTO);
            }
            return (ArrayList<VentaDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
    
       public ArrayList<VentaDTO> getAllVentasByOnlyCustomerFailed(int idPersona) {
        try {
          String sql = "SELECT V.referencia, V.idVenta, V.fechaVenta, V.valorVenta, V.descuento, V.formaPagoVenta, V.idEstadoVentasFK, " +
                    "V.idCompradorFK, M.nombre, E.nombreEstado, P.documentoPersona, P.nombrePersona, P.apellidoPersona, " +
                    "P.correoPersona, P.direccionPersona, P.telefonoPersona, P.celularPersona FROM ventas V " +
                    "INNER JOIN metodopago M ON V.formaPagoVenta = M.idMetodoPago " +
                    "INNER JOIN estadoventas E ON V.idEstadoVentasFK = E.idEstadoVentas " +
                    "INNER JOIN personanatural P ON V.idCompradorFK = P.idPersona "+
                    "WHERE V.idEstadoVentasFK IN(3,4,5) AND P.idUsuarioFK = ? ORDER BY V.idVenta DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idPersona);
            rs = ps.executeQuery();
            List<VentaDTO> list = new ArrayList<VentaDTO>();
            VentaDTO ventaDTO;
            while (rs.next()) {
                ventaDTO = new VentaDTO();
                ventaDTO.setIdVenta(rs.getString("V.idVenta"));
                ventaDTO.setFechaVenta(rs.getTimestamp("V.fechaVenta"));
                ventaDTO.setValorVenta(rs.getDouble("V.valorVenta"));
                ventaDTO.setDescuento(rs.getDouble("V.descuento"));
                ventaDTO.setFormaPago(rs.getString("M.nombre"));
                ventaDTO.setReferencia(rs.getString("V.referencia"));
                ventaDTO.setIdEstadoVentaFK(rs.getString("E.nombreEstado"));
                personaNaturalDTO perDTO = new personaNaturalDTO();
                perDTO.setNumeroDocPer(rs.getString("P.documentoPersona"));
                perDTO.setNombrePer(rs.getString("P.nombrePersona"));
                perDTO.setApellidoPer(rs.getString("P.apellidoPersona"));
                perDTO.setCorreoPer(rs.getString("P.correoPersona"));
                perDTO.setDireccionPer(rs.getString("P.direccionPersona"));
                perDTO.setTelPer(rs.getString("P.telefonoPersona"));
                perDTO.setNumCelularPer(rs.getString("P.celularPersona"));
                ventaDTO.setPerDTO(perDTO);
                list.add(ventaDTO);
            }
            return (ArrayList<VentaDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
       
       
       
        public ArrayList<VentaDTO> getAllVentasByOnlyCustomer(String estado, int idPersona) {
        try {
          String sql = "SELECT V.referencia, V.idVenta, V.fechaVenta, V.valorVenta, V.descuento, V.formaPagoVenta, V.idEstadoVentasFK, " +
                    "V.idCompradorFK, M.nombre, E.nombreEstado, P.documentoPersona, P.nombrePersona, P.apellidoPersona, " +
                    "P.correoPersona, P.direccionPersona, P.telefonoPersona, P.celularPersona FROM ventas V " +
                    "INNER JOIN metodopago M ON V.formaPagoVenta = M.idMetodoPago " +
                    "INNER JOIN estadoventas E ON V.idEstadoVentasFK = E.idEstadoVentas " +
                    "INNER JOIN personanatural P ON V.idCompradorFK = P.idPersona "+
                    "WHERE V.idEstadoVentasFK = ? AND P.idUsuarioFK = ? ORDER BY V.idVenta DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setInt(2, idPersona);
            rs = ps.executeQuery();
            List<VentaDTO> list = new ArrayList<VentaDTO>();
            VentaDTO ventaDTO;
            while (rs.next()) {
                ventaDTO = new VentaDTO();
                ventaDTO.setIdVenta(rs.getString("V.idVenta"));
                ventaDTO.setFechaVenta(rs.getTimestamp("V.fechaVenta"));
                ventaDTO.setValorVenta(rs.getDouble("V.valorVenta"));
                ventaDTO.setDescuento(rs.getDouble("V.descuento"));
                ventaDTO.setFormaPago(rs.getString("M.nombre"));
                ventaDTO.setReferencia(rs.getString("V.referencia"));
                ventaDTO.setIdEstadoVentaFK(rs.getString("E.nombreEstado"));
                personaNaturalDTO perDTO = new personaNaturalDTO();
                perDTO.setNumeroDocPer(rs.getString("P.documentoPersona"));
                perDTO.setNombrePer(rs.getString("P.nombrePersona"));
                perDTO.setApellidoPer(rs.getString("P.apellidoPersona"));
                perDTO.setCorreoPer(rs.getString("P.correoPersona"));
                perDTO.setDireccionPer(rs.getString("P.direccionPersona"));
                perDTO.setTelPer(rs.getString("P.telefonoPersona"));
                perDTO.setNumCelularPer(rs.getString("P.celularPersona"));
                ventaDTO.setPerDTO(perDTO);
                list.add(ventaDTO);
            }
            return (ArrayList<VentaDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
       
   public ArrayList<Producto> getProductosByPrice(String idProducto) {
        try {
            
            String sql = "SELECT P.*, C.nombreColor, PC.idProductoColor, PP.cantidadProductoVenta, PP.valorProductoVenta, P.referencia, (SELECT urlProducto FROM imagenesproductos WHERE idProductoImageFK = P.idProducto LIMIT 1) 'imagen' " +
                "FROM productospedidos PP " +
                "INNER JOIN ProductoColor PC ON PP.idProductoFK=PC.idProductoColor " +
                "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "+
                "INNER JOIN producto P ON PC.productoFK=P.idProducto WHERE PP.idVentaFK = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);
            rs = ps.executeQuery();
            List<Producto> list = new ArrayList<Producto>();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setImagenUnitaria(Producto.SERVER_UPLOAD+rs.getString("imagen"));
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("PP.valorProductoVenta"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setIdProductoColor(rs.getString("PC.idProductoColor"));
                producto.setCantidad(rs.getInt("PP.cantidadProductoVenta"));
                producto.setReferencia(rs.getString("referencia"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
   
   
   public ArrayList<VentaDTO> getBuysNotUpdate() {
        try {
            
            String sql = "SELECT V.referencia, V.idVenta " +
                "FROM ventas V  WHERE V.idEstadoVentasFK = 1 ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<VentaDTO> list = new ArrayList<VentaDTO>();
            VentaDTO ventaDTO;
            while (rs.next()) {
                ventaDTO = new VentaDTO();
                ventaDTO.setIdVenta(rs.getString("V.idVenta"));
                ventaDTO.setReferencia(rs.getString("V.referencia"));
                list.add(ventaDTO);
            }
            return (ArrayList<VentaDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
   
    public ArrayList<VentaDTO> getProductsNotUpdate(int id) {
        try {
            
            String sql = "SELECT V.referencia, V.idVenta " +
                "FROM ventas V  WHERE V.idEstadoVentasFK = 1 AND "
              + "V.idCompradorFK = ? AND V.referencia != 'NULL'";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            List<VentaDTO> list = new ArrayList<VentaDTO>();
            VentaDTO ventaDTO;
            while (rs.next()) {
                ventaDTO = new VentaDTO();
                ventaDTO.setIdVenta(rs.getString("V.idVenta"));
                ventaDTO.setReferencia(rs.getString("V.referencia"));
                list.add(ventaDTO);
            }
            return (ArrayList<VentaDTO>) list;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }
   
   
    public boolean actualizarCantidad(Producto productoDTO) throws SQLException{
        try {

            String sql1 = "SELECT stockProducto FROM ProductoColor WHERE idProductoColor = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql1);
            ps.setString(1, productoDTO.getIdProductoColor());
            rs = ps.executeQuery();

            int actual = 0;

            while (rs.next()) {
                actual = rs.getInt("stockProducto");
            }
            System.out.println("ACTUAL");
            System.out.println(actual);
            System.out.println("STOCK");
            System.out.println(productoDTO.getCantidad());

            productoDTO.setStockProducto(actual - productoDTO.getCantidad());

            String sql = "UPDATE ProductoColor SET stockProducto = ? WHERE idProductoColor=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, productoDTO.getStockProducto());
            ps.setString(2, productoDTO.getIdProductoColor());
            System.out.println("..........................." + ps.toString());
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX error al realizar actualizarCantidad PrpductoDAO " + ex);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXX consulta " + ps.toString());
            return false;
        }
    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
