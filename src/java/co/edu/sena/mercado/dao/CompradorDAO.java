/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.CompradorDTO;
import co.edu.sena.mercado.dto.VentaDTO;
import co.edu.sena.mercado.dto.pedidoDTO;
import co.edu.sena.mercado.dto.productoPedidosDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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

    public int insertReturn(CompradorDTO compradorDTO) {

        int idComprador = 0;

        String sql = "INSERT INTO comprador (idPersonaFK, idEmpresaFK) "
                + "VALUES (?, ?)";
        try {
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, compradorDTO.getIdPersona());
            ps.setString(2, compradorDTO.getIdEmpresa());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idComprador = rs.getInt(1);
            }
            return idComprador;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }

    }
    //SELECT comp.*,ven.*,estVen.*,prodPed.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idVenta INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK WHERE comp.idEmpresaFK=6
    //SELECT comp.*,ven.*,estVen.*,prodPed.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idVenta INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK WHERE comp.idPersonaFK=5

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
        consulta = "SELECT comp.*,ven.*,estVen.*,prodPed.* FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idVenta INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas INNER JOIN productospedidos prodPed on ven.idVenta=prodPed.idVentaFK WHERE " + tipoUsuario + " =? ORDER BY ven.fechaVenta DESC";

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
                ventaDTO.setFechaVenta(rs.getDate("fechaVenta"));
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

    public int consultaNotiPedidos(int idEmpresa) {
        int nroPedidos = 0;
        String sql = "SELECT COUNT(ven.idVenta) as nroVentas FROM comprador comp INNER JOIN ventas ven ON comp.idComprador=ven.idVenta INNER JOIN estadoventas estVen on ven.idEstadoVentasFK=estVen.idEstadoVentas WHERE comp.idEmpresaFK=? AND ven.idEstadoVentasFK=1";
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
