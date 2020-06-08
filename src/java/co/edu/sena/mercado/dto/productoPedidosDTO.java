/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

/**
 *
 * @author serfin
 */
public class productoPedidosDTO {
    
    private String idProductoPedidos;
    private String idProductoFK;
    private String idVentaFK;
    private int cantidad;

    public productoPedidosDTO() {
    }

    public String getIdProductoPedidos() {
        return idProductoPedidos;
    }

    public void setIdProductoPedidos(String idProductoPedidos) {
        this.idProductoPedidos = idProductoPedidos;
    }

    public String getIdProductoFK() {
        return idProductoFK;
    }

    public void setIdProductoFK(String idProductoFK) {
        this.idProductoFK = idProductoFK;
    }

    public String getIdVentaFK() {
        return idVentaFK;
    }

    public void setIdVentaFK(String idVentaFK) {
        this.idVentaFK = idVentaFK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "productoPedidosDTO{" + "idProductoPedidos=" + idProductoPedidos + ", idProductoFK=" + idProductoFK + ", idVentaFK=" + idVentaFK + ", cantidad=" + cantidad + '}';
    }
    
    
    
    
}
