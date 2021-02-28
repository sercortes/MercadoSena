/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.sena.mercado.dto;

import java.sql.Date;

/**
 *
 * @author serfin
 */
public class VentaDTO {
    
   private String idVenta;
   private Date fechaVenta;
   private Double valorVenta;
   private String contactoVenta;
   private String formaPago;
   
   private String idEstadoVentaFK;
   private String idCompradorFK;
   private String idCiudadFK;

    public VentaDTO() {
    }
    
    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
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

    @Override
    public String toString() {
        return "VentaDTO{" + "idVenta=" + idVenta + ", fechaVenta=" + fechaVenta + ", valorVenta=" + valorVenta + ", contactoVenta=" + contactoVenta + ", formaPago=" + formaPago + ", idEstadoVentaFK=" + idEstadoVentaFK + ", idCompradorFK=" + idCompradorFK + ", idCiudadFK=" + idCiudadFK + '}';
    }

}
