/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.Categorys;
import co.edu.sena.mercado.dto.CompradorDTO;
import co.edu.sena.mercado.dto.MetodoPago;
import co.edu.sena.mercado.dto.Producto;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.informePedidosDTO;
import co.edu.sena.mercado.dto.pedidoDTO;
import co.edu.sena.mercado.dto.productoPedidosDTO;
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
public class CompradorDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public CompradorDAO(Connection conn) {
        this.conn = conn;
    }

    
     public String getIdEmpresa(String idProducto) {
        try {
            String id = "";
            String sql = "SELECT p.idProducto, e.idEmpresa FROM producto p INNER JOIN empresa e ON p.idEmpresaFK=e.idEmpresa "
                    + "WHERE p.idProducto = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idProducto);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getString("e.idEmpresa");
            }
            return id;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar checkProducts " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return null;
        }
    }
    
    public int insertReturn(CompradorDTO compradorDTO) throws Exception {

        int idComprador = 0;

        String sql = "INSERT INTO comprador (idPersonaFK, idEmpresaFK) "
                + "VALUES (?, ?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, compradorDTO.getIdPersona());
            ps.setString(2, compradorDTO.getIdEmpresa());
            System.out.println(ps.toString());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idComprador = rs.getInt(1);
            }
            System.out.println("............................."+ ps.toString());
            return idComprador;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }

    }

    public ArrayList<MetodoPago> getMetodos(){
    
         try {
            String sql = "SELECT idMetodoPago, nombre FROM metodopago LIMIT 10";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            List<MetodoPago> list = new ArrayList<MetodoPago>();
            MetodoPago metodoPago;
            while (rs.next()) {
                metodoPago = new MetodoPago();
                metodoPago.setIdMetodo(rs.getString("idMetodoPago"));
                metodoPago.setNombre(rs.getString("nombre"));
                
                list.add(metodoPago);
            }
            return (ArrayList<MetodoPago>) list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
    
    public boolean checkProducts(String idUser, String idProducto) {
        try {
            boolean estado = false;
            String sql = "SELECT V.idEstadoVentasFK, C.idPersonaFK, PP.idProductoFK FROM ventas V "
                    + "INNER JOIN comprador C ON V.idCompradorFK=C.idComprador "
                    + "INNER JOIN productospedidos PP ON V.idVenta = PP.idVentaFK "
                    + "WHERE idEstadoVentasFK = 1 AND C.idPersonaFK = ? "
                    + "AND PP.idProductoFK = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, idUser);
            ps.setString(2, idProducto);
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            while (rs.next()) {
                estado = true;
            }
            return estado;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx error al realizar checkProducts " + e);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx consulta " + ps.toString());
            return false;
        }
    }

    //SELECT comp.*,ven.*,estVen.*,prodPed.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFKINNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK WHERE comp.idEmpresaFK=6
    //SELECT comp.*,ven.*,estVen.*,prodPed.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK WHERE comp.idPersonaFK=5
    public ArrayList<pedidoDTO> consultaPedido(int id, String tipoUsu) {
        //id = comprador o vendedor
        ArrayList<pedidoDTO> listaPedido = new ArrayList<>();
        pedidoDTO pedidoDTO;
        CompradorDTO compradorDTO;
        VentaDTO ventaDTO;
        productoPedidosDTO prodPedDTO;

        String tipoUsuario = "", consulta;
        if (tipoUsu.equalsIgnoreCase("vendedor")) {
            tipoUsuario = "comp.idEmpresaFK";
        } else {
            tipoUsuario = "comp.idPersonaFK";
        }
        consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK WHERE " + tipoUsuario + " =? ORDER BY ven.fechaVenta DESC";
//SELECT comp.*,ven.*,estVen.*,prodPed.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK WHERE comp.idEmpresaFK =5 ORDER BY ven.fechaVenta DESC

        try {
            ps = conn.prepareStatement(consulta);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                pedidoDTO = new pedidoDTO();
                compradorDTO = new CompradorDTO();
                ventaDTO = new VentaDTO();
                prodPedDTO = new productoPedidosDTO();

                compradorDTO.setIdComprador(rs.getString("idComprador"));
                compradorDTO.setIdEmpresa(rs.getString("idEmpresaFK"));
                compradorDTO.setIdPersona(rs.getString("idPersonaFK"));

                ventaDTO.setContactoVenta(rs.getString("contacto"));
                ventaDTO.setFechaVenta(rs.getTimestamp("fechaVenta"));
                ventaDTO.setIdCiudadFK(rs.getString("idCiudadFK"));
                ventaDTO.setIdCompradorFK(rs.getString("idCompradorFK"));
                ventaDTO.setIdEstadoVentaFK(rs.getString("idEstadoVentas"));
                ventaDTO.setIdVenta(rs.getString("idVenta"));
                ventaDTO.setValorVenta(rs.getDouble("valorVenta"));

                prodPedDTO.setCantidad(rs.getInt("cantidadProductoVenta"));
                prodPedDTO.setIdProductoFK(rs.getString("idProductoFK"));
                prodPedDTO.setIdProductoPedidos(rs.getString("idProductosVentas"));
                prodPedDTO.setIdVentaFK(rs.getString("idVentaFK"));

                pedidoDTO.setEstadoVenta(rs.getString("nombreEstado"));
                pedidoDTO.setCompradorDTO(compradorDTO);
                pedidoDTO.setVentaDTO(ventaDTO);
                pedidoDTO.setProdPedidoDTO(prodPedDTO);

                listaPedido.add(pedidoDTO);

            }
            return listaPedido;
        } catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX error al realizar la consulta del pedido " + e);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX consulta " + ps.toString());
            return null;
        }

    }

    public ArrayList<informePedidosDTO> informePedidos(String tipo, String fechaIni, String fechaFin, int idEmpresa) {

        ArrayList<informePedidosDTO> listaInformePedido = new ArrayList<>();
        informePedidosDTO informePedidoDTO;

        String consulta = "";
//SELECT comp.*,ven.*,estVen.*,prodPed.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK WHERE comp.idEmpresaFK =5 ORDER BY ven.fechaVenta DESC

        try {

            switch (tipo) {
                case "0":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =?  ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);

                    break;

                case "1":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =? AND idEstadoVentas = 2  ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);

                    break;
                case "2":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =? AND idEstadoVentas = 3  ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);

                    break;
                case "3":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =? and ven.fechaVenta like ? ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);
                    fechaIni = fechaIni + "%";
                    ps.setString(2, fechaIni);

                    break;

                case "4":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =? and ven.fechaVenta like ? AND idEstadoVentas = 2 ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);
                    fechaIni = fechaIni + "%";
                    ps.setString(2, fechaIni);

                    break;
                case "5":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =? and ven.fechaVenta like ? AND idEstadoVentas = 3 ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);
                    fechaIni = fechaIni + "%";
                    ps.setString(2, fechaIni);

                    break;
                case "6":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =? and ven.fechaVenta BETWEEN ? and ? ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);
                    fechaFin = fechaFin + " 23:59:59";
                    ps.setString(2, fechaIni);
                    ps.setString(3, fechaFin);
                    break;
                case "7":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =? and ven.fechaVenta BETWEEN ? and ? AND idEstadoVentas = 2 ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);
                    fechaFin = fechaFin + " 23:59:59";
                    ps.setString(2, fechaIni);
                    ps.setString(3, fechaFin);
                    break;

                case "8":
                    consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.*,pro.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK INNER JOIN producto pro ON prodPed.idProductoFK=pro.idProducto WHERE comp.idEmpresaFK =? and ven.fechaVenta BETWEEN ? and ? AND idEstadoVentas = 3 ORDER BY ven.fechaVenta DESC";
                    ps = conn.prepareStatement(consulta);
                    fechaFin = fechaFin + " 23:59:59";
                    ps.setString(2, fechaIni);
                    ps.setString(3, fechaFin);
                    break;
            }

            ps.setInt(1, idEmpresa);
            rs = ps.executeQuery();
            while (rs.next()) {
                informePedidoDTO = new informePedidosDTO();

                informePedidoDTO.setCantidadProductos(rs.getString("cantidadProductoVenta"));
                informePedidoDTO.setFechaVenta(rs.getDate("fechaVenta"));
                informePedidoDTO.setIdEstado(rs.getInt("idEstadoVentas"));
                informePedidoDTO.setNombreEstado(rs.getString("nombreEstado"));
                informePedidoDTO.setNombreProducto(rs.getString("nombreProducto"));
                informePedidoDTO.setValor(rs.getString("valorVenta"));
                informePedidoDTO.setValorProducto(rs.getString("valorProducto"));

                listaInformePedido.add(informePedidoDTO);

            }
            return listaInformePedido;
        } catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX error al realizar la consulta informe pedidos pedido " + e);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX consulta " + ps.toString());
            return null;
        }

    }

    public int consultaNotiPedidos(int idEmpresa) {
        int nroPedidos = 0;
        String sql = "SELECT COUNT(ven.idVenta) as nroVentas FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idCompradorFK INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas WHERE comp.idEmpresaFK=? AND ven.idEstadoVentasFK=1";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idEmpresa);
            rs = ps.executeQuery();

            while (rs.next()) {
                nroPedidos = rs.getInt("nroVentas");
            }
            return nroPedidos;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al realizar la consulta de nroVentas venta" + e);
            System.out.println("xxxxxxxxxxxxxxxxxxx consulta" + ps.toString());
            return 0;
        } catch (Exception e) {
            System.out.println("xxxxxxxxxxxxxxxxxxx error al realizar la actualizacion de estado venta" + e);

            return 0;
        }

    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
