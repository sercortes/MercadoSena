/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dao;

import co.edu.sena.mercado.dto.informeProductosDTO;
import co.edu.sena.mercado.dto.productoPedidosDTO;
import co.edu.sena.mercado.util.Conexion;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author serfin
 */
public class ProductosPedidosDAO {

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public ProductosPedidosDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertReturn(productoPedidosDTO pedidosDTO) {

        String sql = "INSERT INTO productospedidos (idProductoFK, idVentaFK, cantidadProductoVenta) "
                + "VALUES (?, ?, ?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, pedidosDTO.getIdProductoFK());
            ps.setString(2, pedidosDTO.getIdVentaFK());
            ps.setInt(3, pedidosDTO.getCantidad());
            ps.executeUpdate();
            System.out.println("............................." + ps.toString());
            return true;
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println(e);
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
//SELECT pP.*, count(*) as total,pro.* FROM productospedidos pP INNER JOIN producto pro on pro.idEmpresaFK=6 GROUP BY pP.idProductoFKSELECT pP.*, count(*) as total,pro.* FROM productospedidos pP INNER JOIN producto pro on pro.idEmpresaFK=6 WHERE pP.idProductoFk=pro.idProducto GROUP BY pP.idProductoFK

    public ArrayList<productoPedidosDTO> produstosMasSolicitados() {
        String consulta = "SELECT *,COUNT(*) as cantidadVentas FROM productospedidos GROUP BY idProductoFK HAVING COUNT(*)>2";
        ArrayList<productoPedidosDTO> listaProducto = new ArrayList<>();
        productoPedidosDTO productosPedDTO;
        try {
            ps = conn.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                productosPedDTO = new productoPedidosDTO();
                productosPedDTO.setCantidad(rs.getInt("cantidadProductoVenta"));
                productosPedDTO.setIdProductoFK(rs.getString("idProductoFK"));
                productosPedDTO.setIdProductoPedidos(rs.getString("idProductosVentas"));
                productosPedDTO.setIdVentaFK(rs.getString("idVentaFK"));
                listaProducto.add(productosPedDTO);
            }
            return listaProducto;
        } catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX error al consultar productos mas vendidos " + e);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX consulta " + ps.toString());
            return null;
        }
    }

    public ArrayList<informeProductosDTO> informeProductosVendidos(String tipo, String fechaIni, String fechaFin, int idEmpresa) {
        String consulta = "";

        // consulta = "SELECT pP.*, count(*) as total,pro.*,SUM(pP.cantidadProductoVenta) as cantidad FROM productospedidos pP INNER JOIN producto pro on pro.idEmpresaFK=? INNER JOIN ventas ven ON ven.idVenta=pP.idVentaFK WHERE pP.idProductoFk=pro.idProducto and ven.idEstadoVentasFK=2 GROUP BY pP.idProductoFK";
        ArrayList<informeProductosDTO> listaProductos = new ArrayList<>();
        informeProductosDTO productosPedDTO;
        try {
            switch (tipo) {
                case "1":
                    consulta = "SELECT pP.*, count(*) as total,pro.*,SUM(pP.cantidadProductoVenta) as cantidad FROM productospedidos pP INNER JOIN producto pro on pro.idEmpresaFK=? INNER JOIN ventas ven ON ven.idVenta=pP.idVentaFK WHERE pP.idProductoFk=pro.idProducto and ven.idEstadoVentasFK=2 GROUP BY pP.idProductoFK";
                    ps = conn.prepareStatement(consulta);

                    break;
                case "2":
                    consulta = "SELECT pP.*, count(*) as total,pro.*,SUM(pP.cantidadProductoVenta) as cantidad,ven.* FROM productospedidos pP INNER JOIN producto pro on pro.idEmpresaFK=? INNER JOIN ventas ven ON ven.idVenta=pP.idVentaFK WHERE pP.idProductoFk=pro.idProducto and ven.idEstadoVentasFK=2 and ven.fechaVenta LIKE ? GROUP BY pP.idProductoFK";
                    ps = conn.prepareStatement(consulta);
                    fechaIni=fechaIni+"%";
                    ps.setString(2, fechaIni);
                    
                    break;
                case "3":
                    consulta = "SELECT pP.*, count(*) as total,pro.*,SUM(pP.cantidadProductoVenta) as cantidad,ven.* FROM productospedidos pP INNER JOIN producto pro on pro.idEmpresaFK=? INNER JOIN ventas ven ON ven.idVenta=pP.idVentaFK WHERE pP.idProductoFk=pro.idProducto and ven.idEstadoVentasFK=2 and ven.fechaVenta BETWEEN ? and ? GROUP BY pP.idProductoFK";
                    ps = conn.prepareStatement(consulta);
                    fechaFin=fechaFin+" 23:59:59";
                    ps.setString(2, fechaIni);
                    ps.setString(3, fechaFin);
                    break;
            }
            ps.setInt(1, idEmpresa);
            rs = ps.executeQuery();
            while (rs.next()) {
         
                productosPedDTO = new informeProductosDTO();
                productosPedDTO.setNombre(rs.getString("nombreProducto"));
                productosPedDTO.setValoruni(rs.getString("valorProducto"));
                productosPedDTO.setCatidadBodega(rs.getString("stockProducto"));
                productosPedDTO.setCantidadVendida(rs.getString("cantidad"));
                productosPedDTO.setPedidoVendido(rs.getString("total"));
               
                listaProductos.add(productosPedDTO);
            }
            return listaProductos;
        } catch (Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX error al consultar informe productos para vendedor " + e);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX consulta " + ps.toString());
            return null;
        }
    }

    public void CloseAll() {
        Conexion.close(conn);
        Conexion.close(ps);
        Conexion.close(rs);
    }

}
