
package co.edu.sena.mercado.dto;

import java.sql.Date;




/**
 *
 * @author DELL
 */
public class informePedidosDTO {
    Date fechaVenta;
    String valor;
    int idEstado;
    String nombreEstado;
    String nombreProducto;
    String cantidadProductos;
    String valorProducto;

    public informePedidosDTO() {
    }

    public informePedidosDTO(Date fechaVenta, String valor, int idEstado, String nombreEstado, String nombreProducto, String cantidadProductos, String valorProducto) {
        this.fechaVenta = fechaVenta;
        this.valor = valor;
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
        this.nombreProducto = nombreProducto;
        this.cantidadProductos = cantidadProductos;
        this.valorProducto = valorProducto;
    }

    public String getValorProducto() {
        return valorProducto;
    }

    public void setValorProducto(String valorProducto) {
        this.valorProducto = valorProducto;
    }

  

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidadProductos() {
        return cantidadProductos;
    }

    public void setCantidadProductos(String cantidadProductos) {
        this.cantidadProductos = cantidadProductos;
    }

    @Override
    public String toString() {
        return "informePedidosDTO{" + "fechaVenta=" + fechaVenta + ", valor=" + valor + ", idEstado=" + idEstado + ", nombreEstado=" + nombreEstado + ", nombreProducto=" + nombreProducto + ", cantidadProductos=" + cantidadProductos + '}';
    }
    
    
}
