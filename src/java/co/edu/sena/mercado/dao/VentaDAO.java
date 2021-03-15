/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.personaNaturalDTO;
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
    
     public ArrayList<VentaDTO> getAllVentasByCustomer(String estado) {
        try {
          String sql = "SELECT V.idVenta, V.fechaVenta, V.valorVenta, V.descuento, V.formaPagoVenta, V.idEstadoVentasFK, " +
                    "V.idCompradorFK, M.nombre, E.nombreEstado, P.documentoPersona, P.nombrePersona, P.apellidoPersona, " +
                    "P.correoPersona, P.direccionPersona, P.telefonoPersona, P.celularPersona FROM ventas V " +
                    "INNER JOIN metodopago M ON V.formaPagoVenta = M.idMetodoPago " +
                    "INNER JOIN estadoventas E ON V.idEstadoVentasFK = E.idEstadoVentas " +
                    "INNER JOIN comprador C ON V.idCompradorFK = C.idComprador " +
                    "INNER JOIN personanatural P ON C.idPersonaFK = P.idPersona " +
                    "WHERE V.idEstadoVentasFK = ? AND V.tipoVentaFK = 2 ORDER BY V.idVenta DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, estado);
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
    
    
       public ArrayList<VentaDTO> getVentasByVendedor(String estado) {
        try {
          String sql = "SELECT V.idVenta, V.fechaVenta, V.valorVenta, V.descuento, V.formaPagoVenta, V.idEstadoVentasFK, " +
                    "V.idCompradorFK, M.nombre, E.nombreEstado, P.documentoPersona, P.nombrePersona, P.apellidoPersona, " +
                    "P.correoPersona, P.direccionPersona, P.telefonoPersona, P.celularPersona FROM ventas V " +
                    "INNER JOIN metodopago M ON V.formaPagoVenta = M.idMetodoPago " +
                    "INNER JOIN estadoventas E ON V.idEstadoVentasFK = E.idEstadoVentas " +
                    "INNER JOIN comprador C ON V.idCompradorFK = C.idComprador " +
                    "INNER JOIN personanatural P ON C.idPersonaFK = P.idPersona " +
                    "WHERE V.idEstadoVentasFK = ? AND V.tipoVentaFK = 1 ORDER BY V.idVenta DESC";
            ps = conn.prepareStatement(sql);
            ps.setString(1, estado);
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
    
   public ArrayList<Producto> getProductosByVenta(String idProducto) {
        try {
            
            String sql = "SELECT P.*, C.nombreColor, PP.cantidadProductoVenta, (SELECT urlProducto FROM imagenesproductos WHERE idProductoImageFK = P.idProducto LIMIT 1) 'imagen' " +
                "FROM productospedidos PP " +
                "INNER JOIN ProductoColor PC ON PP.idProductoFK=PC.idProductoColor " +
                "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "+
                "INNER JOIN producto P ON PC.productoFK=P.idProducto WHERE PP.idVentaFK = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);
            rs = ps.executeQuery();
            System.out.println(ps.toString());
            List<Producto> list = new ArrayList<Producto>();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setImagenUnitaria(rs.getString("imagen"));
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProductoVendedor"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidad(rs.getInt("PP.cantidadProductoVenta"));
                list.add(producto);
            }
            return (ArrayList<Producto>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    

public ArrayList<Producto> getProductosByCustomer(String idProducto) {
        try {
            
            String sql = "SELECT P.*, C.nombreColor, PP.cantidadProductoVenta, (SELECT urlProducto FROM imagenesproductos WHERE idProductoImageFK = P.idProducto LIMIT 1) 'imagen' " +
                "FROM productospedidos PP " +
                "INNER JOIN ProductoColor PC ON PP.idProductoFK=PC.idProductoColor " +
                "INNER JOIN colorProducto C ON PC.colorFK = C.idColor "+
                "INNER JOIN producto P ON PC.productoFK=P.idProducto WHERE PP.idVentaFK = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);
            rs = ps.executeQuery();
            System.out.println(ps.toString());
            List<Producto> list = new ArrayList<Producto>();
            Producto producto;
            while (rs.next()) {
                producto = new Producto();
                producto.setImagenUnitaria(rs.getString("imagen"));
                producto.setIdProducto(rs.getString("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setValorProducto(rs.getDouble("valorProducto"));
                producto.setColor(rs.getString("C.nombreColor"));
                producto.setCantidad(rs.getInt("PP.cantidadProductoVenta"));
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
