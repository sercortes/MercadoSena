/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

public class informeProductosDTO {

    String nombre;
    String valoruni;
    String catidadBodega;
    String cantidadVendida;
    String pedidoVendido;

    public informeProductosDTO() {
    }

    public informeProductosDTO(String nombre, String valoruni, String catidadBodega, String cantidadVendida, String pedidoVendido) {
        this.nombre = nombre;
        this.valoruni = valoruni;
        this.catidadBodega = catidadBodega;
        this.cantidadVendida = cantidadVendida;
        this.pedidoVendido = pedidoVendido;
    }

    public String getPedidoVendido() {
        return pedidoVendido;
    }

    public void setPedidoVendido(String pedidoVendido) {
        this.pedidoVendido = pedidoVendido;
    }

   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValoruni() {
        return valoruni;
    }

    public void setValoruni(String valoruni) {
        this.valoruni = valoruni;
    }

    public String getCatidadBodega() {
        return catidadBodega;
    }

    public void setCatidadBodega(String catidadBodega) {
        this.catidadBodega = catidadBodega;
    }

    public String getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(String cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    @Override
    public String toString() {
        return "informeProductosDTO{" + "nombre=" + nombre + ", valoruni=" + valoruni + ", catidadBodega=" + catidadBodega + ", cantidadVendida=" + cantidadVendida + '}';
    }
   
}
