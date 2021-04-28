/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author serfin
 */
public class VentaDTO {
    
   private String idVenta;
   private Timestamp fechaVenta;
   private Double valorVenta;
   private String contactoVenta;
   private String formaPago;
   
   private String idEstadoVentaFK;
   private String idCompradorFK;
   private String idCiudadFK;
   
   private String nombreFormaPago;

   private personaNaturalDTO perDTO;
   
   private String tipoVenta;
   
  private ArrayList<Producto> listaProductos;
    
   private double descuento;
   private String referencia;
   private String visto;
   
    public VentaDTO() {
    }
    
    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    

    public Double getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(Double valorenta) {
        this.valorVenta = valorenta;
    }

    public String getIdEstadoVentaFK() {
        return idEstadoVentaFK;
    }

    public void setIdEstadoVentaFK(String idEstadoVentaFK) {
        this.idEstadoVentaFK = idEstadoVentaFK;
    }

    public String getIdCompradorFK() {
        return idCompradorFK;
    }

    public void setIdCompradorFK(String idCompradorFK) {
        this.idCompradorFK = idCompradorFK;
    }

    public String getIdCiudadFK() {
        return idCiudadFK;
    }

    public void setIdCiudadFK(String idCiudadFK) {
        this.idCiudadFK = idCiudadFK;
    }

    public String getContactoVenta() {
        return contactoVenta;
    }

    public void setContactoVenta(String contactoVenta) {
        this.contactoVenta = contactoVenta;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public personaNaturalDTO getPerDTO() {
        return perDTO;
    }

    public void setPerDTO(personaNaturalDTO perDTO) {
        this.perDTO = perDTO;
    }

    public String getNombreFormaPago() {
        return nombreFormaPago;
    }

    public void setNombreFormaPago(String nombreFormaPago) {
        this.nombreFormaPago = nombreFormaPago;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    
    public Timestamp getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getVisto() {
        return visto;
    }

    public void setVisto(String visto) {
        this.visto = visto;
    }

    @Override
    public String toString() {
        return "VentaDTO{" + "idVenta=" + idVenta + ", fechaVenta=" + fechaVenta + ", valorVenta=" + valorVenta + ", contactoVenta=" + contactoVenta + ", formaPago=" + formaPago + ", idEstadoVentaFK=" + idEstadoVentaFK + ", idCompradorFK=" + idCompradorFK + ", idCiudadFK=" + idCiudadFK + ", nombreFormaPago=" + nombreFormaPago + ", perDTO=" + perDTO + ", tipoVenta=" + tipoVenta + ", listaProductos=" + listaProductos + ", descuento=" + descuento + ", referencia=" + referencia + ", visto=" + visto + '}';
    }

    
    
}
